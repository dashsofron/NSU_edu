package ru.nsu.fit.g18208.Sofronova;

import java.util.Arrays;

public class HeapSort {
    int getParent(int i){
        return (i+1)/2 -1;
    }
    int getLeft(int i){
        return (i+1)*2-1;
    }
    int getRight(int i){
        return (i+1)*2;
    }
    void sortThree(int[] array,int i,int length){
        int max=i;
        if(getLeft(i)<length)max=array[i]<array[getLeft(i)]?getLeft(i):i;
        if(getRight(i)<length)max=array[max]<array[getRight(i)]?getRight(i):max;
        if(max!=i)
        {
            int temp=array[i];
            array[i]=array[max];
            array[max]=temp;
            sortThree(array, max,length);
        }
    }
    void buildTree(int[] array){
        for(int i=array.length/2 -1;i>=0;i--)
            sortThree(array,i,array.length);
    }

    public void sort(int[] array){
        if(array==null)return;
        buildTree(array);
        for(int i=array.length-1;i>=0;i--) {
            int temp=array[0];
            array[0]=array[i];
            array[i]=temp;
            sortThree(array,0,i);
        }
    }
}
