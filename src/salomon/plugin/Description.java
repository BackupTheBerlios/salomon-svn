/** Java class "Description.java" generated from Poseidon for UML.
 *  Poseidon for UML is developed by <A HREF="http://www.gentleware.com">Gentleware</A>.
 *  Generated with <A HREF="http://jakarta.apache.org/velocity/">velocity</A> template engine.
 */

package salomon.plugin;

import java.io.File;

/**
 *  
 */
public class Description
{
	private String _info;

	private String _input;

	private String _name;

	private String _output;

	private String _version;

	private int _pluginID;

	private File _location;

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
	 * @param info
	 *            The info to set.
	 */
	public void setInfo(String info)
	{
		_info = info;
	}

	/**
	 * @param input
	 *            The input to set.
	 */
	public void setInput(String input)
	{
		_input = input;
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
	 * @param output
	 *            The output to set.
	 */
	public void setOutput(String output)
	{
		_output = output;
	}

	/**
	 * @param version
	 *            The version to set.
	 */
	public void setVersion(String version)
	{
		_version = version;
	}

	/**
	 * @return Returns the location.
	 */
	public File getLocation()
	{
		return _location;
	}

	/**
	 * @param location
	 *            The location to set.
	 */
	public void setLocation(File location)
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
	 * @param pluginID
	 *            The pluginID to set.
	 */
	public void setPluginID(int pluginID)
	{
		this._pluginID = pluginID;
	}
} // end Description
