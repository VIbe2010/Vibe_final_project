package com.vibe.org.vibe;

import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class EditProfileTest {
    //individual/place must login
    //changes pp, password, mood
    //presses back
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    //individual
    private String name = "selam";
    private String password = "selam123";
    private String newPassword = "123Selam";
    private String email = "selam@gmail.com";

    //Place
    private String Placename = "romina";
    private String Placepassword = "Romina";
    private String PlacenewPassword = "123romina";
    private String PlaceEmail = "romina@gmail.com";

    //location
    private String[] location = {"val", "val"};

    @Before
    public void setUp() throws Exception{

    }


    @Test
    public void testEditPasswordLogin(){
        //Login in to the page
        onView(withId(R.id.username)).perform(typeText(name));
        onView(withId(R.id.password)).perform(typeText(password));
        onView(withId(R.id.login)).perform(click());

        //after redirected to home page click edit profile
        onView(withId(R.id.editProfile)).perform(click());
        //goes to change profile page
        //perform action
        onView(withId(R.id.recycler_edit)).perform(click());
        //password
        onView(withId(R.id.oldpassword_edit)).perform(typeText(password));
        onView(withId(R.id.newpassword_edit)).perform(typeText(newPassword));

        onView(withId(R.id.changepassword_edit)).perform(click());
        //check new password is set  ??
//        onView(withId(R.id.tvChangedText)).check(matches(withText(newPassword)));
    }

    @Test
    public void testIndividualEditPasswordSignup(){
        //Login in to the page
        onView(withId(R.id.userRegister)).perform(click());
        onView(withId(R.id.username)).perform(typeText(name));
        onView(withId(R.id.email)).perform(typeText(email));
        onView(withId(R.id.password)).perform(typeText(password));
        onView(withId(R.id.confirmPassword)).perform(typeText(name));
        onView(withId(R.id.register)).perform(click());

        //after redirected to home page click edit profile
        onView(withId(R.id.editProfile)).perform(click());
        //goes to change profile page
        //perform action
        onView(withId(R.id.recycler_edit)).perform(click());
        //password
        onView(withId(R.id.oldpassword_edit)).perform(typeText(password));
        onView(withId(R.id.newpassword_edit)).perform(typeText(newPassword));

        onView(withId(R.id.changepassword_edit)).perform(click());
        //check new password is set ??
        //onView(withId(R.id.tvChangedText)).check(matches(withText(newPassword)));
    }

    @After
    public void tearDown()throws Exception{

    }

}
