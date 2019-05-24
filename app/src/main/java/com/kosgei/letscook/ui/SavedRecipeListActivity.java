package com.kosgei.letscook.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kosgei.letscook.Constants;
import com.kosgei.letscook.R;
import com.kosgei.letscook.adapters.FirebaseRecipeViewHolder;
import com.kosgei.letscook.models.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SavedRecipeListActivity extends AppCompatActivity {
    private DatabaseReference mRecipeReference;
    private FirebaseRecyclerAdapter<Recipe, FirebaseRecipeViewHolder> mFirebaseAdapter;

    @BindView(R.id.recipeListRecycler)
    RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        ButterKnife.bind(this);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        mRecipeReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_RECIPES).child(uid);
        setUpFirebaseAdapter();
    }

    private void setUpFirebaseAdapter(){
        FirebaseRecyclerOptions<Recipe> options =
                new FirebaseRecyclerOptions.Builder<Recipe>()
                        .setQuery(mRecipeReference, Recipe.class)
                        .build();

        mFirebaseAdapter = new FirebaseRecyclerAdapter<Recipe, FirebaseRecipeViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FirebaseRecipeViewHolder firebaseRecipeViewHolder, int position, @NonNull Recipe recipe) {
                firebaseRecipeViewHolder.bindRestaurant(recipe);
            }

            @NonNull
            @Override
            public FirebaseRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list_item, parent, false);
                return new FirebaseRecipeViewHolder(view);
            }
        };

        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mRecyclerView.setAdapter(mFirebaseAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mFirebaseAdapter!= null) {
            mFirebaseAdapter.stopListening();
        }
    }
}
