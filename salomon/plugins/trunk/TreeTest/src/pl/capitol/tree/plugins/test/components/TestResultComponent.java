package pl.capitol.tree.plugins.test.components;

import java.awt.Component;

import salomon.platform.IDataEngine;

import pl.capitol.tree.plugins.test.panels.TestResultPanel;
import pl.capitol.tree.plugins.util.TreeResults;

import salomon.plugin.IResult;
import salomon.plugin.IResultComponent;

public class TestResultComponent implements IResultComponent {

	public Component getComponent(IResult result, IDataEngine dataEngine) {
		return new TestResultPanel(result); 
	}

	public IResult getDefaultResult() {
		return new TreeResults();
	}

}
