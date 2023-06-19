package HangiKrediPages;

import BasePages.DriverManager;
import BasePages.SeleniumBasePage;
import Utilities.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.sql.Driver;
import java.util.List;
import java.util.logging.Logger;


public class HomePage extends SeleniumBasePage {


    @FindBy(xpath = "//*[@class='navigation']//*[@class='has_submenu']/a[@href='/kredi']")
    WebElement btnCredit;

    @FindBy(id = "amount")
    WebElement textAmount;

    @FindBy(id = "maturity")
    WebElement selectLoanTerm;

    @FindBy(id = "CalculateCta")
    WebElement btnCalculate;

    @FindBy(xpath = "//h2")
    WebElement textTitle;

    //bu alanların daha az bakım maliyeti cıkması icin testid verilmeli.
    @FindBy(xpath = "//*[@class='card-content']//*[@class='card__special-header']")
    WebElement titleSponsorBank;

    @FindBy(xpath = "//*[@class='card-content']//*[text()='Sponsor Banka']//parent::div[1]//parent::div//div[@class='interest-rate']//*[contains(@class, 'rates')]")
    WebElement txtInterestRate;
    @FindBy(xpath = "(//div[@class='pfc__details']//parent::dd)[1]")
    WebElement txtDetailsInterestRate;

    @FindBy(xpath = "//*[@class='card-content']//*[text()='Sponsor Banka']//parent::div[1]//parent::div//div[@class='monthly-installment']//*[contains(@class, 'rates')]")
    WebElement txtMonthlyInstallment;
    @FindBy(xpath = "(//div[@class='pfc__details']//parent::dd)[3]")
    WebElement getTxtDetailsInterestRate;

    @FindBy(xpath = "//*[@class='card-content']//*[text()='Sponsor Banka']//parent::div[1]//parent::div//div[@class='total-payment']//*[contains(@class, 'rates')]")
    WebElement txtTotalPayment;
    @FindBy(xpath = "(//div[@class='pfc__details']//parent::dd)[4]")
    WebElement txtDetailsTotalPayment;

    @FindBy(xpath = "//a[@class='detail']")
    WebElement btnDetail;

    @FindBy(xpath = " //*[@class='navigation']//*[@class='has_submenu']//a[contains(@href,'/kredi/')]")
    List<WebElement> hoverElementCredit;

    @FindBy(xpath = " //div[@class='card__footer']/a[1]")
    List<WebElement> consumerLoanDetailButtonLinkList;

    @FindBy(xpath = "//div[contains(@class,'sponsored')]/div[@class='card__container']/div[@class='card__footer']/a[1]")
    List<WebElement> consumerLoanSponsorBankDetailButtonLinkList;



    @FindBy(xpath = " //div[@class='pfc__details']/dl/dd[1]")
    WebElement consumerLoanDetailInterestRateText;

    @FindBy(xpath = "//div[contains(@class,'sponsored')]/div[@class='card__container']/div[@class='card__content']/div[@class='interest-rate']/div[@class='rates ']/em")
    List<WebElement> consumerLoanListPageSponsoredBankInterestRateTextList;



    @FindBy(xpath = " //div[@class='pfc__details']/dl/dd[3]")
    WebElement consumerLoanDetailMonthlyInstallmentText;

    @FindBy(xpath = " //div[@class='pfc__details']/dl/dd[4]")
    WebElement consumerLoanDetailTotalAmountmentText;

    @FindBy(xpath = " //div[@class='interest-rate']/div/em")
    List<WebElement> consumerLoanListPageInterestRateTextList;
















    public HomePage() {
        PageFactory.initElements(DriverManager.getDriver(), this);
    }


    public HomePage navigateToUrl(String url) throws InterruptedException {
        DriverManager.getDriver().navigate().to(url);
        Thread.sleep(5000);
        Log.pass(url + "  --> Sayfasinda land olunacak.");
        return this;
    }

    public HomePage hoverCredit() throws InterruptedException {
        Thread.sleep(5000);
        scrollHover(btnCredit, "Kredi butonuna hover yapildi.");
        Thread.sleep(2000);
        return this;
    }

    public void controlCreditElement() {
            for (int i = 1; i <= hoverElementCredit.size(); i++) {
                List<WebElement> elem = DriverManager.getDriver().findElements(By.xpath("//*[@class='navigation']//*[@class='has_submenu']//a[contains(@href,'/kredi/')][" + i + "]"));
                if (!elem.isEmpty()) Log.pass(elem + " --> Element mevcut bir sekilde goruntulendi.");
            }
    }

    public void sendCreditPrice() throws InterruptedException {
        String number = Integer.toString(generateNumbers(500, 100000));
        Log.pass("Kredi tutari --> " + number);
        textAmount.sendKeys(number);
        Thread.sleep(2000);
        Log.pass("Kredi tutari " + textAmount.getText() + " girildi.");
        if (textAmount.getText().equals(number)) Log.fail("Kredi tutari yanlis girildi.");

    }

    public void selectLoanTerm(String loanTerm) throws InterruptedException {
        selectCombobox(selectLoanTerm, loanTerm);
        Thread.sleep(2000);
        Log.pass("Kredi vadesi secildi.");
    }

    public HomePage controlCreditSuggestions(String loanTerm) throws InterruptedException {
        sendCreditPrice();
        postRequest(DriverManager.getDriver().getCurrentUrl());
        selectLoanTerm(loanTerm);
        btnCalculate.click();
        Log.pass("Kredi hesapla butonuna tiklandi.");
        if (textTitle.isEnabled()) Log.pass("Kredi sonuclari goruntulendi.");
        else Log.fail("Kredi sonuclari goruntulenemedi.");
        return this;

    }

    public void controlSponsorBankDetails() throws InterruptedException {

        String interestRateText = txtInterestRate.getText();
        String monthlyInstallmentText = txtMonthlyInstallment.getText();
        String totalPaymentText = txtTotalPayment.getText();
        Log.pass("Sponsor banka detaylari goruntulendi ; " + " Faiz orani --> " + interestRateText + " Aylik Taksit -->" + monthlyInstallmentText + "Toplam Odeme --> " + totalPaymentText);
        btnDetail.click();
        Log.pass("Detaylar butonuna tiklandi.");
        Thread.sleep(2000);
        compareWebElementText(interestRateText, txtDetailsInterestRate.getText());
        compareWebElementText(monthlyInstallmentText, getTxtDetailsInterestRate.getText());
        compareWebElementText(totalPaymentText, txtDetailsTotalPayment.getText());
        Log.pass("Sponsor banka detaylari dogrulandi.");


    }

    public void controlConsumerLoanListInterestRate() throws InterruptedException {

        for (int i = 0 ; i < consumerLoanSponsorBankDetailButtonLinkList.size() ; i ++) {
            String oldUrl = DriverManager.getDriver().getCurrentUrl().toString();
            String listInterestRateText = consumerLoanListPageSponsoredBankInterestRateTextList.get(i).getText().trim();
           Log.pass(listInterestRateText);

            consumerLoanSponsorBankDetailButtonLinkList.get(i).click();
            //String actmonthlyınstallmentText = consumerLoanDetailMonthlyInstallmentText.getText();
            //String  acttotalamountText  = consumerLoanDetailTotalAmountmentText.getText();
            String actinterestRateText = consumerLoanDetailInterestRateText.getText().trim();
           Log.pass(actinterestRateText);

            compareWebElementText(actinterestRateText , listInterestRateText);
            navigateToUrl(oldUrl);
        }
        Log.pass("Tüm ürün kartlarının faiz oranlarının listelemede ve detayda aynı geldiği doğrulanır.");

    }


}

