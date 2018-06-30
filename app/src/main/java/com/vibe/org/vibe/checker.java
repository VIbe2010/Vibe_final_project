package com.vibe.org.vibe;

/**
 * Created by Hena on 6/19/2018.
 */

public class checker implements MainContract.presenter {
    private MainContract.mvpView mView;
    checker(MainContract.mvpView view){
        mView=view;

    }
    @Override
    public void handleUserSignUpButtonClick() {
        mView.showUserSignUpScreen();

    }

    @Override
    public void handlePlaceRegisterButtonClick() {
        mView.showPlaceRegisterScreen();

    }

    @Override
    public void handleResetPasswordTextClick() {
        mView.resetPasswordScreen();

    }
}
