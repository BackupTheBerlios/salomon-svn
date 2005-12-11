package pl.capitol.tree.plugins.treeConclude;

import pl.capitol.tree.plugins.treeConclude.components.ResultComponent;
import pl.capitol.tree.plugins.treeConclude.components.SettingComponent;
import pl.capitol.tree.plugins.treeConclude.util.Results;
import salomon.platform.IDataEngine;
import salomon.platform.IEnvironment;
import salomon.plugin.IPlugin;
import salomon.plugin.IResult;
import salomon.plugin.IResultComponent;
import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;

public class Plugin implements IPlugin {

	public IResult doJob(IDataEngine eng, IEnvironment env, ISettings settings) {
		// TODO 
		return new Results();
	}

	public ISettingComponent getSettingComponent() {
		return new SettingComponent();
	}

	public IResultComponent getResultComponent() {
		return new ResultComponent(); 
	}

}
