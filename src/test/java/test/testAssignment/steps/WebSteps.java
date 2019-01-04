package test.testAssignment.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebSteps {

    WebDriver driver;

    public void sleep(int seconds) {
        try {
            Thread.sleep(seconds*1000);
        } catch (Exception e) {}
    }

    @Given("^I am a user trying to process a payment$")
    public void i_am_a_user_trying_to_process_a_payment(){
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\matth\\OneDrive\\University\\Semester 1 - year 3\\Software Testing\\TestingAssignment\\src\\test\\java\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/TestingAssignment");
    }

    @When("^I submit correct details$")
    public void i_submit_correct_details(){
        driver.findElement(By.name("name")).sendKeys("Matthew Attard");
        driver.findElement(By.name("address")).sendKeys("jksdjkgj");
        driver.findElement(By.name("card")).sendKeys("378282246310005");
        driver.findElement(By.name("expiry")).sendKeys("fsdfdsf");
        driver.findElement(By.name("cvv")).sendKeys("745");
        driver.findElement(By.name("amount")).sendKeys("15320");
        driver.findElement(By.name("submit")).click();
        sleep(5);
    }

    @Then("^I should be told that the payment was successful$")
    public void i_should_be_told_that_the_payment_was_successful(){
        String expectedTest = "Successful";
        String actualText = driver.findElement(By.id("result")).getText();
        Assert.assertTrue("Payment Successful",expectedTest.equals(actualText));
        driver.quit();
    }

    @When("^I submit a form with all data except <field>$")
    public void iSubmitAFormWithAllDataExceptField(String field) {
        System.out.println(field);
        if(!field.equals("name")){
            driver.findElement(By.name("name")).sendKeys("John Abela");
        }
        if(!field.equals("address")){
            driver.findElement(By.name("address")).sendKeys("Malta");
        }
        if(!field.equals("card")){
            driver.findElement(By.name("card")).sendKeys("378282246310005");
        }
        if(!field.equals("expiry")){
            driver.findElement(By.name("expiry")).sendKeys("22/5/16");
        }
        if(!field.equals("cvv")){
            driver.findElement(By.name("cvv")).sendKeys("745");
        }
        if(!field.equals("amount")){
            driver.findElement(By.name("amount")).sendKeys("15320");
        }
        driver.findElement(By.name("submit")).click();
        sleep(5);
    }

    @Then("^I should be told that <field> is required$")
    public void iShouldBeToldThatFieldIsRequired(String field) {
        String expectedTest = field;
        String actualText = driver.findElement(By.name("result")).getText();
        Assert.assertTrue("Payment Successful",expectedTest.contains(actualText));
        driver.quit();
    }
}
