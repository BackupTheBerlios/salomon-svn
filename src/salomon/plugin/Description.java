
package salomon.plugin;

import java.io.Serializable;
import java.net.URL;

/**
 * Class represents plugin description.
 * 
 * @author nico
 */
public class Description implements Serializable
{
	private String _info;

	private String _input;

	private String _name;

	private String _output;

	private String _version;

	private int _pluginID;

	private URL _location;

	public Description()
	{
	};

	/**
	 * @param info
	 * @param name
	 */
	public Description(String name, String info)
	{
		_name = name;
		_info = info;
	}

	/**
	 * @return Returns the info.
	 */
	public String getInfo()
	{
		return _info;
	}

	/**
	 * @return Returns the input.
	 */
	public String getInput()
	{
		return _input;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName()
	{
		return _name;
	}

	/**
	 * @return Returns the output.
	 */
	public String getOutput()
	{
		return _output;
	}

	/**
	 * @return Returns the version.
	 */
	public String getVersion()
	{
		return _version;
	}

	/**
	 * @param info The info to set.
	 */
	public void setInfo(String info)
	{
		_info = info;
	}

	/**
	 * @param input The input to set.
	 */
	public void setInput(String input)
	{
		_input = input;
	}

	/**
	 * @param name The name to set.
	 */
	public void setName(String name)
	{
		_name = name;
	}

	/**
	 * @param output The output to set.
	 */
	public void setOutput(String output)
	{
		_output = output;
	}

	/**
	 * @param version The version to set.
	 */
	public void setVersion(String version)
	{
		_version = version;
	}

	/**
	 * @return Returns the location.
	 */
	public URL getLocation()
	{
		return _location;
	}

	/**
	 * @param location The location to set.
	 */
	public void setLocation(URL location)
	{
		_location = location;
	}

	/**
	 * @return Returns the pluginID.
	 */
	public int getPluginID()
	{
		return _pluginID;
	}

	/**
	 * @param pluginID The pluginID to set.
	 */
	public void setPluginID(int pluginID)
	{
		this._pluginID = pluginID;
	}

	public String toString()
	{
		return "" + _name + "," + _location + "," + _pluginID;
	}
} // end Description
