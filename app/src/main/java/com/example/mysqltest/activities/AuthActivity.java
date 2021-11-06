package com.example.mysqltest.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.mysqltest.R;
import com.example.mysqltest.connection.Link;
import com.example.mysqltest.connection.Proxy;
import com.example.mysqltest.devtools.ActivityTools;
import com.example.mysqltest.devtools.Encryptor;
import com.example.mysqltest.devtools.InputValidator;
import com.example.mysqltest.devtools.Logger;
import com.example.mysqltest.devtools.LoggerErrors;
import com.example.mysqltest.devtools.validators.InputLatinChars;
import com.example.mysqltest.devtools.validators.InputLengthValidator;
import com.example.mysqltest.entities.ClientProfile;
import com.example.mysqltest.entities.Profile;
import com.example.mysqltest.abstraction.I_RequestabilityJSON;
import com.example.mysqltest.abstraction.I_ValidatorRule;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AuthActivity extends AppCompatActivity implements I_RequestabilityJSON {

    private Button loginBTN;
    private EditText loginEDT;
    private EditText passwordEDT;
    private TextView errorTV;
    private Logger log;

    private AppCompatActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityTools.hideHeaderBar(this);
        log = new Logger(this);
        activity = this;
        setContentView(R.layout.auth_activity);
        setElements();
        setEvents();
    }

    private void setElements(){
        this.loginBTN = findViewById(R.id.auth_login_btn);
        this.loginEDT = findViewById(R.id.auth_login_input);
        this.passwordEDT = findViewById(R.id.auth_password_input);
        this.errorTV = findViewById(R.id.auth_error_output);
    }

    private void setEvents(){
        this.loginBTN.setOnClickListener(view -> {
            //В целом не обязательно, просто тест валидаторов
            ArrayList<I_ValidatorRule> loginRules = new ArrayList<>();
            loginRules.add(new InputLengthValidator(loginEDT, 3, 30));
            loginRules.add(new InputLatinChars(loginEDT, false, true));
            InputValidator loginValid = new InputValidator(loginRules);

            ArrayList<I_ValidatorRule> passwordRules = new ArrayList<>();
            passwordRules.add(new InputLengthValidator(passwordEDT, 3, 30));
            passwordRules.add(new InputLatinChars(loginEDT, false, true));
            InputValidator passwordValid = new InputValidator(passwordRules);
            //////////////////////////////////////////////////

            if(loginValid.check() && passwordValid.check()) {
                Map<String, String> params = new HashMap<>();
                params.put("login", loginEDT.getText().toString());
                params.put("password", Encryptor.md5(passwordEDT.getText().toString()));
                errorTV.setText("");
                new Proxy(activity, Link.AUTH_LOGIN_POST, Link.AUTH_LOGIN_POST, params).sendJSONRequest();
            } else {
                if(!passwordValid.getResult()){
                    errorTV.setText("Пароль введен не корректно!");
                    log.printClientError("Пароль введен не корректно!");
                }
                if(!loginValid.getResult()){
                    errorTV.setText("Логин введен не корректно!");
                    log.printClientError("Логин введен не корректно!");
                }
            }
        });
    }

    @Override
    public void onResponse(JSONObject JSON, String from, String key) {
        if(from.equals(Link.AUTH_LOGIN_POST)) {
            try {
                if (!JSON.getBoolean("result")) {
                    errorTV.setText(JSON.getString("error"));
                    log.printClientError("No such user");
                } else {
                    JSONObject JSONData = JSON.getJSONObject("data");
                    ClientProfile.setClientProfile(
                            new Profile(
                                    JSONData.getInt("id"),
                                    JSONData.getString("login"),
                                    JSONData.getString("mail"),
                                    JSONData.getInt("mail_confirm")
                            )
                    );
                    Intent intent = new Intent(this, MainMenuActivity.class);
                    startActivity(intent);
                    finish();
                }
            } catch (JSONException e) {
                log.printSystemError(LoggerErrors.JSON_PARSE_ERROR);
            }
        }
    }

    @Override
    public void onResponseError(String msg, String from, String key) {
        log.printClientError(msg);
        errorTV.setText(msg);
    }
}