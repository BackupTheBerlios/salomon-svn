/*
 * Created on 2004-06-14
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
public final class Resources
{
	private static final String BUNDLE_NAME = "resources";//$NON-NLS-1$

	private static ResourceBundle RESOURCE_BUNDLE = null;

	private static Logger _logger = Logger.getLogger(Resources.class);

	static {
		try {
			RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
		} catch (Exception e) {
			_logger.fatal("", e);
		}
	}

	/**
	 *  
	 */
	private Resources()
	{
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