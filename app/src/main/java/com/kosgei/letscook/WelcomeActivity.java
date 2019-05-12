package com.kosgei.letscook;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomeActivity extends AppCompatActivity {
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
    }
}
