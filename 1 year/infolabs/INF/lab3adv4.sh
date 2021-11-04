#!/bin/bash
if [[ $3 == "" ]]
then
case $ARITHMETIC_OP in
add)  let a=$1
let b=$2
let vivod=a+b
echo $vivod
;;
sub)  let a=$1
let b=$2
let vivod=a-b
echo $vivod
;;
div)  if [[$2!=0]] 
then
 let a=$1
let b=$2
let vivod=a/b
echo $vivod
else
echo "bad input"
fi
;;
mul)  let a=$1
let b=$2
let vivod=a*b
echo $vivod
;;
esac
else
case $3 in
add)  let a=$1
let b=$2
let vivod=a+b
echo $vivod
;;
sub)  let a=$1
let b=$2
let vivod=a-b
echo $vivod
;;
div)  if [[$2!=0]] 
then
 let a=$1
let b=$2
let vivod=a/b
echo $vivod
else
echo "bad input"
fi
;;
mul)  let a=$1
let b=$2
let vivod=a*b
echo $vivod
;;
esac
fi
