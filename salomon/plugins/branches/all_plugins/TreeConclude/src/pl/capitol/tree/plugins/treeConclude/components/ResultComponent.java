package pl.capitol.tree.plugins.treeConclude.components;

import java.awt.Component;

import salomon.platform.IDataEngine;

import pl.capitol.tree.plugins.treeConclude.panels.ResultPanel;
import pl.capitol.tree.plugins.treeConclude.util.Results;

import salomon.plugin.IResult;
import salomon.plugin.IResultComponent;

/**
 * Komponent zwraca obiekt odpowiedzialny za wizualizacje przeprowadzonych testów
 * 
 * @author Mateusz Nowakowski
 *
 */
public class ResultComponent implements IResultComponent {

	/* (non-Javadoc)
	 * @see salomon.plugin.IResultComponent#getComponent(salomon.plugin.IResult, salomon.platform.IDataEngine)
	 */
	public Component getComponent(IResult result, IDataEngine dataEngine) {
		return new ResultPanel(result); 
	}

	/* (non-Javadoc)
	 * @see salomon.plugin.IResultComponent#getDefaultResult()
	 */
	public IResult getDefaultResult() {
		return new Results();
	}

}
