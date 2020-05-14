package com.qa;

import com.qa.pages.SearchResult.SearchResultsPage;
import com.qa.utils.CommonUtils;
import com.qa.utils.TestUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MenuPage extends BaseTest{
	CommonUtils commonUtils = new CommonUtils();
	TestUtils testUtils = new TestUtils();

	@FindBy(id = "twotabsearchtextbox")
	private WebElement searchTxtFld;

	@FindBy(id = "nav-hamburger-menu")
	private WebElement hamburgerIcon;

	@FindBy(id = "nav-cart")
	private WebElement checkOutCartIcon;

	@FindBy(id = "nav-logo")
	private WebElement amazonIconLink;

	@FindBy(xpath = "//input[@class='nav-input' and @type='submit']")
	private WebElement searchMagIcon;

	public boolean isSearchTxtBoxPresent(){
		testUtils.log("isSearchTxtBoxPresent");
		return commonUtils.isDisplayed(searchTxtFld);
	}

	public boolean isHamburgerIconPresent(){
		testUtils.log("isHamburgerIconPresent");
		return commonUtils.isDisplayed(hamburgerIcon);
	}

	public boolean isCheckoutCartIconPresent(){
		testUtils.log("isCheckoutCartIconPresent");
		return commonUtils.isDisplayed(checkOutCartIcon);
	}

	public boolean isAmazonIconPresent(){
		testUtils.log("isAmazonIconPresent");
		return commonUtils.isDisplayed(amazonIconLink);
	}

	public SearchResultsPage searchItem(String Item){
		testUtils.log("Searching for Item in the search text field");
		commonUtils.sendKeys(searchTxtFld, Item);
		commonUtils.click(searchMagIcon);
		return new SearchResultsPage();
	}

}
