package com.kosgei.letscook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.name) EditText names;
    @BindView(R.id.email) EditText email;
    @BindView(R.id.password) EditText password;
    @BindView(R.id.confirm_password) EditText confirm_password;
    @BindView(R.id.sign_up) Button signUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ButterKnife.bind(this);

        signUp.setOnClickListener(this);

        if (isValid())
        {

        }

    }

    public boolean isValid()
    {
        boolean valid = false;

        if (names.getText().toString().trim().isEmpty())
        {
            names.setError("Please enter your name");
        }
        else if(email.getText().toString().trim().isEmpty())
        {
            email.setError("Please enter your email");
        }
        else if(!(Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()))
        {
            email.setError("Please enter a valid email");
        }
        else if(password.getText().toString().trim().isEmpty())
        {
            password.setError("Please enter a password");
        }
        else if(confirm_password.getText().toString().trim().isEmpty())
        {
            confirm_password.setError("Please enter a password");
        }
        else if(!(confirm_password.getText().toString().trim().equals(password.getText().toString())))
        {
            confirm_password.setError("Password doesn't match");
        }

        else {
            valid = true;
        }
        return valid;
    }

    @Override
    public void onClick(View v) {

        if (v==signUp)
        {
            if(isValid())
            {
                Toast.makeText(this, names.getText() + " welcome", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
