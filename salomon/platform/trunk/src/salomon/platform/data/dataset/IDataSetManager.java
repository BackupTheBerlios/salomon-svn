package salomon.platform.data.dataset;

import salomon.engine.platform.exception.PlatformException;


/**
 * 
 * TODO: add comment.
 * @author krzychu
 * 
 */
public interface IDataSetManager
{

	/**
	 * TODO: add comment.
	 * @return
	 */
	IDataSet[] getDataSets() throws PlatformException;

	/**
	 * TODO: add comment.
	 * @param name
	 * @return
	 */
	IDataSet getDataSet(String name) throws PlatformException;

	/**
	 * TODO: add comment.
	 * @param firstDataSet
	 * @param secondDataSet
	 * @return
	 */
	IDataSet union(IDataSet firstDataSet, IDataSet secondDataSet) throws PlatformException;

	/**
	 * TODO: add comment.
	 * @param dataSet
	 */
	void add(IDataSet dataSet) throws PlatformException;
}