package pl.capitol.tree.plugins.test.panels;

import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Choice;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import org.apache.log4j.Logger;

import salomon.util.serialization.SimpleString;

import salomon.platform.IDataEngine;
import salomon.platform.data.tree.ITree;
import salomon.platform.exception.PlatformException;

import salomon.plugin.ISettings;
//NK
/**
 * @author mnowakowski
 *
 */
public class VisSettingsPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private ISettings settings;
	private IDataEngine dataEngine;
	private ITree[] trees = null;
	private Choice TreeChooser = null;
	private Button button = new Button("Usuñ drzewo");
	private Checkbox checkbox = new Checkbox("Dzia³aj samodzielnie", null, false);
	private static final Logger LOGGER = Logger.getLogger(VisSettingsPanel.class);
	/**
	 * This is the default constructor
	 */
	public VisSettingsPanel(ISettings settings, IDataEngine dataEngine) {
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
	
		TreeChooser = new Choice();
		try {
			trees = dataEngine.getTreeManager().getTrees();
			for(int i=0;i<trees.length;i++)
			{
				TreeChooser.add(trees[i].getName());
			}
		} catch (PlatformException e) {
			LOGGER.fatal("", e);
		}
		this.add(TreeChooser);
		button.addActionListener(new MyActionListener());
		this.add(button);
		this.add(checkbox);
	}
	
	public class MyActionListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
			if (TreeChooser.getSelectedIndex() != -1)
			{
				try {
					dataEngine.getTreeManager().removeTree(trees[TreeChooser.getSelectedIndex()].getId());
				} catch (PlatformException e1) {
					LOGGER.fatal("", e1);
				}
				TreeChooser.remove(TreeChooser.getSelectedIndex());
			}
        }
    }
	
	public ISettings getSettings() {
		
		String checkString = "F";
		int choice = -1;
		if (checkbox.getState()){
			checkString = "T";
		}
		if(TreeChooser.getSelectedIndex() != -1){
			choice = trees[TreeChooser.getSelectedIndex()].getId();	
		}
		settings.setField("checkbox", new SimpleString(checkString));
		settings.setField("choice",new SimpleString("" + choice));
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
	
	
 }  //  @jve:decl-index=0:visual-constraint="10,10"  