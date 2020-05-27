package sp;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import sp.pages.BasePage;
import sp.util.TestConf;

public class Steps {
    TestConf testConf = TestConf.getInstance();
    WebDriver driver;

    @Before
    public void setDriver(){
        driver = new FirefoxDriver();
    }

    @Given("^a base page$")
    public void a_base_page() throws Throwable{
        BasePage.loadUsing(driver, testConf.getSearchUrl());
    }
}
