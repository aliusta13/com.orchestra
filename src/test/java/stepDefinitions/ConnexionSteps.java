package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import pages.HomePage;
import pages.LoginPage;
import pages.ProfilePage;
import utilities.ConfigReader;
import utilities.Driver;

public class ConnexionSteps {

    LoginPage loginPage=new LoginPage();
    HomePage homePage=new HomePage();

    ProfilePage profilPage=new ProfilePage();

    @Given("L'utilisateur accède à la page d'accueil")
    public void l_utilisateur_accède_à_la_page_d_accueil() {
        Driver.getDriver().get(ConfigReader.getProperty("url"));
    }


    @When("L'utilisateur entre le nom d'utilisateur {string} et le mot de passe {string}")
    public void l_utilisateur_entre_le_nom_d_utilisateur_et_le_mot_de_passe(String email_utilisateur, String mot_de_passe) {

        switch (email_utilisateur){

            case "emailUtilisateurCorrect" :
                loginPage.champMailUtilisateur.clear();
                loginPage.champMailUtilisateur.sendKeys(ConfigReader.getProperty("emailUtilisateurCorrect"));
                break;

            case "emailUtilisateurIncorrect" :
                loginPage.champMailUtilisateur.clear();
                loginPage.champMailUtilisateur.sendKeys(ConfigReader.getProperty("emailUtilisateurIncorrect"));
                break;

        }

        switch (mot_de_passe){

            case "motDePasseCorrect" :

                loginPage.champMotDePasse.clear();
                loginPage.champMotDePasse.sendKeys(ConfigReader.getProperty("motDePasseCorrect"));
                break;

            case "motDePasseIncorrect" :
                loginPage.champMotDePasse.clear();
                loginPage.champMotDePasse.sendKeys(ConfigReader.getProperty("motDePasseIncorrect"));
                break;


        }


    }
    @When("Il clique sur le bouton de {string}")
    public void il_clique_sur_le_bouton_de(String boutton) {

        switch (boutton){

            case "compte" :
                homePage.boutonCompte.click();
                break;

            case "connexion" :
                loginPage.boutonDeConnexion.click();
                break;

        }


    }
    @Then("Un message d'erreur {string} doit s'afficher")
    public void un_message_d_erreur_doit_s_afficher(String string) {
        String actualMessageErreur= loginPage.message_erreur.getText();
        Assert.assertEquals(string,actualMessageErreur);
    }



    @When("L'utilisateur attend {int} secondes")
    public void utilisateur_attend_secondes(Integer secondes) {
        try {
            Thread.sleep(secondes * 1000); // Attend le nombre de secondes spécifié
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Then("l'utilisateur {string} accéder au compte")
    public void l_utilisateur_accéder_au_compte(String string) {
        switch (string){

            case "peut" :
                try {
                    Assert.assertTrue(profilPage.boutonDeDéconnexion.isDisplayed());
                } catch (Exception e) {
                    Assert.assertTrue("le compte n'a pas pu être accédé",false);
                }
                break;

            case "ne peut pas" :
                try {
                    Assert.assertTrue(loginPage.boutonDeConnexion.isDisplayed());
                } catch (Exception e) {
                    Assert.assertTrue("le compte a pu être accédé",false);
                }
                break;

            default:
                Assert.assertTrue("il faut saisir \"peut\" ou \"ne peut pas\"",false);

        }

    }
}
