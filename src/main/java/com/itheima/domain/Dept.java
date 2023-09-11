package com.itheima.domain;

public class Dept {
    private String id;
    private String deptName; //部门名称
    private String parentId; //所属部门id
    private Integer state; //状态

    //添加父部门对象，名称必须叫parent，因为获取到时候叫parent
    private Dept parent;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Dept getParent() {
        return parent;
    }

    public void setParent(Dept parent) {
        this.parent = parent;
    }

}
