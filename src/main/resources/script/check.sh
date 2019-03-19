#!/usr/bin/env bash

export LC_ALL=en_US.UTF-8

APP_LOG_HOME=$(cd "$(dirname "$0")/../logs";pwd)
echo "${APP_LOG_HOME}"
PID_FILE="${APP_LOG_HOME}/application.pid"
echo "${PID_FILE}"
PID=""
for i in `seq 0 10`
do
    if [ -f ${PID_FILE} ]
    then
        PID=`cat ${PID_FILE}`
        break
    fi
    sleep 1
done

if [[ "${PID}" == "" ]]
then
    echo "${PID_FILE} not exists"
    exit -1
fi

CHECK_MSG=`ps -p ${PID}`
CHECK_RESULT=$?
echo "current pid : [${PID}] process info:"
echo ${CHECK_RESULT}

if [[ "${CHECK_RESULT}" == "0" ]]
then
    exit 0
else
    echo ${CHECK_MSG}
    exit 1
fi