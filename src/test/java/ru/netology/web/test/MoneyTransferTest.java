package ru.netology.web.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.TransferPage;

import static com.codeborne.selenide.Selenide.open;

class MoneyTransferTest {
    private final int transferSum = 1000;

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
        int firstCardBalanceAfter = dashboardPage.getCardBalance(1);
        int secondCardBalanceAfter = dashboardPage.getCardBalance(2);
        dashboardPage.clickDepositByCardOrder(1);

        TransferPage transferPage = new TransferPage();
        transferPage.checkOpenedPage();
        transferPage.setTransferSum(DataHelper.getTransferSum());
        transferPage.setCardFrom(DataHelper.getCardNumbers().getSecondCardNumber());
        transferPage.clickTransferBtn();

        int firstCardBalanceBefore = dashboardPage.getCardBalance(1);
        int secondCardBalanceBefore = dashboardPage.getCardBalance(2);
        Assertions.assertEquals(firstCardBalanceAfter + transferSum, firstCardBalanceBefore);
        Assertions.assertEquals(secondCardBalanceAfter - transferSum, secondCardBalanceBefore);
        System.out.println("1 test - OK");
    }

    @Test
    void shouldTransferMoneyBetweenOwnCardsToSecondCard() {
        DashboardPage dashboardPage = new DashboardPage();
        int firstCardBalanceAfter = dashboardPage.getCardBalance(1);
        int secondCardBalanceAfter = dashboardPage.getCardBalance(2);
        dashboardPage.clickDepositByCardOrder(2);

        TransferPage transferPage = new TransferPage();
        transferPage.checkOpenedPage();
        transferPage.setTransferSum(transferSum);
        transferPage.setCardFrom(DataHelper.getCardNumbers().getFirstCardNumber());
        transferPage.clickTransferBtn();

        int firstCardBalanceBefore = dashboardPage.getCardBalance(1);
        int secondCardBalanceBefore = dashboardPage.getCardBalance(2);
        Assertions.assertEquals(firstCardBalanceAfter - transferSum, firstCardBalanceBefore);
        Assertions.assertEquals(secondCardBalanceAfter + transferSum, secondCardBalanceBefore);
        System.out.println("2 test - OK");

    }

    @Test
    void cancelMoneyTransfer() {
        DashboardPage dashboardPage = new DashboardPage();
        int firstCardBalanceAfter = dashboardPage.getCardBalance(1);
        int secondCardBalanceAfter = dashboardPage.getCardBalance(2);
        dashboardPage.clickDepositByCardOrder(1);

        TransferPage transferPage = new TransferPage();
        transferPage.checkOpenedPage();
        transferPage.setTransferSum(transferSum);
        transferPage.setCardFrom(DataHelper.getCardNumbers().getSecondCardNumber());
        transferPage.clickCancelBtn();

        int firstCardBalanceBefore = dashboardPage.getCardBalance(1);
        int secondCardBalanceBefore = dashboardPage.getCardBalance(2);
        Assertions.assertEquals(firstCardBalanceAfter, firstCardBalanceBefore);
        Assertions.assertEquals(secondCardBalanceAfter, secondCardBalanceBefore);
        System.out.println("3 test - OK");

    }

}