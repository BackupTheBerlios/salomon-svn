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

package salomon.engine.controller.gui.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import edu.uci.ics.jung.graph.Edge;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Vertex;

import salomon.engine.task.ITask;

public final class GraphPlanner
{
	public static List<ITask> makePlan(Graph graph)
	{
		Set vertices = graph.getVertices();
		Set<Vertex> notPlannedVertices = new HashSet<Vertex>(vertices);
		List<ITask> result = new ArrayList<ITask>(vertices.size());
		while (notPlannedVertices.size() > 0) {
			Vertex nextVertex = getNextVertex(notPlannedVertices);
			if (nextVertex == null) {
				LOGGER.debug("Cannot create plan!");
				return null;
			}
			
			notPlannedVertices.remove(nextVertex);
			result.add(((TaskVertex) nextVertex).getTask());
		}
		
		return result;
	}
	
	private static Vertex getNextVertex(Set<Vertex> notPlannedVertices)
	{
		Vertex result = null;
		for (Vertex vertex : notPlannedVertices) {
			Set<Edge> inEdges = vertex.getInEdges();
			for (Edge edge : inEdges) {
				//////// first or second
				Vertex previousVertex = (Vertex) edge.getEndpoints().getFirst();
				if (notPlannedVertices.contains(previousVertex)) {
					continue;
				}
			}
			return vertex;
		}
		
		return result;
	}
	
	private static final Logger LOGGER = Logger.getLogger(GraphPlanner.class);
}
