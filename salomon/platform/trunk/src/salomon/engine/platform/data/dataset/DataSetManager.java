
package salomon.engine.platform.data.dataset;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;

import org.apache.log4j.Logger;

import salomon.engine.platform.DBManager;
import salomon.engine.platform.data.common.SQLSelect;
import salomon.engine.platform.exception.PlatformException;
import salomon.platform.data.dataset.IDataSet;
import salomon.platform.data.dataset.IDataSetManager;

/**
 * Class manages with datasets.
 */
public class DataSetManager implements IDataSetManager
{
	private static final Logger _logger = Logger.getLogger(DataSetManager.class);

	public IDataSet getDataSet(String name) throws PlatformException
	{
		DataSet dataSet = null;
		ResultSet resultSet = null;
		try {
			resultSet = getDataSetItems(name);
		} catch (Exception e) {
			_logger.fatal("", e);
			throw new PlatformException(e.getLocalizedMessage());
		}
		Collection<String> conditions = new HashSet<String>();
		Collection<String> tableNames = new HashSet<String>();

		// getting conditions
		try {
			while (resultSet.next()) {
				String tableName = resultSet.getString("table_name");
				String condition = resultSet.getString("condition");
				tableNames.add(tableName);
				conditions.add(condition);
			}
		} catch (SQLException e) {
			_logger.fatal("", e);
			throw new PlatformException(e.getLocalizedMessage());
		}
		dataSet = new DataSet();

		dataSet.setTableNames(tableNames);
		dataSet.setConditions(conditions);

		return dataSet;
	}

	private ResultSet getDataSetItems(String dataSetName) throws SQLException,
			ClassNotFoundException
	{
		SQLSelect select = new SQLSelect();
		select.addColumn("table_name");
		select.addColumn("condition");
		select.addTable(DATASETS + " d");
		select.addTable(DATASET_ITEMS + " di");
		select.addCondition("d.dataset_id = di.dataset_id");
		select.addCondition("dataset_name =", dataSetName);

		DBManager connector = DBManager.getInstance();
		return connector.select(select);
	}

	private final static String DATASET_ITEMS = "dataset_items";

	private final static String DATASETS = "datasets";

	/**
	 * @see salomon.platform.data.dataset.IDataSetManager#union(salomon.platform.data.dataset.IDataSet,
	 *      salomon.platform.data.dataset.IDataSet)
	 */
	public IDataSet union(IDataSet firstDataSet, IDataSet secondDataSet)
			throws PlatformException
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("No yet implemented");
	}

	/**
	 * @see salomon.platform.data.dataset.IDataSetManager#add(salomon.platform.data.dataset.IDataSet)
	 */
	public void add(IDataSet dataSet) throws PlatformException
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("No yet implemented");
	}

	/**
	 * @see salomon.platform.data.dataset.IDataSetManager#getDataSets()
	 */
	public IDataSet[] getDataSets() throws PlatformException
	{
		// TODO Auto-generated method stub
		return null;
	}
}