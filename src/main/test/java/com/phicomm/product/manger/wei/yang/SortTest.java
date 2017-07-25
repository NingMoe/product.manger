package com.phicomm.product.manger.wei.yang;

import org.junit.Test;

import java.util.Arrays;

/**
 * Created by wei.yang on 2017/7/21.
 */
public class SortTest {

    @Test
    public void test() {
        int[] a = {8, 62, 9, 25, 35, 44, 15, 68, 2, 7, 6, 7, 99, 65, 4};
        sort(a, 0, a.length - 1);
        int[] normalArr = new int[20];
        for (int i = 0; i < 20; i++) {
            normalArr[i] = (int) (Math.random() * 1000 + i);
        }
        System.out.println(Arrays.toString(normalArr));
        sort(normalArr, 0, normalArr.length - 1);
        System.out.println(Arrays.toString(normalArr));
        sort(normalArr, 5, normalArr.length - 1);
        System.out.println(Arrays.toString(normalArr));
    }

    /**
     * 排序
     *
     * @param a    待排数组
     * @param low  初始位
     * @param high 最高位
     */
    private void sort(int[] a, int low, int high) {
        int l = low;
        int h = high;
        int povit = a[low];
        while (l < h) {
            while (l < h && a[h] >= povit) {
                h--;
            }
            if (l < h) {
                int temp = a[h];
                a[h] = a[l];
                a[l] = temp;
                l++;
            }

            while (l < h && a[l] <= povit) {
                l++;
            }
            if (l < h) {
                int temp = a[h];
                a[h] = a[l];
                a[l] = temp;
                h--;
            }
        }
        if (l > low) {
            sort(a, low, l - 1);
        }
        if (h < high) {
            sort(a, l + 1, high);
        }
    }
}
