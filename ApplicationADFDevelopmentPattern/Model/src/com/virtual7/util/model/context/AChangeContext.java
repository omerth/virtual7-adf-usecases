package com.virtual7.util.model.context;

import java.io.Serializable;


public abstract class AChangeContext extends AContext implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String CHANGES = "CHANGES";

    public AChangeContext() {
        super();
    }

    public void setChanges(String[] changes) {
        setAttribute(CHANGES, changes);
    }

    public String[] getChanges() {
        return (String[])getAttribute(CHANGES);
    }
}
