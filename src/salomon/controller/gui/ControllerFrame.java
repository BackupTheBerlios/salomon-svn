
package salomon.controller.gui;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import org.apache.log4j.Logger;

import salomon.core.Config;
import salomon.core.Messages;

/*
 * Created on 2004-05-03
 *  
 */
/**
 * @author nico
 *  
 */
public final class ControllerFrame extends JFrame
{

	private String _resourcesDir = null;

	public ControllerFrame()
	{
		super();
		_resourcesDir = Config.getString("RESOURCES_DIR");
		initialize();
	}

	/**
	 * Called after project save/load/creation
	 * 
	 * @param project
	 */
	public void refreshGui()
	{
		super.validate();
	}

	public void setMainPanel(JPanel panel)
	{
		//TODO: getContentPane().remove();
		getContentPane().add(panel, BorderLayout.CENTER);
	}

	public void setJToolBar(JToolBar toolBar)
	{
		getContentPane().add(toolBar, BorderLayout.NORTH);
	}

	public void showErrorMessage(String msg)
	{
		JOptionPane.showMessageDialog(this, msg,
				Messages.getString("TIT_ERROR"), JOptionPane.ERROR_MESSAGE);
	}

	public void showInfoMessage(String msg)
	{
		JOptionPane.showMessageDialog(this, msg,
				Messages.getString("TIT_INFO"), JOptionPane.INFORMATION_MESSAGE);
	}

	public boolean showQuestionMessage(String title, String msg)
	{
		int retVal = JOptionPane.showConfirmDialog(this, msg, title,
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		return (retVal == JOptionPane.YES_OPTION);
	}

	public boolean showWarningMessage(String msg)
	{
		int retVal = JOptionPane.showConfirmDialog(this, msg,
				Messages.getString("TIT_WARN"), JOptionPane.YES_NO_OPTION,
				JOptionPane.WARNING_MESSAGE);
		return (retVal == JOptionPane.YES_OPTION);
	}

	private void exit()
	{
		//TODO: change it
		_logger.fatal("###  Application exited  ###"); //$NON-NLS-1$
		System.exit(0);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize()
	{
		getContentPane().setLayout(new BorderLayout());
		this.setSize(600, 500);
		//
		// center frame
		//
		Point location = new Point();
		location.x = (Toolkit.getDefaultToolkit().getScreenSize().width - getWidth()) / 2;
		location.y = (Toolkit.getDefaultToolkit().getScreenSize().height - getHeight()) / 2;
		this.setLocation(location);
		this.setTitle(Messages.getString("APP_NAME")); //$NON-NLS-1$		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e)
			{
				exit();
			}
		});
		_logger.debug("initialize end");
	}

	private static Logger _logger = Logger.getLogger(ControllerFrame.class);
}