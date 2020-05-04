package com.amazon.test;

import static com.amazon.common.Constants.PROPERTY_FILE_PATH;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.ScreenOrientation;
import org.testng.annotations.Test;

import com.amazon.common.PropertyUtility;
import com.amazon.pages.BaseClass;
import com.amazon.pages.LoginPage;
import com.amazon.pages.ProductDetailPage;
import com.amazon.pages.ProductSearchPage;

import io.appium.java_client.MobileElement;

/**
 * Actual class where product checkout test case handled
 * 
 * @author Karthika
 * 
 *         History : 
 *         2020-Apr-29 Karthika : Added simple test case to check
 *         positive flow of selecting the product and navigating to checkout
 *         screen 
 *         2020-Apr-30 Karthika : Handled Swipe event and Re-factored the
 *         testcase
 *         2020-May-04 Karthika : Re-factored the code
 * 
 */

public class ProductCheckout extends BaseClass {
	PropertyUtility putility = new PropertyUtility(PROPERTY_FILE_PATH);
	private static final Logger LOGGER = Logger.getLogger(ProductCheckout.class.getName());
	By languageSelection = putility.getObject("languageSelection");
	By saveChanges = putility.getObject("saveChanges");
	By search = putility.getObject("search");
	By ratings = putility.getObject("ratings");
	By checkoutProductName = putility.getObject("checkoutProductName");

	/*
	 * Testcase to compare the details of product search page with product checkout page
	 */
	@Test
	public void testProductCheckout() {
		LoginPage loginPage = new LoginPage();
		loginPage.loginToAmazon();
		// to select the language
		languageSelection();
		waitForElementPresence(search);
		ProductSearchPage searchPage = new ProductSearchPage();
		searchPage.searchTV();
		waitForElementPresence(ratings);

		// Rotate the screen from default portrait to landscape
		rotateScreen(ScreenOrientation.LANDSCAPE);

		// Identify Elememt using Text usimg scroll gesture
		ProductSearchPage productSearchPage = new ProductSearchPage();

		MobileElement element = productSearchPage.scrollToTheProduct();
		boolean response = element.isDisplayed();
		assertTrue(response);
		LOGGER.info("Scrolled to particular product");

		String searchProductName = productSearchPage.getProductText();
		element.click();
		rotateScreen(ScreenOrientation.PORTRAIT);

		languageSelection();

		waitForElementPresence(checkoutProductName);
		ProductDetailPage productDetailPage = new ProductDetailPage();
		String checkoutProductName = productDetailPage.getProductTitle();
		assertEquals(searchProductName, checkoutProductName);
		if (searchProductName.equalsIgnoreCase(checkoutProductName)) {
			LOGGER.info("Test case ran successfully");
		}
	}
	public void languageSelection() {
		try {
          if( driver.findElement(languageSelection).isDisplayed() && driver.findElement(languageSelection).isEnabled()) {
        	  clickElement(languageSelection);
        	  
        	  waitForElementPresence(saveChanges);
        	  clickElement(saveChanges);
        	  LOGGER.info("saveChanges button" + saveChanges + "  clicked");
          }
		}
		catch(NoSuchElementException igNoSuchElementException) {
			
		}
		LOGGER.info("languageSelection radio button" + languageSelection + "  is not visible");
	}

}