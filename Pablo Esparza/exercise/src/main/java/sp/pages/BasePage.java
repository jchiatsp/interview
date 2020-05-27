package sp.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import sp.util.TestConf;

public class BasePage {

    private WebDriver driver;
    private static final TestConf TEST_CONF = TestConf.getInstance();

    @FindBy(css = "input[name=email]")
    WebElement email;

    public BasePage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, TEST_CONF.getAjaxWaitSeconds()), this);
    }


    public static BasePage loadUsing( WebDriver driver, String URL) {
        driver.get(URL);
        return new BasePage(driver);
    }

    public void setEmail(){
        email.sendKeys("test");
    }
}
