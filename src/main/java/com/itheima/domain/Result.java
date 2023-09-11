package com.itheima.domain;

/**
 * 封装响应结果
 */
public class Result {
    private boolean flag; //true表示操作成功，false表示操作失败
    private String message; //保存响应信息，响应信息都定义在MessageConstant类中了，直接使用即可
    private Object data; //保存响应结果数据。例如：保存查询结果

    public Result() {}

    public Result(boolean flag, String message) {
        this.flag = flag;
        this.message = message;
    }

    public Result(boolean flag, String message, Object data) {
        this.flag = flag;
        this.message = message;
        this.data = data;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
