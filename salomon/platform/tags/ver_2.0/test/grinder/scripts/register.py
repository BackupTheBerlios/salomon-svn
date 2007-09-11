# Tests registering in Salomon
#


from net.grinder.script.Grinder import grinder
from net.grinder.script import Test
from java.rmi.registry import LocateRegistry
from salomon.engine.platform import ManagerEngine
from salomon.engine.remote import RemoteController
from salomon.engine.remote import ICentralController;
from java.lang import System
from java.rmi import RMISecurityManager;
# Client Properties
SERVER     = "localhost"
PORT       = 4321

# A shorter alias for the grinder.logger.output() method.
log = grinder.logger.output

# Create a Test with a test number and a description. The test will be
# automatically registered with The Grinder console if you are using
# it.
test1 = Test(1, "RegisterTest")

# Wrap the log() method with our Test and call the result logWrapper.
# Calls to logWrapper() will be recorded and forwarded on to the real
# log() method.
logWrapper = test1.wrap(log)

# A TestRunner instance is created for each thread. It can be used to
# store thread-specific data.
class TestRunner:
    
    # This method is called for every run.
    def __call__(self):
        System.setSecurityManager(RMISecurityManager());
        managerEngine = ManagerEngine()
        remoteController = RemoteController(managerEngine,SERVER)
        registry = LocateRegistry.getRegistry(SERVER,PORT)
        centralController = registry.lookup("CentralController")
        centralController.register(remoteController)
        if centralController is None:
            grinder.statistics.setSuccess(0)
#        grinder.sleep(1000) 

