package com.kosgei.letscook.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.kosgei.letscook.R;
import com.kosgei.letscook.adapters.RecipePagerAdapter;
import com.kosgei.letscook.models.Meal;
import com.kosgei.letscook.models.Recipe;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailActivity extends AppCompatActivity {
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private RecipePagerAdapter recipePagerAdapter;
    ArrayList<Meal> recipes = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        ButterKnife.bind(this);

        recipes = Parcels.unwrap(getIntent().getParcelableExtra("recipes"));
        int startingPosition = getIntent().getIntExtra("position",0);
        recipePagerAdapter = new RecipePagerAdapter(getSupportFragmentManager(),recipes);
        viewPager.setAdapter(recipePagerAdapter);
        viewPager.setCurrentItem(startingPosition);

    }
}
