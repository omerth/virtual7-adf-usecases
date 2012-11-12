package com.virtual7.programaticPivotTableBinding.view.managed.myTable;

import oracle.dss.util.CubeDataAccess;
import oracle.dss.util.CubeDataDirector;
import oracle.dss.util.DataAccessAdapter;
import oracle.dss.util.DataSource;
import oracle.dss.util.RelationalDataAccess;
import oracle.dss.util.RelationalDataDirector;

public class MyDataSource extends DataAccessAdapter implements CubeDataDirector, CubeDataAccess,
                                                               RelationalDataDirector, RelationalDataAccess,
                                                               DataSource {
    public MyDataSource() {
    }

    public CubeDataDirector createCubeDataDirector() {
        return this;
    }

    public RelationalDataDirector createRelationalDataDirector() {
        return this;
    }
}
