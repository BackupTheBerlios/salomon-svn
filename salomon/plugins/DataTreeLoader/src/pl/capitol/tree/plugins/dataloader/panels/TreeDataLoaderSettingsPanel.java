package pl.capitol.tree.plugins.dataloader.panels;


import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import salomon.platform.IDataEngine;
import salomon.platform.data.IColumn;
import salomon.platform.data.ITable;
import salomon.plugin.ISettings;
import salomon.util.serialization.SimpleString;


/**
 * @author mnowakowski
 *
 */
public class TreeDataLoaderSettingsPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final SimpleString[] SimpleString = null;
	private ISettings settings;
	private IDataEngine dataEngine;
	private JList jListTable = null;
	private JList jListDecisioned = null;
	private JList jListDecisioning = null;
	private JLabel jLabelTable = null;
	private JLabel jLabelDecisioned = null;
	private JLabel jLabelDecisioning = null;

	DefaultListModel dListTable = new DefaultListModel();  //  @jve:decl-index=0:visual-constraint="627,38"
	DefaultListModel dListDecisioned = new DefaultListModel();  //  @jve:decl-index=0:visual-constraint="710,58"
	DefaultListModel dListDecisioning = new DefaultListModel();
	
	private ITable [] listTable;
	private IColumn [] listDecisioned;
	private IColumn [] listDecisioning;

	
	/**
	 * This is the default constructor
	 */
	public TreeDataLoaderSettingsPanel(ISettings settings, IDataEngine dataEngine) {
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
		jLabelDecisioning = new JLabel();
		jLabelDecisioning.setBounds(new java.awt.Rectangle(306,4,120,23));
		jLabelDecisioning.setText("Decisioned columns");
		jLabelDecisioned = new JLabel();
		jLabelDecisioned.setBounds(new java.awt.Rectangle(151,2,122,26));
		jLabelDecisioned.setText("Decisioned column");
		jLabelTable = new JLabel();
		jLabelTable.setBounds(new java.awt.Rectangle(20,4,100,24));
		jLabelTable.setText("Table");
		this.setLayout(null);
		this.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		this.setPreferredSize(new java.awt.Dimension(487,188));
		this.setSize(451, 189);
		this.setName("settingsPanel");

		this.add(getJListTable(), null);
		this.add(getJListDecisioned(), null);
		this.add(getJListDecisioning(), null);
		this.add(jLabelTable, null);
		this.add(jLabelDecisioned, null);
		this.add(jLabelDecisioning, null);
		
		listTable = dataEngine.getMetaData().getTables();
		for (int i=0; i<listTable.length; i++)
		{
			dListTable.addElement(listTable[i].toString());
		}
		
	}
	public ISettings getSettings() {
		//TODO umiescic z pol settingsy dla doJob()
		settings.setField("table", new SimpleString(jListTable.getSelectedValue().toString()));
		settings.setField("decisionedColumn", new SimpleString(jListDecisioned.getSelectedValue().toString()));
		
		String decisioningColumn = new String("decisioningColumn"); 
		Object [] selectedValues = jListDecisioning.getSelectedValues();
		for (int i=0; i<selectedValues.length; i++)
		{
			settings.setField(decisioningColumn+i, new SimpleString(selectedValues[i].toString()));
		}
		
		/*
		 * nie dzialalo:/
		Object [] selectedValues = jListDecisioning.getSelectedValues();
		
		String [] selectedValuesS = new String[selectedValues.length];
		for (int i=0; i<selectedValues.length; i++)
		{
			selectedValuesS[i] = (String)selectedValues[i];
			System.out.println(selectedValuesS[i]);
		}
		
		
		SimpleString [] selectedValuesSS = new SimpleString[selectedValuesS.length];
		for (int i=0; i<selectedValuesS.length; i++)
		{
			selectedValuesSS[i] = new SimpleString(selectedValuesS[i]);
			System.out.println(selectedValuesSS[i].getValue());
		}
		
		System.out.println("aaaaa"); 
		
		SimpleArray selectedValuesSA = new SimpleArray(selectedValuesSS);
		
		System.out.println("bbbbb");
		
		SimpleString [] decisioningColumnsSS = (SimpleString[]) selectedValuesSA.getValue();
		
		System.out.println("cccccc");
		
		for (int i=0; i<decisioningColumnsSS.length; i++)
		{
			System.out.println(decisioningColumnsSS[i].getValue());
		}
		
		settings.setField("decisioningColumns", selectedValuesSA);
		 * 
		 */
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
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	/**
	 * This method initializes jListTable	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getJListTable() {
		if (jListTable == null) {
			jListTable = new JList(dListTable);
			jListTable.setBounds(new java.awt.Rectangle(7,27,133,156));
			jListTable.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					listDecisioned = listTable[jListTable.getSelectedIndex()].getColumns();
					dListDecisioned.clear();
					for (int i=0; i<listDecisioned.length; i++)
					{
						dListDecisioned.addElement(listDecisioned[i].getName());
					}
					
					listDecisioning = listTable[jListTable.getSelectedIndex()].getColumns();
					dListDecisioning.clear();
					for (int i=0; i<listDecisioning.length; i++)
					{
						dListDecisioning.addElement(listDecisioning[i].getName());
					}

				}
			});
		}
		return jListTable;
	}
	/**
	 * This method initializes jListDecisioned	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getJListDecisioned() {
		if (jListDecisioned == null) {
			jListDecisioned = new JList(dListDecisioned);
			jListDecisioned.setBounds(new java.awt.Rectangle(148,26,142,157));
			jListDecisioned.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					System.out.println("mouseClicked()"); // TODO Auto-generated Event stub mouseClicked()
				}
			});
		}
		return jListDecisioned;
	}
	/**
	 * This method initializes jListDecisioning	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getJListDecisioning() {
		if (jListDecisioning == null) {
			jListDecisioning = new JList(dListDecisioning);
			jListDecisioning.setBounds(new java.awt.Rectangle(296,25,150,157));
			jListDecisioning.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					System.out.println("mouseClicked()"); // TODO Auto-generated Event stub mouseClicked()
				}
			});
		}
		return jListDecisioning;
	}
	
	
	
 }  //  @jve:decl-index=0:visual-constraint="21,17"