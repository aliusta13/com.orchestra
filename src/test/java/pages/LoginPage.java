package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class LoginPage {

    public LoginPage(){

        PageFactory.initElements(Driver.getDriver(),this);
    }

    @FindBy(xpath = "(//input[@placeholder='Mon e-mail'])[1]")
    public WebElement champMailUtilisateur;

    @FindBy(xpath = "(//input[@placeholder='Mon mot de passe'])[1]")
    public WebElement champMotDePasse;

    @FindBy(xpath = "//button[@class='btn btn-block btn-primary submit-customer-login']")
    public WebElement boutonDeConnexion;

    @FindBy(xpath = "//div[@class='alert alert-danger']")
    public WebElement message_erreur;





}
