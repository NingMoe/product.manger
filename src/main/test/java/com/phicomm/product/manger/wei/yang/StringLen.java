package com.phicomm.product.manger.wei.yang;

import org.junit.Test;

/**
 * test
 * Created by wei.yang on 2017/7/4.
 */
public class StringLen {

    @Test
    public void test(){
        System.out.println("5c:cf:7f:41:77:cf".length());
        System.out.println("A020A6351AD5".length());
    }

    @Test
    public void test1(){
        System.out.println(formatMac("A020A6351AD5"));
    }

    private String formatMac(String mac){
        int len=mac.length();
        StringBuilder builder=new StringBuilder();
        if (mac.length()==17){
            return mac.toLowerCase();
        }else {
            for (int i=0;i<len/2;i++){
                builder.append(mac.subSequence(2*i,2*i+2));
                if (!(i==len/2-1)){
                    builder.append(":");
                }
            }
            return builder.toString().toLowerCase();
        }
    }
}
