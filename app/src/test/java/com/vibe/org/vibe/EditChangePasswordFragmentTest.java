package com.vibe.org.vibe.Important;

import com.vibe.org.vibe.Important.R;
import android.widget.EditText;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Aman on 26/06/2018.
 */
public class EditChangePasswordFragmentTest {
    EditText oldPassword;
    EditText newPassword;


    EditChangePasswordFragment mEditChangePasswordFragment= new EditChangePasswordFragment();
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void onCreateView() throws Exception {
        oldPassword= mEditChangePasswordFragment.getActivity().findViewById(R.id.oldpassword_edit);
        newPassword= mEditChangePasswordFragment.getActivity().findViewById(R.id.newpassword_edit);

        assertNotNull(oldPassword);
        assertNotNull(newPassword);
    }


    @Test
    public void verify() throws Exception {
        assertNotEquals(oldPassword.getText().toString(), "");
        assertNotEquals(newPassword.getText().toString(), "");

        assertNotEquals(oldPassword.getText().toString(), null);
        assertNotEquals(newPassword.getText().toString(), null);

        assertTrue(oldPassword.getText().toString().length()>=6);
        assertTrue(newPassword.getText().toString().length()>=6);

    }

}