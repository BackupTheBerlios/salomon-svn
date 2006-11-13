package pl.edu.agh.capitol.veniTreeCreator.panels;

import javax.swing.JPanel;

import salomon.plugin.IResult;

public class VeniTreeCreatorResultPanel extends JPanel {

	IResult result = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public VeniTreeCreatorResultPanel(IResult result) {
		super();
		this.result = result;
	}
}
