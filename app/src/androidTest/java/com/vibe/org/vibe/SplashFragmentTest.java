package com.vibe.org.vibe;

import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.LinearLayout;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by Hena on 6/19/2018.
 */
public class SplashFragmentTest  {
    //using rule to actually launch the activity
    @Rule
    public ActivityTestRule<TestActivity> launcherTestRule= new ActivityTestRule<TestActivity>(TestActivity.class);
    private TestActivity demoActivity=null;

    public void setUp() throws Exception {
//        super.setUp();
        demoActivity=launcherTestRule.getActivity();

    }
    @Test(expected = Exception.class)
    public void  testingLaunchingFragment() throws Exception {
        LinearLayout test = (LinearLayout) demoActivity.findViewById(R.id.test_container);
        assertNotNull(test);
        SplashFragment splashFragment = new SplashFragment();
        demoActivity.getFragmentManager().beginTransaction().replace(test.getId(), splashFragment).commitAllowingStateLoss();
        getInstrumentation().waitForIdleSync();
        View view = splashFragment.getView().findViewById(R.id.splash_container);
        assertNotNull(view);
        //test if the fragment is launched or not
    }
    public void tearDown() throws Exception {
        demoActivity=null;
        demoActivity=null;




    }

}