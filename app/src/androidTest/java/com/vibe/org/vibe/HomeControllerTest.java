package com.vibe.org.vibe;

import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static  org.mockito.Mockito.*;

import org.mockito.invocation.InvocationOnMock;
import  org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.*;

//test if the fragment is launched or not
@RunWith(MockitoJUnitRunner.class)
public class HomeControllerTest {
    PlaceFragment fragmentToTest;

    @Rule
    public ActivityTestRule<TestActivity> mActivityTestRule = new ActivityTestRule<TestActivity>(TestActivity.class);

    private TestActivity mActivity = null;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();

    }

    //This mocks all the methods to return some default value
    @Mock
    JSONArray jsonArray;
    JSONObject jsonObject;
    @Test
    public void toIndividual() throws Exception {
        when(jsonObject.getString(String.valueOf(anyInt()))).thenReturn("User");
        boolean isPlace = false;
        RelativeLayout rlContainer = (RelativeLayout) mActivity.findViewById(R.id.test_container);
        assertNotNull(rlContainer);
        IndividualFragment fragmentToTest = new IndividualFragment();
        mActivity.getFragmentManager().beginTransaction().add(rlContainer.getId(),fragmentToTest).commit();
        View view = fragmentToTest.getView().findViewById(R.id.fixed);
        assertNotNull(view);

    }
    public void toPlaces() throws Exception {
        when(jsonObject.getString(String.valueOf(anyInt()))).thenReturn("User");
        boolean isPlace = true;
        RelativeLayout rlContainer = (RelativeLayout) mActivity.findViewById(R.id.test_container);
        assertNotNull(rlContainer);
        fragmentToTest = new PlaceFragment();
        mActivity.getFragmentManager().beginTransaction().add(rlContainer.getId(),fragmentToTest).commit();
        View view = fragmentToTest.getView().findViewById(R.id.fixed);
        assertNotNull(view);

    }
    @After
    public void tearDown() throws Exception {
        mActivity = null;
        mActivity = null;

    }
}

}