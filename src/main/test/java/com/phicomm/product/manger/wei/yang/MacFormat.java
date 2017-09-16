package com.phicomm.product.manger.wei.yang;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 格式化mac
 * Created by wei.yang on 2017/7/13.
 */
public class MacFormat {

    private static final String macString="5c:cf:7f:91:06:b4\n" +
            "5c:cf:7f:91:06:8c\n" +
            "5c:cf:7f:91:06:b4\n" +
            "5c:cf:7f:91:06:b4\n" +
            "5c:cf:7f:da:ba:f5\n" +
            "5c:cf:7f:da:c1:8d\n" +
            "5c:cf:7f:91:06:8c\n" +
            "60:01:94:17:62:c1\n" +
            "5c:cf:7f:d8:2e:b3\n" +
            "5c:cf:7f:d8:2e:b3\n" +
            "5c:cf:7f:91:01:83\n" +
            "5c:cf:7f:db:26:b8\n" +
            "5c:cf:7f:d8:2e:b3\n" +
            "5c:cf:7f:db:2d:1b\n" +
            "5c:cf:7f:db:27:dd\n" +
            "5c:cf:7f:db:27:dd\n" +
            "60:01:94:17:62:c1\n" +
            "5c:cf:7f:d8:2e:b3\n" +
            "5c:cf:7f:d7:e5:f0\n" +
            "5c:cf:7f:db:2d:1b";

    private static final String macString1="5c:cf:7f:91:06:b4、5c:cf:7f:91:06:8c、5c:cf:7f:91:06:b4、5c:cf:7f:91:06:b4、5c:cf:7f:da:ba:f5、5c:cf:7f:da:c1:8d、5c:cf:7f:91:06:8c、60:01:94:17:62:c1、5c:cf:7f:d8:2e:b3、5c:cf:7f:d8:2e:b3";
    private static final String errorMacString1=":cf:7f:91:06:b4、5c:cf:7f:91:06:8c、5c:cf:7f:91:06:b4、5c:cf:7f:91:06:b4、5c:cf:7f:da:ba:f5、5c:cf:7f:da:c1:8d、5c:cf:7f:91:06:8c、60:01:94:17:62:c1、5c:cf:7f:d8:2e:b3、5c:cf:7f:d8:2e:b3";
    private static final String errorMacString="5c:cf:7f:91:06:b4\n" +
            "5c:cf:7f:91:06:8c\n" +
            "5c:cf:7f:91:06:b4\n" +
            ":cf:7f:91:06:b4\n" +
            "5c:cf:7f:da:ba:f5\n" +
            "5c:cf:7f:da:c1:8d\n" +
            "5c:cf:7f:91:06:8c\n" +
            "60:01:94:17:62:c1\n" +
            "5c:cf:7f:d8:2e:b3\n" +
            "5c:cf:7f:d8:2e:b3\n" +
            "5c:cf:7f:91:01:83\n" +
            "5c:cf:7f:db:26:b8\n" +
            "5c:cf:7f:d8:2e:b3\n" +
            "5c:cf:7f:db:2d:1b\n" +
            "5c:cf:7f:db:27:dd\n" +
            "5c:cf:7f:db:27:dd\n" +
            "60:01:94:17:62:c1\n" +
            "5c:cf:7f:d8:2e:b3\n" +
            "5c:cf:7f:d7:e5:f0\n" +
            "5c:cf:7f:db:2d:1b";

    @Test
    public void test(){
        System.out.println(formatMacAbort(errorMacString));
    }

    /**
     * 只有换行的，无分隔符分割：无法识别错误mac
     */
    private List formatMac(String macString){
        List macList;
        macString=macString.replaceAll("\\s+", "、");
        macList= Arrays.asList(macString.split("、"));
        return macList;
    }

    @SuppressWarnings(value = "all")
    private List formatMacAbort(String macString){
        List<String> macList=new ArrayList<>();
        //去除所有空格
        macString=macString.replaceAll("\\s+", "").
                replaceAll("、","").
                replaceAll(",","");
        String[] splitMac=macString.split(":");
        String nextMacHeader="";
        boolean start=true;
        StringBuilder macBuilder = null;
        for (String aSplitMac : splitMac) {
            if (start){
                macBuilder=new StringBuilder();
            }
            if (!nextMacHeader.equalsIgnoreCase("")){
                macBuilder.append(nextMacHeader).append(":");
            }
            if (aSplitMac.length() != 4) {
                start=false;
                macBuilder.append(aSplitMac).append(":");
                nextMacHeader="";
            }
            if (aSplitMac.length() == 4) {
                macBuilder.append(aSplitMac.subSequence(0, 2));
                nextMacHeader=aSplitMac.substring(2,4);
                start=true;
                if (macBuilder.length()==17){
                    macList.add(macBuilder.toString());
                }
            }
        }
        return macList;
    }
}
