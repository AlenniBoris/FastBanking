package com.alenniboris.fastbanking.di

import com.alenniboris.fastbanking.domain.model.appliances.CardDetailedApplianceType
import com.alenniboris.fastbanking.domain.model.appliances.CreditDetailedApplianceType
import com.alenniboris.fastbanking.domain.model.appliances.DepositDetailedApplianceType
import com.alenniboris.fastbanking.domain.usecase.logic.accounts.IGetAllUserAccountsCurrencyAmountUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.accounts.IGetAllUserAccountsUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.appliance.IGetAllUserAppliancesUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.appliance.IGetCardApplianceByIdUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.appliance.IGetCreditApplianceByIdUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.appliance.IGetDepositApplianceByIdUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.appliance.ISendApplianceForCardUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.appliance.ISendApplianceForCreditUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.appliance.ISendApplianceForDepositUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.authorization.IChangePasswordUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.authorization.ICheckVerificationCodeUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.authorization.IGetCurrentUserUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.authorization.IGetUserByIdUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.authorization.ILoginUserIntoBankingUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.authorization.IRegisterUserIntoBankingUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.authorization.ISendVerificationCodeUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.authorization.ISignOutUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.bank_info.IGetApplicationInfoUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.bank_info.IGetBankNewsByIdUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.bank_info.IGetBankNewsUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.bank_info.IGetBankRecommendedNewsUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.cards.IGetAllUserCardsUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.cards.IGetCardByIdUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.credits.IGetAllUserCreditsUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.currency.IGetAllCurrenciesInfoUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.currency.IGetAllExchangeRatesForCurrencyUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.currency.IGetCurrenciesExchangeRateUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.help.ICallPhoneNumberUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.help.IOpenMessengerUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.map.IGetBankLocationsUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.transactions.IGetAllUserTransactionsByCardUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.transactions.IGetAllUserTransactionsUseCase
import com.alenniboris.fastbanking.presentation.model.appliance.ProductApplianceType
import com.alenniboris.fastbanking.presentation.screens.account_settings.AccountSettingsScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.activity.MainActivityViewModel
import com.alenniboris.fastbanking.presentation.screens.additions.AdditionsScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.application_information.ApplicationInformationScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.bank_news.BankNewsScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.card_details.CardDetailsScreenViewModel
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
import com.alenniboris.fastbanking.presentation.screens.product_appliance_choosing.ProductApplianceChoosingScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.product_appliance_details.ProductApplianceDetailsScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.product_appliances_forms.card_form_screen.CardApplianceFormScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.product_appliances_forms.credit_form_screen.CreditApplianceFormScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.product_appliances_forms.deposit_form_screen.DepositApplianceFormScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.product_history.ProductHistoryScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.product_information.ProductInformationScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.registration.registration_as_app_client.RegistrationAsAppClientScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.registration.registration_options.RegistrationOptionsScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.theme_settings.ThemeSettingsScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.transactions_history.TransactionsHistoryScreenViewModel
import com.alenniboris.fastbanking.presentation.screens.user_appliances.UserAppliancesScreenViewModel
import com.alenniboris.fastbanking.presentation.uikit.values.BankProduct
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

    viewModel<UserAppliancesScreenViewModel>() {
        UserAppliancesScreenViewModel(
            getAllUserAppliancesUseCase = get<IGetAllUserAppliancesUseCase>()
        )
    }

    viewModel<ProductApplianceChoosingScreenViewModel>() { (product: BankProduct) ->
        ProductApplianceChoosingScreenViewModel(
            currentProduct = product
        )
    }

    viewModel<CardApplianceFormScreenViewModel>() { (detailedApplianceType: CardDetailedApplianceType) ->
        CardApplianceFormScreenViewModel(
            detailedApplianceType = detailedApplianceType,
            sendApplianceForCardUseCase = get<ISendApplianceForCardUseCase>(),
            getAllCurrenciesUseCase = get<IGetAllCurrenciesInfoUseCase>(),
            getBankLocationsUseCase = get<IGetBankLocationsUseCase>(),
            getCurrentUser = get<IGetCurrentUserUseCase>()
        )
    }

    viewModel<CreditApplianceFormScreenViewModel>() { (detailedApplianceType: CreditDetailedApplianceType) ->
        CreditApplianceFormScreenViewModel(
            detailedApplianceType = detailedApplianceType,
            getAllCurrenciesUseCase = get<IGetAllCurrenciesInfoUseCase>(),
            getBankLocationsUseCase = get<IGetBankLocationsUseCase>(),
            getCurrentUserUseCase = get<IGetCurrentUserUseCase>(),
            sendApplianceForCreditUseCase = get<ISendApplianceForCreditUseCase>()
        )
    }

    viewModel<DepositApplianceFormScreenViewModel>() { (detailedApplianceType: DepositDetailedApplianceType) ->
        DepositApplianceFormScreenViewModel(
            detailedApplianceType = detailedApplianceType,
            getAllCurrenciesUseCase = get<IGetAllCurrenciesInfoUseCase>(),
            getBankLocationsUseCase = get<IGetBankLocationsUseCase>(),
            getCurrentUserUseCase = get<IGetCurrentUserUseCase>(),
            sendApplianceForDepositUseCase = get<ISendApplianceForDepositUseCase>()
        )
    }

    viewModel<ProductApplianceDetailsScreenViewModel>() { (applianceId: String, productApplianceType: ProductApplianceType) ->
        ProductApplianceDetailsScreenViewModel(
            applianceId = applianceId,
            applianceType = productApplianceType,
            getCardApplianceByIdUseCase = get<IGetCardApplianceByIdUseCase>(),
            getCreditApplianceByIdUseCase = get<IGetCreditApplianceByIdUseCase>(),
            getDepositApplianceByIdUseCase = get<IGetDepositApplianceByIdUseCase>()
        )
    }

    viewModel<TransactionsHistoryScreenViewModel>() {
        TransactionsHistoryScreenViewModel(
            getAllUserTransactionsUseCase = get<IGetAllUserTransactionsUseCase>()
        )
    }

    viewModel<ProductHistoryScreenViewModel>() { (productType: BankProduct, product: String) ->
        ProductHistoryScreenViewModel(
            productType = productType,
            product = product,
            getAllUserTransactionsByCardUseCase = get<IGetAllUserTransactionsByCardUseCase>()
        )
    }

    viewModel<ProductInformationScreenViewModel>() {
        ProductInformationScreenViewModel()
    }

    viewModel<CardDetailsScreenViewModel>() { (cardId: String) ->
        CardDetailsScreenViewModel(
            cardId = cardId,
            getCardByIdUseCase = get<IGetCardByIdUseCase>()
        )
    }
}