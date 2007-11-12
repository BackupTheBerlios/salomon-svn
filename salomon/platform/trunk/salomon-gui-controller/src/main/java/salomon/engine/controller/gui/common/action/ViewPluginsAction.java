
package salomon.engine.controller.gui.common.action;

import java.awt.event.ActionEvent;

import salomon.engine.controller.gui.plugin.PluginManagerGUI;

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
