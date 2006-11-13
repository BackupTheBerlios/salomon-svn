package pl.capitol.tree.plugins.test;

import pl.capitol.tree.plugins.test.components.TestResultComponent;
import pl.capitol.tree.plugins.test.components.TestSettingComponent;
import pl.capitol.tree.plugins.util.TreeResults;
import salomon.platform.IDataEngine;
import salomon.platform.IEnvironment;

import salomon.plugin.IPlatformUtil;
import salomon.plugin.IPlugin;
import salomon.plugin.IResult;
import salomon.plugin.IResultComponent;
import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;

public class TestPlugin implements IPlugin {

	public IResult doJob(IDataEngine eng, IEnvironment env, ISettings settings) {
		// TODO 
		return new TreeResults();
	}

	public ISettingComponent getSettingComponent(IPlatformUtil platformUtil) {
		return new TestSettingComponent();
	}

	public IResultComponent getResultComponent() {
		return new TestResultComponent(); 
	}

}
