package pl.capitol.tree.plugins.test.panels;

import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import salomon.platform.IDataEngine;
import salomon.platform.data.tree.ITreeManager;
import salomon.plugin.ISettings;

/**
 * @author mnowakowski
 *
 */
public class TestSettingsPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private ISettings settings;
	private IDataEngine dataEngine;
	
	private JTextField tableNameText = null;
	private JLabel tableNameLabel = null;
	private JTextArea testText = null;
	private JLabel testLabel = null;
	private JLabel decisionedLabel = null;
	private JTextField decisionedText = null;
	private JTextField decisioning1Text = null;
	private JTextField decisioning2Text = null;
	private JTextField decisioning3Text = null;
	private JLabel decisioning1Label = null;
	private JLabel decisioning2Label = null;
	private JLabel decisioning3Label = null;
	private JButton testButton = null;
	/**
	 * This is the default constructor
	 */
	public TestSettingsPanel(ISettings settings, IDataEngine dataEngine) {
		super();
		this.dataEngine = dataEngine;
		this.settings = settings;
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private  void initialize() {
		tableNameLabel = new JLabel();
		decisionedLabel = new JLabel();
		decisioning3Label = new JLabel();
		decisioning2Label = new JLabel();
		decisioning1Label = new JLabel();
		testLabel = new JLabel();
		this.setLayout(null);
		this.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		this.setPreferredSize(new java.awt.Dimension(487,188));
		this.setSize(487, 188);
		tableNameLabel.setBounds(7, 22, 65, 22);
		tableNameLabel.setText("Table:");
		this.setName("settingsPanel");
		testLabel.setBounds(247, 24, 72, 16);
		testLabel.setText("Test text:");
		decisionedLabel.setBounds(7, 49, 77, 27);
		decisionedLabel.setText("Decisioned:");
		decisioning1Label.setBounds(7, 77, 81, 21);
		decisioning1Label.setText("Decisioning1:");
		decisioning2Label.setBounds(7, 100, 79, 23);
		decisioning2Label.setText("Decisioning2:");
		decisioning3Label.setBounds(7, 129, 80, 22);
		decisioning3Label.setText("Decisioning3:");
		this.add(getTableNameText(), null);
		this.add(getTestText(), null);
		this.add(testLabel, null);
		this.add(decisionedLabel, null);
		this.add(getDecisionedText(), null);
		this.add(getDecisioning1Text(), null);
		this.add(getDecisioning2Text(), null);
		this.add(getDecisioning3Text(), null);
		this.add(decisioning1Label, null);
		this.add(decisioning2Label, null);
		this.add(decisioning3Label, null);
		this.add(tableNameLabel, null);
		this.add(getTestButton(), null);
	}
	/**
	 * This method initializes decisionedText	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getTableNameText() {
		if (tableNameText == null) {
			tableNameText = new JTextField();
			tableNameText.setBounds(90, 23, 152, 20);;
		}
		return tableNameText;
	}
	/**
	 * This method initializes jTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */    
	private JTextArea getTestText() {
		if (testText == null) {
			testText = new JTextArea();
			testText.setBounds(246, 46, 230, 101);
		}
		return testText;
	}
	/**
	 * This method initializes decisionedText	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getDecisionedText() {
		if (decisionedText == null) {
			decisionedText = new JTextField();
			decisionedText.setBounds(90, 52, 152, 20);
		}
		return decisionedText;
	}
	/**
	 * This method initializes decisioning1Text	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getDecisioning1Text() {
		if (decisioning1Text == null) {
			decisioning1Text = new JTextField();
			decisioning1Text.setBounds(90, 78, 152, 20);
		}
		return decisioning1Text;
	}
	/**
	 * This method initializes decisioning2Text	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getDecisioning2Text() {
		if (decisioning2Text == null) {
			decisioning2Text = new JTextField();
			decisioning2Text.setBounds(91, 101, 151, 20);
		}
		return decisioning2Text;
	}
	/**
	 * This method initializes decisioning3Text	
	 * 	
	 * @return javax.swing.JTextField	
	 */    
	private JTextField getDecisioning3Text() {
		if (decisioning3Text == null) {
			decisioning3Text = new JTextField();
			decisioning3Text.setBounds(95, 128, 147, 20);
		}
		return decisioning3Text;
	}
	public ISettings getSettings() {
		//TODO umiescic z pol settingsy dla doJob()
		return settings;
	}
	public void setSettings(ISettings settings) {
		this.settings = settings;
	}
	public IDataEngine getDataEngine() {
		return dataEngine;
	}
	public void setDataEngine(IDataEngine dataEngine) {
		this.dataEngine = dataEngine;
	}
	/**
	 * This method initializes testButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getTestButton() {
		if (testButton == null) {
			testButton = new JButton();
			testButton.setBounds(new java.awt.Rectangle(9,159,180,19));
			testButton.setText("Test table and columns");

			testButton.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					String tableName = tableNameText.getText();
					LinkedList<String> columns = new LinkedList<String>();
					if ((decisionedText.getText() != null)&&(!decisionedText.getText().equals(""))) columns.add(decisionedText.getText());
					if ((decisioning1Text.getText() != null)&&(!decisioning1Text.getText().equals(""))) columns.add(decisioning1Text.getText());
					if ((decisioning2Text.getText() != null)&&(!decisioning2Text.getText().equals(""))) columns.add(decisioning2Text.getText());
					if ((decisioning3Text.getText() != null)&&(!decisioning3Text.getText().equals(""))) columns.add(decisioning3Text.getText());
					
					try{
						ITreeManager manager = dataEngine.getTreeManager();
						boolean b = manager.checkTableAndColumns(tableName,columns);
						if (b) {
							getTestText().setText("Table and columns existed");
						} else {
							getTestText().setText("Table and columns not  existed");
						}
					}catch(Throwable e1) {
						getTestText().setText(e1.getMessage());
					}
				}
			});
		}
		return testButton;
	}
	
	
 }  //  @jve:decl-index=0:visual-constraint="10,10"  