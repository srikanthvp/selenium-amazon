package com.qa.listeners;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.MediaEntityBuilder;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.aventstack.extentreports.Status;
import com.qa.BaseTest;
import com.qa.reports.ExtentReport;
import com.qa.utils.TestUtils;

public class TestListener implements ITestListener {
	TestUtils utils = new TestUtils();
	BaseTest base = new BaseTest();
	byte[] encoded = null;
	public static ExtentTest Report;

	public void onTestFailure(ITestResult result) {
		if(result.getThrowable() != null) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			result.getThrowable().printStackTrace(pw);
			utils.log().error(sw.toString());
		}

		Map <String, String> params = new HashMap<String, String>();
		params = result.getTestContext().getCurrentXmlTest().getAllParameters();

		BaseTest base = new BaseTest();

		TakesScreenshot scrShot =((TakesScreenshot) base.getDriver());
		File file=scrShot.getScreenshotAs(OutputType.FILE);

		byte[] encoded = null;
		try {
			encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String imagePath = "Screenshots" + File.separator + params.get("browserName")
				+ "_" + params.get("environment") + File.separator + base.getDateTime() + File.separator
				+ result.getTestClass().getRealClass().getSimpleName() + File.separator + result.getName() + ".png";

		String completeImagePath = System.getProperty("user.dir") + File.separator + imagePath;

		try {
			FileUtils.copyFile(file, new File(imagePath));
			Reporter.log("This is the sample screenshot");
			Reporter.log("<a href='"+ completeImagePath + "'> <img src='"+ completeImagePath + "' height='400' width='400'/> </a>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			ExtentReport.getTest().fail("Test Failed",
					MediaEntityBuilder.createScreenCaptureFromPath(completeImagePath).build());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ExtentReport.getTest().fail(result.getThrowable());
	}



	@Override
	public void onTestStart(ITestResult result) {

		Map <String, String> params = new HashMap<String, String>();
		params = result.getTestContext().getCurrentXmlTest().getAllParameters();

		BaseTest base = new BaseTest();
		ExtentReport.startTest(result.getName(), result.getMethod().getDescription())
		.assignCategory(params.get("browserName") + "_" + base.getDateTime())
		.assignAuthor("Srikanth");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		ExtentReport.getTest().log(Status.PASS, "Test Passed");
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		ExtentReport.getTest().log(Status.SKIP, "Test Skipped");
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFinish(ITestContext context) {
		ExtentReport.getReporter().flush();		
	}

}
