package com.buaa.domain;

/**
 * 用户的属性。
 */
public class Customer {
    private String id;              //用户ID
    private String username;        //用户名
    private String password;        //登录秘密
    private String address;         //用户地址
    private String phone;           //用户联系方式
    private String email;           //用户邮件地址

    private String code;            //用户激活码
    private boolean isActived;      //账户是否被激活

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setActived(boolean actived) {
        isActived = actived;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getCode() {
        return code;
    }

    public boolean isActived() {
        return isActived;
    }
}
