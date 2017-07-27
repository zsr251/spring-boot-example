package com.jc.security.model;

import javax.persistence.*;

public class Permission {
    @Id
    private Integer id;

    /**
     * 权限名
     */
    private String name;

    /**
     * 介绍
     */
    private String description;

    /**
     * 链接
     */
    private String url;

    /**
     * 调用方法
     */
    private String method;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取权限名
     *
     * @return name - 权限名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置权限名
     *
     * @param name 权限名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取介绍
     *
     * @return description - 介绍
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置介绍
     *
     * @param description 介绍
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * 获取链接
     *
     * @return url - 链接
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置链接
     *
     * @param url 链接
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * 获取调用方法
     *
     * @return method - 调用方法
     */
    public String getMethod() {
        return method;
    }

    /**
     * 设置调用方法
     *
     * @param method 调用方法
     */
    public void setMethod(String method) {
        this.method = method == null ? null : method.trim();
    }
}