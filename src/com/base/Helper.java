package com.base;


import java.util.function.Supplier;
import java.time.Duration;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import org.openqa.selenium.JavascriptExecutor;

public class Helper {
	
	private final static int defaultWaitUntilTimeoutWait = 10;
	private final static int defaultWaitUntilPollingInterval = 500;
	
	private final static int defaultRepeatedGoToURLTimeout = 3;
	
	private final static int defaultWaitPageLoadDelay = 3;
	private final static int defaultWaitPageLoadTimeout = 10;
	
	public ChromeDriver driver;

	Helper (ChromeDriver driver) {
		
		this.driver = driver;
		
	}
	
	public void waitUntil(Supplier<WebElement> find) {
		
		Wait<ChromeDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(defaultWaitUntilTimeoutWait))
                .pollingEvery(Duration.ofMillis(defaultWaitUntilPollingInterval))
                .ignoring(Exception.class);
		
		wait.until(d -> find.get());
	}
	
	public void waitUntil(Supplier<WebElement> find, int timeout) {
			
			Wait<ChromeDriver> wait = new FluentWait<>(driver)
	                .withTimeout(Duration.ofSeconds(timeout))
	                .pollingEvery(Duration.ofMillis(defaultWaitUntilPollingInterval))
	                .ignoring(Exception.class);
			
			wait.until(d -> find.get());
		}


	public void waitUntil(Supplier<WebElement> find, int timeout, int pollingInterval) {
		
		Wait<ChromeDriver> wait = new FluentWait<>(driver)
	            .withTimeout(Duration.ofSeconds(timeout))
	            .pollingEvery(Duration.ofMillis(pollingInterval))
	            .ignoring(Exception.class);
		
		wait.until(d -> find.get());
	}
	
	public void wait(int t) {
		
		Wait<ChromeDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(t))
                .pollingEvery(Duration.ofMillis(defaultWaitUntilPollingInterval))
                .ignoring(Exception.class);
		try {
			wait.until(null);
		} catch (Exception e) {}
	}
	
	
	
	public boolean isPageLoadComplete() {
		return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
	}
	
	
	
	public void repeatedGoToURL (String url) {
		
		wait(1);
		driver.get(url);
		
		int i = 1;
		
		while (i < defaultRepeatedGoToURLTimeout && !driver.getCurrentUrl().equals(url)) {
			wait(1);
			driver.get(url);
			i++;
		}
		
	}
	
	public void repeatedGoToURL (int timeout, String url) {
		
		wait(1);
		driver.get(url);
		
		int i = 1;
		
		while (i < timeout && !driver.getCurrentUrl().equals(url)) {
			wait(1);
			driver.get(url);
			i++;
		}
		
	}
	
	
	/**
	* Waits for set amount of seconds before starting to check for page load complete or timeout.
	* Helper.defaultWaitPageLoadTimeout = {@value com.base.Helper#defaultWaitPageLoadTimeout} and Helper.defaultWaitPageLoadDelay = {@value com.base.Helper#defaultWaitPageLoadDelay} is used.
	* 
	* 
	* @note <i>Delay is useful to allow such actions as prolonged page scroll to complete during which
	* document.readyState is, in fact, "complete". </i>
	*/
	public void delayedWaitPageToLoad() {
		
		wait(defaultWaitPageLoadDelay);
		
		boolean isComplete = false;
		int i = 0;
		
		isComplete = isPageLoadComplete();
		
		while (i < defaultWaitPageLoadTimeout && !isComplete) {
			wait(1);
			isComplete = isPageLoadComplete();
			i++;
		}
	}
	
	
	/**
	* Waits for set amount of seconds before starting to check for page load complete or timeout.
	* Helper.defaultWaitPageLoadTimeout = {@value com.base.Helper#defaultWaitPageLoadTimeout} is used.
	* 
	* @param  delay time in seconds for how long initial wait should take
	* 
	* @note <i>Delay is useful to allow such actions as prolonged page scroll to complete during which
	* document.readyState is, in fact, "complete". </i>
	*/
	public void delayedWaitPageToLoad(int delay) {
		
		wait(delay);
		
		boolean isComplete = false;
		int i = 0;
		
		isComplete = isPageLoadComplete();
		
		while (i < defaultWaitPageLoadTimeout && !isComplete) {
			wait(1);
			isComplete = isPageLoadComplete();
			i++;
		}
	}
	
	
	/**
	* Waits for set amount of seconds before starting to check for page load complete or timeout.
	*
	* @param  delay time in seconds for how long initial wait should take
	* @param  timeout time in seconds for how long method should check if document.readyState = "complete"
	* 
	* @note <i>Delay is useful to allow such actions as prolonged page scroll to complete during which
	* document.readyState is, in fact, "complete". </i>
	*/
	public void delayedWaitPageToLoad(int delay, int timeout) {
		
		wait(delay);
		
		boolean isComplete = false;
		int i = 0;
		
		isComplete = isPageLoadComplete();
		
		while (i < timeout && !isComplete) {
			wait(1);
			isComplete = isPageLoadComplete();
			i++;
		}
	}
	
	public void waitPageToLoad() {

		boolean isComplete = false;
		int i = 0;
		
		isComplete = isPageLoadComplete();
		
		while (i < defaultWaitPageLoadTimeout && !isComplete) {
			wait(1);
			isComplete = isPageLoadComplete();
			i++;
		}
		
	}
	
	public void waitPageToLoad(int timeout) {
		
		boolean isComplete = false;
		int i = 0;
		
		isComplete = isPageLoadComplete();
		
		while (i < timeout && !isComplete) {
			wait(1);
			isComplete = isPageLoadComplete();
			i++;
			System.out.println(i); // for debugging - print number of tries
			// for debugging - print state of page load
			System.out.println(((JavascriptExecutor) driver).executeScript("return document.readyState"));
		}
		
	}
	
}
