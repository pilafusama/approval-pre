#!/bin/sh
export JAVA_HOME=/usr/java/jdk1.8.0_101 && mvn clean package -Dmaven.test.skip=true -Pdev
