
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

	private Description _description = null;

	private static Logger _logger = Logger.getLogger(AveragePrice.class);

	/**
	 * This is the default constructor
	 */
	public AveragePrice()
	{
		initizalize();
		_description = new Description("AveragePrice",
				"Calculates average price of sold cars");
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
		return _description;
	}

	public IResult doJob(DataEngine engine, Environment environment,
			ISettings settings)
	{
		_logger.warn("###    doJob()   ###");
		DataSetManager dataSetManager = engine.getTestingDataSetManager();
		APResult apResult = new APResult();
		DataSet dataSet = null;
		try {
			dataSet = dataSetManager.getDataSetForName("test1");
		} catch (SQLException e1) {
			_logger.fatal("", e1);
			return apResult;
		} catch (ClassNotFoundException e1) {
			_logger.fatal("", e1);
			return apResult;
		}
		//
		// bulding query (getting all sold cars)
		//
		DBTableName[] tableNames = {new DBTableName("users"),
				new DBTableName("cars"), new DBTableName("car_sales")};
		DBColumnName[] columnNames = {
				new DBColumnName(tableNames[0], "nick", "user_nick"),
				new DBColumnName(tableNames[1], "brand", "marka"),
				new DBColumnName(tableNames[1], "name", "model"),
				new DBColumnName(tableNames[1], "price", "cena")};
		DBCondition[] conditions = {
				new DBCondition(new DBColumnName(tableNames[2], "user_id"),
						DBCondition.REL_EQ, new DBColumnName(tableNames[0],
								"user_id"), DBCondition.JOIN),
				new DBCondition(new DBColumnName(tableNames[1], "car_id"),
						DBCondition.REL_EQ, new DBColumnName(tableNames[2],
								"car_id"), DBCondition.JOIN)};
		_logger.info("settings = " + settings);
		//
		// getting additional conditions (from settings)
		//
		List additionalConditions = new LinkedList();
		String value = null;
		APSettings apSettings = (APSettings) settings;
		value = apSettings.getName();
		if (value != null && ! "".equals(value.trim())) {
			additionalConditions.add(new DBCondition(new DBColumnName(
					tableNames[0], "name"), DBCondition.REL_LIKE,
					value.toString(), DBCondition.TEXT));
		}
		value = apSettings.getSurname();
		if (value != null && ! "".equals(value.trim())) {
			additionalConditions.add(new DBCondition(new DBColumnName(
					tableNames[0], "surname"), DBCondition.REL_LIKE,
					value.toString(), DBCondition.TEXT));
		}
		value = apSettings.getNick();
		if (value != null && ! "".equals(value.trim())) {
			additionalConditions.add(new DBCondition(new DBColumnName(
					tableNames[0], "nick"), DBCondition.REL_LIKE,
					value.toString(), DBCondition.TEXT));
		}
		value = apSettings.getEmail();
		if (value != null && ! "".equals(value.trim())) {
			additionalConditions.add(new DBCondition(new DBColumnName(
					tableNames[0], "email"), DBCondition.REL_LIKE,
					value.toString(), DBCondition.TEXT));
		}
		//
		// concatenating with old conditons
		//
		DBCondition[] queryConditions = null;
		_logger.info("AveragePrice.doJob() additionalConditions: "
				+ additionalConditions);
		if (additionalConditions.size() > 0) {
			//adding conditions determinating data set
			queryConditions = new DBCondition[additionalConditions.size()
					+ conditions.length];
			additionalConditions.addAll(Arrays.asList(conditions));
			queryConditions = (DBCondition[]) additionalConditions.toArray(queryConditions);
		} else {
			queryConditions = conditions;
		}
		//
		// executing plugin
		//
		ResultSet resultSet = null;
		try {
			resultSet = dataSet.selectData(columnNames, tableNames,
					queryConditions);
		} catch (SQLException e) {
			_logger.fatal("", e);
			return apResult;
		} catch (ClassNotFoundException e) {
			_logger.fatal("", e);
			return apResult;
		}
		//
		// Getting result
		//
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
			return apResult;
		}
		//
		// returning result
		//
		double result = (size > 0) ? ((double) priceSum / (double) size) : 0;
		apResult.setAveragePrice(result);
		apResult.setSuccessful(true);
		return apResult;
	}

	public String toString()
	{
		return _description.getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.plugin.IGraphicPlugin#getSettingComponent()
	 */
	public ISettingComponent getSettingComponent()
	{
		return new APSettingComponent();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.plugin.IGraphicPlugin#getResultComponent()
	 */
	public IResultComponent getResultComponent()
	{
		return new APResultComponent();
	}
} //  @jve:visual-info decl-index=0 visual-constraint="10,10"
