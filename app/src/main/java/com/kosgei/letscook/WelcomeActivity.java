package com.kosgei.letscook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.to_login) Button toLogin;
    @BindView(R.id.to_signup) Button toSignUp;
    @BindView(R.id.title) TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        ButterKnife.bind(this);

        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/Hollaboi FREE.ttf");
        title.setTypeface(typeface);

        toLogin.setOnClickListener(this);
        toSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == toLogin)
        {
            startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
        }

        else if (v == toSignUp)
        {
            startActivity(new Intent(WelcomeActivity.this,SignUpActivity.class));
        }

    }
}
