# Tests registering in Salomon
#


from net.grinder.script.Grinder import grinder
from net.grinder.script import Test
from salomon.engine import TestRegister


def registerTestCase():
	case = TestRegister()
	case.test()

testRegister = Test(1, "RegisterTest").wrap(registerTestCase)

class TestRunner:
    def __call__(self):
        testRegister()

