
package pl.edu.agh.icsr.salomon.plugin.datasetunion;

import java.sql.SQLException;

import salomon.core.data.DataEngine;
import salomon.core.data.Environment;
import salomon.core.data.dataset.DataSet;
import salomon.core.data.dataset.DataSetManager;
import salomon.plugin.Description;
import salomon.plugin.IPlugin;
import salomon.plugin.IResult;
import salomon.plugin.IResultComponent;
import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;

/**
 * 
 * TODO: add comment.
 * 
 * @author krzychu
 *  
 */
public class UPlugin implements IPlugin
{

	private Description _description;

	private DataEngine _dataEngine;

	/**
	 *  
	 */
	public UPlugin()
	{
		_description = new Description("UPlugin", DESCRPTION);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.plugin.IPlugin#destroy()
	 */
	public void destroy()
	{
		// not used
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.plugin.IDataPlugin#doJob(salomon.core.data.DataEngine,
	 *      salomon.core.data.Environment, salomon.plugin.ISettings)
	 */
	public IResult doJob(DataEngine engine, Environment environment,
			ISettings settings)
	{
		DataSetManager dataSetManager = engine.getDataSetManager();
		USettings uSettings = (USettings) settings;
        UResult uResult = new UResult();
		DataSet firstDataSet;
		try {
			firstDataSet = dataSetManager.getDataSetForName(uSettings.getFirstDataSet());
			DataSet secondDataSet = dataSetManager.getDataSetForName(uSettings.getSecondDataSet());
			DataSet result = dataSetManager.union(firstDataSet, secondDataSet);
			result.setName(uSettings.getResultDataSet());
			dataSetManager.add(result);
			uResult.setSuccessfull(true);    
		} catch (Exception e) {
            uResult.setSuccessfull(false);
		}
		return uResult;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.plugin.IGraphicPlugin#getResultComponent()
	 */
	public IResultComponent getResultComponent()
	{
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.plugin.IGraphicPlugin#getSettingComponent()
	 */
	public ISettingComponent getSettingComponent()
	{
		return new USettingComponent();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.plugin.IPlugin#initizalize(salomon.core.data.DataEngine)
	 */
	public void initizalize()
	{
	}

	private static final String DESCRPTION = "Creates dataset which is a union of two other datasets";

}