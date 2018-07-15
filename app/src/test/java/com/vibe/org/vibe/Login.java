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
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onIdle;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.supportsInputMethods;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withInputType;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.*;

public class Login {

    @Rule
    public ActivityTestRule<MainActivity> mActivity = new ActivityTestRule<MainActivity>(MainActivity.class);

    private MainActivity mActivitys = null;

    Instrumentation.ActivityMonitor monitor,monitor2,monitor3,monitor4;

    {
        monitor = getInstrumentation().addMonitor(HomeController.class.getName(),null,false);
        monitor2 = getInstrumentation().addMonitor(UserRegisterFragment.class.getName(), null, false);
        monitor3 = getInstrumentation().addMonitor(PlaceRegisterFragment.class.getName(),null,false);
        monitor4 = getInstrumentation().addMonitor(ResetPasswordFragment.class.getName(),null,false);
    }


    private String uName = "Johngmail.com";
    private String uPass = "32342";

    private String wPass = "1243452";


    @Before
    public void setUp() throws Exception {
        mActivitys = mActivity.getActivity();
    }

    @Test
    public void loginSuccess(){
        Log.e("@Test", "Performing Login success test");
        //check if the activity is not null
        assertNotNull(mActivitys.findViewById(R.id.login));

        //type into the edit texts user name and password
        onView(withId(R.id.username)).perform(typeText(uName));
        onView(withId(R.id.password)).perform(typeText(uPass));
        closeSoftKeyboard();

        //click the login button
        onView(withId(R.id.login)).perform(click());
        //see if it gave the second activity
        Activity secondActivity = getInstrumentation().waitForMonitorWithTimeout(monitor,5000);
        assertNotNull(secondActivity);
    }
    @Test
    public void loginFail() {
        Log.e("@Test", "Performing Login failure test");
        //check if the activity is not null
        assertNotNull(mActivitys.findViewById(R.id.password));

        //type into the edit texts user name and password
        onView(withId(R.id.username)).perform(typeText(uName));
        onView(withId(R.id.password)).perform(typeText(wPass));
        closeSoftKeyboard();

        //click the login button
        onView(withId(R.id.button)).perform(click());
        //see if the result is equal to the error holder
        onView(withId(R.id.username)).check(matches(allOf(withInputType(InputType.TYPE_CLASS_TEXT))));
        onView(withId(R.id.password)).check(matches(allOf(withInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD))));
    }

    @Test
    public void userRegister(){
        Log.e("@Test","Performing Sign in success test");
        //check if the activity is not null
        assertNotNull(mActivitys.findViewById(R.id.userRegister));

        //click the login button
        onView(withId(R.id.userRegister)).perform(click());
        //check if it activates the next activity
        Activity thirdActivity = getInstrumentation().waitForMonitorWithTimeout(monitor2,5000);
        assertNotNull(thirdActivity);
    }

    @Test
    public void placeRegister(){
        Log.e("@Test","Performing Sign in success test");
        //check if the activity is not null
        assertNotNull(mActivitys.findViewById(R.id.placeRegister));

        //click the login button
        onView(withId(R.id.placeRegister)).perform(click());
        //check if it activates the next activity
        Activity fourthActivity = getInstrumentation().waitForMonitorWithTimeout(monitor3,5000);
        assertNotNull(fourthActivity);
    }

    @Test
    public void forgotPassword(){
        Log.e("@Test","Performing Sign in success test");
        //check if the activity is not null
        assertNotNull(mActivitys.findViewById(R.id.resetPassword));

        //click the login button
        onView(withId(R.id.resetPassword)).perform(click());
        //check if it activates the next activity
        Activity fifthActivity = getInstrumentation().waitForMonitorWithTimeout(monitor4,5000);
        assertNotNull(fifthActivity);
    }

        private void pauseTestFor (long milliseconds){
            try {
                Thread.sleep(milliseconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    @After
    public void tearDown () throws Exception {
        }
    }