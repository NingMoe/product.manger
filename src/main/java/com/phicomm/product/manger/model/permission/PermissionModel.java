package com.phicomm.product.manger.model.permission;

import com.phicomm.product.manger.enumeration.PermissionEnum;

/**
 * type/id/name/description
 * Created by yufei.liu on 2017/6/2.
 */
public class PermissionModel {

    private PermissionEnum type;

    private String id;

    private String name;

    private String description;

    public PermissionModel(PermissionEnum type, String id, String name, String description) {
        this.type = type;
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public void setType(PermissionEnum type) {
        this.type = type;
    }

    public PermissionEnum getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("id = %s, name = %s, description = %s", id, name, description);
    }
}
