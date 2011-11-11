package com.virtual7.amComplexParams.model;

import java.io.Serializable;

import java.util.List;

public class UserList implements Serializable {
    private static final long serialVersionUID = -3276363768411424390L;
    List<UserBean> users;

    public UserList() {
        super();
    }

    public void setUsers(List<UserBean> users) {
        this.users = users;
    }

    public List<UserBean> getUsers() {
        return users;
    }
}
