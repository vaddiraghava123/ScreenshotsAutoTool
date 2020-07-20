
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class AutoScreenBookingWindow extends Frame {
	
	private static final long serialVersionUID = 1L;
	JFrame frame;
	private JTextField textField;
	private JTextField textField2;
	private JTextField textField3;
	private JTextField textField4;
	public AutoScreenBookingWindow()
	{

		frame = new JFrame("Auto Screenshots of MAPhase2");
		frame.setBounds(200, 100, 650, 580);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(Color.white);

		JPanel panel = (JPanel) frame.getContentPane();
		JLabel label = new JLabel();
		panel.add(label);
		label.setBounds(200, 3, 600, 10);
		label.setText("MA  Phase2 Auto Screenshots Tool");
		label.setBackground(Color.green);


		textField2 = new JTextField();
		JPanel panel2 = (JPanel) frame.getContentPane();
		JLabel label2 = new JLabel();
		panel2.add(label2);
		label2.setBounds(30, 30, 30, 30);
		label2.setText("URL");

		textField2.setBounds(180, 30, 250, 30);
		frame.getContentPane().add(textField2);
		textField2.setColumns(10);
		textField2.setToolTipText("Provide MA CERT URL");
		textField2.setText("https://macert.dtrts.com");
		
		textField3 = new JTextField();
		JPanel panel3 = (JPanel) frame.getContentPane();
		JLabel label3 = new JLabel();
		panel3.add(label3);
		label3.setBounds(30, 120, 100, 80);
		label3.setText("Transactin List");


		textField3.setBounds(180,160, 250, 300);
		frame.getContentPane().add(textField3);
		textField3.setColumns(400);
		textField3.setToolTipText("Provide comma seperated(ex) TranId,TestId,AtlasKey ~ TranId,TestId,AtlasKey"); 
		
		
		textField4 = new JTextField();
		JPanel panel4 = (JPanel) frame.getContentPane();
		JLabel label4 = new JLabel();
		panel4.add(label4);
		label4.setBounds(450, 120, 100, 80);
		label4.setText("TransactionName");


		textField4.setBounds(450,170, 150, 30);
		frame.getContentPane().add(textField4);
		textField4.setColumns(10);
		textField4.setToolTipText("Specify Transaction Name");
		textField4.setText("Cancel Registration");
		
		final JRadioButton autoOptions1 = new JRadioButton("REGCARD");
		final JRadioButton autoOptions2 = new JRadioButton("NO REGCARDS", true);
		autoOptions1.setBounds(450, 200, 150, 20);
		autoOptions2.setBounds(450, 230, 170, 20);

		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(autoOptions1);
		buttonGroup.add(autoOptions2);

		frame.getContentPane().add(autoOptions1);frame.getContentPane().add(autoOptions2);
		
		textField = new JTextField();
		JPanel panel1 = (JPanel) frame.getContentPane();
		JLabel label1 = new JLabel();
		panel1.add(label1);
		label1.setBounds(30, 80, 100, 30);
		label1.setText("Folder Path ..");


		textField.setBounds(180,90, 250, 30);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		textField.setToolTipText("All screenshots are placed under specified folder path");
		textField.setText("c://Cox//Cancel//");
		
		JButton browseButton = new JButton("Browse");
		browseButton.setBounds(430, 90, 150, 30);
		browseButton.setBackground(Color.WHITE);
		frame.getContentPane().add(browseButton);


		// BrowseButton          
		browseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JFileChooser chooseFile = new JFileChooser();
				// For Directory
				chooseFile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooseFile.setAcceptAllFileFilterUsed(false);

				int rVal = chooseFile.showOpenDialog(null);
				if (rVal == JFileChooser.APPROVE_OPTION) {
					textField.setText(chooseFile.getSelectedFile().toString());
				}
			}
		});

		final JButton clickButton = new JButton("Take Screenshots");
		clickButton.setBounds(150, 490, 460, 30);
		clickButton.setBackground(Color.WHITE);
		clickButton.setForeground(Color.BLACK);
		frame.getContentPane().add(clickButton);


		// Process Seva Booking
		clickButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
				
					if(!validationCheck()) {
						System.out.println("Moving to Auto Screenshots.");
							try {
								new ScreenshotsWithoutRegCards2(textField2.getText(),
										textField.getText(),
										textField3.getText(),
										textField4.getText(), autoOptions1.isSelected());
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					} else {
						
						JOptionPane.showMessageDialog(clickButton,"Fill all details(URL ,TransactionList and Path).."+	
								"\n","Warning!!",JOptionPane.ERROR_MESSAGE);
					}


			}

			protected void processSevaBooking(String username) throws InterruptedException {
				System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--disable-extensions");

				WebDriver driver = new ChromeDriver(options);
				driver.navigate().to("https://ttdsevaonline.com/#/login");
				driver.manage().window().maximize();
				driver.get("https://ttdsevaonline.com/#/login");

				driver.findElement(By.xpath("//input[@name=\"uName\"]")).sendKeys(username);
				Thread.sleep(2000);
				driver.findElement(By.xpath("//input[@name=\"pwd\"]")).sendKeys("Raghava123*");
				Thread.sleep(1000);

				driver.findElement(By.xpath("//button[@id=\"login_sub\"]")).click();
				Thread.sleep(10000);
				driver.findElement(By.xpath("//a[contains(@class,\"tplogout_pilgrim\")]")).click();
				System.out.println("Logout Successfully");
				

				driver.close();
			}

			private boolean validationCheck() {
				boolean flag = false;
				if(textField.getText().isBlank() || textField.getText().isEmpty()
						&& textField2.getText().isBlank() || textField2.getText().isEmpty()
						&& textField3.getText().isBlank() || textField3.getText().isEmpty()
						 ) {
					flag = true;
				}
				return flag;
			}

		});
		frame.setBackground(Color.WHITE);
		frame.addNotify();
		frame.setVisible(true);
	
	}
}
