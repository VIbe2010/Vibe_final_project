package com.vibe.org.vibe;

/**
 * Created by Hena on 6/24/2018.
 */
public class LoginService {
    private LoginFragmentActivity loginFrag;
    String username,password;
    private  LoginView view;
    Boolean value;
    public Boolean login(String james, String bond) {
        username = loginFrag.userName1.getText().toString();
        password = loginFrag.passWord1.getText().toString();
        if (!username.isEmpty() && username != null && !password.isEmpty() && password != null) {
            view.startProfileActivity();
            value=true;
        }
        else  {
            view.showLoginError(R.string.login_failed);
            value=false;
        }
        return value;
    }
}
