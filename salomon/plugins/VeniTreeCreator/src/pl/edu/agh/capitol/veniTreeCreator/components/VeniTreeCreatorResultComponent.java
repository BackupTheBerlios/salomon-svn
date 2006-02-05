package pl.edu.agh.capitol.veniTreeCreator.components;

import java.awt.Component;

import salomon.platform.IDataEngine;

import pl.edu.agh.capitol.veniTreeCreator.panels.VeniTreeCreatorResultPanel;
import pl.edu.agh.capitol.veniTreeCreator.util.VeniTreeCreatorResult;

import salomon.plugin.IResult;
import salomon.plugin.IResultComponent;

public class VeniTreeCreatorResultComponent implements IResultComponent {

	public Component getComponent(IResult result, IDataEngine dataEngine) {
		return new VeniTreeCreatorResultPanel(result); 
	}

	public IResult getDefaultResult() {
		return new VeniTreeCreatorResult();
	}
}
