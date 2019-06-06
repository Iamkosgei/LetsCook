package com.kosgei.letscook.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.kosgei.letscook.R;
import butterknife.ButterKnife;

public class RecipeListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        ButterKnife.bind(this);
    }

}
