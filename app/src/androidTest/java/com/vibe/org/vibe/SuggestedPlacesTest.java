package com.vibe.org.vibe;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.ArrayList;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SuggestedPlacesTest {


    SuggestedPlaces suggestedPlaces;

    @Rule
    public ActivityTestRule<TestActivity> mActivityTestRule = new ActivityTestRule<>(TestActivity.class);
    private TestActivity mActivity =  null;
    private Context context;


    ArrayList<String> placeUserNameList;
    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch(){
        RelativeLayout rlContainer = (RelativeLayout) mActivity.findViewById(R.id.test_container);
        assertNotNull(rlContainer);

        SuggestedPlaces fragment = new SuggestedPlaces();
        mActivity.getFragmentManager().beginTransaction().add(rlContainer.getId(), fragment).commitAllowingStateLoss();
        getInstrumentation().waitForIdleSync();

        View view = fragment.getView().findViewById(R.id.fixed);
        assertNotNull(view);

    }

}