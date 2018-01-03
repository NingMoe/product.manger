package com.phicomm.product.manger.caosong.mongo;

/**
 * Created by song02.cao on 2017/12/28.
 */
public class PersonCount {
    private int count;
    private String name;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    private int age;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PersonCount{" +
                "count=" + count +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
