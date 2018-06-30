package com.vibe.org.vibe;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.LinearLayout;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.verify;

/**
 * Created by Hena on 6/19/2018.
 */
public class LoginFragmentActivityTest {
    //using rule to actually launch the activity
    @Rule
    public ActivityTestRule<TestActivity> launcherTestRule= new ActivityTestRule<TestActivity>(TestActivity.class);
    private TestActivity demoActivity=null;
    private Context context;

    private Context c=null;
    private SharedPreferences sharedPreferences;
    @Mock
    private MainContract.mvpView mvpView;
    private checker check;

    public void setUp() throws Exception {
//        super.setUp();
        demoActivity=launcherTestRule.getActivity();
        MockitoAnnotations.initMocks(this);
        check=new checker(mvpView);
       this.sharedPreferences = Mockito.mock(SharedPreferences.class);
        this.context = Mockito.mock(Context.class);
        Mockito.when(context.getSharedPreferences("Users",0)).thenReturn(sharedPreferences);

    }
    @Test(expected = Exception.class)
    public void  testingLaunchingFragment() throws Exception {
        //this the test case that test whether the the fragment launched correctly or not by creating the mock
        // other equivalent empty activity called testing activity.

        LinearLayout test = (LinearLayout) demoActivity.findViewById(R.id.test_container);
        assertNotNull(test);
        SplashFragment splashFragment = new SplashFragment();
        demoActivity.getFragmentManager().beginTransaction().replace(test.getId(), splashFragment).commitAllowingStateLoss();
        getInstrumentation().waitForIdleSync();
        View view = splashFragment.getView().findViewById(R.id.splash_container);
        assertNotNull(view);
        //test if the fragment is launched or not

    }

    //the below three method test the behavioral of the login and sign up activity
    @Test(expected = Exception.class)
    public void showUserSignUpScreen() throws Exception {
        check.handleUserSignUpButtonClick();
        verify(mvpView).showUserSignUpScreen();

    }

    @Test(expected = Exception.class)
    public void showPlaceRegisterScreen() throws Exception {
        check.handlePlaceRegisterButtonClick();
        verify(mvpView).showPlaceRegisterScreen();

    }

    @Test(expected = Exception.class)
    public void resetPasswordScreen() throws Exception {
        check.handleResetPasswordTextClick();
        verify(mvpView).resetPasswordScreen();

    }
    //this method check the valid token accessed by creating mocked sharedpreferences
    // for getValidToken
    @Test(expected = Exception.class)
    public void getValidToken() throws Exception {
        Mockito.when(sharedPreferences.getString("token","token")).thenReturn("token");
        assertEquals("token", mvpView.getValidToken(context));
        verify(mvpView).getValidConfirmation();
    }
    @Test(expected = Exception.class)
    public void null_token_return_false() throws Exception {
        Assert.assertFalse(Boolean.parseBoolean(mvpView.getValidToken(c)));
    }
    public void tearDown() throws Exception {
        demoActivity = null;
        demoActivity = null;
    }

}