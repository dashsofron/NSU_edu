#!/bin/bash
cat text.txt | awk '{s+=$1} {t+=$2} END {print s,t}'
