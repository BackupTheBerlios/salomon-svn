package pl.capitol.tree.plugins.treeConclude.panels;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

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
	
	private IResult result;

	private JLabel resultLabel = null;

	private JTextArea resultText = null;
	/**
	 * This is the default constructor
	 */
	public ResultPanel(IResult result) {
		super();
		this.result = result;
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private  void initialize() {
		resultLabel = new JLabel();
		resultLabel.setText("Result");
		resultLabel.setBounds(new java.awt.Rectangle(18,8,71,22));
		this.setLayout(null);
		this.setSize(435, 176);
		this.setPreferredSize(new java.awt.Dimension(435,176));
		this.add(resultLabel, null);
		this.add(getResultText(), null);
	}
	public IResult getResult() {
		return result;
	}
	public void setResult(IResult result) {
		this.result = result;
	}
	/**
	 * This method initializes resultText	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getResultText() {
		if (resultText == null) {
			resultText = new JTextArea();
			resultText.setBounds(new java.awt.Rectangle(6,35,416,138));
			resultText.setEditable(false);
		}
		return resultText;
	}
	
	
  }
