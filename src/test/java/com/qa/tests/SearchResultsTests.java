package com.qa.tests;

import com.qa.BaseTest;
import com.qa.Exceptions.elementNotFoundException;
import com.qa.Exceptions.loginFailedException;
import com.qa.pages.Landing.LandingPage;
import com.qa.pages.Product.ProductInfoPage;
import com.qa.pages.SearchResult.SearchResultsPage;
import com.qa.utils.CommonUtils;
import com.qa.utils.TestUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;
import java.lang.reflect.Method;

public class SearchResultsTests extends BaseTest {
	LandingPage landingPage;
	JSONObject testData;
	SearchResultsPage searchResultsPage;
	CommonUtils commonUtils;
	ProductInfoPage productInfoPage;
	TestUtils utils = new TestUtils();

	  @BeforeClass
	  public void beforeClass() throws Exception {
		  commonUtils = new CommonUtils();
		  landingPage = new LandingPage();
		  searchResultsPage = new SearchResultsPage();
		  productInfoPage = new ProductInfoPage();
		  testData = commonUtils.dataProvider("data/searchResultsTestData.json");
	  }

	  @BeforeMethod
	  public void beforeMethod(Method m) {
		  utils.log().info("\n" + "****** starting test:" + m.getName() + "******" + "\n");
	  }

	  @Test
	  public void landingPageSearchTV() throws loginFailedException, elementNotFoundException {
	  	searchResultsPage = landingPage.searchItem(testData.getJSONObject("TvSearch").getString("searchFor"));

		  //Assertion on Search results page
		  Assert.assertTrue(searchResultsPage.isLeftSearchRefinePaneDisplayed());

		productInfoPage = searchResultsPage.searchForItemAndClick(testData.getJSONObject("TvSearch").getString("TVName"));
		String actualItemTitle = productInfoPage.getItemTitle().trim();
		String expectedItemTitle = getStrings().get("tv_product_title");

		Assert.assertEquals(actualItemTitle, expectedItemTitle);

	  }

}
