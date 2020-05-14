package com.qa.tests;

import com.qa.BaseTest;
import com.qa.Exceptions.elementNotFoundException;
import com.qa.Exceptions.loginFailedException;
import com.qa.pages.Landing.LandingPage;
import com.qa.pages.Product.ProductInfoPage;
import com.qa.pages.SearchResult.SearchResultsPage;
import com.qa.tests.helper.LandingPageHelper;
import com.qa.tests.helper.SearchResultsHelper;
import com.qa.utils.CommonUtils;
import com.qa.utils.TestUtils;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.io.InputStream;
import java.lang.reflect.Method;

public class ProductInfoTests extends BaseTest {
	LandingPage landingPage;
	JSONObject testData;
	SearchResultsPage searchResultsPage;
	TestUtils utils = new TestUtils();
	CommonUtils commonUtils = new CommonUtils();

	//TODO : Data provider has to be improvised
	  @BeforeClass
	  public void beforeClass() throws Exception {
		  testData = commonUtils.dataProvider("data/searchResultsTestData.json");
	  }

	  @AfterClass
	  public void afterClass() {
	  }
	  
	  @BeforeMethod
	  public void beforeMethod(Method m) {
		  utils.log().info("\n" + "****** starting test:" + m.getName() + "******" + "\n");
	  }

	  @AfterMethod
	  public void afterMethod() {

	  }


	  @Test
	  public void ProductInfoDisplayed() throws elementNotFoundException {

		  searchResultsPage = landingPage.searchItem(testData.getJSONObject("TvSearch").getString("searchFor"));
		  searchResultsPage.searchForItemAndClick(testData.getJSONObject("TvSearch").getString("TVName"));

	  }

}
