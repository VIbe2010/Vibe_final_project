package com.vibe.org.vibe;

import android.content.Context;

/**
 * Created by Hena on 6/19/2018.
 */

public interface MainContract {
    interface mvpView{
        void showUserSignUpScreen();
        void showPlaceRegisterScreen();
        void resetPasswordScreen();
        String getValidToken(Context context);
        void getValidConfirmation();
    }
    interface presenter{
        void handleUserSignUpButtonClick();
        void handlePlaceRegisterButtonClick();
        void handleResetPasswordTextClick();


    }
}
