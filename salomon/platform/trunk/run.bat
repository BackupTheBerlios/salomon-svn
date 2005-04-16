REM Comment the line below if you have jre installed
SET JAVA_HOME=jre
SET PATH=%JAVA_HOME%\bin;%JAVA_HOME%\bin\client;%PATH%
java -Djava.rmi.server.RMIClassLoaderSpi=salomon.core.plugin.PluginRMIClassLoaderSpi -Djava.security.policy=all.policy -jar salomon.jar