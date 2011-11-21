package com.virtual7.amComplexParams.model;

import java.io.Serializable;

public class UserBean implements Serializable {

    private static final long serialVersionUID = 5852467050309482681L;
    private String firstName;
    private String lastName;

    public UserBean() {
        super();
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }
}
