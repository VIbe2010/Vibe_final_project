package com.vibe.org.vibe;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Hena on 6/26/2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserRegisterFragmentValidationTest {
    @Mock
    private LoginPresenter presenter;
    @Mock
    private LoginView view;
    @Mock
    private LoginService service;

    @Before
    public void setUp() throws Exception {
        presenter=new LoginPresenter(view,service);
    }
    @Test
    public void ShouldShowErrorWhenUserNameIsEmpty() throws Exception{
        when(view.getUserName()).thenReturn("");
        presenter.validate();
        verify(view).showUserNameError(R.string.username_error);
    }
    @Test
    public void ShouldShowErrorWhenPasswordIsEmpty() throws Exception{
        when(view.getUserName()).thenReturn("honey");
        when(view.getPassword()).thenReturn("");
        presenter.validate();
        verify(view).showPasswordError(R.string.password_error);
    }
    @Test
    public void validate_userName_null_returnsFalse() throws Exception {
        Assert.assertFalse(presenter.isUserNameValid(null));
    }

    @Test
    public void validate_password_null_returnsFalse() throws Exception {

        Assert.assertFalse(presenter.isPasswordValid(null));
    }
    @Test
    public void StartWhenBothUsernameAndPsswordCorrect() throws  Exception{
        when(view.getUserName()).thenReturn("honey");
        when(view.getPassword()).thenReturn("hena");
        when(service.login("honey","hena")).thenReturn(true);
        presenter.validate();
        verify(view).startProfileActivity();

    }
    @Test
    public void ShouldBeErrorWhenUserNameAndPasswordInValidate() throws Exception{
        when(view.getUserName()).thenReturn("honey");
        when(view.getPassword()).thenReturn("fufa");
        when(service.login("honey","fufa")).thenReturn(false);
        presenter.validate();
        verify(view).showLoginError(R.string.login_failed);
    }

    @After
    public void tearDown() throws Exception {

    }

}