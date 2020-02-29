#!/bin/bash
grep -o '\b[A,B,E,K,M,H,O,P,C,T,Y,X]{1}[0-9]{3}[A,B,E,K,M,H,O,P,C,T,Y,X]{2}[0-9]{2,3}\b' patterns.txt
