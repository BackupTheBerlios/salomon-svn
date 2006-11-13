package pl.capitol.tree.plugins.dataloader.components;

import java.awt.Component;

import salomon.platform.IDataEngine;

import salomon.plugin.IResult;
import salomon.plugin.IResultComponent;

import pl.capitol.tree.plugins.dataloader.panels.TreeDataLoaderResultPanel;
import pl.capitol.tree.plugins.util.TreeDataLoaderResults;

public class TreeDataLoaderResultComponent implements IResultComponent {

	public Component getComponent(IResult result, IDataEngine dataEngine) {
		return new TreeDataLoaderResultPanel(result); 
	}

	public IResult getDefaultResult() {
		return new TreeDataLoaderResults();
	}

}