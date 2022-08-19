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

    public int getCardBalance(int cardNumber) {
        if (cardNumber == 1) {
            val text = cards.first().text();
            return extractBalance(text);
        } else if (cardNumber == 2) {
            val text = cards.last().text();
            return extractBalance(text);
        } else throw new RuntimeException("У нас всего 2 карты. А введен номер " + cardNumber);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public TransferPage clickDepositByCardOrder(int cardNumber) {
        if (cardNumber == 1) {
            depositBtns.first().click();
        } else if (cardNumber == 2) {
            depositBtns.last().click();
        } else throw new RuntimeException("У нас всего 2 карты. А введен номер " + cardNumber);
        return new TransferPage();
    }

}