package com.virtual7.xdm.model.views.applicationModule;

import oracle.jbo.ApplicationModule;
import oracle.jbo.client.Configuration;

public class CommonModelTestServiceAMFixture {
    private static CommonModelTestServiceAMFixture fixture1 = new CommonModelTestServiceAMFixture();
    private ApplicationModule _am;

    private CommonModelTestServiceAMFixture() {
        _am = Configuration.createRootApplicationModule("com.virtual7.xdm.model.views.CommonModelTestService","CommonModelTestServiceLocal");
    }

    public void setUp() {
    }

    public void tearDown() {
    }

    public static CommonModelTestServiceAMFixture getInstance() {
        return fixture1;
    }

    public void release() throws Exception {
        Configuration.releaseRootApplicationModule(_am, true);
    }

    public ApplicationModule getApplicationModule() {
        return _am;
    }
}
