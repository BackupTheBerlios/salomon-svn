
package salomon.controller.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.lang.reflect.InvocationTargetException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import salomon.core.Config;
import salomon.core.Messages;
import salomon.core.Resources;

/**
 * Class represents splash screen.
 */
public final class SplashScreen
{
	private String _resourcesDir = null;

	private JWindow _splashScreen = null;

	private long _startTime = 0;

	private SplashScreen()
	{
		_startTime = System.currentTimeMillis();
		_resourcesDir = Config.getString("RESOURCES_DIR");
		ImageIcon image = new ImageIcon(_resourcesDir + Config.FILE_SEPARATOR
				+ Resources.getString("SPLASH_SCREEN"));
		SplashLabel splashLabel = new SplashLabel(image);
		_splashScreen = new JWindow();
		_splashScreen.setLocation(splashLabel.getLocation());
		_splashScreen.getContentPane().add(splashLabel);
		_splashScreen.pack();
	}

	public static void hide()
	{
		if (SwingUtilities.isEventDispatchThread()) {
			getInstance().hideSplashScreen();
		} else {
			try {
				SwingUtilities.invokeAndWait(new Runnable() {
					public void run()
					{
						getInstance().hideSplashScreen();
					}
				});
			} catch (InterruptedException e) {
				_logger.fatal("", e);
			} catch (InvocationTargetException e) {
				_logger.error("", e);
			}
		}
	}

	private void hideSplashScreen()
	{
		long currentTime = System.currentTimeMillis();
		long splashTime = 0;
		long waitingTime = 0;
		try {
			splashTime = Long.parseLong(Config.getString("SPLASH_TIME")) * 1000;
			waitingTime = splashTime - (currentTime - _startTime);
		} catch (NumberFormatException e) {
			_logger.fatal("", e);
		}
		_logger.debug("waitingtime: " + waitingTime);
		if (waitingTime > 0) {
			try {
				Thread.sleep(waitingTime);
			} catch (InterruptedException e) {
				_logger.fatal("", e);
			}
		}

		_splashScreen.setVisible(false);
		_splashScreen = null;
	}

	private void showSplashScreen()
	{
		_splashScreen.setVisible(true);
	}

	public static void show()
	{

		if (SwingUtilities.isEventDispatchThread()) {
			getInstance().showSplashScreen();
		} else {
			try {
				SwingUtilities.invokeAndWait(new Runnable() {
					public void run()
					{
						getInstance().showSplashScreen();
					}
				});
			} catch (InterruptedException e) {
				_logger.fatal("", e);
			} catch (InvocationTargetException e) {
				_logger.fatal("", e);
			}
		}
	}

	private static SplashScreen getInstance()
	{
		if (_instance == null) {
			_instance = new SplashScreen();
		}

		return _instance;
	}

	/**
	 * 
	 * Class represents splash screen label. It allows to place version and
	 * other information at the screen
	 *  
	 */
	private class SplashLabel extends JLabel
	{
		Point _location = null;

		public SplashLabel(ImageIcon image)
		{
			super(image);
			_logger.debug("image: " + image);
			_location = new Point();
			_location.x = (Toolkit.getDefaultToolkit().getScreenSize().width - image.getIconWidth()) / 2;
			_location.y = (Toolkit.getDefaultToolkit().getScreenSize().height - image.getIconHeight()) / 2;
		}

		public Point getLocation()
		{
			return _location;
		}

		public void paint(Graphics g)
		{
			super.paint(g);
			Color oldColor = g.getColor();
			Font oldFont = g.getFont();
			Color newColor = Color.WHITE;
			Font newFont = new Font("Dialog", Font.BOLD, 15);
			g.setColor(newColor);
			g.setFont(newFont);
			String version = Messages.getString("TIT_VERSION") + ": "
					+ Messages.getString("VERSION");
			g.drawString(version, 255, 260);
			// setting back old color and font
			g.setColor(oldColor);
			g.setFont(oldFont);
		}
	}

	private static SplashScreen _instance = null;

	private static Logger _logger = Logger.getLogger(SplashScreen.class);
}