package com.kosgei.letscook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.login)
    Button login;

    @BindView(R.id.loading)
    AVLoadingIndicatorView loadingIndicatorView;


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        ButterKnife.bind(this);

        login.setOnClickListener(this);

        loadingIndicatorView.hide();
    }

    @Override
    public void onClick(View v) {
        if (v == login) {
            if (isValid()) {
                loadingIndicatorView.show();
                login.setEnabled(false);
                mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                    intent.putExtra("email", email.getText().toString());
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    loadingIndicatorView.hide();
                                    startActivity(intent);
//                                    finish();
                                } else {
                                    loadingIndicatorView.hide();
                                    login.setEnabled(true);
                                    Toast.makeText(LoginActivity.this, task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }

        }

    }

    private boolean isValid() {
        boolean valid = false;


        if (password.getText().toString().trim().isEmpty()) {
            password.setError("Please a password");
        } else if (email.getText().toString().trim().isEmpty()) {
            email.setError("Please enter your email");
        } else if (!(Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches())) {
            email.setError("Email address not valid");
        } else {
            valid = true;
        }


        return valid;
    }
}
