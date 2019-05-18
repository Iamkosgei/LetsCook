package com.kosgei.letscook.ui;

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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kosgei.letscook.R;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.name) EditText names;
    @BindView(R.id.email) EditText email;
    @BindView(R.id.password) EditText password;
    @BindView(R.id.confirm_password) EditText confirm_password;
    @BindView(R.id.sign_up) Button signUp;

    @BindView(R.id.loading)
    AVLoadingIndicatorView loadingIndicatorView;


    private FirebaseAuth mAuth;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ButterKnife.bind(this);

        loadingIndicatorView.hide();


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        signUp.setOnClickListener(this);

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
        else if(!(confirm_password.getText().toString().trim().equals(password.getText().toString().trim())))
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
                loadingIndicatorView.show();
                signUp.setEnabled(false);
                mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    loadingIndicatorView.hide();
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    mDatabase.child("Users").child(user.getUid()).child("name").setValue(names.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);

                                            //avoids using the back btn to go back to the signup activity after successful login
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                        }
                                    });
                                } else {
                                    loadingIndicatorView.hide();
                                    Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    signUp.setEnabled(true);
                                }
                            }
                        });
            }

        }
    }
}
