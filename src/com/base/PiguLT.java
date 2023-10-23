package com.base;

import java.awt.Point;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait; 

public class PiguLT {
	
	private WebDriver driver;
	
	public PageNavigationBar pageNavBar;
	public ProductListView productListView;
	
	class ItemInfo {
			public String name;
			public String price;
	}
	
	class GetOptions {
			public boolean name;
			public boolean price;
	}
	
	
	PiguLT (WebDriver driver)
	{
		this.driver = driver;
		
		this.pageNavBar = new PageNavigationBar();
		this.productListView = new ProductListView();
	}
	
	
	public void GoToHomePage() {
		
		driver.get("http://www.pigu.lt");
		
	}
	
	public void ClickAgreeCookies() {
	
		WebElement el = driver.findElement(By.xpath("//button[@widget-attachpoint='agree']"));
		el.click();
	}
	
	
	
	
	/**
	 * Class of methods for interacting with list of products.
	 */
	
	class ProductListView {
		
		public List<WebElement> getIemContainers() {
			
			List<WebElement> elContainers = new ArrayList<WebElement>();
			elContainers = driver.findElements(By.xpath("//div[starts-with(@class, 'api product-list-item tag-top product-block-')]"));
			
			return elContainers;
		}
		
		public String getItemName(WebElement elContainer) {
			
			String name;
			WebElement nameContainer;
			
			nameContainer = elContainer.findElement(By.xpath(".//p[@class='product-name']/a"));
			name = nameContainer.getAttribute("title");
			
			return name;
		}
		
		public String getItemPrice(WebElement elContainer) {
			
			String price;
			WebElement priceContainer;
			String eur;
			String eurocents;
			
			priceContainer = elContainer.findElement(By.xpath(".//div[@class='product-price']/span[@class='price notranslate ']"));
			eur = ((JavascriptExecutor) driver).executeScript("return arguments[0].firstChild.textContent", priceContainer).toString();
			eur = eur.trim();
			
			eurocents = priceContainer.findElement(By.xpath("./sup")).getText();
			
			price = eur + "." + eurocents;
			
			return price;
		}
		
		public List<PiguLT.ItemInfo> getItemsInfo(GetOptions getOptions) {
			
			List<PiguLT.ItemInfo> itemsInfo = new ArrayList<PiguLT.ItemInfo>();
			
			List<WebElement> elContainers = getIemContainers();
			
			int index = 0;
			for (WebElement container : elContainers) {
				
				itemsInfo.add(new PiguLT.ItemInfo());
				index++;
				
				if (getOptions.name) {
					itemsInfo.get(index).name = getItemName(container);
				}
				
				if(getOptions.price) {
					itemsInfo.get(index).price = getItemName(container);
				}					
				
			}
			
			return itemsInfo;
		}
	}
	
	
	
	
	
	
	/**
	 * Class of methods for interacting with page navigation bar.
	 */
	
	class PageNavigationBar {
		
		public int getCurrentPageIndex() {
			
			int index = 0;
			WebElement paginationBar;
			WebElement activePageSlot;
			String indexStr;
			
			paginationBar = driver.findElement(By.xpath("//span[@class='c-pagination ignore-childs']"));
			activePageSlot = paginationBar.findElement(By.xpath(".//a[contains(@class,'s-is-active') and starts-with(@class, pageSlot-)]"));

			indexStr = activePageSlot.getText();
			index = Integer.parseInt(indexStr);
			
			return index;
			
		}
		
		public int getTotalPagesCount() {
			
			int lastPage = 0;
			WebElement paginationBar;
			List<WebElement> pageSlots = new ArrayList<WebElement>(); 
			List<String> pageNumbersStr = new ArrayList<String>();
			List<Integer> pageNumbers = new ArrayList<Integer>();
			
			paginationBar = driver.findElement(By.xpath("//span[@class='c-pagination ignore-childs']"));
			pageSlots = paginationBar.findElements(By.xpath(".//a[starts-with(@class, pageSlot-)]"));
			
			for (WebElement pageSlot : pageSlots) {
				pageNumbersStr.add(pageSlot.getText());
			}
			
			for (String pageNumStr : pageNumbersStr) {
				
				try {
					pageNumbers.add(Integer.parseInt(pageNumStr)); // if not a numeric value skip to catch
				} catch (NumberFormatException e) { 
					// do nothing
				}

			}
			
			lastPage = Collections.max(pageNumbers);
			
			return lastPage;
		}
		
		public boolean isNextPageAvailable() {
			
			boolean isAvailable = false;
			WebElement paginationBar;
			WebElement nextPageEl;
			
			paginationBar = driver.findElement(By.xpath("//span[@class='c-pagination ignore-childs']"));
			nextPageEl = paginationBar.findElement(By.xpath(".//a[@widget-attachpoint='pageNext']"));
			
			
			if (nextPageEl.getAttribute("class").equals("s-is-disabled")) {
				isAvailable = false;
			} else {
				isAvailable = true;
			}
			
			return isAvailable;
		}
		
		public boolean isPreviousPageAvailable() {
			
			boolean isAvailable = false;
			WebElement paginationBar;
			WebElement PrevPageEl;
			
			paginationBar = driver.findElement(By.xpath("//span[@class='c-pagination ignore-childs']"));
			PrevPageEl = paginationBar.findElement(By.xpath(".//a[@widget-attachpoint='pagePrev']"));
			
			
			if (PrevPageEl.getAttribute("class").equals("s-is-disabled")) {
				isAvailable = false;
			} else {
				isAvailable = true;
			}
			
			return isAvailable;
		}
		
		public void clickNextPage() {
			
			WebElement paginationBar;
			WebElement nextPageEl;
			
			paginationBar = driver.findElement(By.xpath("//span[@class='c-pagination ignore-childs']"));
			nextPageEl = paginationBar.findElement(By.xpath(".//a[@widget-attachpoint='pageNext']"));
			
			// scroll to element
			new Actions(driver)
	        .scrollToElement(nextPageEl)
	        .perform();
			
			// scroll past lease-warning-message element by 100px
			new Actions(driver)
		    .scrollByAmount(0, 100)
		    .perform();
			 
			nextPageEl.click();
		}
		
		public void clickPrevPage() {
			
			WebElement paginationBar;
			WebElement PrevPageEl;
			
			paginationBar = driver.findElement(By.xpath("//span[@class='c-pagination ignore-childs']"));
			PrevPageEl = paginationBar.findElement(By.xpath(".//a[@widget-attachpoint='pagePrev']"));
			
			// scroll to element
			new Actions(driver)
	        .scrollToElement(PrevPageEl)
	        .perform();
			
			// scroll past lease-warning-message element by 100px
			new Actions(driver)
		    .scrollByAmount(0, 100)
		    .perform();
			
			PrevPageEl.click();
		}
	}
	
}