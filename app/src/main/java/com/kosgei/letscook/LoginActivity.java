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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.email) EditText email;
    @BindView(R.id.password) EditText password;
    @BindView(R.id.login) Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == login)
        {
            if(isValid())
            {
                Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private boolean isValid()
    {
        boolean valid = false;


        if (password.getText().toString().trim().isEmpty())
        {
            password.setError("Please a password");
        }
        else if(email.getText().toString().trim().isEmpty())
        {
            email.setError("Please enter your email");
        }
        else if(!(Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()))
        {
            email.setError("Email address not valid");
        }
        else
        {
            valid = true;
        }


        return valid;
    }
}
