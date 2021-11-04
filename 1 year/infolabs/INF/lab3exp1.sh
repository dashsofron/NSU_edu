#!/bin/bash
egrep -o '\bz\w*\b'  crusoe.txt
egrep -o '\b\w{16}*\b' crusoe.txt
egrep -o '\ba\w*c\b' crusoe.txt
egrep -o '\bab[^o]\w*\b'  crusoe.txt
