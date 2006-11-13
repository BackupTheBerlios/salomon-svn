package pl.capitol.tree.plugins.test.components;

import java.awt.Component;

import salomon.platform.IDataEngine;

import pl.capitol.tree.plugins.test.panels.VisResultPanel;
import pl.capitol.tree.plugins.util.TreeVisResults;
import salomon.plugin.IResult;
import salomon.plugin.IResultComponent;

public class VisResultComponent implements IResultComponent {

	public Component getComponent(IResult result, IDataEngine dataEngine) {
		return new VisResultPanel(result); 
	}

	public IResult getDefaultResult() {
		return new TreeVisResults();
	}

}
