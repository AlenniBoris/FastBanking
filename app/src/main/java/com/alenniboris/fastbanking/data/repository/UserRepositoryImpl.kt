package com.alenniboris.fastbanking.data.repository

import android.util.Log
import com.alenniboris.fastbanking.data.mappers.toAuthenticationException
import com.alenniboris.fastbanking.data.model.UserModelData
import com.alenniboris.fastbanking.data.model.toModelData
import com.alenniboris.fastbanking.data.model.toModelDomain
import com.alenniboris.fastbanking.domain.model.AuthenticationExceptionModelDomain
import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.UserModelDomain
import com.alenniboris.fastbanking.domain.repository.IUserRepository
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    private val database: FirebaseDatabase,
    private val dispatchers: IAppDispatchers
) : IUserRepository {

    private val userFlow = MutableStateFlow<UserModelDomain?>(null)
    override val user: StateFlow<UserModelDomain?> = userFlow.asStateFlow()

    override fun signOut() {
        userFlow.update { null }
    }

    override suspend fun loginUserIntoBanking(
        login: String,
        password: String
    ): CustomResultModelDomain<Unit, AuthenticationExceptionModelDomain> =
        withContext(dispatchers.IO) {
            runCatching {
                val snapshot =
                    database.reference
                        .child(FirebaseDatabaseValues.TABLE_USERS)
                        .child(login)
                        .get()
                        .await()
                val user = snapshot.getValue(UserModelData::class.java)

                user?.let {
                    if (user.hasSomeValueMissing) {
                        return@withContext CustomResultModelDomain.Error<Unit, AuthenticationExceptionModelDomain>(
                            AuthenticationExceptionModelDomain.ErrorGettingData
                        )
                    }

                    if (user.password != password) {
                        return@withContext CustomResultModelDomain.Error<Unit, AuthenticationExceptionModelDomain>(
                            AuthenticationExceptionModelDomain.WrongEnteringFieldException
                        )
                    }

                    userFlow.update { user.toModelDomain() }

                    return@withContext CustomResultModelDomain.Success<Unit, AuthenticationExceptionModelDomain>(
                        Unit
                    )
                }
                    ?: return@withContext CustomResultModelDomain.Error<Unit, AuthenticationExceptionModelDomain>(
                        AuthenticationExceptionModelDomain.NoSuchUserException
                    )
            }.getOrElse { exception ->
                Log.e("!!!", exception.stackTraceToString())
                return@withContext CustomResultModelDomain.Error<Unit, AuthenticationExceptionModelDomain>(
                    exception.toAuthenticationException()
                )
            }
        }

    override suspend fun registerUserIntoBanking(
        login: String,
        password: String,
        passwordCheck: String
    ): CustomResultModelDomain<Unit, AuthenticationExceptionModelDomain> =
        withContext(dispatchers.IO) {
            runCatching {

                if (password != passwordCheck) {
                    return@withContext CustomResultModelDomain.Error(
                        AuthenticationExceptionModelDomain.PasswordsCheckException
                    )
                }

                val databaseReference = database.reference
                val snapshot = databaseReference
                    .child(FirebaseDatabaseValues.TABLE_USERS)
                    .child(login)
                    .get()
                    .await()

                val user = snapshot.getValue(UserModelData::class.java)?.toModelDomain()

                user?.let {
                    if (!user.hasOnlineBanking) {
                        return@withContext CustomResultModelDomain.Error(
                            AuthenticationExceptionModelDomain.OnlineBankingNotAllowed
                        )
                    }

                    if (user.password.isNotEmpty()) {
                        return@withContext CustomResultModelDomain.Error(
                            AuthenticationExceptionModelDomain.UserAlreadyExistsException
                        )
                    }

                    val updatedUser = user.copy(password = password).toModelData().toUpdatesMap()
                    val updates = hashMapOf<String, Any>(
                        "/users/${user.id}" to updatedUser
                    )
                    databaseReference.updateChildren(updates)

                    return@withContext CustomResultModelDomain.Success<Unit, AuthenticationExceptionModelDomain>(
                        Unit
                    )
                }
                    ?: return@withContext CustomResultModelDomain.Error<Unit, AuthenticationExceptionModelDomain>(
                        AuthenticationExceptionModelDomain.NoSuchUserException
                    )
            }.getOrElse { exception ->
                Log.e("!!!", exception.stackTraceToString())
                return@withContext CustomResultModelDomain.Error<Unit, AuthenticationExceptionModelDomain>(
                    exception.toAuthenticationException()
                )
            }
        }
}