package ru.netology.web.test;

import org.junit.jupiter.api.*;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.TransferPage;

import static com.codeborne.selenide.Selenide.open;

class MoneyTransferTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldTransferMoneyBetweenOwnCardsToFirstCard() {
        DashboardPage dashboardPage = new DashboardPage();
        int firstCardBalanceAfter = dashboardPage.getFirstCardBalance();
        int secondCardBalanceAfter = dashboardPage.getSecondCardBalance();
        int transferSum = DataHelper.calcTransferSum(1234, secondCardBalanceAfter);
        dashboardPage.clickFirstCardDeposit();

        TransferPage transferPage = new TransferPage();
        transferPage.checkOpenedPage();
        transferPage.setTransferSum(transferSum);
        transferPage.setCardFrom(DataHelper.getSecondCardNumber());
        transferPage.clickTransferBtn();

        int firstCardBalanceBefore = dashboardPage.getFirstCardBalance();
        int secondCardBalanceBefore = dashboardPage.getSecondCardBalance();
        Assertions.assertEquals(firstCardBalanceAfter + transferSum, firstCardBalanceBefore);
        Assertions.assertEquals(secondCardBalanceAfter - transferSum, secondCardBalanceBefore);
    }

    @Test
    void shouldTransferMoneyBetweenOwnCardsToSecondCard() {
        DashboardPage dashboardPage = new DashboardPage();
        int firstCardBalanceAfter = dashboardPage.getFirstCardBalance();
        int secondCardBalanceAfter = dashboardPage.getSecondCardBalance();
        int transferSum = DataHelper.calcTransferSum(1234, firstCardBalanceAfter);
        dashboardPage.clickSecondCardDeposit();

        TransferPage transferPage = new TransferPage();
        transferPage.checkOpenedPage();
        transferPage.setTransferSum(transferSum);
        transferPage.setCardFrom(DataHelper.getFirstCardNumber());
        transferPage.clickTransferBtn();

        int firstCardBalanceBefore = dashboardPage.getFirstCardBalance();
        int secondCardBalanceBefore = dashboardPage.getSecondCardBalance();
        Assertions.assertEquals(firstCardBalanceAfter - transferSum, firstCardBalanceBefore);
        Assertions.assertEquals(secondCardBalanceAfter + transferSum, secondCardBalanceBefore);
    }

    @Test
    void cancelMoneyTransfer() {
        DashboardPage dashboardPage = new DashboardPage();
        int firstCardBalanceAfter = dashboardPage.getFirstCardBalance();
        int secondCardBalanceAfter = dashboardPage.getSecondCardBalance();
        int transferSum = DataHelper.calcTransferSum(1234, firstCardBalanceAfter);
        dashboardPage.clickFirstCardDeposit();

        TransferPage transferPage = new TransferPage();
        transferPage.checkOpenedPage();
        transferPage.setTransferSum(transferSum);
        transferPage.setCardFrom(DataHelper.getSecondCardNumber());
        transferPage.clickCancelBtn();

        int firstCardBalanceBefore = dashboardPage.getFirstCardBalance();
        int secondCardBalanceBefore = dashboardPage.getSecondCardBalance();
        Assertions.assertEquals(firstCardBalanceAfter, firstCardBalanceBefore);
        Assertions.assertEquals(secondCardBalanceAfter, secondCardBalanceBefore);
    }

}