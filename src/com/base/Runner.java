package com.base;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.openqa.selenium.JavascriptExecutor;

public class Runner {
	
	public static void main(String[] args) {
		
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
	    
	    
		ChromeDriver driver = new ChromeDriver(options);
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		Helper helper = new Helper(driver);
		
		PiguLT pigu = new PiguLT(driver);

		pigu.GoToHomePage();
		helper.repeatedGoToURL("https://pigu.lt/lt/kompiuteriai/nesiojami-kompiuteriai");
		helper.waitUntil(() -> driver.findElement(By.xpath("//button[@widget-attachpoint='agree']")));
		pigu.ClickAgreeCookies();
		//driver.findElement(By.id("madeitup"));
		
		helper.repeatedGoToURL("https://pigu.lt/lt/kompiuteriai/nesiojami-kompiuteriai");
		helper.delayedWaitPageToLoad();
		List<WebElement> x = pigu.productListView.getIemContainers();
		System.out.println(x.size());
		System.out.println(pigu.productListView.getItemName(x.get(0)));
		System.out.println(x.size());
		System.out.println(pigu.productListView.getItemPrice(x.get(0)));
		

		System.out.println(driver.getCurrentUrl());
//		helper.waitUntil(() -> driver.findElement(By.xpath("//div[@id='productListLoader']")));
//		driver.findElement(By.xpath("//div[@id='productListLoader']"));
		helper.wait(1);
		//System.out.println(pigu.getCurrentPageIndex());
		pigu.pageNavBar.getTotalPagesCount();
		pigu.pageNavBar.isNextPageAvailable();
		pigu.pageNavBar.clickNextPage();
//		helper.wait(2);
		helper.delayedWaitPageToLoad();
		System.out.println(pigu.pageNavBar.getCurrentPageIndex());
		pigu.pageNavBar.clickPrevPage();
		
		try {
			// Wait for cookies approve request for 3 sec.
			//helper.waitUntil(() -> driver.findElement(By.xpath("//button[@widget-attachpoint='agree']")), 3);
			//pigu.ClickAgreeCookies();
			driver.findElement(By.xpath("//button[@widget-attachpoint='agree']"));
		} catch (org.openqa.selenium.NoSuchElementException e) {
			System.out.print("here");
			// if no cookies approve required, do nothing.
		}
		//List<WebElement> items = driver.findElements(By.xpath("//div[@class='api product-list-item tag-top']"));
		
		XMLEditor xed = new XMLEditor();
		xed.createFile("test", true);
		xed.addItemToRootElement(xed.createItemElement("Asus", "900"));
		xed.addItemToRootElement(xed.createItemElement("Lenovo", "200"));
		xed.writeToXMLFile();
		
	}

}
