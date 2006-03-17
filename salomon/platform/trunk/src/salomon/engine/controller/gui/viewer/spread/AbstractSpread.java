/*
 * Copyright (C) 2006 Salomon Team
 *
 * This file is part of Salomon.
 *
 * Salomon is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * Salomon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Salomon; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 */

package salomon.engine.controller.gui.viewer.spread;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import salomon.engine.database.DBManager;
import salomon.engine.database.queries.SQLSelect;

/**
 * @author nico
 */
public abstract class AbstractSpread extends JPanel
{
    /**
     * 
     * @uml.property name="_dbManager"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    protected DBManager _dbManager;

    protected JScrollPane _scrTablePane;

    protected SQLSelect _select;

    protected JTable _table;

    public AbstractSpread(DBManager dbManager)
    {
        _dbManager = dbManager;
        _select = new SQLSelect();

        this.setLayout(new BorderLayout());
        _scrTablePane = new JScrollPane();
        this.add(_scrTablePane, BorderLayout.CENTER);

        this.initColumns();
        this.setPreferredSize(new Dimension(640, 480));
    }

    protected void addColumn(String title, String columnName)
    {
        _select.addColumn(columnName + " " + title);
    }

    /**
     * In this method select object should be filled and apropriate column and
     * filters should be added.
     * 
     */
    public abstract void initColumns();

    @Override
    public final String toString()
    {
        return getName();
    }
}
