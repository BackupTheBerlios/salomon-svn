package pl.capitol.tree.plugins.treeConclude;

import java.util.ArrayList;
import java.util.Collection;

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
		Results results = new Results();
		results.setSuccess(true);
		results.setAllTests(10);
		results.setPositiveTests(9);
		results.setTreeName("tree (1)");
		
		Collection<Object []> list = new ArrayList<Object []>();
		list.add(new Object[]{"col1","col2"});
		list.add(new Object[]{"1","2"});
		list.add(new Object[]{"3","5"});
		results.setInvalidRows(list);
		return results;
	}

	public ISettingComponent getSettingComponent() {
		return new SettingComponent();
	}

	public IResultComponent getResultComponent() {
		return new ResultComponent(); 
	}

}
