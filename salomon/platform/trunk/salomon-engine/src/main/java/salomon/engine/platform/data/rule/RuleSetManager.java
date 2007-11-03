/*
 * Copyright (C) 2004 Salomon Team
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

package salomon.engine.platform.data.rule;

import salomon.engine.database.DBManager;
import salomon.engine.solution.ShortSolutionInfo;
import salomon.platform.data.rule.IRuleSet;
import salomon.platform.data.rule.IRuleSetManager;
import salomon.platform.exception.PlatformException;

/**
 *  Not used yet.
 */
public final class RuleSetManager implements IRuleSetManager
{
    public RuleSetManager(DBManager dbManager, ShortSolutionInfo solutionInfo)
    {
    }

    /**
     * @see salomon.platform.data.rule.IRuleSetManager#addRuleSet()
     */
    public void addRuleSet() throws PlatformException
    {
        throw new UnsupportedOperationException(
                "Method addRuleSet() not implemented yet!");
    }

    /**
     * @see salomon.platform.data.rule.IRuleSetManager#createRuleSet()
     */
    public IRuleSet createRuleSet() throws PlatformException
    {
        throw new UnsupportedOperationException(
                "Method createRuleSet() not implemented yet!");
    }

    /**
     * @see salomon.platform.data.rule.IRuleSetManager#getRuleSets()
     */
    public IRuleSet[] getRuleSets() throws PlatformException
    {
        throw new UnsupportedOperationException(
                "Method getRuleSets() not implemented yet!");
    }

} // end RuleSetManager
