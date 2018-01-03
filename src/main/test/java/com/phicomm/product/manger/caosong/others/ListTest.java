package com.phicomm.product.manger.caosong.others;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * List相关测试
 *
 * @author song02.cao
 */
public class ListTest {

    @Test
    public void test() {
        List<Long> list = new ArrayList<>(14);
        list.add(0, Long.valueOf(0));
        list.add(1,Long.valueOf(1));
        list.set(0, Long.valueOf(2));
        System.out.println(list);
    }
}
