/*
 * Created on 2004-05-21
 *
 */

package salomon.plugin;

/**
 * Represents result of plugin execution.
 *
 */
public interface IResult
{
	/**
	 * Method parses result from their string representation.
	 * 
	 * @param result
	 */
	void parseResult(String result);

	/**
	 * 
	 * @return String representation of result
	 */
	String resultToString();

	/**
	 * 
	 * @return true if plugin successfully finished, false otherwise
	 */
	boolean isSuccessful();
}