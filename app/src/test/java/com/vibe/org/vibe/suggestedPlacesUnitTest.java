package com.vibe.org.vibe;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
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
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SuggestedPlacesUnitTest {

    SuggestedPlaces suggestedPlaces;

    @Rule
    public ActivityTestRule<TestActivity> mActivityTestRule = new ActivityTestRule<>(TestActivity.class);
    private TestActivity mActivity =  null;
    private Context context;

    private SharedPreferences sharedPreferences;

    @Mock
    private ServerHelper serverHelper;
    private String mockArray = "[" +
            "{\"userName\":\"Romina\",\"email\":\"romina@gmail.com\",\"loaction\": [10,20],\"vibe\":\"happy\",\"ratign\":5,\"comments\":[[comment1],[comment2]]}," +
            "{\"userName\":\"Malda\",\"email\":\"maleda@gmail.com\",\"loaction\": [11,22],\"vibe\":\"happy\",\"rating\":4.5,\"comments\":[[comment1], [comment2],[comment3]]}]";

    ArrayList<String> placeUserNameList;
    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
        MockitoAnnotations.initMocks(this);
        serverHelper = new ServerHelper();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Test
    public void testParseJOSNArray(){
        suggestedPlaces = new SuggestedPlaces();
        suggestedPlaces.parseJSONArray(mockArray);
        parseJSONArrayGivenMockList();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void parseJSONArrayGivenMockList(){

        suggestedPlaces = new SuggestedPlaces();

        //a mock object is shell of an object that wil do whatever we want
        ServerHelper serverHelper = mock(ServerHelper.class);
        //mock the arrayList

        placeUserNameList = new ArrayList<>();
        when(serverHelper.placeUserNames.add(anyString())).thenReturn(placeUserNameList.add(anyString()));

        assertEquals(2, placeUserNameList.size());

    }

    @Test
    public void testGetProfilePic() throws JSONException {
        suggestedPlaces = new SuggestedPlaces();
        JSONArray jsonArray = new JSONArray(mockArray);
        verify(suggestedPlaces, times(mockArray.length())).getProfilePic(anyString(), jsonArray);
    }

    @Test
    public void testGotPlace() throws JSONException {
        suggestedPlaces = new SuggestedPlaces();
        JSONArray jsonArray = new JSONArray(mockArray);
        verify(suggestedPlaces, times(mockArray.length())).gotPlace((Bitmap) any(), jsonArray);
    }


    @After
    public void tearDown() throws Exception {
        mActivity = null;
        mActivity = null;

    }
}