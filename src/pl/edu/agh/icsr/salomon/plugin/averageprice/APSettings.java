/*
 * Created on 2004-05-22
 *
 */

package pl.edu.agh.icsr.salomon.plugin.averageprice;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import salomon.plugin.ISettings;

/**
 * @author nico
 *  
 */
public class APSettings implements ISettings
{
	private String _email = null;

	private String _name = null;

	private String _nick = null;

	private String _surname = null;

	/**
	 * @return Returns the email.
	 */
	public String getEmail()
	{
		return _email;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName()
	{
		return _name;
	}

	/**
	 * @return Returns the nick.
	 */
	public String getNick()
	{
		return _nick;
	}

	public String settingsToString()
	{
		String settings = ((_name == null) ? "" : _name) + ";";
		settings += ((_surname == null) ? "" : _surname) + ";";
		settings += ((_nick == null) ? "" : _nick) + ";";
		settings += ((_email == null) ? "" : _email);
		return settings;
	}

	/**
	 * @return Returns the surname.
	 */
	public String getSurname()
	{
		return _surname;
	}

	public void parseSettings(String stringSettings)
			throws NoSuchElementException
	{
		if (stringSettings.trim().equals("")) {
			_name = _surname = _email = _nick = null;
		} else {
			String separator = ";";
			StringTokenizer tokenizer = new StringTokenizer(stringSettings,
					separator, true);
			_name = tokenizer.nextToken();
			if (_name.equals(separator)) {
				_name = null;
			} else {
				// missing separator
				tokenizer.nextToken();
			}
			_surname = tokenizer.nextToken();
			if (_surname.equals(separator)) {
				_surname = null;
			} else {
				// missing separator
				tokenizer.nextToken();
			}
			_nick = tokenizer.nextToken();
			if (_nick.equals(separator)) {
				_nick = null;
			} else {
				// missing separator
				tokenizer.nextToken();
			}
			// last token treated in the special way
			if (tokenizer.hasMoreTokens()) {
				_email = tokenizer.nextToken();
			} else {
				_email = null;
			}
		}
	}

	/**
	 * @param email
	 *            The email to set.
	 */
	public void setEmail(String email)
	{
		_email = email;
	}

	/**
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name)
	{
		_name = name;
	}

	/**
	 * @param nick
	 *            The nick to set.
	 */
	public void setNick(String nick)
	{
		_nick = nick;
	}

	/**
	 * @param surname
	 *            The surname to set.
	 */
	public void setSurname(String surname)
	{
		_surname = surname;
	}

	/**
	 *  
	 */
	public String toString()
	{
		return "(" + _name + "," + _surname + "," + _nick + "," + _email + ")";
	}
}