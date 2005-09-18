package pl.edu.agh.capitol.veniTreeCreator;

import org.apache.log4j.Logger;

import pl.edu.agh.capitol.veniTreeCreator.components.VeniTreeCreatorResultComponent;
import pl.edu.agh.capitol.veniTreeCreator.components.VeniTreeCreatorSettingsComponent;
import pl.edu.agh.capitol.veniTreeCreator.logic.TreeConstructionTask;
import pl.edu.agh.capitol.veniTreeCreator.util.VeniTreeCreatorResult;
import salomon.platform.IDataEngine;
import salomon.platform.IEnvironment;
import salomon.platform.IVariable;

import salomon.platform.data.tree.IDataSource;
import salomon.platform.data.tree.ITree;
import salomon.platform.exception.PlatformException;
import salomon.platform.serialization.IObject;
import salomon.plugin.IPlugin;
import salomon.plugin.IResult;
import salomon.plugin.IResultComponent;
import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;
import salomon.util.serialization.SimpleString;

public class VeniTreeCreatorPlugin implements IPlugin {

	private static final Logger LOGGER = Logger
			.getLogger(VeniTreeCreatorPlugin.class);

	public IResult doJob(IDataEngine eng, IEnvironment env, ISettings settings) {
		IDataSource dane = null;
		ITree result = null;
		try {
			dane = getIDataSourceFromEnv(eng, env);
			/** ************ Calculations *************** */
			result = performCalculations(dane, eng);
			/** ************ Calculations done ********** */
			IVariable iv = env.createEmpty("tree_name");
			IObject io = new SimpleString("" + result.getId());
			iv.setValue(io);
			env.add(iv);
		} catch (PlatformException e) {
			LOGGER.error("PlatformException occured ", e);

			return getDefaultErrorResult("PlatformException occured "
					+ e.getLocalizedMessage());
		}

		return new VeniTreeCreatorResult();
	}

	public ISettingComponent getSettingComponent() {
		return new VeniTreeCreatorSettingsComponent();
	}

	public IResultComponent getResultComponent() {
		return new VeniTreeCreatorResultComponent();
	}

	ITree performCalculations(IDataSource ds, IDataEngine eng) throws PlatformException {
		TreeConstructionTask tc = new TreeConstructionTask();
		tc.loadFromDataSource(ds, eng.getTreeManager().getTreeDataSourceData(ds));
		tc.createTree();
		
		return tc.returnResult(eng.getTreeManager(), ds);
	}

	IResult getDefaultErrorResult(String result) {
		VeniTreeCreatorResult res = new VeniTreeCreatorResult();
		res.setSuccessful(false);
		res.setResult(result);
		return res;
	}

	IDataSource getIDataSourceFromEnv(IDataEngine eng, IEnvironment env)
			throws PlatformException {
		// TODO do uzgodnienia jak to bedzie
		try {
			/*int id = Integer.parseInt(env.getVariable("ds_name").getValue()
					.toString());
			return eng.getTreeManager().getTreeDataSource(id);*/
			//zaslepka :/
			return eng.getTreeManager().getTreeDataSource(18);
		} catch (NullPointerException e) {
			LOGGER.error("Variable ds_name not set in env", e);
			throw new PlatformException("Variable ds_name not set in env");
		} catch (PlatformException e) {
			LOGGER.error("PlatformException occured ", e);
			throw new PlatformException("PlatformException occured ");
		} catch (Throwable e) {
			LOGGER.error("Exception occured ", e);
			throw new PlatformException("Exception occured ");
		}
	}
}
