package pl.capitol.tree.plugins.treeConclude;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import pl.capitol.tree.plugins.treeConclude.components.ResultComponent;
import pl.capitol.tree.plugins.treeConclude.components.SettingComponent;
import pl.capitol.tree.plugins.treeConclude.util.Results;
import salomon.platform.IDataEngine;
import salomon.platform.IEnvironment;
import salomon.platform.serialization.IInteger;
import salomon.plugin.IPlugin;
import salomon.plugin.IResult;
import salomon.plugin.IResultComponent;
import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;

public class Plugin implements IPlugin {

	private static final Logger LOGGER = Logger.getLogger(Plugin.class);
	
	public IResult doJob(IDataEngine eng, IEnvironment env, ISettings settings) {
		
		Results results = null;
		try {
			int treeId;
			results = new Results();
			if (((IInteger)settings.getField("isAlone")).getValue() == 1) { //isAlone == true
				treeId = ((IInteger)settings.getField("treeId")).getValue();
			} else {
				IInteger vTreeId = (IInteger)env.getVariable("tree_name").getValue();
				if (vTreeId == null) {
				//	results.set
				}
			}
			
			
			
			results.setSuccess(true);
			results.setAllTests(10);
			results.setPositiveTests(9);
			results.setTreeName("tree (1)");
			
			Collection<Object []> list = new ArrayList<Object []>();
			list.add(new Object[]{"col1","col2"});
			list.add(new Object[]{"1","2"});
			list.add(new Object[]{"3","5"});
			results.setInvalidRows(list);
			
		} catch (Exception e){
			LOGGER.error("Error in TreeConcludePlugin : "+e.getMessage());
			e.printStackTrace();
			results = new Results();
			results.setErrorMessage("Wystapil nieoczekiwany blad: \n"+e.getMessage());
		}
		return results;
	}

	public ISettingComponent getSettingComponent() {
		return new SettingComponent();
	}

	public IResultComponent getResultComponent() {
		return new ResultComponent(); 
	}

}
