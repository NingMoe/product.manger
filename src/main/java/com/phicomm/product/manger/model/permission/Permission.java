package com.phicomm.product.manger.model.permission;

/**
 * 权限
 *
 * @author qiang.ren
 * @date 2017/11/14
 */
public class Permission {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Permission(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "name='" + name + '\'' +
                '}';
    }
}
