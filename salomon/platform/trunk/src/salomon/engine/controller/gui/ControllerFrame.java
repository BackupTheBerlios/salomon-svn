
package salomon.controller.gui;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JToolBar;

import org.apache.log4j.Logger;

import salomon.Starter;
import salomon.platform.Config;
import salomon.platform.Messages;

/**
 * Class represents main program frame. All components are put on its content
 * pane.
 *  
 */
public final class ControllerFrame extends JFrame
{

	private ControllerPanel _controllerPanel;

	private String _resourcesDir;

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
		_logger.debug("refreshing GUI");
		_controllerPanel.refresh();
	}

	public void setControllerPanel(ControllerPanel controllerPanel)
	{
		_controllerPanel = controllerPanel;
	}

	public void setJToolBar(JToolBar toolBar)
	{
		getContentPane().add(toolBar, BorderLayout.NORTH);
	}

	public void setMainPanel(JComponent panel)
	{
		//TODO: getContentPane().remove();
		getContentPane().add(panel, BorderLayout.CENTER);
	}

	private void exit()
	{
		Starter.exit();
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