#!/usr/bin/env bash
FROM=/home/configs
CONFIGS=/papsource/configs/src/main/resources
DBCONFIGS=/papsource/DB/src/main/resources
ln -s $FROM/db.properties $DBCONFIGS