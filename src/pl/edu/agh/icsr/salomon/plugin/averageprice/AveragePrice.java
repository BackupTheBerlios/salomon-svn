package pl.edu.agh.icsr.salomon.plugin.averageprice;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import salomon.core.data.DataEngine;
import salomon.core.data.Environment;
import salomon.core.data.common.DBColumnName;
import salomon.core.data.common.DBCondition;
import salomon.core.data.common.DBTableName;
import salomon.core.data.dataset.DataSet;
import salomon.core.data.dataset.DataSetManager;
import salomon.plugin.Description;
import salomon.plugin.IPlugin;
import salomon.plugin.IResult;
import salomon.plugin.IResultComponent;
import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;

/*
 * Created on 2004-05-03
 * 
 */
/**
 * @author nico
 *  
 */
public class AveragePrice implements IPlugin
{
	private JPanel _contentPane = null;

	private JPanel _settingsPanel = null;

	private JPanel _resultPanel = null;

	private JPanel _pnlAttributes = null;

		private JTextField _txtResult = null;

	private static Logger _logger = Logger.getLogger(AveragePrice.class);



	/**
	 * This is the default constructor
	 */
	public AveragePrice()
	{
		super();
		initizalize();
	}

	/**
	 * This method initializes _settingsPanel
	 * 
	 * @return JPanel
	 */
	public JPanel getSettingsPanel()
	{
		if (_settingsPanel == null) {
		}
		return _settingsPanel;
	}

	

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.plugin.IPlugin#initizalize()
	 */
	public void initizalize()
	{
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.plugin.IPlugin#destroy()
	 */
	public void destroy()
	{
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.plugin.IPlugin#getDescription()
	 */
	public Description getDescription()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public IResult doJob(DataEngine engine, Environment environment,
			ISettings settings)
	{
		_logger.warn("###    doJob()   ###");
		DataSetManager dataSetManager = engine.getTestingDataSetManager();
		DataSet dataSet = null;
		try {
			dataSet = dataSetManager.getDataSetForName("test1");
		} catch (SQLException e1) {
			_logger.fatal("", e1);
			return null;
		} catch (ClassNotFoundException e1) {
			_logger.fatal("", e1);
			return null;
		}
		// bulding query (getting all sold cars)
		DBTableName[] tableNames = {new DBTableName("users"),
				new DBTableName("cars"), new DBTableName("car_sales")};
		DBColumnName[] columnNames = {
				new DBColumnName(tableNames[0], "nick", "user_nick"),
				new DBColumnName(tableNames[1], "brand", "marka"),
				new DBColumnName(tableNames[1], "name", "model"),
				new DBColumnName(tableNames[1], "price", "cena")};
		DBColumnName carPrice = new DBColumnName(tableNames[1], "price");
		DBCondition[] conditions = {
				new DBCondition(new DBColumnName(tableNames[2], "user_id"),
						DBCondition.REL_EQ, new DBColumnName(tableNames[0],
								"user_id"), DBCondition.JOIN),
				new DBCondition(new DBColumnName(tableNames[1], "car_id"),
						DBCondition.REL_EQ, new DBColumnName(tableNames[2],
								"car_id"), DBCondition.JOIN)};
		//		TODO: wykorzystac je :-)
		_logger.info("settings = " + settings);
		// getting additional conditions (from settings)
		List additionalConditions = new LinkedList();
		Object value = null;
		APSettings apSettings = (APSettings) settings;
		value = apSettings.name;
		if (value != null) {
			additionalConditions.add(new DBCondition(new DBColumnName(
					tableNames[0], "name"), DBCondition.REL_LIKE, value
					.toString(), DBCondition.TEXT));
		}
		value = apSettings.surname;
		if (value != null) {
			additionalConditions.add(new DBCondition(new DBColumnName(
					tableNames[0], "surname"), DBCondition.REL_LIKE, value
					.toString(), DBCondition.TEXT));
		}
		value = apSettings.nick;
		if (value != null) {
			additionalConditions.add(new DBCondition(new DBColumnName(
					tableNames[0], "nick"), DBCondition.REL_LIKE, value
					.toString(), DBCondition.TEXT));
		}
		value = apSettings.email;
		if (value != null) {
			additionalConditions.add(new DBCondition(new DBColumnName(
					tableNames[0], "email"), DBCondition.REL_LIKE, value
					.toString(), DBCondition.TEXT));
		}
		// concatenating with old conditons
		DBCondition[] queryConditions = null;
		_logger.info("AveragePrice.doJob() additionalConditions: "
				+ additionalConditions);
		if (additionalConditions.size() > 0) {
			//adding conditions determinating data set
			queryConditions = new DBCondition[additionalConditions.size()
					+ conditions.length];
			additionalConditions.addAll(Arrays.asList(conditions));
			queryConditions = (DBCondition[]) additionalConditions
					.toArray(queryConditions);
		} else {
			queryConditions = conditions;
		}
		// executing plugin
		ResultSet resultSet = null;
		try {
			resultSet = dataSet.selectData(columnNames, tableNames,
					queryConditions);
		} catch (SQLException e) {
			_logger.fatal("", e);
			return null;
		} catch (ClassNotFoundException e) {
			_logger.fatal("", e);
			return null;
		}
		//!!!!!!!! if we want to see all result set using method
		// printResultSet
		// we have to execute query again to get result
		/*
		 * try { SQLConsole.printResultSet(resultSet); } catch (SQLException e) {
		 * _logger.fatal("", e);
		 */
		// getting result:
		// getting data
		LinkedList rows = new LinkedList();
		int size = 0;
		int priceSum = 0;
		try {
			while (resultSet.next()) {
				//			using alias specified in columnNames!
				int price = resultSet.getInt("cena");
				String buffer = resultSet.getString("user_nick") + " | "
						+ resultSet.getString("marka");
				buffer += " |" + resultSet.getString("model") + " | "
						+ resultSet.getString("cena");
				_logger.warn(buffer);
				priceSum += price;
				size++;
			}
		} catch (SQLException e) {
			_logger.fatal("", e);
			return null;
		}
		// returning result
		double result = (size > 0) ? ((double) priceSum / (double) size) : 0;
		APResult apResult = new APResult();
		apResult.averagePrice = result;
		return apResult;
	}
	
	public String toString()
	{
		// TODO Auto-generated method stub
		return "Average price";
	}

	/* (non-Javadoc)
	 * @see salomon.plugin.IGraphicPlugin#getSettingComponent()
	 */
	public ISettingComponent getSettingComponent()
	{
		return new APSettingComponent();
	}

	/* (non-Javadoc)
	 * @see salomon.plugin.IGraphicPlugin#getResultComponent()
	 */
	public IResultComponent getResultComponent()
	{
		return new APResultComponent();
	}
} //  @jve:visual-info decl-index=0 visual-constraint="10,10"
