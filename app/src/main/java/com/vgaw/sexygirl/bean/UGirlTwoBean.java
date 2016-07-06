package com.vgaw.sexygirl.bean;

/**
 * from : Volodymyr
 * to : caojinmail@163.com
 * me : github.com/VolodymyrCj/
 */
public class UGirlTwoBean extends BaseBean {
    // 没有下载
    public static final int STATUS_NONE = 1;
    // 准备下载
    public static final int STATUS_READY = 2;
    // 已经下载
    public static final int STATUS_DONE = 3;

    private String url;
    private int status;

    public UGirlTwoBean(){}

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
