package com.kosgei.letscook;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.kosgei.letscook.adapters.CategoryListAdapter;
import com.kosgei.letscook.models.Category;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

   @BindView(R.id.categories_recyclerView)
    RecyclerView categoryRecyclerView;

    private CategoryListAdapter mAdapter;

    //Dummy Category Data
    ArrayList<Category> categories = new ArrayList<Category>(Arrays.asList(
            new Category("Beef"),
            new Category("Pork"),
            new Category("Chicken"),
            new Category("Mutton"),
            new Category("Fish"),
            new Category("Turkey")));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //get the intent
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");


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
        emailTextView.setText(email);


        //setting the layout manager and populating the recyclerview
        mAdapter = new CategoryListAdapter(categories,getApplicationContext());
        categoryRecyclerView.setAdapter(mAdapter);

        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(HomeActivity.this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        categoryRecyclerView.setLayoutManager(layoutManager);
        categoryRecyclerView.setHasFixedSize(true);


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

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
