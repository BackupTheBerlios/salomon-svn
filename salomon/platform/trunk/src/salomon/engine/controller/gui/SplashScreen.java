/*
 * Copyright (C) 2004 Salomon Team
 *
 * This file is part of Salomon.
 *
 * Salomon is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * Salomon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Salomon; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 */

package salomon.engine.controller.gui;

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

import salomon.engine.Config;
import salomon.engine.Messages;
import salomon.engine.Resources;
import salomon.engine.Version;

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
				LOGGER.fatal("", e);
			} catch (InvocationTargetException e) {
				LOGGER.error("", e);
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
			LOGGER.fatal("", e);
		}
		LOGGER.debug("waitingtime: " + waitingTime);
		if (waitingTime > 0) {
			try {
				Thread.sleep(waitingTime);
			} catch (InterruptedException e) {
				LOGGER.fatal("", e);
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
				LOGGER.fatal("", e);
			} catch (InvocationTargetException e) {
				LOGGER.fatal("", e);
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
			LOGGER.debug("image: " + image);
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
			String revision = Messages.getString("BUILD") + ": "
					+ Version.getString("REVISION_VERSION");
			g.drawString(version, 255, 260);
			g.drawString(revision, 255, 290);
			// setting back old color and font
			g.setColor(oldColor);
			g.setFont(oldFont);
		}
	}

	/**
	 * 
	 * @uml.property name="_instance"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private static SplashScreen _instance = null;

	private static final Logger LOGGER = Logger.getLogger(SplashScreen.class);
}