package pl.edu.agh.capitol.c45TreeCreator.components;

import java.awt.Component;

import salomon.platform.IDataEngine;

import pl.edu.agh.capitol.c45TreeCreator.panels.C45TreeCreatorResultPanel;
import pl.edu.agh.capitol.c45TreeCreator.util.C45TreeCreatorResult;

import salomon.plugin.IResult;
import salomon.plugin.IResultComponent;

/**
 * 
 * Komponent rezultatu (nieużywane)
 * 
 * @author Lukasz
 * 
 */
public class C45TreeCreatorResultComponent implements IResultComponent {

	public Component getComponent(IResult result, IDataEngine dataEngine) {
		return new C45TreeCreatorResultPanel(result); 
	}

	public IResult getDefaultResult() {
		return new C45TreeCreatorResult();
	}
}
