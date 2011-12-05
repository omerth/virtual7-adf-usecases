package com.virtual7.xdm.model.views.view.XdmElements1VO;

import com.virtual7.xdm.model.views.applicationModule.CommonModelTestServiceAMFixture;

import oracle.jbo.ViewObject;

import org.junit.*;
import static org.junit.Assert.*;

public class XdmElements1VOTest {
    private CommonModelTestServiceAMFixture fixture1 = CommonModelTestServiceAMFixture.getInstance();

    public XdmElements1VOTest() {
    }

    @Test
    public void testAccess() {
        ViewObject view = fixture1.getApplicationModule().findViewObject("XdmElements1");
        assertNotNull(view);
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
}
