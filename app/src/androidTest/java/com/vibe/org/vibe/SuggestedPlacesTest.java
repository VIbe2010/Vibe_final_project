package com.vibe.org.vibe;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.RelativeLayout;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertNotNull;

public class SuggestedPlacesTest {


    SuggestedPlaces suggestedPlaces;

    @Rule
    public ActivityTestRule<TestActivitySuggest> mActivityTestRule = new ActivityTestRule<>(TestActivitySuggest.class);
    private TestActivitySuggest mActivity =  null;
    private Context context;


    ArrayList<String> placeUserNameList;
    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
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
