package com.qa.pages.Product;

import com.qa.BaseTest;
import com.qa.utils.CommonUtils;
import com.qa.utils.TestUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductInfoPage extends BaseTest {
	TestUtils utils = new TestUtils();
	CommonUtils commonUtils = new CommonUtils();

	@FindBy(id = "productTitle")
    private WebElement productTitle;

    public String getItemTitle() {
        String title = null;
        try {
            commonUtils.windowHandles();
            utils.log().info("Item Title "+ commonUtils.getText(productTitle));
            title = commonUtils.getText(productTitle);
        }catch (Exception e){
            e.printStackTrace();
        }
        return title;
    }
}
