package com.terry.shame.ui.LR;

import java.io.Serializable;

/**
 * Created By Terry on 2020/6/8
 */
public class Person implements Serializable {
    private String name;
    private String ID;
    private String password;
    private String phone;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPhone(){
        return phone;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }
}
