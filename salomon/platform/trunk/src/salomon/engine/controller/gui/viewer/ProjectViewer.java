/*
 * Copyright (C) 2005 Salomon Team
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

package salomon.engine.controller.gui.viewer;

import java.sql.SQLException;

import javax.swing.JPanel;

import salomon.engine.controller.gui.viewer.spread.AbstractChildForm;
import salomon.engine.controller.gui.viewer.spread.AbstractManagedSearchSpread;
import salomon.engine.database.DBManager;
import salomon.engine.database.queries.SQLDelete;
import salomon.engine.project.ProjectInfo;
import salomon.platform.exception.DBException;

/**
 * 
 */
public final class ProjectViewer extends AbstractManagedSearchSpread
{
    public ProjectViewer(DBManager dbManager)
    {
        super(dbManager);
        setButtonVisable(Buttons.ADD, false);
        setButtonVisable(Buttons.EDIT, false);
        _childForm = new ProjectChildForm(this, dbManager);
    }

    @Override
    public void initColumns()
    {
        _select.addTable(ProjectInfo.VIEW_NAME);
    }

    @Override
    public void initFilters()
    {
        addFilteredField("Id", "project_id");
        addFilteredField("Name", "project_name");
        addFilteredField("Info", "project_info");
    }

    private class ProjectChildForm extends AbstractChildForm
    {

        public ProjectChildForm(AbstractManagedSearchSpread spread,
                DBManager manager)
        {
            super(spread, manager);
        }

        @Override
        public JPanel getPanel()
        {
            throw new UnsupportedOperationException(
                    "Method ProjectChildForm.getPanel() not implemented yet!");
        }

        @Override
        public void initData()
        {
            throw new UnsupportedOperationException(
                    "Method ProjectChildForm.initData() not implemented yet!");
        }

        @Override
        public void loadData(int key) throws DBException
        {
            throw new UnsupportedOperationException(
                    "Method ProjectChildForm.loadData() not implemented yet!");
        }

        @Override
        public void refreshControls()
        {
            throw new UnsupportedOperationException(
                    "Method ProjectChildForm.refreshControls() not implemented yet!");
        }

        @Override
        public void removeData(int key) throws DBException
        {
            SQLDelete delete = new SQLDelete(ProjectInfo.TABLE_NAME);
            delete.addCondition(ProjectInfo.PRIMARY_KEY + "=", key);
            try {
                _manager.delete(delete);
                _manager.commit();
            } catch (SQLException e) {
                throw new DBException(e);
            }
        }

        @Override
        public void save() throws DBException
        {
            throw new UnsupportedOperationException(
                    "Method ProjectChildForm.save() not implemented yet!");
        }

        @Override
        public void save(int key) throws DBException
        {
            throw new UnsupportedOperationException(
                    "Method ProjectChildForm.save() not implemented yet!");
        }

        @Override
        protected void refreshForm(int key)
        {
            throw new UnsupportedOperationException(
                    "Method ProjectChildForm.refreshForm() not implemented yet!");
        }

    }
}
