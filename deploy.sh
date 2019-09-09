#!/usr/bin/env bash

# Edit CATALINA_HOME to point to your tomcat installation
export CATALINA_HOME=/usr/local/Cellar/tomcat/9.0.5/libexec

#sudo $CATALINA_HOME/bin/shutdown.sh
sudo cp build/libs/myRetail.war $CATALINA_HOME/webapps/
#sudo $CATALINA_HOME/bin/startup.sh