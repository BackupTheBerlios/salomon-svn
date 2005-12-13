package pl.capitol.tree.plugins.treeConclude.panels;

import javax.swing.JLabel;
import javax.swing.JPanel;

import pl.capitol.tree.plugins.treeConclude.util.Results;

import salomon.plugin.IResult;

/**
 * @author mnowakowski
 *
 */
public class ResultPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Results result;

	private JLabel errorLabel = null;

	private JPanel concludePanel = null;

	private JLabel treeNameLabel = null;

	private JLabel testedRowsAmountLabel = null;

	private JLabel positiveTestsAmountLabel = null;

	private JLabel percentTreeLabel = null;

	private JLabel treeName = null;

	private JLabel testedRowsAmount = null;

	private JLabel percentTree = null;

	private JLabel positiveTestsAmount = null;

	/**
	 * This is the default constructor
	 */
	public ResultPanel(IResult result) {
		super();
		this.result = (Results)result;
		initialize();
		
		if (!this.result.isSuccessful()) {
			this.concludePanel.setVisible(false);
			this.errorLabel.setVisible(true);
			this.errorLabel.setText(this.result.getErrorMessage());
		}
		
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private  void initialize() {
		errorLabel = new JLabel();
		errorLabel.setBounds(new java.awt.Rectangle(22,15,371,68));
		errorLabel.setPreferredSize(new java.awt.Dimension(371,68));
		errorLabel.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
		errorLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
		errorLabel.setForeground(java.awt.Color.red);
		errorLabel.setText("");
		errorLabel.setVisible(false);
		this.setLayout(null);
		this.setSize(435, 261);
		this.setPreferredSize(new java.awt.Dimension(435,261));
		this.add(errorLabel, null);
		this.add(getConcludePanel(), null);
	}
	public IResult getResult() {
		return result;
	}
	public void setResult(IResult result) {
		this.result = (Results)result;
	}
	/**
	 * This method initializes concludePanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getConcludePanel() {
		if (concludePanel == null) {
			positiveTestsAmount = new JLabel();
			positiveTestsAmount.setBounds(new java.awt.Rectangle(206,67,195,16));
			positiveTestsAmount.setPreferredSize(new java.awt.Dimension(195,16));
			positiveTestsAmount.setText("");
			percentTree = new JLabel();
			percentTree.setBounds(new java.awt.Rectangle(206,90,195,16));
			percentTree.setPreferredSize(new java.awt.Dimension(195,16));
			percentTree.setText("");
			testedRowsAmount = new JLabel();
			testedRowsAmount.setBounds(new java.awt.Rectangle(206,44,195,16));
			testedRowsAmount.setPreferredSize(new java.awt.Dimension(195,16));
			testedRowsAmount.setText("");
			treeName = new JLabel();
			treeName.setBounds(new java.awt.Rectangle(206,21,195,16));
			treeName.setPreferredSize(new java.awt.Dimension(195,16));
			treeName.setText("");
			percentTreeLabel = new JLabel();
			percentTreeLabel.setText("Zgodnoœæ drzewa:");
			percentTreeLabel.setLocation(new java.awt.Point(11,90));
			percentTreeLabel.setPreferredSize(new java.awt.Dimension(195,16));
			percentTreeLabel.setSize(new java.awt.Dimension(195,16));
			positiveTestsAmountLabel = new JLabel();
			positiveTestsAmountLabel.setText("Liczba testów pozytywnych:");
			positiveTestsAmountLabel.setLocation(new java.awt.Point(11,67));
			positiveTestsAmountLabel.setPreferredSize(new java.awt.Dimension(195,16));
			positiveTestsAmountLabel.setSize(new java.awt.Dimension(195,16));
			testedRowsAmountLabel = new JLabel();
			testedRowsAmountLabel.setBounds(new java.awt.Rectangle(11,44,195,16));
			testedRowsAmountLabel.setPreferredSize(new java.awt.Dimension(195,16));
			testedRowsAmountLabel.setText("Liczba przetestowanych wierszy:");
			treeNameLabel = new JLabel();
			treeNameLabel.setBounds(new java.awt.Rectangle(11,21,195,16));
			treeNameLabel.setPreferredSize(new java.awt.Dimension(195,16));
			treeNameLabel.setText("Nazwa drzewa:");
			concludePanel = new JPanel();
			concludePanel.setLayout(null);
			concludePanel.setBounds(new java.awt.Rectangle(1,2,429,256));
			concludePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Drzewa decyzyjne", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
			concludePanel.setPreferredSize(new java.awt.Dimension(429,256));
			concludePanel.add(treeNameLabel, null);
			concludePanel.add(testedRowsAmountLabel, null);
			concludePanel.add(positiveTestsAmountLabel, null);
			concludePanel.add(percentTreeLabel, null);
			concludePanel.add(treeName, null);
			concludePanel.add(testedRowsAmount, null);
			concludePanel.add(percentTree, null);
			concludePanel.add(positiveTestsAmount, null);
		}
		return concludePanel;
	}
	
	
  }  //  @jve:decl-index=0:visual-constraint="10,10"
