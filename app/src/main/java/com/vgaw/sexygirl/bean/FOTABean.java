package com.vgaw.sexygirl.bean;

import java.text.DecimalFormat;

/**
 * 　　　　　　　╔—のStrat゛——————————————————————╗
 * .
 * 　　　　　   　　Author: Zccf
 * .
 * 　　　　　   　　Date: 2016/6/1
 * .
 * 　　　　　   　　Time: 17:36
 * .
 * 　　　　　   　　Email: zccfmail@foxmail.com
 * .
 * 　　　　　　　╚————————————————————————のEnd゛—╝
 * .
 * 　　　　　　　　　　Content about:『  』
 */
public class FOTABean {

    /**
     * name : 应用名称
     * version : 版本
     * changelog : 更新信息
     * updated_at : 1464771465
     * versionShort : 版本编号(兼容旧版字段)
     * build : 编译号
     * installUrl : 安装地址（兼容旧版字段）
     * install_url : 安装地址(新增字段)
     * direct_install_url :
     * update_url : 更新地址(新增字段)
     * binary : {"fsize":7881689}   更新文件的对象，仅有大小字段fsize
     */

    private String name;
    private int version;
    private String changelog;
    private int updated_at;
    private String versionShort;
    private String build;
    private String installUrl;
    private String install_url;
    private String direct_install_url;
    private String update_url;
    /**
     * fsize : 7881689
     */

    private BinaryBean binary;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getChangelog() {
        return changelog;
    }

    public void setChangelog(String changelog) {
        this.changelog = changelog;
    }

    public int getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(int updated_at) {
        this.updated_at = updated_at;
    }

    public String getVersionShort() {
        return versionShort;
    }

    public void setVersionShort(String versionShort) {
        this.versionShort = versionShort;
    }

    public String getBuild() {
        return build;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public String getInstallUrl() {
        return installUrl;
    }

    public void setInstallUrl(String installUrl) {
        this.installUrl = installUrl;
    }

    public String getInstall_url() {
        return install_url;
    }

    public void setInstall_url(String install_url) {
        this.install_url = install_url;
    }

    public String getDirect_install_url() {
        return direct_install_url;
    }

    public void setDirect_install_url(String direct_install_url) {
        this.direct_install_url = direct_install_url;
    }

    public String getUpdate_url() {
        return update_url;
    }

    public void setUpdate_url(String update_url) {
        this.update_url = update_url;
    }

    public BinaryBean getBinary() {
        return binary;
    }

    public void setBinary(BinaryBean binary) {
        this.binary = binary;
    }

    public static class BinaryBean {
        private float fsize;

        public float getFsize() {
            DecimalFormat df = new DecimalFormat("#.00");
            return Float.parseFloat(df.format(fsize / 1024 / 1024));//返回m为单位的大小
        }

        public void setFsize(float fsize) {
            this.fsize = fsize;
        }
    }
}
