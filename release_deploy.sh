#!/bin/bash
export JAVA_HOME=$(/usr/libexec/java_home -v 1.7)
mvn javadoc:jar deploy