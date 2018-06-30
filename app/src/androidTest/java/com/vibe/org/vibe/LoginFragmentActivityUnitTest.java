package com.vibe.org.vibe;

import android.support.test.filters.SmallTest;
import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by Hena on 6/18/2018.
 */

public class LoginFragmentActivityUnitTest extends ActivityInstrumentationTestCase2<LoginActivity> {
    public LoginFragmentActivityUnitTest(String pkg, Class<LoginActivity> activityClass) {
        super(pkg, activityClass);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    @SmallTest
    public void validater(){
    LoginActivity loginActivity = getActivity();

    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
