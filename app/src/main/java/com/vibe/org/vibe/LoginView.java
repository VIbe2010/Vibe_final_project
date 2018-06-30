package com.vibe.org.vibe;

/**
 * Created by Hena on 6/24/2018.
 */
public interface LoginView {
    String getUserName();

    void showUserNameError(int resId);

    Boolean showUserNameSuccess(int username_success);

    String getPassword();

    void showPasswordError(int password_error);

    void startProfileActivity();

    void showLoginError(int login_failed);
}
