package com.alenniboris.fastbanking.presentation.screens.registration.registration_as_app_client.state

import com.alenniboris.fastbanking.presentation.screens.registration.registration_as_app_client.state.values.DataInputPartState
import com.alenniboris.fastbanking.presentation.screens.registration.registration_as_app_client.state.values.PhoneCodeCheckPartState
import com.alenniboris.fastbanking.presentation.screens.registration.registration_as_app_client.state.values.PhoneNumberInputPartState
import com.alenniboris.fastbanking.presentation.screens.registration.registration_as_app_client.state.values.RegistrationAsAppClientProcessPart
import com.alenniboris.fastbanking.presentation.screens.registration.registration_as_app_client.state.values.SettingPasswordPartState

data class RegistrationAsAppClientScreenState(
    val currentProcessPart: RegistrationAsAppClientProcessPart =
        RegistrationAsAppClientProcessPart.DataInput,
    val processPartList: List<RegistrationAsAppClientProcessPart> =
        RegistrationAsAppClientProcessPart.entries.toList(),
    val isOptionsBottomSheetVisible: Boolean = false,
    val dataInputPartState: DataInputPartState = DataInputPartState(),
    val phoneNumberInputPartState: PhoneNumberInputPartState = PhoneNumberInputPartState(),
    val phoneCodeCheckPartState: PhoneCodeCheckPartState = PhoneCodeCheckPartState(),
    val settingPasswordPartState: SettingPasswordPartState = SettingPasswordPartState()
)