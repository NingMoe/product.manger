package com.phicomm.product.manger.wei.yang;

import org.apache.commons.codec.binary.Hex;
import org.junit.Test;
import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Created by wei.yang on 2017/7/21.
 */
public class SortTest {

    private static final String CHAR_REPOSITORY = "abcdefghijklmnopqrstuvwxyz0123456789";

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

    @Test
    public void compareByValue() {
        Map<String, String> map = getMap();
        System.out.println(map);
        List<Map.Entry<String, String>> entryList = new ArrayList<>(map.entrySet());
        entryList.sort(Comparator.comparing(Map.Entry::getValue));
        System.out.println(entryList);
        entryList.sort(Comparator.comparing(Map.Entry::getKey));
        System.out.println(entryList);
    }

    @Test
    public void compareByValueV2() {
        Map<String, String> map = getMap();
        System.out.println(getMap());
        Stream<Map.Entry<String, String>> sorted = map.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getValue));
        map = sorted.collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (s, s2) -> s,
                (Supplier<Map<String, String>>) LinkedHashMap::new
        ));
        System.out.println(map);
        Stream<Map.Entry<String, String>> sorted1 = map.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey));
        map = sorted1.collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (e1, e2) -> e1,
                LinkedHashMap::new));
        System.out.println(map);
        System.out.println(
                map.entrySet().stream().reduce((stringStringEntry, stringStringEntry2) -> stringStringEntry.getValue().contains("2") ? stringStringEntry : stringStringEntry2)
        );
        System.out.println(
                map.entrySet().stream().reduce("guaguaguaga",
                        (s, stringStringEntry) -> stringStringEntry.getValue(),
                        (s, s2) -> s)
        );
    }

    @Test
    public void compareList() {
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            stringList.add(getRandomKey(6));
        }
        System.out.println(stringList);
        Stream<String> stringStream = stringList.stream().sorted(Comparator.naturalOrder());
        stringStream = stringStream.filter(s -> !s.contains("2"));
        stringList = stringStream.collect(Collectors.toList());
        System.out.println(stringList);
        System.out.println(stringList.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toMap(
                a -> 4,
                Function.identity(),
                (s, s1) -> s
        )));
    }

    /**
     * 获取map
     */
    private Map<String, String> getMap() {
        Map<String, String> map = new LinkedHashMap<>();
        for (int i = 0; i < 10; i++) {
            map.put(getRandomKey(5), getRandomKey(8));
        }
        return map;
    }

    /**
     * 获取长度固定的随机字符串
     *
     * @param len 长度
     * @return 字符串
     */
    private String getRandomKey(int len) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            builder.append(CHAR_REPOSITORY.charAt((int) Math.round(Math.random() * (CHAR_REPOSITORY.length() - 1))));
        }
        return builder.toString();
    }

    @Test
    public void test6() {
        String[] datas = new String[10];
        for (int i = 0; i < 10; i++) {
            datas[i] = getRandomKey(5);
        }
        List<String> stringList = new ArrayList<>(Arrays.asList(datas));
        List<String> stringList1 = Arrays.asList(datas);
        System.out.println(stringList1.getClass());
        System.out.println(stringList);
        System.out.println(stringList1 instanceof ArrayList);
        System.out.println(stringList1 instanceof Arrays);
        System.out.println(stringList1 instanceof AbstractList);
        System.out.println(stringList1 instanceof Arrays);
        List<String> stringList2 = new ArrayList<>(stringList);
        System.out.println(stringList2 instanceof ArrayList);
        System.out.println(stringList2.getClass());
    }

    @Test
    public void test7() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        System.out.println(getRandomKey(16));
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        System.out.println(Arrays.toString(md5.digest("wzan142857G".getBytes("UTF-8"))));
        System.out.println(Hex.encodeHex(md5.digest("wzan142857G".getBytes("UTF-8"))));
        System.out.println(DigestUtils.md5DigestAsHex("wzan142857G".getBytes("UTF-8")));
        System.out.println(org.apache.commons.codec.digest.DigestUtils.md5Hex("wzan142857G"));
        System.out.println(org.apache.commons.codec.digest.DigestUtils.sha1Hex("wzan142857G"));
        System.out.println(toHex(md5.digest("wzan142857G".getBytes())));
        System.out.println(toOtc(md5.digest("wzan142857G".getBytes())));
    }

    /**
     * 转为16进制
     *
     * @param data 待转化数据
     * @return 转化好的数据
     */
    private String toHex(byte[] data) {
        char[] hex = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        StringBuilder builder = new StringBuilder();
        for (byte aData : data) {
            builder.append(hex[(aData >>> 4) & 0xf]).append(hex[aData & 0xf]);
        }
        return builder.toString();
    }

    /**
     * 转为8进制
     *
     * @param data 待转化数据
     * @return 转化好的数据
     */
    private String toOtc(byte[] data) {
        char[] otc = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8'};
        StringBuilder builder = new StringBuilder();
        for (byte a : data) {
            builder.append(otc[(a >>> 6) & 0x7]).append(otc[(a >>> 3) & 0x7]).append(otc[a & 0x7]);
        }
        return builder.toString();
    }

    @Test
    public void test8() {
        System.out.println(Integer.toOctalString(86));
    }
}
