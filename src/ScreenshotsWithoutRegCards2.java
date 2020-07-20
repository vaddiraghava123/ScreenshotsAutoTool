


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import word.api.interfaces.IDocument;
import word.w2004.Document2004;
import word.w2004.Document2004.Encoding;
import word.w2004.elements.BreakLine;
import word.w2004.elements.Heading1;
import word.w2004.elements.Heading3;
import word.w2004.elements.Image;
import word.w2004.elements.Paragraph;
import word.w2004.style.HeadingStyle.Align;

public class ScreenshotsWithoutRegCards2 {

	static WebDriver driver;
	ScreenshotsWithoutRegCards2(String url, String path, String tranList, String TranName, boolean regcardFlag) throws IOException, InterruptedException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-extensions");

		WebDriver driver = new ChromeDriver(options);
		driver.navigate().to(url);
		driver.manage().window().maximize();
		
		driver.findElement(By.xpath("//input[@name=\"userId\"]")).sendKeys("KASOPR42");
		driver.findElement(By.xpath("//input[@name=\"userPwd\"]")).sendKeys("Password1");
		driver.findElement(By.xpath("//button[@class=\"btn btn-default\"]")).click();

		Thread.sleep(3000);
		List<String> tranId = new ArrayList<String>();
		List<IDocument> l1 = new ArrayList<IDocument>();
		
		String[] values = tranList.split("~");
	    for (int i=0; i < values.length; i++)
	    {
	      tranId.add(values[i]);
	    }
	    
		IDocument myDoc = new Document2004();
		File fileObj = new File(path + "\\"+TranName+"_finalDoc_"+formatter.format(new Date()) + ".doc");
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(fileObj);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		myDoc.getHeader().addEle(Heading3.with(TranName).withStyle().align(Align.RIGHT).create());
		for(int k=0;k<tranId.size();k++) {
			driver.navigate().to(url+"/#/secure/transaction/NewReg/"+tranId.get(k).split(",")[0]);
			int a[]=new int[5];
			a[0]  =0 ;
			a[1] = 550;
			a[2] = 650;
			a[3] = 850;
			a[4] = 1050;

			myDoc.addEle(Heading1.with("Test Scenario ID: " + tranId.get(k).split(",")[1] + "\t\t "+
			"AtlasTransactionKey: " + tranId.get(k).split(",")[2]).withStyle().align(Align.LEFT).create());
			String winHandleBefore = driver.getWindowHandle();

			for(int i=0; i<a.length;i++) {

				Thread.sleep(3000);
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("window.scrollBy(0,"+a[i]+")", "");

				SaveScreenshot.newCapture("Doc"+tranId.get(k).split(",")[0] + a[i], driver ,path);

				myDoc.encoding(Encoding.UTF_8);

				System.out.println("Image file is ::: "+ i + ":"+ path +"\\"+ "Doc"+tranId.get(k).split(",")[0]+a[i] + ".png");
				//To image display path is "c://Cox//Cancel//"
				myDoc.addEle(Image.from_FULL_LOCAL_PATHL(path + "Doc"+tranId.get(k).split(",")[0]+a[i] + ".png") 
						.setHeight("350")
						.setWidth("550")
						.getContent());
				myDoc.addEle(BreakLine.times(5).create());

				
				if(regcardFlag && i == a.length-1) {
					driver.findElement(By.xpath("//div[@id='select-all-optional-documents']//input" )).click();
					Thread.sleep(50);
					driver.findElement(By.xpath("//span[contains(text(),'View')]" )).click();
					Thread.sleep(12000);
					int a1[]=new int[6];
					for(String winHandle : driver.getWindowHandles()){
						System.out.println("Child window :: " + winHandle);
						Thread.sleep(50);
						driver.switchTo().window(winHandle);
						
						a1[0]  =10 ;
						a1[1]  =800;
						a1[2]  =1000 ;
						a1[3]  =1500;
						a1[4]  =2000 ;
						a1[5]  =2500;
					}
						System.out.println("Child final va :"+ a1.length );
						for(int i1=0; i1<a1.length;i1++) {
							Thread.sleep(3000);
							driver.manage().window().maximize();
							Thread.sleep(3000);
							JavascriptExecutor js1 = (JavascriptExecutor) driver;
							js1.executeScript("window.scrollBy(0,"+a1[i1]+")", "");
							
							Actions act = new Actions(driver);
							act.keyDown(Keys.CONTROL).sendKeys("REGISTRATION").perform();
							
//							Actions act = new Actions(driver);
							act.keyDown(Keys.CONTROL).sendKeys("VEHICLE INQUIRY").perform();

							Screenshot myScreenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
					        ImageIO.write(myScreenshot.getImage(),"PNG",new File( path + "CDoc"+tranId.get(k).split(",")[0]+a1[i1] + ".png"));

							myDoc.encoding(Encoding.UTF_8);

							System.out.println("CImage file is ::: "+ i + ":"+ path + "CDoc"+tranId.get(k).split(",")[0]+a1[i1] + ".png");
							myDoc.addEle(Image.from_FULL_LOCAL_PATHL(path + "CDoc"+tranId.get(k).split(",")[0]+a1[i1] + ".png") 
									.setHeight("350")
									.setWidth("550")
									.getContent());
							myDoc.addEle(BreakLine.times(5).create());
						}
						Thread.sleep(3000);
				}
				l1.add(myDoc);

			}

			System.out.println("valuess " + tranId.get(k).split(",")[1]+ "--"+tranId.get(k).split(",")[2]);
			driver.switchTo().window(winHandleBefore);
		}
		myDoc.getFooter().addEle(
				Paragraph.with("@DealerTrack").create());

		l1.add(myDoc);
		
		String myWord = myDoc.getContent();
		writer.println(myWord);
		writer.close();

		System.out.println("Word document created successfully!");
		//Quit the driver
		driver.quit();
	}
}