/*
 * Copyright (C) 2005 KnowledgeSystem Team
 *
 * This file is part of KnowledgeSystem.
 *
 * KnowledgeSystem is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * KnowledgeSystem is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with KnowledgeSystem; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 */

package salomon.engine;

import java.rmi.RMISecurityManager;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import salomon.engine.platform.ManagerEngine;
import salomon.engine.remote.ICentralController;
import salomon.engine.remote.RemoteController;

/**
 * 
 */
public class TestRegister
{
    public TestRegister()
    {
        //		PropertyConfigurator.configure("log.conf");
    }

    /**
     * test method
     * @throws Exception
     */
    public void test() throws Exception
    {
        String server = Config.getString("SERVER_HOST");

        String port = Config.getString("SERVER_PORT");
        int serverPort = Integer.parseInt(port);
        System.setSecurityManager(new RMISecurityManager());
        ManagerEngine managerEngine = new ManagerEngine();
        RemoteController remoteController = new RemoteController(managerEngine,
                server);
        Registry registry = LocateRegistry.getRegistry(server, serverPort);
        ICentralController centralController = (ICentralController) registry.lookup("CentralController");
        centralController.register(remoteController);
        centralController.unregister(remoteController);
    }

    public static void main(String[] args)
    {
        try {
            new TestRegister().test();
        } catch (Exception e) {
            System.exit(0);
            e.printStackTrace();
        }
        System.exit(0);
    }
}
