package com.phicomm.product.manger.wei.yang;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * try_with_resources
 * Created by wei.yang on 2017/7/26.
 */
public class FileTest {

    /**
     * normal
     */
    @Test
    public void test() throws IOException {
        File file = new File("F:\\软件\\ShadowsocksR-4.3.1-win\\chn_ip.txt");
        FileInputStream fileInputStream = null;
        StringBuilder builder = new StringBuilder();
        try {
            fileInputStream = new FileInputStream(file);
            byte[] data = new byte[1024];
            int len;

            while ((len = fileInputStream.read(data)) != -1) {
                builder.append(new String(data, 0, len));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (fileInputStream != null) {
            fileInputStream.close();
        }
        System.out.println(builder.toString());
    }

    /**
     * try_with_resources：实现AutoCloseable 即可
     */
    @Test
    public void test1() {
        File file = new File("F:\\软件\\ShadowsocksR-4.3.1-win\\chn_ip.txt");
        StringBuilder builder = new StringBuilder();
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            byte[] data = new byte[1024];
            int len;

            while ((len = fileInputStream.read(data)) != -1) {
                builder.append(new String(data, 0, len));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(builder);
    }

    @Test
    public void test2() throws IOException {
        byte[] data = Files.readAllBytes(Paths.get("F:\\软件\\ShadowsocksR-4.3.1-win\\chn_ip.txt"));
        String content = new String(data, StandardCharsets.UTF_8);
        System.out.println(content);
    }

    @Test
    public void test3() throws IOException {
        List<String> data = Files.readAllLines(Paths.get("F:\\软件\\ShadowsocksR-4.3.1-win\\chn_ip.txt"));
        System.out.println(data);
    }

    /**
     * 覆盖式
     *
     * @throws IOException 文件不存在
     */
    @Test
    public void test4() throws IOException {
        Files.write(Paths.get("F:\\b.txt"), "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa".getBytes());
    }

    /**
     * 此方法跟之前的File.delete差不多
     */
    @Test
    public void test5() throws IOException {
        Path path = Paths.get("F:\\新建文件夹");
        /*Files.delete(path);*/
        File[] files = path.toFile().listFiles();
        System.out.println(Arrays.toString(files));
        delete(path.toFile());
    }

    private void delete(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files != null) {
                    for (File file2 : files) {
                        if (file2.isDirectory()) {
                            delete(file2);
                        } else {
                            if (file2.delete()) {
                                System.out.println(file2.getName());
                            }
                        }
                    }
                }
            }
        }
        if (file.delete()) {
            System.out.println(file.getName());
        }
    }


}
