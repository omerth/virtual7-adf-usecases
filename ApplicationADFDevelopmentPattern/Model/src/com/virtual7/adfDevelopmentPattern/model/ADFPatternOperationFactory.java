package com.virtual7.adfDevelopmentPattern.model;

import com.virtual7.adfDevelopmentPattern.model.opperations.ADFPatternAddOperation;
import com.virtual7.adfDevelopmentPattern.model.opperations.CommitOperation;
import com.virtual7.adfDevelopmentPattern.model.opperations.EmployyesFilterOperation;
import com.virtual7.adfDevelopmentPattern.model.opperations.RollbackOperation;
import com.virtual7.util.model.operations.AOperationFactory;

public class ADFPatternOperationFactory extends AOperationFactory {
    public static final String EMPLOYEE_FILTER = "EMPLOYEE_FILTER";
    public static final String COMMIT = "COMMIT";
    public static final String ROLLBACK = "ROLLBACK";
    public static final String ADD_EMPLOYEE = "ADD_EMPLOYEE";

    private static final AOperationFactory instance = new ADFPatternOperationFactory();

    public ADFPatternOperationFactory() {
        super(EMPLOYEE_FILTER);
        registerFilterOperation(EMPLOYEE_FILTER, new EmployyesFilterOperation());
        
        registerChangeOperation(ADD_EMPLOYEE, new ADFPatternAddOperation());
        registerChangeOperation(COMMIT, new CommitOperation());
        registerChangeOperation(ROLLBACK, new RollbackOperation());
    }


    public static AOperationFactory getInstance() {
        return instance;
    }
}
