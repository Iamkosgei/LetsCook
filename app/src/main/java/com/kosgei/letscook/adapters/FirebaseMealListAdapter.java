package com.kosgei.letscook.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.kosgei.letscook.Constants;
import com.kosgei.letscook.R;
import com.kosgei.letscook.models.Meal;
import com.kosgei.letscook.ui.RecipeDetailActivity;
import com.kosgei.letscook.ui.RecipeDetailFragment;
import com.kosgei.letscook.util.ItemTouchHelperAdapter;
import com.kosgei.letscook.util.OnStartDragListener;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;

public class FirebaseMealListAdapter extends FirebaseRecyclerAdapter<Meal,FirebaseRecipeViewHolder> implements ItemTouchHelperAdapter {
    private DatabaseReference mRef;
    private OnStartDragListener mOnStartDragListener;
    private Context mContext;

    private int mOrientation;

    private ChildEventListener mChildEventListener;
    private ArrayList<Meal> mMeals = new ArrayList<>();


    public FirebaseMealListAdapter(FirebaseRecyclerOptions<Meal> options,
                                   Query ref,
                                   OnStartDragListener onStartDragListener,
                                   Context context){
        super(options);
        mRef = ref.getRef();
        mOnStartDragListener = onStartDragListener;
        mContext = context;

        mChildEventListener = mRef.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mMeals.add(dataSnapshot.getValue(Meal.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    @Override
    protected void onBindViewHolder(@NonNull FirebaseRecipeViewHolder viewHolder, int position, @NonNull Meal meal) {
        viewHolder.bindRestaurant(meal);

        mOrientation = viewHolder.itemView.getResources().getConfiguration().orientation;
        if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            createDetailFragment(0);
        }

        //TODO FIX THIS
        viewHolder.image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN ) {
                    mOnStartDragListener.onStartDrag(viewHolder);
                }
                return false;
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemPosition = viewHolder.getAdapterPosition();

                if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                    createDetailFragment(itemPosition);
                } else
            {
                Intent intent = new Intent(mContext, RecipeDetailActivity.class);
                intent.putExtra("position", itemPosition);
                intent.putExtra("recipes", Parcels.wrap(mMeals));

                mContext.startActivity(intent);
            }
                    }


                });
            }


    private void createDetailFragment(int position) {
        // Creates new RestaurantDetailFragment with the given position:
        RecipeDetailFragment detailFragment =  RecipeDetailFragment.newInstance(mMeals, position);
        // Gathers necessary components to replace the FrameLayout in the layout with the RestaurantDetailFragment:
        FragmentTransaction ft = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
        //  Replaces the FrameLayout with the RestaurantDetailFragment:
        ft.replace(R.id.recipeDetailContainer, detailFragment);
        // Commits these changes:
        ft.commit();
    }
    @NonNull
    @Override
    public FirebaseRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list_item_2, parent, false);
        return new FirebaseRecipeViewHolder (view);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition){
        Collections.swap(mMeals, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        setIndexInFirebase();
        return false;
    }

    @Override
    public void onItemDismiss(int position){
        mMeals.remove(position);
        getRef(position).removeValue();

    }

    private void setIndexInFirebase() {
        for (Meal meal : mMeals) {
            int index = mMeals.indexOf(meal);
            DatabaseReference ref = getRef(index);
            meal.setIndex(Integer.toString(index));
            ref.setValue(meal);
        }
    }
}