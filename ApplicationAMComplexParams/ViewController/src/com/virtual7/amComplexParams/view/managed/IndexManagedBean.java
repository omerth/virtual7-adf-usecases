package com.virtual7.amComplexParams.view.managed;


import com.virtual7.amComplexParams.model.UserBean;

import com.virtual7.amComplexParams.model.UserList;

import java.util.List;
import java.util.Vector;

public class IndexManagedBean {
    private List<UserBean> users = new Vector<UserBean>();

    private UserList userList = new UserList();

    public IndexManagedBean() {
        super();
        userList.setUsers(users);

        UserBean user = new UserBean();
        user.setFirstName("Adrian");
        user.setLastName("Andreca");
        users.add(user);

        user = new UserBean();
        user.setFirstName("Adorian");
        user.setLastName("Szabo");
        users.add(user);

        user = new UserBean();
        user.setFirstName("Bogdan");
        user.setLastName("Moza");
        users.add(user);
    }

    public List<UserBean> getUsers() {
        return users;
    }

    public UserList getUserList() {
        return userList;
    }
}
