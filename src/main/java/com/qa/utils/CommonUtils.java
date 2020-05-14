package com.qa.utils;

import com.aventstack.extentreports.Status;
import com.qa.BaseTest;
import com.qa.reports.ExtentReport;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.aventstack.extentreports.Status;
import com.qa.reports.ExtentReport;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class CommonUtils {
    TestUtils utils = new TestUtils();
    BaseTest base = new BaseTest();

    // This method is used to wait thread until the element is visible
    public void waitForVisibility(WebElement e) {
        WebDriverWait wait = new WebDriverWait(base.getDriver(), TestUtils.WAIT);
        wait.until(ExpectedConditions.visibilityOf(e));
    }

    // This method is used to validate if the element is visible
    public Boolean isDisplayed(WebElement e){
        waitForVisibility(e);
        return e.isDisplayed();
    }

    // This method is used to cleat a text field
    public void clear(WebElement e) {
        waitForVisibility(e);
        e.clear();
    }

    // This method is used to click an element
    public void click(WebElement e) {
        waitForVisibility(e);
        e.click();
    }

    // This method is used to click an element and log the info
    public void click(WebElement e, String msg) {
        waitForVisibility(e);
        utils.log().info(msg);
        ExtentReport.getTest().log(Status.INFO, msg);
        e.click();
    }

    // This method is used to send keys in an text box
    public void sendKeys(WebElement e, String txt) {
        waitForVisibility(e);
        e.sendKeys(txt);
    }

    // This method is used to send keys in an text box and log info
    public void sendKeys(WebElement e, String txt, String msg) {
        waitForVisibility(e);
        utils.log().info(msg);
        ExtentReport.getTest().log(Status.INFO, msg);
        e.sendKeys(txt);
    }

    // This method is used to send keys and along press enter
    public void sendKeysAndPressEnter(WebElement element, String input){
        Actions actions = new Actions(base.getDriver());
        actions.click(element).sendKeys(input).sendKeys(Keys.ENTER).build().perform();
    }

    // This method is used to get text attribute value of an MobileElement
    public String getAttribute(WebElement e, String attribute) {
        waitForVisibility(e);
        ExtentReport.getTest().log(Status.INFO, "Get Attribute value");
        return e.getAttribute(attribute);
    }

    // This method is used to get text from an MobileElement
    public String getText(WebElement element) {
        return element.getText();
    }

    public String windowHandles(){
        String oldTab = null;
        try {
            oldTab = base.getDriver().getWindowHandle();
            ArrayList<String> newTab = new ArrayList<String>(base.getDriver().getWindowHandles());
            newTab.remove(oldTab);
            // change focus to new tab
            base.getDriver().switchTo().window(newTab.get(0));
        }catch (Exception e){
            utils.log().info("Error switching window");
            e.printStackTrace();
        }
        return oldTab;
    }

    public boolean scrollToElement(WebElement element){
        try {
            waitForVisibility(element);
            Actions actions = new Actions(base.getDriver());
            actions.moveToElement(element);
            actions.perform();
            return true;
        }catch (Exception e){
            utils.log().error("scroll to element failed!");
            e.printStackTrace();
            return false;
        }
    }

    public JSONObject dataProvider(String dataFileName) throws IOException {
        InputStream datais = null;
        JSONObject jsonObject;
        try {
            datais = getClass().getClassLoader().getResourceAsStream(dataFileName);
            JSONTokener tokener = new JSONTokener(datais);
            jsonObject = new JSONObject(tokener);
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(datais != null) {
                datais.close();
            }
        }
        return jsonObject;
    }
}
