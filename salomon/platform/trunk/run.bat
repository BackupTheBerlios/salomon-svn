SET JAVA_HOME=jre
set PATH=%JAVA_HOME%\bin;%JAVA_HOME%\bin\client
java -Djava.rmi.server.RMIClassLoaderSpi=salomon.core.plugin.PluginRMIClassLoaderSpi -Djava.security.policy=all.policy -jar salomon.jar