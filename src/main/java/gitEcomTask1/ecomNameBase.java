package gitEcomTask1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ecomNameBase {
	
	public static void main(String[] args) throws InterruptedException, IOException {
		
		
		WebDriverManager.chromedriver().setup();
		
		WebDriver driver = new ChromeDriver();
		
		driver.get("https://www.flipkart.com");
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		Thread.sleep(3000);
		
		WebElement close = driver.findElement(By.xpath("//button[@class='_2KpZ6l _2doB4z']"));
		close.click();
		
		Thread.sleep(2000);
		WebElement search = driver.findElement(By.xpath("//input[@name='q']"));
		search.sendKeys("Apple Watches");
		
		WebElement submit = driver.findElement(By.xpath("//button[@type='submit']"));
		submit.click();
		
		List<WebElement> itemName = driver.findElements(By.xpath("//div[@class='_4rR01T']"));
		
		List<WebElement> itemPrice = driver.findElements(By.xpath("//div[@class='_30jeq3 _1_WHN1']"));
		
		String name = null;
		String price = null;
		Map<String,Integer> newMap = new LinkedHashMap<>();
		
		File loc = new File("D:\\AutomationTestingFiles\\gitEcomTask1\\src\\test\\resources\\dataFile.xlsx");
		
		Workbook workFile = new XSSFWorkbook();
		
		Sheet sheet = workFile.createSheet("ItemDetails");	
		
		List itemNameList = new ArrayList();
		List itemPriceList = new ArrayList();
		
		for(int i=0;i<itemName.size();i++) {
			
			WebElement item = itemName.get(i);
			name = item.getText();
			
			Row row = sheet.createRow(i);
			itemNameList.add(name);
			
			WebElement item2 = itemPrice.get(i);
			price = item2.getText();
			Cell cell = row.createCell(0);
			cell.setCellValue(name);
			Cell cell1 = row.createCell(1);
			
			
			System.out.println(" Product Name : " + name);
			String priceNew = "";
			
			if(price.contains("???")) {
				priceNew = price.replace("???","");
			}
			if(price.contains(",")) {
				priceNew = priceNew.replace(",","");
				System.out.println(" Product Price : " + priceNew);
				
			}
				
			int intPrice = Integer.parseInt(priceNew);
			itemPriceList.add(intPrice);
			cell1.setCellValue(intPrice);
			newMap.put(name, intPrice);
						
		}
		
		newMap.get(name);

		FileOutputStream OP = new FileOutputStream(loc);

		workFile.write(OP);
		
		
		System.out.println("Size of the Map " + newMap.size());
		
		Collections.sort(itemPriceList);
		
		System.out.println("List of Prices in Ascending Order " + itemPriceList);
		
		System.out.println("Min Price in the Items " + Collections.min(itemPriceList));
		System.out.println("Max Price in the Items " + Collections.max(itemPriceList));
			
	}

}
