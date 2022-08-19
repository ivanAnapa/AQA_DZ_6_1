package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class TransferPage {
    private final SelenideElement pageTitle = $x("//div[@id='root']//h1");
    private final SelenideElement sumInput = $x("//span[./span[text()='Сумма']]//input");
    private final SelenideElement cardFromInput = $x("//span[./span[text()='Откуда']]//input");
    private final SelenideElement transferBtn = $("[data-test-id='action-transfer']");
    private final SelenideElement cancelBtn = $("[data-test-id='action-cancel']");


    public void checkOpenedPage() {
        pageTitle.shouldBe(visible);
    }

    public void setTransferSum(int transferSum) {
        sumInput.setValue(String.valueOf(transferSum));
    }
    public void setCardFrom(String cardNumber) {
        cardFromInput.setValue(cardNumber);
    }

    public DashboardPage clickTransferBtn() {
        transferBtn.click();
        return new DashboardPage();
    }

    public DashboardPage clickCancelBtn() {
        cancelBtn.click();
        return new DashboardPage();
    }
}
