package pl.edu.agh.capitol.c45TreeCreator.components;

import java.awt.Component;

import pl.edu.agh.capitol.c45TreeCreator.panels.C45TreeCreatorResultPanel;
import pl.edu.agh.capitol.c45TreeCreator.util.C45TreeCreatorResult;

import salomon.plugin.IResult;
import salomon.plugin.IResultComponent;

public class C45TreeCreatorResultComponent implements IResultComponent {

	public Component getComponent(IResult result) {
		return new C45TreeCreatorResultPanel(result); 
	}

	public IResult getDefaultResult() {
		return new C45TreeCreatorResult();
	}
}
