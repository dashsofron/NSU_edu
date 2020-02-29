#!/bin/bash
grep egrep -o '\b[0-9]*\b' patterns.txt
grep egrep -o '\b[A-Za-z]*\b' patterns.txt

