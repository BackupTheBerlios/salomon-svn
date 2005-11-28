package pl.edu.agh.capitol.c45TreeCreator.panels;

import javax.swing.JPanel;

import salomon.plugin.IResult;

public class C45TreeCreatorResultPanel extends JPanel {

	IResult result = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public C45TreeCreatorResultPanel(IResult result) {
		super();
		this.result = result;
	}
}
