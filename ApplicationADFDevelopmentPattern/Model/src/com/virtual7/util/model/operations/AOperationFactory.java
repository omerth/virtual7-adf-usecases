package com.virtual7.util.model.operations;

import java.util.HashMap;
import java.util.Map;

public class AOperationFactory {
    private Map<String, AFilterOperation> filterOperations = new HashMap<String, AFilterOperation>();
    private Map<String, AChangeOperation> changeOperations = new HashMap<String, AChangeOperation>();
    private String initialFilter;

    public AOperationFactory(String initialFilter) {
        this.initialFilter = initialFilter;
    }

    protected void registerFilterOperation(String name, AFilterOperation operation) {
        filterOperations.put(name, operation);
    }

    protected void registerChangeOperation(String name, AChangeOperation operation) {
        changeOperations.put(name, operation);
    }

    public AChangeOperation getChangeOperation(String name) {
        return changeOperations.get(name);
    }

    public AFilterOperation getFilterOperation(String name) {
        return filterOperations.get(name);
    }

    public AFilterOperation getInitialFilterOperation() {
        return filterOperations.get(initialFilter);
    }


    public AOperationFactory() {
        super();
    }
}
