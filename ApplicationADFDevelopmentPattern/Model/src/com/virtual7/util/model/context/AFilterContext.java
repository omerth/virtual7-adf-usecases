package com.virtual7.util.model.context;

import java.io.Serializable;


public abstract class AFilterContext extends AContext implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String FILTERS = "FILTERS";

    public AFilterContext() {
        super();
    }

    public void setFilters(String[] filters) {
        this.setAttribute(FILTERS, filters);
    }

    public String[] getFilters() {
        return (String[])getAttribute(FILTERS);
    }
}
