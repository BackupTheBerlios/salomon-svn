package ks.plugins;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import ks.data.DBCondition;
import ks.data.DBColumnName;
import ks.data.DBTableName;
import ks.data.DataEngine;
import ks.data.DataSet;
import ks.data.DataSetManager;
import ks.data.Environment;
import org.apache.log4j.Logger;

/*
 * Created on 2004-05-03
 * 
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
/**
 * @author nico
 *  
 */
public class AveragePrice extends  AbstractPlugin
{
	private JPanel jContentPane = null;

	private JPanel _settingsPanel = null;

	private JPanel _resultPanel = null;

	private JPanel _pnlAttributes = null;

	private JTextField _txtName = null;

	private JTextField _txtSurname = null;

	private JTextField _txtEmail = null;

	private JTextField _txtResult = null;

	private Result _result = null;

	private static Logger _logger = Logger.getLogger(AveragePrice.class);

	private JTextField _txtNick = null;

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
			_settingsPanel = new JPanel();
			_settingsPanel.setLayout(new BorderLayout());
			_settingsPanel.add(new JLabel("Age Counter Settings"),
					BorderLayout.NORTH);
			_settingsPanel.add(getPnlAttributes(), BorderLayout.CENTER);
			_settingsPanel.setSize(80, 66);
		}
		return _settingsPanel;
	}

	/**
	 * This method initializes _resultPanel
	 * 
	 * @return JPanel
	 */
	public JPanel getResultPanel()
	{
		if (_resultPanel == null) {
			_resultPanel = new JPanel();
			_resultPanel.setLayout(new BorderLayout());
			_resultPanel.add(new JLabel("Age Counter result"),
					BorderLayout.NORTH);
			_txtResult = getTxtResult(_result.get("result").toString());
			_resultPanel.add(_txtResult, BorderLayout.CENTER);
			_resultPanel.setSize(71, 69);
		} else {
			_txtResult.setText(_result.get("result").toString());
		}
		return _resultPanel;
	}

	/**
	 * This method initializes _pnlAttributes
	 * 
	 * @return JPanel
	 */
	private JPanel getPnlAttributes()
	{
		if (_pnlAttributes == null) {
			_pnlAttributes = new JPanel();
			GridLayout layGridLayout1 = new GridLayout();
			layGridLayout1.setColumns(2);
			layGridLayout1.setRows(0);
			_pnlAttributes.setLayout(layGridLayout1);
			_pnlAttributes.add(new JLabel("Name"));
			_pnlAttributes.add(getTxtName());
			_pnlAttributes.add(new JLabel("Surname"));
			_pnlAttributes.add(getTxtSurname());
			_pnlAttributes.add(new JLabel("Nick"));
			_pnlAttributes.add(getTxtNick());
			_pnlAttributes.add(new JLabel("Email"));
			_pnlAttributes.add(getTxtEmail());
		}
		return _pnlAttributes;
	}

	/**
	 * This method initializes _txtName
	 * 
	 * @return JTextField
	 */
	private JTextField getTxtName()
	{
		if (_txtName == null) {
			_txtName = new JTextField();
		}
		return _txtName;
	}

	/**
	 * This method initializes _txtName
	 * 
	 * @return JTextField
	 */
	private JTextField getTxtNick()
	{
		if (_txtNick == null) {
			_txtNick = new JTextField();
		}
		return _txtNick;
	}

	/**
	 * This method initializes _txtSurname
	 * 
	 * @return JTextField
	 */
	private JTextField getTxtSurname()
	{
		if (_txtSurname == null) {
			_txtSurname = new JTextField();
		}
		return _txtSurname;
	}

	/**
	 * This method initializes _txtEmail
	 * 
	 * @return JTextField
	 */
	private JTextField getTxtEmail()
	{
		if (_txtEmail == null) {
			_txtEmail = new JTextField();
		}
		return _txtEmail;
	}

	/**
	 * This method initializes _txtResult
	 * 
	 * @return JTextField
	 */
	private JTextField getTxtResult(String text)
	{
		if (_txtResult == null) {
			_txtResult = new JTextField(text);
		}
		return _txtResult;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ks.plugins.AbstractPlugin#initizalize()
	 */
	public void initizalize()
	{
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ks.plugins.AbstractPlugin#destroy()
	 */
	public void destroy()
	{
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ks.plugins.AbstractPlugin#getDescription()
	 */
	public Description getDescription()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ks.plugins.IDataPlugin#doJob(ks.data.DataEngine,
	 *      ks.data.Environment, ks.plugins.Settings)
	 */
	public void doJob(DataEngine engine, Environment environment,
			Settings settings)
	{
		_logger.warn("###    doJob()   ###");
		DataSetManager dataSetManager = engine.getTestingDataSetManager();
		DataSet dataSet = null;
		try {
			dataSet = dataSetManager.getDataSetForName("test1");
		} catch (SQLException e1) {
			_logger.fatal("", e1);
			return; //TODO: place error in result?
		} catch (ClassNotFoundException e1) {
			_logger.fatal("", e1);
			return;
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
		value = settings.get("name");
		if (value != null) {
			additionalConditions.add(new DBCondition(new DBColumnName(
					tableNames[0], "name"), DBCondition.REL_LIKE, value
					.toString(), DBCondition.TEXT));
		}
		value = settings.get("surname");
		if (value != null) {
			additionalConditions.add(new DBCondition(new DBColumnName(
					tableNames[0], "surname"), DBCondition.REL_LIKE, value
					.toString(), DBCondition.TEXT));
		}
		value = settings.get("nick");
		if (value != null) {
			additionalConditions.add(new DBCondition(new DBColumnName(
					tableNames[0], "nick"), DBCondition.REL_LIKE, value
					.toString(), DBCondition.TEXT));
		}
		value = settings.get("email");
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
			return;
		} catch (ClassNotFoundException e) {
			_logger.fatal("", e);
			return;
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
			return;
		}
		// putting result in _result
		double result = (size > 0) ? ((double) priceSum / (double) size) : 0;
		_result = new Result();
		_result.put("result", new Double(result));
	}

	/**
	 * Returns settings basing on filled panel TODO:
	 * 
	 * @return
	 */
	public Settings getSettings()
	{
		Settings settings = new Settings();
		String text = null;
		text = _txtName.getText().trim();
		if (!text.equals("")) {
			settings.put("name", text);
		}
		text = _txtSurname.getText().trim();
		if (!text.equals("")) {
			settings.put("surname", text);
		}
		text = _txtNick.getText().trim();
		if (!text.equals("")) {
			settings.put("nick", text);
		}
		text = _txtEmail.getText().trim();
		if (!text.equals("")) {
			settings.put("email", text);
		}
		return settings;
	}

	public Result getResult()
	{
		return _result;
	}
	
	public String toString()
	{
		// TODO Auto-generated method stub
		return "Average price";
	}
} //  @jve:visual-info decl-index=0 visual-constraint="10,10"
