package com.virtual7.xdm.model.views.view.XdmPersons1VO;

import com.virtual7.xdm.model.views.applicationModule.CommonModelTestServiceAMFixture;

import oracle.jbo.ViewObject;

import org.junit.*;
import static org.junit.Assert.*;

public class XdmPersons1VOTest {
    private CommonModelTestServiceAMFixture fixture1 = CommonModelTestServiceAMFixture.getInstance();

    public XdmPersons1VOTest() {
    }

    @Test
    public void testAccess() {
        ViewObject view = fixture1.getApplicationModule().findViewObject("XdmPersons1");
        assertNotNull(view);
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
}
