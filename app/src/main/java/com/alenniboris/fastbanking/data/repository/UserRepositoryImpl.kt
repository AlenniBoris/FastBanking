package com.alenniboris.fastbanking.data.repository

import android.app.Application
import android.content.Intent
import android.util.Log
import com.alenniboris.fastbanking.data.mappers.toAuthenticationException
import com.alenniboris.fastbanking.data.model.UserModelData
import com.alenniboris.fastbanking.data.model.toModelData
import com.alenniboris.fastbanking.data.model.toModelDomain
import com.alenniboris.fastbanking.data.receiver.MessageReceiver.Companion.SEND_MESSAGE_WITH_VERIFICATION_CODE_ACTION
import com.alenniboris.fastbanking.data.receiver.MessageReceiver.Companion.VERIFICATION_CODE
import com.alenniboris.fastbanking.data.utils.CodeGenerator
import com.alenniboris.fastbanking.domain.model.CustomResultModelDomain
import com.alenniboris.fastbanking.domain.model.exception.AuthenticationExceptionModelDomain
import com.alenniboris.fastbanking.domain.model.user.UserModelDomain
import com.alenniboris.fastbanking.domain.repository.IUserRepository
import com.alenniboris.fastbanking.domain.utils.GsonUtil.fromJson
import com.alenniboris.fastbanking.domain.utils.GsonUtil.toJson
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    private val apl: Application,
    private val database: FirebaseDatabase,
    private val dispatchers: IAppDispatchers
) : IUserRepository {
    private val userFlow = MutableStateFlow<UserModelDomain?>(null)
    override val user: StateFlow<UserModelDomain?> = userFlow.asStateFlow()

    private var verificationCode: String = ""

    /**
     * Normal verification code verification is needed to be implemented
     *  **/
    override fun checkVerificationCodeForRegistration(
        code: String
    ): CustomResultModelDomain<Unit, AuthenticationExceptionModelDomain> {
        if (code == verificationCode) {
            return CustomResultModelDomain.Success(Unit)
        }
        return CustomResultModelDomain.Error(
            AuthenticationExceptionModelDomain.VerificationWithCodeFailed
        )
    }

    /**
     * Normal verification code sending is needed to be implemented
     *  **/
    override fun sendVerificationCode() {
        val generatedVerificationCode = CodeGenerator.generate()
        verificationCode = generatedVerificationCode

        val intent = Intent()
        intent.action = SEND_MESSAGE_WITH_VERIFICATION_CODE_ACTION
        intent.putExtra(VERIFICATION_CODE, generatedVerificationCode)
        intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND)
        intent.setPackage(apl.packageName)

        apl.sendBroadcast(intent)
    }

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
                    ?: return@withContext CustomResultModelDomain.Error(
                        AuthenticationExceptionModelDomain.NoSuchUserException
                    )

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
            }.getOrElse { exception ->
                Log.e("!!!", exception.stackTraceToString())
                return@withContext CustomResultModelDomain.Error<Unit, AuthenticationExceptionModelDomain>(
                    exception.toAuthenticationException()
                )
            }
        }

    override suspend fun registerUserIntoBanking(
        login: String,
        password: String
    ): CustomResultModelDomain<Unit, AuthenticationExceptionModelDomain> =
        withContext(dispatchers.IO) {
            runCatching {

                val databaseReference = database.reference
                val snapshot = databaseReference
                    .child(FirebaseDatabaseValues.TABLE_USERS)
                    .child(login)
                    .get()
                    .await()

                val user = snapshot.value.toJson().fromJson<UserModelData>().toModelDomain()

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