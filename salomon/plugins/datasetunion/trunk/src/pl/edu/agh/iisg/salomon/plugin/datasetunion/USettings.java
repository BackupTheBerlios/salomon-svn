
package pl.edu.agh.iisg.salomon.plugin.datasetunion;

import java.util.StringTokenizer;

import salomon.plugin.ISettings;

/**
 * 
 * TODO: add comment.
 * 
 * @author krzychu
 *  
 */
public final class USettings implements ISettings
{

	private String _firstDataSet;

	private String _secondDataSet;

	private String _resultDataSet;
    
	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.plugin.ISettings#parseSettings(java.lang.String)
	 */
	public void parseSettings(String stringSettings)
	{
        if (stringSettings.trim().equals("")) {
            _firstDataSet = _secondDataSet = _resultDataSet = null;
        } else {
            String separator = ";";
            StringTokenizer tokenizer = new StringTokenizer(stringSettings,
                    separator, true);
            _firstDataSet = tokenizer.nextToken();
            if (_firstDataSet.equals(separator)) {
                _firstDataSet = null;
            } else {
                // missing separator
                tokenizer.nextToken();
            }
            _secondDataSet = tokenizer.nextToken();
            if (_secondDataSet.equals(separator)) {
                _secondDataSet = null;
            } else {
                // missing separator
                tokenizer.nextToken();
            }
            // last token treated in the special way
            if (tokenizer.hasMoreTokens()) {
                _resultDataSet = tokenizer.nextToken();
            } else {
                _resultDataSet = null;
            }
        }
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.plugin.ISettings#settingsToString()
	 */
	public String settingsToString()
	{

		String settings = ((_firstDataSet == null) ? "" : _firstDataSet) + ";";
		settings += ((_secondDataSet == null) ? "" : _secondDataSet) + ";";
		settings += ((_resultDataSet == null) ? "" : _resultDataSet);
		return settings;
	}

	/**
	 * @return Returns the firstDataSet.
	 */
	public String getFirstDataSet()
	{
		return _firstDataSet;
	}

	/**
	 * @param firstDataSet The firstDataSet to set.
	 */
	public void setFirstDataSet(String firstDataSet)
	{
		_firstDataSet = firstDataSet;
	}

	/**
	 * @return Returns the resultDataSet.
	 */
	public String getResultDataSet()
	{
		return _resultDataSet;
	}

	/**
	 * @param resultDataSet The resultDataSet to set.
	 */
	public void setResultDataSet(String resultDataSet)
	{
		_resultDataSet = resultDataSet;
	}

	/**
	 * @return Returns the secondDataSet.
	 */
	public String getSecondDataSet()
	{
		return _secondDataSet;
	}

	/**
	 * @param secondDataSet The secondDataSet to set.
	 */
	public void setSecondDataSet(String secondDataSet)
	{
		_secondDataSet = secondDataSet;
	}
}