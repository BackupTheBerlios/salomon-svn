
package pl.edu.agh.icsr.salomon.plugin.datasetunion;

import salomon.platform.IDataEngine;
import salomon.platform.IEnvironment;
import salomon.platform.data.dataset.IDataSet;
import salomon.platform.data.dataset.IDataSetManager;
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
	 * @see salomon.plugin.IDataPlugin#doJob(salomon.engine.platform.data.DataEngine,
	 *      salomon.engine.platform.data.Environment, salomon.plugin.ISettings)
	 */
	public IResult doJob(IDataEngine engine, IEnvironment environment,
			ISettings settings)
	{
		IDataSetManager dataSetManager = engine.getDataSetManager();
		USettings uSettings = (USettings) settings;
        UResult uResult = new UResult();
		IDataSet firstDataSet;
		try {
			firstDataSet = dataSetManager.getDataSet(uSettings.getFirstDataSet());
			IDataSet secondDataSet = dataSetManager.getDataSet(uSettings.getSecondDataSet());
			IDataSet result = dataSetManager.union(firstDataSet, secondDataSet);
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
	 * @see salomon.plugin.IPlugin#initizalize(salomon.engine.platform.data.DataEngine)
	 */
	public void initizalize()
	{
	}

	private static final String DESCRPTION = "Creates dataset which is a union of two other datasets";

}