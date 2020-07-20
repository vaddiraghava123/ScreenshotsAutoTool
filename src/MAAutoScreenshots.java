
import java.awt.EventQueue;

public class MAAutoScreenshots {
	MAAutoScreenshots() {
		retrieveInputValues();
	}
	
	public static void main(String... args) throws InterruptedException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				AutoScreenBookingWindow sevaWindow = new AutoScreenBookingWindow();
				sevaWindow.frame.setVisible(true);
			}
		});
	}

	protected void retrieveInputValues() {
		new AutoScreenBookingWindow();
	}

}
