package com.example.johnlee.fragments;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;
import android.text.InputType;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withInputType;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.*;

public class userRegister {
    @Rule
    public ActivityTestRule<MainActivity> mActivity = new ActivityTestRule<MainActivity>(MainActivity.class);

    private MainActivity mActivitys = null;

    Instrumentation.ActivityMonitor monitor,monitor2,monitor3,monitor4;

    {
        monitor = getInstrumentation().addMonitor(HomeController.class.getName(), null, false);
    }

    private String uName = "John";
    private String uPass = "123456";
    private String uEmail = "yohannesfassil@gmail.com";

    private String wEmail = "efwa2"
    private String wName = "e324f";
    private String wPass = "13214";

    @Before
    public void setUp() throws Exception {
        mActivitys = mActivity.getActivity();
    }

    @Test
    public void registerSuccess(){
        Log.e("@Test", "Performing Login success test");
        //check if the activity is not null
        assertNotNull(mActivitys.findViewById(R.id.register));

        //type into the edit texts user name and password
        onView(withId(R.id.username)).perform(typeText(uName));
        onView(withId(R.id.email)).perform(typeText(uEmail));
        onView(withId(R.id.password)).perform(typeText(uPass));
        onView(withId(R.id.confirmPassword)).perform(typeText(uPass));
        closeSoftKeyboard();

        //click the login button
        onView(withId(R.id.register)).perform(click());
        //see if it gave the second activity
        Activity secondActivity = getInstrumentation().waitForMonitorWithTimeout(monitor,5000);
        assertNotNull(secondActivity);

        pauseTestFor(5000);
    }

    @Test
    public void registerFail() {
        Log.e("@Test", "Performing Login success test");
        //check if the activity is not null
        assertNotNull(mActivitys.findViewById(R.id.register));

        //type into the edit texts user name and password
        onView(withId(R.id.username)).perform(typeText(wName));
        onView(withId(R.id.email)).perform(typeText(wEmail));
        onView(withId(R.id.password)).perform(typeText(wPass));
        onView(withId(R.id.confirmPassword)).perform(typeText(wPass));
        closeSoftKeyboard();

        //click the login button
        onView(withId(R.id.register)).perform(click());

        onView(withId(R.id.email)).check(matches(allOf(withInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS))));
        onView(withId(R.id.password)).check(matches(allOf(withInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD))));
        onView(withId(R.id.confirmPassword)).check(matches(allOf(withInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD))));
    }


    private void pauseTestFor (long milliseconds){
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() throws Exception {
    }
}