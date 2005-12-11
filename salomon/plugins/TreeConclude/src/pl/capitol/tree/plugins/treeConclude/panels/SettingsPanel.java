package pl.capitol.tree.plugins.treeConclude.panels;

import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import salomon.platform.IDataEngine;
import salomon.platform.data.tree.IDataSource;
import salomon.platform.data.tree.ITree;
import salomon.platform.exception.PlatformException;
import salomon.plugin.ISettings;
import salomon.util.serialization.SimpleInteger;

/**
 * @author mnowakowski
 *
 */
public class SettingsPanel extends JPanel {

	private static final Logger LOGGER = Logger.getLogger(SettingsPanel.class);
	private ISettings settings;
	private IDataEngine dataEngine;
	private ITree [] trees;
	
	private JCheckBox workAloneCheckBox = null;
	private JComboBox chooseTreeComboBox = null;
	private JPanel chosenTreePanel = null;
	private JLabel treeNameLabel = null;
	private JLabel treeName = null;
	private JLabel treeInfoLabel = null;
	private JLabel treeInfo = null;
	private JLabel dataSourceLabel = null;
	private JLabel dataSource = null;
	private JLabel dataSourceDecideLabel = null;
	private JLabel dataSourceDecide = null;
	private JLabel dataSourceDecisioningLabel = null;
	private JLabel dataSourceDecisioning = null;
	private JLabel dataSourceRowsLabel = null;
	private JLabel dataSourceRows = null;
	private JLabel titleLabel = null;
	private JLabel chooseTreeLabel = null;
	private JLabel errorLabel = null;
	/**
	 * This is the default constructor
	 */
	public SettingsPanel(ISettings settings, IDataEngine dataEngine) {
		super();
		this.dataEngine = dataEngine;
		this.settings = settings;
		initialize();
		
		// wczytanie danych
		try {
			this.trees = dataEngine.getTreeManager().getTrees();
			for (ITree tree : this.trees) this.chooseTreeComboBox.addItem(tree);
			chosenTreeComboBoxChanged();
			selectChooseTreeComboBoxItem();
		} catch (PlatformException e) {
			LOGGER.error("Error occured while initialising plugin: "+e.getMessage());
			e.printStackTrace();
			errorLabel.setText("Error: "+e.getMessage());
		}
		
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private  void initialize() {
		errorLabel = new JLabel();
		errorLabel.setBounds(new java.awt.Rectangle(215,58,282,20));
		errorLabel.setForeground(java.awt.Color.red);
		errorLabel.setPreferredSize(new java.awt.Dimension(282,20));
		errorLabel.setText("");
		chooseTreeLabel = new JLabel();
		chooseTreeLabel.setBounds(new java.awt.Rectangle(18,86,172,22));
		chooseTreeLabel.setText("Wybierz drzewo:");
		titleLabel = new JLabel();
		titleLabel.setBounds(new java.awt.Rectangle(144,16,290,30));
		titleLabel.setFont(new java.awt.Font("Comic Sans MS", java.awt.Font.BOLD | java.awt.Font.ITALIC, 18));
		titleLabel.setText("Plugin wnioskuj¹cy: Ustawienia");
		this.setLayout(null);
		this.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Drzewa decyzyjne", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
		this.setPreferredSize(new java.awt.Dimension(509,280));
		this.setSize(509, 280);
		this.setName("settingsPanel");
		this.add(getWorkAloneCheckBox(), null);
		this.add(getChosenTreePanel(), null);
		this.add(getChooseTreeComboBox(), null);
		this.add(titleLabel, null);
		this.add(chooseTreeLabel, null);
		this.add(errorLabel, null);
	}
	public ISettings getSettings() {

		settings.setField("isAlone",new SimpleInteger((workAloneCheckBox.isSelected() ? 1 : 0)));
		ITree selectedTree = (ITree)this.chooseTreeComboBox.getSelectedItem();
		if (selectedTree != null) settings.setField("treeId",new SimpleInteger(selectedTree.getId()));
		else settings.setField("treeId",new SimpleInteger(-1)); // -1 oznacza blad
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
	
	
	private void selectChooseTreeComboBoxItem(){
		ITree selectedTree = (ITree)this.chooseTreeComboBox.getSelectedItem();
		if (selectedTree != null) {
			treeName.setText(selectedTree.getName());
			treeInfo.setText(selectedTree.getInfo());
			IDataSource selDataSource = selectedTree.getDataSource();
			String decCols = "";
			for (String col : selDataSource.getDecioningColumns()) decCols +=col+",";
			if (!decCols.equals("")) decCols = decCols.substring(0,decCols.length()-1);
			dataSource.setText(selDataSource.getName());
			dataSourceDecide.setText(selDataSource.getDecisionedColumn());
			dataSourceDecisioning.setText(decCols);
			dataSourceRows.setText(selDataSource.getFirstRowIndex()+" - "+selDataSource.getLastRowIndex());
		}else {
			treeName.setText("");
			treeInfo.setText("");
			dataSource.setText("");
			dataSourceDecide.setText("");
			dataSourceDecisioning.setText("");
			dataSourceRows.setText("");			
		}
	}
	
	/**
	 * Pomocnicza metoda ktora disable/enable pola w zaleznosci od stanu checkbox`a
	 *
	 */
	private void chosenTreeComboBoxChanged(){
		if (!workAloneCheckBox.isSelected()) {
			chooseTreeComboBox.setEnabled(false);
			treeName.setEnabled(false);
			treeInfo.setEnabled(false);
			dataSource.setEnabled(false);
			dataSourceDecide.setEnabled(false);
			dataSourceDecisioning.setEnabled(false);
			dataSourceRows.setEnabled(false);
		} else {
			chooseTreeComboBox.setEnabled(true);
			treeName.setEnabled(true);
			treeInfo.setEnabled(true);
			dataSource.setEnabled(true);
			dataSourceDecide.setEnabled(true);
			dataSourceDecisioning.setEnabled(true);
			dataSourceRows.setEnabled(true);					
		}	
	}
	
	/**
	 * This method initializes workAloneCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getWorkAloneCheckBox() {
		if (workAloneCheckBox == null) {
			workAloneCheckBox = new JCheckBox();
			workAloneCheckBox.setBounds(new java.awt.Rectangle(17,55,148,21));
			workAloneCheckBox.setText("Pracuj samodzielnie");
			workAloneCheckBox.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					chosenTreeComboBoxChanged();
				}
			});
		}
		return workAloneCheckBox;
	}
	/**
	 * This method initializes chooseTreeComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getChooseTreeComboBox() {
		if (chooseTreeComboBox == null) {
			chooseTreeComboBox = new JComboBox();
			chooseTreeComboBox.setBounds(new java.awt.Rectangle(207,85,229,25));
			chooseTreeComboBox.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					selectChooseTreeComboBoxItem();
				}
			});
		}
		return chooseTreeComboBox;
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getChosenTreePanel() {
		if (chosenTreePanel == null) {
			dataSourceRows = new JLabel();
			dataSourceRows.setText("");
			dataSourceRows.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			dataSourceRowsLabel = new JLabel();
			dataSourceRowsLabel.setText("Wiersze:");
			dataSourceRowsLabel.setPreferredSize(new java.awt.Dimension(50,16));
			dataSourceDecisioning = new JLabel();
			dataSourceDecisioning.setText("");
			dataSourceDecisioning.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
			dataSourceDecisioningLabel = new JLabel();
			dataSourceDecisioningLabel.setText("Kolumny decyzyjne:");
			dataSourceDecisioningLabel.setPreferredSize(new java.awt.Dimension(50,16));
			dataSourceDecide = new JLabel();
			dataSourceDecide.setText("");
			dataSourceDecide.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
			dataSourceDecideLabel = new JLabel();
			dataSourceDecideLabel.setText("Kolumna decyzyjna:");
			dataSourceDecideLabel.setPreferredSize(new java.awt.Dimension(50,16));
			dataSource = new JLabel();
			dataSource.setText("");
			dataSource.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
			dataSourceLabel = new JLabel();
			dataSourceLabel.setText("Zbiór danych:");
			dataSourceLabel.setPreferredSize(new java.awt.Dimension(50,16));
			treeInfo = new JLabel();
			treeInfo.setText("");
			treeInfo.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
			treeInfoLabel = new JLabel();
			treeInfoLabel.setText("Opis drzewa:");
			treeInfoLabel.setPreferredSize(new java.awt.Dimension(50,16));
			treeName = new JLabel();
			treeName.setText("");
			treeName.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
			GridLayout gridLayout = new GridLayout();
			gridLayout.setRows(6);
			gridLayout.setHgap(0);
			gridLayout.setColumns(2);
			treeNameLabel = new JLabel();
			treeNameLabel.setText("Nazwa drzewa:");
			treeNameLabel.setPreferredSize(new java.awt.Dimension(50,16));
			chosenTreePanel = new JPanel();
			chosenTreePanel.setPreferredSize(new java.awt.Dimension(484,154));
			chosenTreePanel.setLayout(gridLayout);
			chosenTreePanel.setBounds(new java.awt.Rectangle(11,119,484,154));
			chosenTreePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Wybrane drzewo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), new java.awt.Color(51,51,51)));
			chosenTreePanel.add(treeNameLabel, null);
			chosenTreePanel.add(treeName, null);
			chosenTreePanel.add(treeInfoLabel, null);
			chosenTreePanel.add(treeInfo, null);
			chosenTreePanel.add(dataSourceLabel, null);
			chosenTreePanel.add(dataSource, null);
			chosenTreePanel.add(dataSourceDecideLabel, null);
			chosenTreePanel.add(dataSourceDecide, null);
			chosenTreePanel.add(dataSourceDecisioningLabel, null);
			chosenTreePanel.add(dataSourceDecisioning, null);
			chosenTreePanel.add(dataSourceRowsLabel, null);
			chosenTreePanel.add(dataSourceRows, null);
		}
		return chosenTreePanel;
	}
	
	
 }  //  @jve:decl-index=0:visual-constraint="35,18"