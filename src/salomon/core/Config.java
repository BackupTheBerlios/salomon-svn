/*
 * Created on 2004-05-10
 *
 */

package salomon.core;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

/**
 * @author nico
 *
 */
public class Config
{
	private static final String BUNDLE_NAME = "config";//$NON-NLS-1$
	 
	private static ResourceBundle RESOURCE_BUNDLE = null;
	
	private static Logger _logger = Logger.getLogger(Config.class);
	
	public static String FILE_SEPARATOR = null;
	
	public static String CURR_DIR = null;

	static {
		try {
			RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
			FILE_SEPARATOR = System.getProperty("file.separator");
			CURR_DIR = System.getProperty("user.dir");
		} catch (Exception e) {
			_logger.fatal("", e);			
		}
	}
	
	/**
	 * 
	 */
	private Config()
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param key
	 * @return
	 */
	public static String getString(String key)
	{		
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			_logger.fatal("", e);
			return '!' + key + '!';
		}
	}
}