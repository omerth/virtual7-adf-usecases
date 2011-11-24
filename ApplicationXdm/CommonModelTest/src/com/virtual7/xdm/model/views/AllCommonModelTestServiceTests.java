package com.virtual7.xdm.model.views;

import com.virtual7.xdm.model.views.applicationModule.CommonModelTestServiceAMFixture;
import com.virtual7.xdm.model.views.view.XdmElements1VO.XdmElements1VOTest;
import com.virtual7.xdm.model.views.view.XdmPersons1VO.XdmPersons1VOTest;
import com.virtual7.xdm.model.views.view.XdmProgrammes1VO.XdmProgrammes1VOTest;
import com.virtual7.xdm.model.views.view.XdmTasks1VO.XdmTasks1VOTest;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { XdmTasks1VOTest.class, XdmProgrammes1VOTest.class, XdmPersons1VOTest.class,
                       XdmElements1VOTest.class })
public class AllCommonModelTestServiceTests {
    @BeforeClass
    public static void setUp() {
    }

    @AfterClass
    public static void tearDown() throws Exception {
        CommonModelTestServiceAMFixture.getInstance().release();
    }
}
