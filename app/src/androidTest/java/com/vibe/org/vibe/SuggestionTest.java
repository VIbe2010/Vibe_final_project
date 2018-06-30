package com.vibe.org.vibe;

import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewAssertion;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isSelected;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withInputType;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.AllOf.allOf;

public class SuggestionTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private String name = "selam";
    private String password = "password";
    private String suggestedPage = "SUGGESTED PLACES";
    private String map = "map";
    private String comment= "This is a really great place";
    private String vibe = "happy";
    @Before
    public void setUp() throws Exception{

    }


    //empty vibe entered
   @Test
   public void EmptySuggestion(){
        //login
       onView(withId(R.id.username)).perform(typeText(name));
       onView(withId(R.id.password)).perform(typeText(password));
       onView(withId(R.id.login)).perform(click());
       //choose spinner ?

       onView(withId(R.id.go_button)).perform(click());

       //check ??
        onView(withId(R.id.suggestions)).check(matches(suggestedPage));
   }

    //vibe choosen
    @Test
    public void selectMood(){
        //login
        onView(withId(R.id.username)).perform(typeText(name));
        onView(withId(R.id.password)).perform(typeText(password));
        onView(withId(R.id.login)).perform(click());
        //choose spinner
        onView(withId(R.id.spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(vibe))).perform(click());

        onView(withId(R.id.spinner)).check(matches(withSpinnerText(vibe)));
    }

    @Test
    public void suggestion(){
        //login
        onView(withId(R.id.username)).perform(typeText(name));
        onView(withId(R.id.password)).perform(typeText(password));
        onView(withId(R.id.login)).perform(click());
        //choose spinner ?

        onView(withId(R.id.spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(vibe))).perform(click());

        onView(withId(R.id.go_button)).perform(click());

        onView(withId(R.id.suggestedPage)).check(matches(isDisplayed()));

    }

    //view profile clicked
    @Test
    public void viewProfile(){
        //login
        onView(withId(R.id.username)).perform(typeText(name));
        onView(withId(R.id.password)).perform(typeText(password));
        onView(withId(R.id.login)).perform(click());
        //choose spinner ?

        onView(withId(R.id.spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(vibe))).perform(click());

        onView(withId(R.id.go_button)).perform(click());

        onView(withId(R.id.viewProfile)).perform(click());


        onView(withId(R.id.placeName)).check(matches(isDisplayed()));

    }
    @Test
    public void vewOnMap(){
        onView(withId(R.id.username)).perform(typeText(name));
        onView(withId(R.id.password)).perform(typeText(password));
        onView(withId(R.id.login)).perform(click());
        //choose spinner ?

        onView(withId(R.id.spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(vibe))).perform(click());

        onView(withId(R.id.go_button)).perform(click());

        onView(withId(R.id.viewMap_img)).perform(click());

        //check ??
        onView(withId(R.id.map)).check(matches(isDisplayed()));

    }

    @Test
    public void rate(){
        onView(withId(R.id.username)).perform(typeText(name));
        onView(withId(R.id.password)).perform(typeText(password));
        onView(withId(R.id.login)).perform(click());
        //choose spinner ?
        onView(withId(R.id.spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(vibe))).perform(click());

        onView(withId(R.id.go_button)).perform(click());

        onView(withId(R.id.viewProfile)).perform(click());

        onView(withId(R.id.ratingBar2)).perform(withInputType(3));

        //onView(withId(R.id.ratingBar2)).toString();

        onView(withId(R.id.sendRateBtn)).perform(click());
        //check??
        onView(withId(R.id.ratingBar2)).check(matches( ));


    }


    @Test
    public void  comment(){
        onView(withId(R.id.username)).perform(typeText(name));
        onView(withId(R.id.password)).perform(typeText(password));
        onView(withId(R.id.login)).perform(click());
        //choose spinner ?
        onView(withId(R.id.spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(vibe))).perform(click());

        onView(withId(R.id.go_button)).perform(click());

        onView(withId(R.id.viewProfile)).perform(click());

        onView(withId(R.id.editText2)).perform(typeText(comment));
        closeSoftKeyboard();
        onView(withId(R.id.comment)).perform(click());
        //check
        onView(withText(comment)).perform((ViewAction) isDisplayed());
    }

    @Test
    public void emptyComment(){
        onView(withId(R.id.username)).perform(typeText(name));
        onView(withId(R.id.password)).perform(typeText(password));
        onView(withId(R.id.login)).perform(click());
        //choose spinner ?
        onView(withId(R.id.spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(vibe))).perform(click());

        onView(withId(R.id.go_button)).perform(click());

        onView(withId(R.id.viewProfile)).perform(click());

        onView(withId(R.id.comment)).perform(click());

        //check
        onView(withText(" ")).perform((doesNotExist());
    }

    @After
    public void tearDown()throws Exception{

    }

}
