
package pl.capitol.tree.plugins.dataloader.components;

import java.awt.Component;

import salomon.platform.IDataEngine;

import salomon.plugin.IResult;
import salomon.plugin.IResultComponent;

import pl.capitol.tree.plugins.dataloader.panels.TestAttrSetResultPanel;
import pl.capitol.tree.plugins.util.TestAttrSetResults;

public class TestAttrSetResultComponent implements IResultComponent
{

    public Component getComponent(IResult result, IDataEngine dataEngine)
    {
        return new TestAttrSetResultPanel(result);
    }

    public IResult getDefaultResult()
    {
        return new TestAttrSetResults();
    }

}