package com.qa.pages.SearchResult;

import com.qa.Exceptions.elementNotFoundException;
import com.qa.MenuPage;
import com.qa.pages.Product.ProductInfoPage;
import com.qa.utils.CommonUtils;
import com.qa.utils.TestUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class SearchResultsPage extends MenuPage {
	TestUtils utils = new TestUtils();
	CommonUtils commonUtils = new CommonUtils();

	@FindBy(id = "s-refinements")
	private WebElement leftSearchRefinePane;

	public boolean isLeftSearchRefinePaneDisplayed(){
		utils.log().info("Validating left search pane is displayed");
		return commonUtils.isDisplayed(leftSearchRefinePane);
	}

	public ProductInfoPage searchForItemAndClick(String itemName) throws elementNotFoundException {
		String xpath= "//*[contains(text(),'"+itemName+"')]";
		WebElement ele = getDriver().findElement(By.xpath(xpath));
		if(commonUtils.scrollToElement(ele)){
			commonUtils.click(ele);
			return new ProductInfoPage();
		}else {
			utils.log().error("Error searching for required element!");
			throw new elementNotFoundException();
		}
	}
}
