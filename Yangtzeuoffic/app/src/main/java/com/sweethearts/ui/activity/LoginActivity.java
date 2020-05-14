package com.sweethearts.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.sweethearts.R;
import com.sweethearts.presenter.LoginPresenter;
import com.sweethearts.ui.view.LoginView;

public class LoginActivity extends AppCompatActivity implements LoginView {

    private EditText userName_ET;
    private EditText password_ET;
    private Button login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        setEvents();
    }

    public EditText getUserName_ET() {
        return userName_ET;
    }

    public EditText getPassword_ET() {
        return password_ET;
    }

    public Button getLogin_btn() {
        return login_btn;
    }

    public void init() {
        userName_ET = findViewById(R.id.username);
        password_ET = findViewById(R.id.password);
        login_btn =  findViewById(R.id.login);
    }


    public void setEvents(){
        LoginPresenter loginPresenter = new LoginPresenter(this, this);
        loginPresenter.loadLoginEvent();
    }
}
