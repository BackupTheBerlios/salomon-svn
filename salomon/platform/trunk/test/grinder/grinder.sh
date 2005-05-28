#!/bin/bash
LIB_HOME=../../ext
CP=../../ext/grinder/grinder.jar
CP=$CP\;../../bin/
for i in `ls $LIB_HOME | grep jar`
do
CP=$CP\;$LIB_HOME/$i
done
echo $CP
java -cp $CP -Djava.security.policy=../../all.policy net.grinder.Grinder
#java -cp $CP salomon.engine.platform.ManagerEngine
