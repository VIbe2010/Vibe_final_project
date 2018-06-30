package com.vibe.org.vibe;

/**
 * Created by Hena on 6/24/2018.
 */
public class LoginPresenter {
    private LoginView view;
    private LoginService service;
    private LoginFragmentActivity loginFrag;
    String username,password;

    public LoginPresenter(LoginView view, LoginService service){

        this.view = view;
        this.service = service;
    }

    public void validate() {
        username=view.getUserName();
        try {
            if(username.isEmpty()){
                view.showUserNameError(R.string.username_error);
                return;
            }
            else if(username == null){
                view.showUserNameError(R.string.username_error);

            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        password=view.getPassword();
        try {
            if(password.isEmpty()){
                view.showPasswordError(R.string.password_error);
                return;
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
       boolean success= service.login(username,password);
        if(success){
            view.startProfileActivity();
            return;

        }
        view.showLoginError(R.string.login_failed);



    }
    public boolean isPasswordValid(Object o) {
        return false;
    }

    public boolean isUserNameValid(Object o) {
        return false;
    }
}
