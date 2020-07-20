import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

public class ScreenshotsWithRegCards {

	static WebDriver driver;
	public static void main(String[] args) throws IOException, InterruptedException {

		//Set ChomeDriver as we are using Chrome Browser in our Example.
		//Either set the ChromeDriver using traditional way by setting it using system property with hardcoded location like below
		//System.setProperty("webdriver.chrome.driver","C://driver/chromedriver.exe"); 

		//Or Use WebDriverManager to setup the chromedriver like below
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-extensions");

		WebDriver driver = new ChromeDriver(options);
		driver.navigate().to("http://macert.dtrts.com");
		driver.manage().window().maximize();
		//		driver.get("https://ttdsevaonline.com/#/login");

		//        driver = new ChromeDriver();

		driver.findElement(By.xpath("//input[@name=\"userId\"]")).sendKeys("KASOPR42");
		driver.findElement(By.xpath("//input[@name=\"userPwd\"]")).sendKeys("Password1");

		driver.findElement(By.xpath("//button[@class=\"btn btn-default\"]")).click();


		Thread.sleep(3000);
		List<String> tranId = new ArrayList<String>();
		List<String> l = new ArrayList<String>();
		List<IDocument> l1 = new ArrayList<IDocument>();
		tranId.add("840830,01,2323233232");
//		tranId.add("840227,22,3434343433");

		/*tranId.add("841432,102,1005036343288");
		tranId.add("841433,103,1005036148430");
		tranId.add("841434,104,No Key");
		tranId.add("841435,105,1005036440581");
		tranId.add("841436,106,1005036440601");
		tranId.add("841437,107,1005035992675");
		tranId.add("841438,108,1005036603022");
		tranId.add("841439,109,1005036877405");
		tranId.add("841440,110,1005036602770");
		tranId.add("841441,111,1005036637249");
		tranId.add("841442,112,1005036877375");
		tranId.add("841445,113,1005036778717");
		tranId.add("841446,114,1005036237868");
		tranId.add("841447,115,1005036238786");
		tranId.add("841448,116,1005036088001");
		tranId.add("841449,117,1005036087634");
		tranId.add("841450,118,1005036238758");
		tranId.add("841451,119,1005036910541");
		tranId.add("841452,120,1005036468183");
		tranId.add("841453,121,1005036872830");
		tranId.add("841454,122,1005036971517");
		tranId.add("841396 ,123,1005037012760");
		tranId.add("841397 ,125,1005036095973");
		tranId.add("841398 ,126,1005036332817");
		tranId.add("841399 ,127,1005036332264");
		tranId.add("841400 ,128,1005036124062");
		tranId.add("841401 ,129,1005037012186");
		tranId.add("841402 ,130,1005036332843");
		tranId.add("841403 ,131,1005036123220");
		tranId.add("841404 ,132,1005036999875");
		tranId.add("841405 ,133,1005036197109");
		tranId.add("841408,134,1005036950860");
		tranId.add("841409 ,135,1005036983470");
		tranId.add("841410 ,136,1005036098628");
		tranId.add("841411 ,137,1005036590404");
		tranId.add("841412 ,138,1005036456371");
		tranId.add("841413 ,139,1005036299387");
		tranId.add("841414 ,140,1005036165623");
		tranId.add("841415 ,141,1005036418173");
		tranId.add("841416 ,142,1005036098615");
		tranId.add("841361 ,143,1005036057752");
		tranId.add("841362 ,144,1005036960330");
		tranId.add("841363 ,145,1005036321083");
		tranId.add("841364 ,146,1005036009946");
		tranId.add("841353 ,147,1005036336141");
		tranId.add("841365 ,148,No keys Generated");
		tranId.add("841355 ,149,1005036336154");
		tranId.add("841356 ,150,1005036009894");
		tranId.add("841357 ,151,1005036877065");
		tranId.add("841358 ,152,1005036918683");
		tranId.add("841359 ,153,1005036292606");
		tranId.add("841360 ,154,1005036960171");
		tranId.add("841366 ,157,1005036272521");
		tranId.add("841367 ,158,1005036239439");
		tranId.add("841368 ,159,1005036337079");
//		tranId.add("No Tran Id Generated,161,No keys Generated");
		tranId.add("841369 ,162,1005036796462");
		tranId.add("841370 ,163,No keys Generated");
		tranId.add("841371 ,164,1005036939080");
		tranId.add("841372 ,165,1005036106761");
		tranId.add("841374,167,No keys Generated");
		tranId.add("841375 ,168,No keys Generated");*/


		boolean regcardFlag = true;

		IDocument myDoc = new Document2004();
		File fileObj = new File("C:\\Cox\\1\\" + "finalDoc" + ".doc");
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(fileObj);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		myDoc.getHeader().addEle(Heading3.with("CancelRegistration").withStyle().align(Align.RIGHT).create());
		for(int k=0;k<tranId.size();k++) {
			driver.navigate().to("https://macert.dtrts.com/#/secure/transaction/NewReg/"+tranId.get(k).split(",")[0]);
			int a[]=new int[5];
			a[0]  =0 ;
			a[1] = 550;
			a[2] = 650;
			a[3] = 850;
			a[4] = 1050;

			myDoc.addEle(Heading1.with("Test Scenario ID: " + tranId.get(k).split(",")[1]).withStyle().align(Align.LEFT).create());
			myDoc.addEle(Heading1.with("AtlasTransactionKey: " + tranId.get(k).split(",")[2]).withStyle().align(Align.LEFT).create());
			String winHandleBefore = driver.getWindowHandle();
			
			

			for(int i=0; i<a.length;i++) {

				Thread.sleep(3000);
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("window.scrollBy(0,"+a[i]+")", "");

				SaveScreenshot.capture("Doc"+tranId.get(k).split(",")[0] + a[i], driver);

				myDoc.encoding(Encoding.UTF_8);

				System.out.println("Image file is ::: "+ i + ":"+ "c://Cox//1//" + "Doc"+tranId.get(k).split(",")[0]+a[i] + ".png");
				myDoc.addEle(Image.from_FULL_LOCAL_PATHL("c://Cox//1//" + "Doc"+tranId.get(k).split(",")[0]+a[i] + ".png") 
						.setHeight("350")
						.setWidth("550")
						.getContent());
				myDoc.addEle(BreakLine.times(5).create());

				System.out.println("final va :"+ a.length+ ":"+i);
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
							act.keyDown(Keys.CONTROL).sendKeys("VEHICEL INQUIRY").perform();

									
							
							Screenshot myScreenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
					        ImageIO.write(myScreenshot.getImage(),"PNG",new File("c://Cox//1//" + "CDoc"+tranId.get(k).split(",")[0]+a1[i1] + ".png"));

//							SaveScreenshot.capture("CDoc"+tranId.get(k).split(",")[0] + a1[i1], driver);
							//				l.add("Doc"+tranId.get(k).split(",")[0]+a[i]);

							myDoc.encoding(Encoding.UTF_8);

							System.out.println("CImage file is ::: "+ i + ":"+ "c://Cox//1//" + "CDoc"+tranId.get(k).split(",")[0]+a1[i1] + ".png");
							myDoc.addEle(Image.from_FULL_LOCAL_PATHL("c://Cox//1//" + "CDoc"+tranId.get(k).split(",")[0]+a1[i1] + ".png") 
									.setHeight("350")
									.setWidth("550")
									.getContent());
							myDoc.addEle(BreakLine.times(5).create());
						}
						Thread.sleep(3000);
						//					driver.close();
						//perform action that you want to perform on child window

				}

				l1.add(myDoc);


			}

			System.out.println("valuess " + tranId.get(k).split(",")[1]+ "--"+tranId.get(k).split(",")[2]);
			driver.switchTo().window(winHandleBefore);
		}
		myDoc.getFooter().addEle(
				Paragraph.with("@DealerTrack").create());

		l1.add(myDoc);

		//	 System.out.println(myDoc.getContent());
		String myWord = myDoc.getContent();
		writer.println(myWord);
		writer.close();


		// Print a confirmation image to console
		System.out.println("Word document created successfully!");
		//Quit the driver
		driver.quit();
	}
}