
package salomon.engine.controller.gui.action;

import java.awt.event.ActionEvent;

import salomon.engine.controller.gui.PluginManagerGUI;

public class ViewPluginsAction extends AbstractPluginAction
{
	protected ViewPluginsAction(PluginManagerGUI pluginMangerGUI)
	{
		super(pluginMangerGUI);
	}

	public void actionPerformed(ActionEvent arg0)
	{
		_pluginMangerGUI.viewPlugins();
	}

}
