package com.virtual7.util.model.context;

import java.io.Serializable;

import oracle.jbo.NameValuePairs;


public abstract class AContext extends NameValuePairs implements Serializable {
    private static final long serialVersionUID = 1L;

    public AContext() {
        super();
    }
}
