/*
 * Copyright (C) 2007 salomon-engine Team
 *
 * This file is part of salomon-engine.
 *
 * salomon-engine is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * salomon-engine is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with salomon-engine; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 */

package salomon.engine.agent;

import salomon.agent.IAgentDecisionComponentInfo;

/**
 * 
 */
public interface IAgentDecisionComponentInfoDAO
{
    void save(IAgentDecisionComponentInfo component);

    void remove(IAgentDecisionComponentInfo component);

    IAgentDecisionComponentInfo[] getAgentDecisionComponentInfos();

    IAgentDecisionComponentInfo getAgentDecisionComponentInfo(Long id);

    IAgentDecisionComponentInfo getAgentDecisionComponentInfo(String componentName);
}
