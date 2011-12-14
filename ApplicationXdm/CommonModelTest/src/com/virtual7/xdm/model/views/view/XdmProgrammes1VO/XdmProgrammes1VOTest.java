package com.virtual7.xdm.model.views.view.XdmProgrammes1VO;

import com.virtual7.xdm.model.views.applicationModule.CommonModelTestServiceAMFixture;

import oracle.jbo.ViewObject;

import org.junit.*;
import static org.junit.Assert.*;

public class XdmProgrammes1VOTest {
    private CommonModelTestServiceAMFixture fixture1 = CommonModelTestServiceAMFixture.getInstance();

    public XdmProgrammes1VOTest() {
    }

    @Test
    public void testAccess() {
        ViewObject view = fixture1.getApplicationModule().findViewObject("XdmProgrammes1");
        assertNotNull(view);
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
}
