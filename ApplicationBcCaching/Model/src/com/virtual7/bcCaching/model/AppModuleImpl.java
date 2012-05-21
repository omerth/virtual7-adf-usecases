package com.virtual7.bcCaching.model;


import com.virtual7.bcCaching.model.common.AppModule;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.server.ApplicationModuleImpl;
import oracle.jbo.server.DSViewObjectImpl;
import oracle.jbo.server.ViewObjectImpl;


// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue May 15 11:25:14 EEST 2012
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class AppModuleImpl extends ApplicationModuleImpl implements AppModule {
    /**
     * This is the default constructor (do not remove).
     */
    public AppModuleImpl() {
    }

    // Simple View Object related methods.

    public void vo_printCurrentVOQueryMode() {
        System.out.println("Query mode for the DepartmentsVO is:" + this.getDepartmentVO().getQueryMode());
    }

    public void vo_executeQueryVOViewRows() {
        ViewObjectImpl viewObjImpl = this.getDepartmentVO();
        
        // Get default
        
        viewObjImpl.setQueryMode(ViewObject.QUERY_MODE_SCAN_VIEW_ROWS);
        viewObjImpl.executeQuery();
        
        // Reset to default
    }

    public void vo_executeQueryVODBTables() {
        ViewObjectImpl viewObjImpl = this.getDepartmentVO();
        viewObjImpl.setQueryMode(ViewObject.QUERY_MODE_SCAN_DATABASE_TABLES);
        viewObjImpl.executeQuery();
    }

    public void vo_executeQueryVOEntityObjects() {
        ViewObjectImpl viewObjImpl = this.getDepartmentVO();
        viewObjImpl.setQueryMode(ViewObject.QUERY_MODE_SCAN_ENTITY_ROWS);
        viewObjImpl.executeQuery();
    }

    // View Object based on entityes related methods.

    public void eo_printCurrentVOQueryMode() {
        System.out.println("Query mode for the DepartmentsEOVO is:" + this.getDepartmentsEOVO().getQueryMode());
    }

    // TODO: Add other 3 methods

    // View Object based on Entity Object with Web Service related methods.

    public void so_printCurrentVOQueryMode() {
        System.out.println("Query mode for the tepartmentservicevoView is:" +
                           this.getDepartmentservicevoView().getQueryMode());
    }

    // TODO: Add other 3 methods.

    /**
     * Container's getter for DepartmentsEOVO.
     * @return DepartmentsEOVO
     */
    public ViewObjectImpl getDepartmentsEOVO() {
        return (ViewObjectImpl)findViewObject("DepartmentsEOVO");
    }

    public void readAndPrintDepartment() {
        Connection conn = null;
        Statement stm = null;
        String query = "select * from Departments";
        ResultSet depSet = null;
        try {
            stm = this.getDBTransaction().createStatement(1);
            conn = stm.getConnection();
            depSet = stm.executeQuery(query);
            while (depSet.next()) {
                System.out.println(depSet.getBigDecimal(1) + " --- " + depSet.getString(2) + " --- " +
                                   depSet.getBigDecimal(3) + " --- " + depSet.getBigDecimal(4));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            if (depSet != null) {
                try {
                    depSet.close();
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            }
        }
    }

    public void insertNewDepartment() {
        Connection conn = null;
        Statement stm = null;
        String query =
            "insert into departments (department_id,department_name,location_id) values (1,'TestingDep',2400)";
        try {
            stm = this.getDBTransaction().createStatement(1);
            conn = stm.getConnection();
            stm.executeUpdate(query);
            conn.commit();
        } catch (Exception e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException f) {
                f.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void deleteDepartment() {
        Connection conn = null;
        Statement stm = null;
        String query = "delete from departments where department_id = 1";
        try {
            stm = this.getDBTransaction().createStatement(1);
            conn = stm.getConnection();
            stm.executeUpdate(query);
            conn.commit();
        } catch (Exception e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException f) {
                f.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void updateDepartment() {
        Connection conn = null;
        Statement stm = null;
        String query = "update departments set department_name = 'TestingEdit' where department_id = 1";
        try {
            stm = this.getDBTransaction().createStatement(1);
            conn = stm.getConnection();
            stm.executeUpdate(query);
            conn.commit();
        } catch (Exception e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException f) {
                f.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }


    public void executeQuery() {
        ViewObjectImpl viewObjImpl = this.getDepartmentVO();
        viewObjImpl.setQueryMode(ViewObject.QUERY_MODE_SCAN_DATABASE_TABLES | ViewObject.QUERY_MODE_SCAN_ENTITY_ROWS);
        viewObjImpl.executeQuery();
    }

    public void executeQuery1() {
        ViewObjectImpl viewObjImpl = this.getDepartmentsEOVO();
        viewObjImpl.setQueryMode(ViewObject.QUERY_MODE_SCAN_DATABASE_TABLES);
        viewObjImpl.executeQuery();
    }

    public void executeQuery2() {
        ViewObjectImpl viewObjImpl = this.getDepartmentsEOVO();
        viewObjImpl.setQueryMode(ViewObject.QUERY_MODE_SCAN_ENTITY_ROWS);
        viewObjImpl.executeQuery();
        viewObjImpl.setQueryMode(ViewObject.QUERY_MODE_SCAN_DATABASE_TABLES);
    }

    public void executeQuery3() {
        ViewObjectImpl viewObjImpl = this.getDepartmentsEOVO();
        viewObjImpl.getQueryMode();
        viewObjImpl.setQueryMode(ViewObject.QUERY_MODE_SCAN_VIEW_ROWS);
        viewObjImpl.executeQuery();
        viewObjImpl.setQueryMode(ViewObject.QUERY_MODE_SCAN_DATABASE_TABLES);
    }

    public void executeQuery4() {
        ViewObjectImpl viewObjImpl = this.getDepartmentVO();
        viewObjImpl.getQueryMode();
        viewObjImpl.setQueryMode(ViewObject.QUERY_MODE_SCAN_DATABASE_TABLES);
        viewObjImpl.executeQuery();
    }

    public void executeQuery5() {
        ViewObjectImpl viewObjImpl = this.getDepartmentVO();
        viewObjImpl.getQueryMode();
        viewObjImpl.setQueryMode(ViewObject.QUERY_MODE_SCAN_ENTITY_ROWS);
        viewObjImpl.executeQuery();
        viewObjImpl.setQueryMode(ViewObject.QUERY_MODE_SCAN_DATABASE_TABLES);
    }

    public void executeQuery6() {
        ViewObjectImpl viewObjImpl = this.getDepartmentVO();
        viewObjImpl.getQueryMode();
        viewObjImpl.setQueryMode(ViewObject.QUERY_MODE_SCAN_VIEW_ROWS);
        viewObjImpl.executeQuery();
        viewObjImpl.setQueryMode(ViewObject.QUERY_MODE_SCAN_DATABASE_TABLES);
    }

    public void executeServiceQuery() {
        ViewObjectImpl viewObjImpl = this.getDepartmentservicevoView();
        viewObjImpl.setRangeSize(30);
        viewObjImpl.setQueryMode(ViewObject.QUERY_MODE_SCAN_DATABASE_TABLES);
        viewObjImpl.executeQuery();
    }

    public void executeServiceQueryEL() {
        ViewObjectImpl viewObjImpl = this.getDepartmentservicevoView();
        viewObjImpl.setRangeSize(30);
        viewObjImpl.setQueryMode(ViewObject.QUERY_MODE_SCAN_ENTITY_ROWS);
        viewObjImpl.executeQuery();
        viewObjImpl.setQueryMode(ViewObject.QUERY_MODE_SCAN_DATABASE_TABLES);
    }

    public void executeServiceQueryVL() {
        ViewObjectImpl viewObjImpl = this.getDepartmentservicevoView();
        viewObjImpl.setRangeSize(30);
        viewObjImpl.setQueryMode(ViewObject.QUERY_MODE_SCAN_VIEW_ROWS);
        viewObjImpl.executeQuery();
        viewObjImpl.setQueryMode(ViewObject.QUERY_MODE_SCAN_DATABASE_TABLES);
    }

    public void readAndPrintDepartmentService() {
        DSViewObjectImpl viewObjImpl = this.getDepartmentservicevoView();
        viewObjImpl.setQueryMode(ViewObject.QUERY_MODE_SCAN_DATABASE_TABLES);
        viewObjImpl.getRangeSize();
        viewObjImpl.setRangeSize(30);
        viewObjImpl.executeQuery();
        Row[] rows = viewObjImpl.getAllRowsInRange();
        for (Row row : rows) {
            System.out.println(row.getAttribute(0) + " --- " + row.getAttribute(1) + " --- " + row.getAttribute(2) +
                               " --- " + row.getAttribute(3));
        }
    }

    public void readAndPrintDepartmentNewJDBC() {
        Connection conn = null;
        String jdbcString = "jdbc:oracle:thin:@localhost:1521:XE";
        Statement stm = null;
        String query = "select * from Departments";
        ResultSet depSet = null;
        try {
            conn = DriverManager.getConnection(jdbcString, "hr", "hr");
            stm = conn.createStatement();
            depSet = stm.executeQuery(query);
            while (depSet.next()) {
                System.out.println(depSet.getBigDecimal(1) + " --- " + depSet.getString(2) + " --- " +
                                   depSet.getBigDecimal(3) + " --- " + depSet.getBigDecimal(4));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            if (depSet != null) {
                try {
                    depSet.close();
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            }
        }
    }

    public void insertNewDepartmentNewJDBC() {
        Connection conn = null;
        String jdbcString = "jdbc:oracle:thin:@localhost:1521:XE";
        Statement stm = null;
        String query =
            "insert into departments (department_id,department_name,location_id) values (2,'TestingDepJDBC',2400)";
        try {
            conn = DriverManager.getConnection(jdbcString, "hr", "hr");
            stm = conn.createStatement();
            stm.executeUpdate(query);
            conn.commit();
        } catch (Exception e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException f) {
                f.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void deleteDepartmentNewJDBC() {
        Connection conn = null;
        String jdbcString = "jdbc:oracle:thin:@localhost:1521:XE";
        Statement stm = null;
        String query = "delete from departments where department_id = 2";
        try {
            conn = DriverManager.getConnection(jdbcString, "hr", "hr");
            stm = conn.createStatement();
            stm.executeUpdate(query);
            conn.commit();
        } catch (Exception e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException f) {
                f.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void updateDepartmentNewJDBC() {
        Connection conn = null;
        String jdbcString = "jdbc:oracle:thin:@localhost:1521:XE";
        Statement stm = null;
        String query = "update departments set department_name = 'TestingEditJDBC' where department_id = 2";
        try {
            conn = DriverManager.getConnection(jdbcString, "hr", "hr");
            stm = conn.createStatement();
            stm.executeUpdate(query);
            conn.commit();
        } catch (Exception e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException f) {
                f.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }


    /**
     * Container's getter for DepartmentVO.
     * @return DepartmentVO
     */
    public ViewObjectImpl getDepartmentVO() {
        return (ViewObjectImpl)findViewObject("DepartmentVO");
    }

    /**
     * Container's getter for DepartmentservicevoView.
     * @return DepartmentservicevoView
     */
    public DSViewObjectImpl getDepartmentservicevoView() {
        return (DSViewObjectImpl)findViewObject("DepartmentservicevoView");
    }
}