#!/bin/sh
nohup java -Dserver.port=8090  -jar build/libs/quickat-0.0.1-SNAPSHOT.war > /var/www/log/quickat/quickat.log 2>&1 &
tail -100f /var/www/log/quickat/quickat.log
