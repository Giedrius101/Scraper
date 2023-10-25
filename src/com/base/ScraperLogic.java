package com.base;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.base.PiguLT.ItemInfo;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ScraperLogic {

	public ChromeDriver driver;
	
	public XMLEditor xmlEditor;
	public PiguLT pigu;
	public Helper helper;
	
	
	ScraperLogic () {
		initialize();
	}
	
	public void initialize() {
		
		WebDriverManager.chromedriver().setup();
		
		ChromeOptions options = new ChromeOptions();
//	    options.addArguments("start-maximized"); // open Browser in maximized mode
	    options.addArguments("disable-infobars"); // disabling infobars
	    options.addArguments("--disable-extensions"); // disabling extensions
	    options.addArguments("--disable-gpu"); // applicable to Windows os only
	    options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
	    options.addArguments("--no-sandbox"); // Bypass OS security model
	    options.addArguments("--disable-in-process-stack-traces");
	    options.addArguments("--disable-logging");
	    options.addArguments("--log-level=3");
	    options.addArguments("--remote-allow-origins=*");
	    
	    
	    driver = new ChromeDriver(options);	
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		
		helper = new Helper(driver);
		
		pigu = new PiguLT(driver);
		
		xmlEditor = new XMLEditor();
		
	}
	
	public void scrapeToFile(String prodListUrl, String fileName) {
		
		// default values for debugging
//		prodListUrl = "https://pigu.lt/lt/kompiuteriai/nesiojami-kompiuteriai";
//		fileName = "Laptops";
		
		xmlEditor.createFile(fileName, true);
		
		List<PiguLT.ItemInfo> itemList = new ArrayList<PiguLT.ItemInfo>();
		List<WebElement> itemContainers = new ArrayList<WebElement>();
		
		helper.repeatedGoToURL(prodListUrl);
		helper.delayedWaitPageToLoad();

		try {
			pigu.ClickAgreeCookies();
		} catch (org.openqa.selenium.NoSuchElementException e) {
			// if no cookies approve required, do nothing.
		}
		
		helper.delayedWaitPageToLoad();
		
		itemContainers =  pigu.productListView.getIemContainers();
		
		ItemInfo tempItem;
		for (WebElement itemContainer : itemContainers) {
			tempItem = new ItemInfo();
			tempItem.name = pigu.productListView.getItemName(itemContainer);
			tempItem.price = pigu.productListView.getItemPrice(itemContainer);
			
			itemList.add(tempItem);
		}
		
		for (ItemInfo item : itemList) {
			xmlEditor.addItemToRootElement(xmlEditor.createItemElement(item.name, item.price));
		}
		
		xmlEditor.writeToXMLFile();
		
	}
}
