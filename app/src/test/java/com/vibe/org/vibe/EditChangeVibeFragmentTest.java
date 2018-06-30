package com.vibe.org.vibe.Important;

import com.vibe.org.vibe.Important.R;
import android.widget.Button;
import android.widget.Spinner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Aman on 27/06/2018.
 */
public class EditChangeVibeFragmentTest {
    Spinner mSpinner;
    Button changeVibe;
    EditChangeVibeFragment mEditChangeVibeFragment= new EditChangeVibeFragment();

    @Before
    public void setUp() throws Exception {

    }


    @Test
    public void onCreateView() throws Exception {
        mSpinner=mEditChangeVibeFragment.getActivity().findViewById(R.id.spinner_edit);
        changeVibe=mEditChangeVibeFragment.getActivity().findViewById(R.id.ChangeVibe_changevibe_edit);
        assertNotNull(mSpinner);
        assertNotNull(changeVibe);
    }

}