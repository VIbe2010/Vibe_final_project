package com.vibe.org.vibe;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.LinearLayout;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by Hena on 6/26/2018.
 */

public class UserRegisterFragmentTest {
    //using rule to actually launch the activity
    @Rule
    public ActivityTestRule<TestActivity> launcherTestRule= new ActivityTestRule<TestActivity>(TestActivity.class);
    private TestActivity demoActivity=null;
    private Context context;

    private Context c=null;
    private SharedPreferences sharedPreferences;

    public void setUp() throws Exception {
//        super.setUp();

        demoActivity=launcherTestRule.getActivity();
    }
    @Test(expected = Exception.class)
    public void  testingLaunchFragment() throws Exception {
        //this the test case that test whether the the fragment launched correctly or not by creating the mock
        // other equivalent empty activity called testing activity.

        LinearLayout test = (LinearLayout) demoActivity.findViewById(R.id.test_container);
        assertNotNull(test);
        LoginFragmentActivity loginFragment= new LoginFragmentActivity();
        demoActivity.getFragmentManager().beginTransaction().replace(test.getId(),loginFragment ).commitAllowingStateLoss();
        getInstrumentation().waitForIdleSync();
        View view = loginFragment.getView().findViewById(R.id.activity_login_fragment);
        assertNotNull(view);
        //test if the fragment is launched or not
    }

    public void tearDown() throws Exception {
        demoActivity = null;
        demoActivity = null;
    }


}