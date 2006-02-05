package pl.capitol.tree.plugins.treeConclude.components;

import java.awt.Component;

import salomon.platform.IDataEngine;

import pl.capitol.tree.plugins.treeConclude.panels.ResultPanel;
import pl.capitol.tree.plugins.treeConclude.util.Results;

import salomon.plugin.IResult;
import salomon.plugin.IResultComponent;

public class ResultComponent implements IResultComponent {

	public Component getComponent(IResult result, IDataEngine dataEngine) {
		return new ResultPanel(result); 
	}

	public IResult getDefaultResult() {
		return new Results();
	}

}
