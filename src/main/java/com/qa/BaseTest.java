package com.qa;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Properties;
import org.apache.logging.log4j.ThreadContext;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.qa.utils.TestUtils;

public class BaseTest {
	protected static ThreadLocal <WebDriver> driver = new ThreadLocal<WebDriver>();
	protected static ThreadLocal <Properties> props = new ThreadLocal<Properties>();
	protected static ThreadLocal <HashMap<String, String>> strings = new ThreadLocal<HashMap<String, String>>();
	protected static ThreadLocal <String> browser = new ThreadLocal<String>();
	protected static ThreadLocal <String> dateTime = new ThreadLocal<String>();

	String browserName;

	TestUtils utils = new TestUtils();
	  public SoftAssert softAssert;

	  public WebDriver getDriver() {
		  return driver.get();
	  }

	  public void setDriver(WebDriver driver2) {
		driver.set(driver2);
		}

	  public String getBrowserName() {
	  	return this.browserName;
	}

	  public void setBrowserName(String browserName) {
		this.browserName = browserName;
	}

	  public HashMap<String, String> getStrings() {
		return strings.get();
	}

	  public void setStrings(HashMap<String, String> strings2) {
		strings.set(strings2);
	}

	  public void setDateTime(String dateTime2) {
		dateTime.set(dateTime2);
	}

	  public static void setProps(Properties props2) {
		  props.set(props2);
	  }

	  public String getDateTime() {
		  return dateTime.get();
	  }

	public BaseTest() {
		PageFactory.initElements(getDriver(), this);
	}
	
	@BeforeMethod
	public void beforeMethod() {
	}
	
	//stop video capturing and create *.mp4 file
	@AfterMethod
	public synchronized void afterMethod(ITestResult result) throws Exception {
	}

	@BeforeSuite
	public void beforeSuite() {

	}

  @Parameters({"browserName", "browserVersion", "headless", "environment"})
  @BeforeTest
  public void beforeTest(String browserName, String browserVersion, String headless, String environment) throws Exception {
	  setDateTime(utils.dateTime());
	  URL url;
	  InputStream inputStream = null;
	  InputStream stringsis = null;
	  WebDriver driver = null;
	  String propFileName = null;
	  Properties props = new Properties();
	  String xmlFileName = null;
		
		String strFile = "logs" + File.separator + browserName + "_" + environment;
		File logFile = new File(strFile);
		if (!logFile.exists()) {
			logFile.mkdirs();
		}
		//route logs to separate file for each thread
		ThreadContext.put("ROUTINGKEY", strFile);
		utils.log().info("log path: " + strFile);
		
	  try {
		  props = new Properties();
		  if(environment.equalsIgnoreCase("stage")) {
		  	propFileName = "config-files/staging-config.properties";
		  	xmlFileName = "strings/stage/strings.xml";
		  }else if(environment.equalsIgnoreCase("prod")){
		  	propFileName = "config-files/prod-config.properties";
		  	xmlFileName = "strings/prod/strings.xml";
		  }
		  
		  utils.log().info("load " + propFileName);
		  inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
		  props.load(inputStream);
		  setProps(props);
		  
		  utils.log().info("load " + xmlFileName);
		  stringsis = getClass().getClassLoader().getResourceAsStream(xmlFileName);
		  setStrings(utils.parseStringXML(stringsis));


		  String URL = props.getProperty("BaseURL");
		  DesiredCapabilities capabilities = new DesiredCapabilities();

		  capabilities.setCapability(CapabilityType.BROWSER_NAME, browserName);
		  capabilities.setCapability(CapabilityType.BROWSER_VERSION, browserName);
		  capabilities.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
		  capabilities.setCapability(CapabilityType.APPLICATION_NAME, "Amazon-Selenium");

			switch(browserName) {
				case "chrome":
					ChromeOptions options = new ChromeOptions();
					if(headless.equalsIgnoreCase("true")){
						options.addArguments("headless");
						options.addArguments("window-size=1200x600");
					}
					ChromeDriverService service = new ChromeDriverService.Builder()
							.usingDriverExecutable(new File("src/test/resources/driver/chromedriver"))
							.usingAnyFreePort()
							.build();
					options.merge(capabilities);
					driver = new ChromeDriver(service, options);
					driver.manage().window().maximize();
					driver.get(URL);
				break;
				case "firefox":
					System.setProperty("webdriver.gecko.driver","src/test/resources/driver/geckodriver");
					driver = new FirefoxDriver();
					driver.get(URL);
				break;
			default:
				throw new Exception("Invalid browserName! - " + browserName);
			}
		  	setBrowserName(browserName);
			setDriver(driver);
			utils.log().info("driver initialized: " + driver);
	  } catch (Exception e) {
		  utils.log().fatal("driver initialization failure. ABORT!!!\n" + e.toString());
		  throw e;
	  } finally {
		  if(inputStream != null) {
			  inputStream.close();
		  }
		  if(stringsis != null) {
			  stringsis.close();
		  }
	  }
  }

  @AfterTest
  public void afterTest() {
	  getDriver().quit();
  }

}
