package com.virtual7.xdm.model.views.view.XdmTasks1VO;

import com.virtual7.xdm.model.views.applicationModule.CommonModelTestServiceAMFixture;
import oracle.jbo.ApplicationModule;
import oracle.jbo.ViewObject;
import oracle.jbo.*;
import org.junit.*;
import static org.junit.Assert.*;

public class XdmTasks1VOTest {
    private static CommonModelTestServiceAMFixture fixture1 = CommonModelTestServiceAMFixture.getInstance();
    private static ApplicationModule am = fixture1.getApplicationModule();

    public XdmTasks1VOTest() {
    }

    @Test
    public void testAccess() {
        ViewObject view = fixture1.getApplicationModule().findViewObject("XdmTasks1");
        assertNotNull(view);
    }
    
    @Test
    public void testDelete() {
        // add a test record directly to the table
        Transaction tr = am.getTransaction();
        tr.executeCommand("INSERT INTO xdm_tasks (task_id, start_date, text) VALUES (-1, sysdate, 'Test Task')");
        tr.commit();
        // find the row with ID -1 and remove it
        ViewObject v = am.findViewObject("XdmTasks1");
        Key k = new Key(new Object[] { -1 });
        Row r1 = v.getRow(k);
        assertNotNull("Test row (-1) found", r1);
        v.setCurrentRow(r1);
        v.removeCurrentRow();
        tr.commit();
        // look for the row
        v.executeQuery();
        Row r2 = v.getRow(k);
        assertNotNull("Test row (-1) found again", r2);
        // test that CxlYn attribute is now Y, indicating deletion
        assertEquals("Test row CxlYn value is Y", "Y", r2.getAttribute("CxlYn"));
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    
    @AfterClass
    public static void deleteTestData() {
    Transaction tr = am.getTransaction();
    tr.executeCommand("DELETE FROM xdm_tasks " +
    "WHERE task_id = -1");
    tr.commit();
    }
}
