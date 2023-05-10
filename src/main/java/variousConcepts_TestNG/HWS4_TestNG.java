package variousConcepts_TestNG;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HWS4_TestNG {

	WebDriver driver;
	String browser;

	By CUSTOMER_FIELD = By.xpath("//span[text()='Customers']");
	By ADDCUST_FIELD = By.xpath("//A[text()='Add Customer']");
	By ADD_CONTACT_HEADER_FIELD = By.xpath("//h5[text()='Add Contact']");
	By FULL_NAME_FIELD = By.xpath("//input[@id='account']");
	By COMPANY_NAME_FIELD = By.xpath("//select[@id='cid']");
	By Email_ID_FIELD = By.xpath("//input[@name='email']");
	By PHONE_FIELD = By.xpath("//input[@name='phone']");
	By ADDRESS_FIELD = By.xpath("//input[@name='address']");
	By CITY_FIELD = By.xpath("//input[@name='city']");
	By STATE_FIELD = By.xpath("//input[@name='state']");
	By POSTAL_CODE_FIELD = By.xpath("//input[@name='zip']");
	By COUNTRY_FIELD = By.xpath("//select[@name='country']");
	By TAGS_FIELD = By.xpath("//ul[@class='select2-selection__rendered']");
	By CURRENCY_FIELD = By.xpath("//select[@id='currency']");
	By GROUP_FIELD = By.xpath("//select[@id='group']");
	By SAVE_FIELD = By.xpath("//button[@class='md-btn md-btn-primary waves-effect waves-light']");
	By LISTCUST_FIELD = By.xpath("//a[text()='List Customers']");
	By SEARCH_CUSTOMER_FIELD = By.xpath("//input[@placeholder='Search...']");
	By Con_FIELD = By.xpath("//td[contains(text(),'5683920223')]");

	String country = "United States";
	String currency = "USD";
	String group = "Selenium";

	@BeforeClass
	public void readConfig() {

		// FileReader //InputSteam //Scanner //BufferedReader

		try {

			InputStream input = new FileInputStream("src\\main\\java\\config\\config.properties");
			// FileReader reader = new FileReader("src\\main\\java\\config\\config.properties");
			Properties prop = new Properties();
			prop.load(input);
			browser = prop.getProperty("browser");
			System.out.println("Browser used: " + browser);
			// url = prop.getProperty("url");

		} catch (IOException e) {
            System.out.println("file not found");
		}
	}
	
	@BeforeMethod
	public void init() {
		
		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					"C:\\Users\\patel\\Selenium2022\\session5_TestNG\\drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("edge")) {
			System.setProperty("webdriver.edge.driver", "C:\\Users\\patel\\Selenium2022\\session5_TestNG\\drivers\\msedgedriver.exe");
			driver = new EdgeDriver();
		}

		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.get("http://www.techfios.com/billing/?ng=admin/");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Test (priority=1)
	public void LogInTest() {
		WebElement USERNAME_ELEMENT = driver.findElement(By.xpath("//input[@id='username']"));
		WebElement PASSWORD_ELEMENT = driver.findElement(By.xpath("//input[@id='password' and @name='password']"));
		WebElement LOGIN_ELEMENT = driver.findElement(By.xpath("//button[@name='login']"));

		USERNAME_ELEMENT.sendKeys("demo@techfios.com");
		PASSWORD_ELEMENT.sendKeys("abc123");
		LOGIN_ELEMENT.click();

		WebElement HEADER_ELEMENT = driver.findElement(By.xpath("//h2[text()=' Dashboard ']"));
		WebDriverWait wait = new WebDriverWait(driver, 10);
		boolean Header = HEADER_ELEMENT.isDisplayed();
		wait.until(ExpectedConditions.visibilityOfAllElements(HEADER_ELEMENT));
		Assert.assertEquals(HEADER_ELEMENT.getText(), "Dashboard", "Page not found!!!");
		String HeadText = HEADER_ELEMENT.getText();
		System.out.println(Header);
		System.out.println(HeadText);
	}

	
	@Test (priority=2)
	public void AddCustomer() {

		// ---------------------add customer-------------------------------------

		LogInTest();

		driver.findElement(CUSTOMER_FIELD).click();
		driver.findElement(ADDCUST_FIELD).click();
		String exp = driver.findElement(ADD_CONTACT_HEADER_FIELD).getText();
		String act = "Add Contact";
		Assert.assertEquals(act, exp);
		System.out.println(exp);

		Random rdm = new Random();
		int generateNum = rdm.nextInt(999);
		driver.findElement(FULL_NAME_FIELD).sendKeys("Bappp" + generateNum);

		WebElement Company_DROPDOWN_Element = driver.findElement(COMPANY_NAME_FIELD);

		// to test all options from dropdown list with the help of for loop
		Select sel = new Select(Company_DROPDOWN_Element);
		List<WebElement> all_elements = sel.getOptions();
		System.out.println(all_elements.size());
		for (int i = 0; i < all_elements.size(); i++) {
			System.out.println(all_elements.get(i).getText());
		}
		sel.selectByVisibleText("Uber");

		driver.findElement(Email_ID_FIELD).sendKeys(generateNum + "vgdp2787@gmail.com");
		driver.findElement(PHONE_FIELD).sendKeys("5683920223");
		driver.findElement(ADDRESS_FIELD).sendKeys("225 NEW YORK ROAD");
		driver.findElement(CITY_FIELD).sendKeys("NEWYORK");
		driver.findElement(STATE_FIELD).sendKeys("NEW YORK");
		driver.findElement(POSTAL_CODE_FIELD).sendKeys("K9HJKA");

		WebElement COUNTRY_DROPDOWN_Element = driver.findElement(COUNTRY_FIELD);
		dropDownSelectMethod(country, COUNTRY_DROPDOWN_Element);
        
		driver.findElement(TAGS_FIELD).click();
		WebElement TAGFILL_ELEMENT = driver.findElement(By.xpath("//li[text()='IT Training']"));
		TAGFILL_ELEMENT.click();
		/*
		 * Actions action = new Actions(driver); WebElement Target_ELEMENT=(WebElement)
		 * driver.findElements(TAGS_FIELD); action.dragAndDrop(Target_ELEMENT,
		 * TAGFILL_ELEMENT); action.moveToElement(TAGFILL_ELEMENT).build().perform();
		 */

		WebElement CURRENCY_DROPDOWN_Element = driver.findElement(CURRENCY_FIELD);
		dropDownSelectMethod(currency, CURRENCY_DROPDOWN_Element);

		WebElement GROUP_DROPDOWN_Element = driver.findElement(GROUP_FIELD);
		dropDownSelectMethod(group, GROUP_DROPDOWN_Element);
		driver.findElement(SAVE_FIELD).click();

		// --------------------validate customer contact number------------------------

		driver.navigate().back();
		driver.findElement(CUSTOMER_FIELD).click();
		driver.findElement(LISTCUST_FIELD).click();
		driver.findElement(SEARCH_CUSTOMER_FIELD).sendKeys("Bappp");
		String ACTUAL = "5683920223";
		String EXPECTED = driver.findElement(Con_FIELD).getText();
		System.out.println(EXPECTED);
		Assert.assertEquals(ACTUAL, EXPECTED, "Contact number not displayed");
		System.out.println("Valid customer contact");

	}

	public void dropDownSelectMethod(String visibleText, WebElement element) {
		Select sel = new Select(element);
		sel.selectByVisibleText(visibleText);
	}
	
	@AfterMethod
	public void TearDown() {
		driver.close();
		driver.quit();
	}

}
