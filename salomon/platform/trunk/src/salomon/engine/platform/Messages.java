/*
 * Created on 2004-05-23
 *
 */

package salomon.engine.platform;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

/**
 * Class is responsible for managing all texts in application.
 * It holds resource bundle for messages.properties file.
 * 
 * @author nico
 */
public final class Messages
{
	private static final String BUNDLE_NAME = "messages";//$NON-NLS-1$

	private static Logger _logger = Logger.getLogger(Messages.class);

	private static ResourceBundle RESOURCE_BUNDLE;
	static {
		try {
			RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
		} catch (Exception e) {
			_logger.fatal("", e);
		}
	}

	private Messages()
	{
	}

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