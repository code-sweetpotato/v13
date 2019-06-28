package com.qf.v13centerweb.polo;

public class WangeditorResultBean {

    private String errno;//错误代码,0表示无错误

    private String[] data;//图片的地址

    public WangeditorResultBean(String errno, String[] data) {
        this.errno = errno;
        this.data = data;
    }

    public WangeditorResultBean() {
    }

    public String getErrno() {
        return errno;
    }

    public void setErrno(String errno) {
        this.errno = errno;
    }

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
    }

}
