
package salomon.controller.gui;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

import salomon.core.Messages;

/**
 * 
 * TODO: add comment.
 * 
 * @author nico
 *  
 */
public class Utils
{
	private JComponent _parent;

	private void setParentImpl(JComponent parent)
	{
		_parent = parent;
	}

	private void showErrorMessageImpl(String msg)
	{
		JOptionPane.showMessageDialog(_parent, msg,
				Messages.getString("TIT_ERROR"), JOptionPane.ERROR_MESSAGE);
	}

	private void showInfoMessageImpl(String msg)
	{
		JOptionPane.showMessageDialog(_parent, msg,
				Messages.getString("TIT_INFO"), JOptionPane.INFORMATION_MESSAGE);
	}

	private boolean showQuestionMessageImpl(String title, String msg)
	{
		int retVal = JOptionPane.showConfirmDialog(_parent, msg, title,
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		return (retVal == JOptionPane.YES_OPTION);
	}

	private boolean showWarningMessageImpl(String msg)
	{
		int retVal = JOptionPane.showConfirmDialog(_parent, msg,
				Messages.getString("TIT_WARN"), JOptionPane.YES_NO_OPTION,
				JOptionPane.WARNING_MESSAGE);
		return (retVal == JOptionPane.YES_OPTION);
	}

	public static void setParent(JComponent parent)
	{
		getInstance().setParentImpl(parent);
	}

	public static void showErrorMessage(String msg)
	{
		getInstance().showErrorMessageImpl(msg);
	}

	public static void showInfoMessage(String msg)
	{
		getInstance().showInfoMessageImpl(msg);
	}

	public static boolean showQuestionMessage(String title, String msg)
	{
		return getInstance().showQuestionMessageImpl(title, msg);
	}

	public static boolean showWarningMessage(String msg)
	{
		return getInstance().showWarningMessageImpl(msg);
	}

	private static Utils getInstance()
	{
		if (_instance == null) {
			_instance = new Utils();
		}
		return _instance;
	}

	private static Utils _instance;
}