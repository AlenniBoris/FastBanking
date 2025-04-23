package com.alenniboris.fastbanking.di

import com.alenniboris.fastbanking.domain.usecase.logic.accounts.IGetAllUserAccountsCurrencyAmountUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.accounts.IGetAllUserAccountsUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.bank_info.IGetApplicationInfoUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.bank_info.IGetBankNewsByIdUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.bank_info.IGetBankNewsUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.bank_info.IGetBankRecommendedNewsUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.cards.IGetAllUserCardsUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.credits.IGetAllUserCreditsUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.currency.IGetAllCurrenciesInfoUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.currency.IGetAllExchangeRatesForCurrencyUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.currency.IGetCurrenciesExchangeRateUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.help.ICallPhoneNumberUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.help.IOpenMessengerUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.map.IGetBankLocationsUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.transactions.IGetAllUserTransactionsByCardUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.user.IChangePasswordUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.user.ICheckVerificationCodeUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.user.IGetCurrentUserUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.user.IGetUserByIdUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.user.ILoginUserIntoBankingUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.user.IRegisterUserIntoBankingUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.user.ISendVerificationCodeUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.user.ISignOutUseCase
import com.alenniboris.fastbanking.presentation.screens.account_settings.AccountSettingsScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.activity.MainActivityViewModel
import com.alenniboris.fastbanking.presentation.screens.additions.AdditionsScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.application_information.ApplicationInformationScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.bank_news.BankNewsScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.currency.CurrencyScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.help.HelpScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.language_settings.LanguageSettingsScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.login.LoginScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.main.MainScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.map.AtmMapScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.news_details.NewsDetailsScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.password_reset.PasswordResetScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.personal.PersonalScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.personal_details.PersonalDetailsScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.registration.registration_as_app_client.RegistrationAsAppClientScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.registration.registration_options.RegistrationOptionsScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.theme_settings.ThemeSettingsScreenViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ViewModelsModule = module {

    viewModel<MainActivityViewModel> {
        MainActivityViewModel(
            getCurrentUserUseCase = get<IGetCurrentUserUseCase>(),
            signOutUseCase = get<ISignOutUseCase>()
        )
    }

    viewModel<LoginScreenViewModel> {
        LoginScreenViewModel(
            loginUserIntoBankingUseCase = get<ILoginUserIntoBankingUseCase>()
        )
    }

    viewModel<RegistrationOptionsScreenViewModel> {
        RegistrationOptionsScreenViewModel()
    }

    viewModel<RegistrationAsAppClientScreenViewModel> {
        RegistrationAsAppClientScreenViewModel(
            sendVerificationCodeForRegistrationToNumberUseCase = get<ISendVerificationCodeUseCase>(),
            checkVerificationCodeForRegistrationUseCase = get<ICheckVerificationCodeUseCase>(),
            registerUserIntoBankingUseCase = get<IRegisterUserIntoBankingUseCase>()
        )
    }

    viewModel<AtmMapScreenViewModel> {
        AtmMapScreenViewModel(
            apl = androidApplication(),
            getBankLocationsUseCase = get<IGetBankLocationsUseCase>()
        )
    }

    viewModel<CurrencyScreenViewModel> {
        CurrencyScreenViewModel(
            getCurrenciesExchangeRateUseCase = get<IGetCurrenciesExchangeRateUseCase>(),
            getAllCurrenciesInfoUseCase = get<IGetAllCurrenciesInfoUseCase>(),
            getAllExchangeRatesForCurrencyUseCase = get<IGetAllExchangeRatesForCurrencyUseCase>()
        )
    }

    viewModel<HelpScreenViewModel> {
        HelpScreenViewModel(
            apl = androidApplication(),
            callPhoneNumberUseCase = get<ICallPhoneNumberUseCase>(),
            openMessengerUseCase = get<IOpenMessengerUseCase>()
        )
    }

    viewModel<AdditionsScreenViewModel> {
        AdditionsScreenViewModel()
    }

    viewModel<ApplicationInformationScreenViewModel> {
        ApplicationInformationScreenViewModel(
            getApplicationInfoUseCase = get<IGetApplicationInfoUseCase>()
        )
    }

    viewModel<BankNewsScreenViewModel> {
        BankNewsScreenViewModel(
            getBankNewsUseCase = get<IGetBankNewsUseCase>()
        )
    }

    viewModel<NewsDetailsScreenViewModel> { (newsId: String) ->
        NewsDetailsScreenViewModel(
            newsId = newsId,
            getNewsByIdUseCase = get<IGetBankNewsByIdUseCase>()
        )
    }

    viewModel<PasswordResetScreenViewModel> {
        PasswordResetScreenViewModel(
            getUserByIdUseCase = get<IGetUserByIdUseCase>(),
            checkVerificationCode = get<ICheckVerificationCodeUseCase>(),
            sendVerificationCode = get<ISendVerificationCodeUseCase>(),
            changePasswordUseCase = get<IChangePasswordUseCase>()
        )
    }

    viewModel<MainScreenViewModel> {
        MainScreenViewModel(
            getBankRecommendedNewsUseCase = get<IGetBankRecommendedNewsUseCase>(),
            getAllUserCardsUseCase = get<IGetAllUserCardsUseCase>(),
            getAllUserCreditsUseCase = get<IGetAllUserCreditsUseCase>(),
            getAllUserAccountsUseCase = get<IGetAllUserAccountsUseCase>(),
            getAllUserTransactionsByCardUseCase = get<IGetAllUserTransactionsByCardUseCase>(),
            getCurrenciesExchangeRateUseCase = get<IGetCurrenciesExchangeRateUseCase>(),
            getAllUserAccountsCurrencyAmountUseCase = get<IGetAllUserAccountsCurrencyAmountUseCase>()
        )
    }

    viewModel<PersonalScreenViewModel>() {
        PersonalScreenViewModel(
            getCurrentUserUseCase = get<IGetCurrentUserUseCase>(),
            signOutUseCase = get<ISignOutUseCase>()
        )
    }

    viewModel<PersonalDetailsScreenViewModel>() {
        PersonalDetailsScreenViewModel(
            getCurrentUserUseCase = get<IGetCurrentUserUseCase>()
        )
    }

    viewModel<ThemeSettingsScreenViewModel>() {
        ThemeSettingsScreenViewModel()
    }

    viewModel<LanguageSettingsScreenViewModel>() {
        LanguageSettingsScreenViewModel()
    }

    viewModel<AccountSettingsScreenViewModel>() {
        AccountSettingsScreenViewModel(
            getCurrentUserUseCase = get<IGetCurrentUserUseCase>()
        )
    }
}