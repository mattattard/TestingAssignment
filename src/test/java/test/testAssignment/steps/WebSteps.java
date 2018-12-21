package test.testAssignment.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
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
    public void i_am_a_user_trying_to_process_a_payment() throws Exception{
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\matth\\OneDrive\\University\\Semester 1 - year 3\\Software Testing\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/TestingAssignment");

    }

    @When("^I submit correct details$")
    public void i_submit_correct_details() throws Exception {
        driver.findElement(By.name("name")).sendKeys("Matthew Attard");
        driver.findElement(By.name("address")).sendKeys("jksdjkgj");
        driver.findElement(By.name("card")).sendKeys("378282246310005");
        driver.findElement(By.name("cardType")).sendKeys("");
        driver.findElement(By.name("cvv")).sendKeys("745");
        driver.findElement(By.name("amount")).sendKeys("15320");
        sleep(10);
    }

    @Then("^I should be told that the payment was successful$")
    public void i_should_be_told_that_the_payment_was_successful() throws Exception {
        //Todo: to check the process Payment was successful.
        driver.quit();
    }
}
