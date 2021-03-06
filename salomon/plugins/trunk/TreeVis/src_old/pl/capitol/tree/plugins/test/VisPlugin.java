
package pl.capitol.tree.plugins.test;

import org.apache.log4j.Logger;

import pl.capitol.tree.plugins.test.components.VisResultComponent;
import pl.capitol.tree.plugins.test.components.VisSettingComponent;
import pl.capitol.tree.plugins.util.TreeVisResults;

import salomon.util.serialization.SimpleString;

import salomon.platform.IDataEngine;
import salomon.platform.IEnvironment;
import salomon.platform.exception.PlatformException;

import salomon.plugin.IPlatformUtil;
import salomon.plugin.IPlugin;
import salomon.plugin.IResult;
import salomon.plugin.IResultComponent;
import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;

public class VisPlugin implements IPlugin
{

    public IResult doJob(IDataEngine eng, IEnvironment env, ISettings settings)
    {

        int treeId = -1;
        String[] fieldNames = settings.getFieldNames();
        String checkString = "F";
        int choice = -1;

        for (int i = 0; i < fieldNames.length; i++) {

            if (fieldNames[i].contains("checkbox")) {
                checkString = ((SimpleString) settings.getField(fieldNames[i])).getValue();
                settings.setField("checkbox", new SimpleString("F"));
            } else if (fieldNames[i].contains("choice")) {
                choice = Integer.parseInt(((SimpleString) settings.getField(fieldNames[i])).getValue());
            }
        }

        if (checkString == "F") {
            try {
                treeId = Integer.parseInt(((SimpleString) env.getVariable(
                        "tree_name").getValue()).getValue());
            } catch (NumberFormatException e1) {
                LOGGER.fatal("", e1);
            } catch (PlatformException e1) {
                LOGGER.fatal("", e1);
            }
        } else {
            treeId = choice;
        }

        String result = "";
        TreeVisResults treeResult = new TreeVisResults();
        enginik = eng;

        result = result + treeId;
        treeResult.parseResult(result);
        return treeResult;
    }

    public IResultComponent getResultComponent()
    {
        return new VisResultComponent();
    }

    public ISettingComponent getSettingComponent(IPlatformUtil platformUtil)
    {
        return new VisSettingComponent();
    }

    public static IDataEngine enginik;

    private static final Logger LOGGER = Logger.getLogger(VisPlugin.class);

}
