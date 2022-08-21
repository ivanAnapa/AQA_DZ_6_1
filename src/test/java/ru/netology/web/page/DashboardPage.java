package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private ElementsCollection cards = $$(".list__item div");
    private ElementsCollection depositBtns = $$("[data-test-id='action-deposit']");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public int getFirstCardBalance() {
        val text = cards.first().text();
        return extractBalance(text);
    }

    public int getSecondCardBalance() {
        val text = cards.last().text();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public TransferPage clickFirstDepositByCardOrder() {
        depositBtns.first().click();
        return new TransferPage();
    }

    public TransferPage clickSecondDepositByCardOrder() {
        depositBtns.last().click();
        return new TransferPage();
    }

    public int calcTransferSum(int initTransSum, int cardBalance) {
        int transferSum = 0;
        if (cardBalance == 0) {
            throw new RuntimeException("Баланс карты = 0 и не возволяет провести тест");
        } else if (initTransSum > cardBalance || initTransSum == cardBalance) {
            transferSum = cardBalance - 1;
        } else {
            transferSum = initTransSum;
        }
        return transferSum;
    }

}