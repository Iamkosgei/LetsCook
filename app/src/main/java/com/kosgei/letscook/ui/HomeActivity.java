package com.kosgei.letscook.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kosgei.letscook.R;
import com.kosgei.letscook.adapters.CategoryListAdapter;
import com.kosgei.letscook.adapters.LatestMealsAdapter;
import com.kosgei.letscook.adapters.RecipeListAdapter;
import com.kosgei.letscook.models.Category;
import com.kosgei.letscook.models.Meal;
import com.kosgei.letscook.services.EdamamService;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

   @BindView(R.id.categories_recyclerView)
    RecyclerView categoryRecyclerView;

   @BindView(R.id.latest_recyclerView)
   RecyclerView latestRecyclerView;

  private LatestMealsAdapter latestMealsAdapter;

    private CategoryListAdapter categoryListAdapter;

    private DatabaseReference mDatabase;

    private ArrayList<Meal> latestMeals;


    @BindView(R.id.shimmer_view_container)
    ShimmerFrameLayout mShimmerViewContainer;

    @BindView(R.id.shimmer_view_container1)
    ShimmerFrameLayout mShimmerViewContainer1;

    FirebaseUser user;

    ArrayList<Category> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);


        user = FirebaseAuth.getInstance().getCurrentUser();


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        //Update header email address with the email from previous activity
        View headerView =navigationView.getHeaderView(0);
        TextView emailTextView =  headerView.findViewById(R.id.textView_email);
        emailTextView.setText(user.getEmail());

        TextView name = headerView.findViewById(R.id.name);
        name.setText(user.getDisplayName());


        getMealCategories();
        getLatestMeals();
    }

    private void getMealCategories() {
        final EdamamService edamamService = new EdamamService();
        edamamService.getAllCategories(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                categories = edamamService.processCategoryResults(response);

                HomeActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        categoryListAdapter = new CategoryListAdapter(categories,getApplicationContext());
                        categoryRecyclerView.setAdapter(categoryListAdapter);
                        //LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
                        categoryRecyclerView.setLayoutManager(layoutManager);
                       categoryRecyclerView.setHasFixedSize(true);

                        mShimmerViewContainer1.stopShimmer();
                        mShimmerViewContainer1.setVisibility(View.GONE);


                    }
                });
            }
        });
    }

    public void getLatestMeals()
    {
        final EdamamService edamamService = new EdamamService();
        edamamService.getLatestMeals(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                latestMeals = edamamService.processLatestMealsResults(response);

                HomeActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        latestMealsAdapter = new LatestMealsAdapter(getApplicationContext(),latestMeals);
                        latestRecyclerView.setAdapter(latestMealsAdapter);
                        //LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
                        latestRecyclerView.setLayoutManager(layoutManager);
                        latestRecyclerView.setHasFixedSize(true);

                        mShimmerViewContainer.stopShimmer();
                        mShimmerViewContainer.setVisibility(View.GONE);

                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.home, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        }
        else if (id == R.id.nav_logout)
        {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(this, "Bye", Toast.LENGTH_SHORT).show();
            finish();
        }
        else if(id == R.id.nav_favourite)
        {
            startActivity(new Intent(HomeActivity.this,SavedRecipeListActivity.class));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
