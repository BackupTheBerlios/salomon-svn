/*
 *
 * Author: Jakub Pawlowski
 * Created on:2004-12-19 02:07:32
 */

package salomon.engine.platform.serialization;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * 
 * TODO: add comment.
 * 
 * @author kuba
 * 
 */
public class SalomonErrorHandler extends DefaultHandler
{
	/**
	 * @see org.xml.sax.helpers.DefaultHandler#error(org.xml.sax.SAXParseException)
	 */
	public void error(SAXParseException e) throws SAXException
	{
		throw e;
	}
}
