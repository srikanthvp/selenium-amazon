package com.qa.tests;

import com.qa.MenuPage;
import com.qa.pages.Landing.LandingPage;
import com.qa.pages.SearchResult.SearchResultsPage;
import com.qa.utils.CommonUtils;
import com.qa.utils.TestUtils;
import org.json.JSONObject;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.lang.reflect.Method;

public class LandingPageTests extends MenuPage {
	TestUtils utils = new TestUtils();
	CommonUtils commonUtils = new CommonUtils();
	LandingPage landingPage;
	JSONObject testData;
	SearchResultsPage searchResultsPage;

	//TODO : Data provider has to be improvised
	  @BeforeClass
	  public void beforeClass() throws Exception {
		  landingPage = new LandingPage();
		  searchResultsPage = new SearchResultsPage();
	  }
	  
	  @BeforeMethod
	  public void beforeMethod(Method m) {
		  utils.log().info("\n" + "****** starting test:" + m.getName() + "******" + "\n");
	  }

	  @Test
	  public void homePage() {
	  	// Assertions of landing page
		  SoftAssert sf = new SoftAssert();
		  sf.assertTrue(landingPage.isSearchTxtBoxPresent());
		  sf.assertTrue(landingPage.isHamburgerIconPresent());
		  sf.assertTrue(landingPage.isCheckoutCartIconPresent());
		  sf.assertTrue(landingPage.isAmazonIconPresent());

		  sf.assertAll();
	  }

}
