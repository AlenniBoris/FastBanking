package com.alenniboris.fastbanking.di

import com.alenniboris.fastbanking.domain.repository.IAuthenticationRepository
import com.alenniboris.fastbanking.domain.repository.IBankInfoRepository
import com.alenniboris.fastbanking.domain.repository.IBankProductsRepository
import com.alenniboris.fastbanking.domain.repository.ICurrencyRepository
import com.alenniboris.fastbanking.domain.repository.IHelpRepository
import com.alenniboris.fastbanking.domain.repository.IMapsRepository
import com.alenniboris.fastbanking.domain.usecase.implementation.accounts.ChangeAccountNameUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.accounts.GetAccountByIdUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.accounts.GetAllUserAccountsCurrencyAmountUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.accounts.GetAllUserAccountsUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.appliance.GetAllUserAppliancesUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.appliance.GetCardApplianceByIdUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.appliance.GetCreditApplianceByIdUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.appliance.GetDepositApplianceByIdUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.appliance.SendApplianceForCardUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.appliance.SendApplianceForCreditUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.appliance.SendApplianceForDepositUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.authorization.ChangePasswordUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.authorization.CheckVerificationCodeUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.authorization.GetCurrentUserUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.authorization.GetUserByIdUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.authorization.LoginUserIntoBankingUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.authorization.RegisterUserIntoBankingUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.authorization.SendVerificationCodeUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.authorization.SignOutUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.bank_info.GetApplicationInfoUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.bank_info.GetBankNewsByIdUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.bank_info.GetBankNewsUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.bank_info.GetBankRecommendedNewsUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.bank_info.SendUserPraiseUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.cards.ChangeCardNameUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.cards.GetAllUserCardsUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.cards.GetCardByIdUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.cards.GetFullModelsForAllSimpleCardsUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.credits.ChangeCreditNameUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.credits.GetAllUserCreditsUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.credits.GetCreditByIdUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.currency.GetAllCurrenciesInfoUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.currency.GetAllExchangeRatesForCurrencyUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.currency.GetCurrenciesExchangeRateUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.help.CallPhoneNumberUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.help.OpenMessengerUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.map.GetBankLocationsUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.transactions.GetAllTransactionsForAccountByIdUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.transactions.GetAllTransactionsForCreditByIdUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.transactions.GetAllUserTransactionsByCardUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.transactions.GetAllUserTransactionsUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.transactions.MakeTransactionByCardNumberUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.transactions.MakeTransactionByEripNumberUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.implementation.transactions.MakeTransactionForCreditByContractNumberUseCaseImpl
import com.alenniboris.fastbanking.domain.usecase.logic.accounts.IChangeAccountNameUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.accounts.IGetAccountByIdUseCase
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
import com.alenniboris.fastbanking.domain.usecase.logic.bank_info.ISendUserPraiseUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.cards.IChangeCardNameUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.cards.IGetAllUserCardsUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.cards.IGetCardByIdUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.cards.IGetFullModelsForAllSimpleCardsUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.credits.IChangeCreditNameUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.credits.IGetAllUserCreditsUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.credits.IGetCreditByIdUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.currency.IGetAllCurrenciesInfoUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.currency.IGetAllExchangeRatesForCurrencyUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.currency.IGetCurrenciesExchangeRateUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.help.ICallPhoneNumberUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.help.IOpenMessengerUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.map.IGetBankLocationsUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.transactions.IGetAllTransactionsForAccountByIdUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.transactions.IGetAllTransactionsForCreditByIdUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.transactions.IGetAllUserTransactionsByCardUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.transactions.IGetAllUserTransactionsUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.transactions.IMakeTransactionByCardNumberUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.transactions.IMakeTransactionByEripNumberUseCase
import com.alenniboris.fastbanking.domain.usecase.logic.transactions.IMakeTransactionForCreditByContractNumberUseCase
import com.alenniboris.fastbanking.domain.utils.IAppDispatchers
import org.koin.dsl.module

val UseCaseModule = module {

    factory<ISendUserPraiseUseCase> {
        SendUserPraiseUseCaseImpl(
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IChangeCreditNameUseCase> {
        ChangeCreditNameUseCaseImpl(
            bankProductsRepository = get<IBankProductsRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IChangeAccountNameUseCase> {
        ChangeAccountNameUseCaseImpl(
            bankProductsRepository = get<IBankProductsRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IChangeCardNameUseCase> {
        ChangeCardNameUseCaseImpl(
            bankProductsRepository = get<IBankProductsRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    single<IGetCurrentUserUseCase> {
        GetCurrentUserUseCaseImpl(
            authRepository = get<IAuthenticationRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<ILoginUserIntoBankingUseCase> {
        LoginUserIntoBankingUseCaseImpl(
            authRepository = get<IAuthenticationRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IRegisterUserIntoBankingUseCase> {
        RegisterUserIntoBankingUseCaseImpl(
            authRepository = get<IAuthenticationRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<ISignOutUseCase> {
        SignOutUseCaseImpl(
            authRepository = get<IAuthenticationRepository>()
        )
    }


    factory<ISendVerificationCodeUseCase> {
        SendVerificationCodeUseCaseImpl(
            authRepository = get<IAuthenticationRepository>()
        )
    }

    factory<ICheckVerificationCodeUseCase> {
        CheckVerificationCodeUseCaseImpl(
            authRepository = get<IAuthenticationRepository>()
        )
    }

    factory<IGetBankLocationsUseCase> {
        GetBankLocationsUseCaseImpl(
            mapsRepository = get<IMapsRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IGetAllCurrenciesInfoUseCase> {
        GetAllCurrenciesInfoUseCaseImpl(
            currencyRepository = get<ICurrencyRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IGetCurrenciesExchangeRateUseCase> {
        GetCurrenciesExchangeRateUseCaseImpl(
            currencyRepository = get<ICurrencyRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IGetAllExchangeRatesForCurrencyUseCase> {
        GetAllExchangeRatesForCurrencyUseCaseImpl(
            currencyRepository = get<ICurrencyRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<ICallPhoneNumberUseCase> {
        CallPhoneNumberUseCaseImpl(
            helpRepository = get<IHelpRepository>()
        )
    }

    factory<IOpenMessengerUseCase> {
        OpenMessengerUseCaseImpl(
            helpRepository = get<IHelpRepository>()
        )
    }

    factory<IGetApplicationInfoUseCase> {
        GetApplicationInfoUseCaseImpl(
            bankInfoRepository = get<IBankInfoRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IGetBankNewsUseCase> {
        GetBankNewsUseCaseImpl(
            infoRepository = get<IBankInfoRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IGetBankNewsByIdUseCase> {
        GetBankNewsByIdUseCaseImpl(
            infoRepository = get<IBankInfoRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IGetUserByIdUseCase> {
        GetUserByIdUseCaseImpl(
            authRepository = get<IAuthenticationRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IChangePasswordUseCase> {
        ChangePasswordUseCaseImpl(
            authRepository = get<IAuthenticationRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IGetAllUserCardsUseCase> {
        GetAllUserCardsUseCaseImpl(
            bankRepository = get<IBankProductsRepository>(),
            getCurrentUserUseCase = get<IGetCurrentUserUseCase>(),
            getCurrenciesExchangeRateUseCase = get<IGetCurrenciesExchangeRateUseCase>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IGetAllUserTransactionsByCardUseCase> {
        GetAllUserTransactionsByCardUseCaseImpl(
            bankRepository = get<IBankProductsRepository>(),
            getCurrentUserUseCase = get<IGetCurrentUserUseCase>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IGetBankRecommendedNewsUseCase> {
        GetBankRecommendedNewsUseCaseImpl(
            bankInfoRepository = get<IBankInfoRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IGetCardByIdUseCase> {
        GetCardByIdUseCaseImpl(
            bankRepository = get<IBankProductsRepository>(),
            getCurrenciesExchangeRateUseCase = get<IGetCurrenciesExchangeRateUseCase>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IGetAllUserAccountsUseCase> {
        GetAllUserAccountsUseCaseImpl(
            bankRepository = get<IBankProductsRepository>(),
            getCurrentUserUseCase = get<IGetCurrentUserUseCase>(),
            getCurrenciesExchangeRateUseCase = get<IGetCurrenciesExchangeRateUseCase>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IGetAccountByIdUseCase> {
        GetAccountByIdUseCaseImpl(
            bankRepository = get<IBankProductsRepository>(),
            getCurrenciesExchangeRateUseCase = get<IGetCurrenciesExchangeRateUseCase>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IGetAllUserCreditsUseCase> {
        GetAllUserCreditsUseCaseImpl(
            bankRepository = get<IBankProductsRepository>(),
            getCurrentUserUseCase = get<IGetCurrentUserUseCase>(),
            getCurrenciesExchangeRateUseCase = get<IGetCurrenciesExchangeRateUseCase>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IGetCreditByIdUseCase> {
        GetCreditByIdUseCaseImpl(
            bankRepository = get<IBankProductsRepository>(),
            getCurrenciesExchangeRateUseCase = get<IGetCurrenciesExchangeRateUseCase>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IGetAllUserAccountsCurrencyAmountUseCase> {
        GetAllUserAccountsCurrencyAmountUseCaseImpl(
            getAllUserAccountsUseCase = get<IGetAllUserAccountsUseCase>(),
            getCurrenciesExchangeRateUseCase = get<IGetCurrenciesExchangeRateUseCase>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IGetAllUserAppliancesUseCase> {
        GetAllUserAppliancesUseCaseImpl(
            bankRepository = get<IBankProductsRepository>(),
            getCurrentUserUseCase = get<IGetCurrentUserUseCase>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IGetCardApplianceByIdUseCase> {
        GetCardApplianceByIdUseCaseImpl(
            bankRepository = get<IBankProductsRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IGetCreditApplianceByIdUseCase> {
        GetCreditApplianceByIdUseCaseImpl(
            bankRepository = get<IBankProductsRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IGetDepositApplianceByIdUseCase> {
        GetDepositApplianceByIdUseCaseImpl(
            bankRepository = get<IBankProductsRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<ISendApplianceForCardUseCase> {
        SendApplianceForCardUseCaseImpl(
            bankRepository = get<IBankProductsRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<ISendApplianceForCreditUseCase> {
        SendApplianceForCreditUseCaseImpl(
            bankRepository = get<IBankProductsRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<ISendApplianceForDepositUseCase> {
        SendApplianceForDepositUseCaseImpl(
            bankRepository = get<IBankProductsRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IGetAllUserTransactionsUseCase> {
        GetAllUserTransactionsUseCaseImpl(
            bankProductsRepository = get<IBankProductsRepository>(),
            getCurrentUserUseCase = get<IGetCurrentUserUseCase>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IGetAllTransactionsForCreditByIdUseCase> {
        GetAllTransactionsForCreditByIdUseCaseImpl(
            bankProductsRepository = get<IBankProductsRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IGetAllTransactionsForAccountByIdUseCase> {
        GetAllTransactionsForAccountByIdUseCaseImpl(
            bankProductsRepository = get<IBankProductsRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IGetFullModelsForAllSimpleCardsUseCase> {
        GetFullModelsForAllSimpleCardsUseCaseImpl(
            getCardByIdUseCase = get<IGetCardByIdUseCase>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IMakeTransactionByCardNumberUseCase> {
        MakeTransactionByCardNumberUseCaseImpl(
            bankProductsRepository = get<IBankProductsRepository>(),
            currencyRepository = get<ICurrencyRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IMakeTransactionByEripNumberUseCase> {
        MakeTransactionByEripNumberUseCaseImpl(
            bankProductsRepository = get<IBankProductsRepository>(),
            currencyRepository = get<ICurrencyRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IMakeTransactionForCreditByContractNumberUseCase> {
        MakeTransactionForCreditByContractNumberUseCaseImpl(
            bankProductsRepository = get<IBankProductsRepository>(),
            currencyRepository = get<ICurrencyRepository>(),
            dispatchers = get<IAppDispatchers>()
        )
    }
}