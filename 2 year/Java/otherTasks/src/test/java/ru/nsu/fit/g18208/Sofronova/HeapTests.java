package ru.nsu.fit.g18208.Sofronova;

import org.junit.Assert;
import org.junit.Test;
import ru.nsu.fit.g18208.Sofronova.HeapSort;

import java.util.Arrays;

public class HeapTests {
    @Test
    public void work(){
        int[] array={1,2,3,4,5,6,7};
        int[] array1={1,2,3,4,5,6,7};
        new HeapSort().sort(array);
        Assert.assertArrayEquals(array,array1);
    }
    @Test
    public void work2(){
        int[] array={7,6,5,4,3,2,1};
        int[] array1={1,2,3,4,5,6,7};
        new HeapSort().sort(array);
        Assert.assertArrayEquals(array,array1);
    }
    @Test
    public void work3(){
        int[] array={7,6,5,4,7,2,1};
        int[] array1={1,2,4,5,6,7,7};
        new HeapSort().sort(array);
        Assert.assertArrayEquals(array,array1);
    }
    @Test
    public void emptyShit(){
        int[] array={};
        int[] array1={};
        new HeapSort().sort(array);
        Assert.assertArrayEquals(array,array1);
    }
    @Test
    public void Null(){
        int[] array=null;
        int[] array1=null;
        new HeapSort().sort(array);
        Assert.assertArrayEquals(array,array1);
    }
}
