package com.kosgei.letscook.ui;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.kosgei.letscook.Constants;
import com.kosgei.letscook.R;
import com.kosgei.letscook.adapters.FirebaseMealListAdapter;
import com.kosgei.letscook.models.Meal;
import com.kosgei.letscook.util.OnStartDragListener;
import com.kosgei.letscook.util.SimpleItemTouchHelperCallback;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SavedRecipeListFragment extends Fragment implements OnStartDragListener {
    private FirebaseMealListAdapter mFirebaseAdapter;
    private ItemTouchHelper mItemTouchHelper;


    @BindView(R.id.recipeListRecycler)
    RecyclerView mRecyclerView;



    public SavedRecipeListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_saved_recipe_list, container, false);
        // Inflate the layout for this fragment


        ButterKnife.bind(this,view);


        setUpFirebaseAdapter();
        return view;
    }
    private void setUpFirebaseAdapter(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        //mRecipeReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_RECIPES).child(uid);

        Query query = FirebaseDatabase.getInstance()
                .getReference(Constants.FIREBASE_CHILD_RECIPES)
                .child(uid)
                .orderByChild(Constants.FIREBASE_QUERY_INDEX);

        FirebaseRecyclerOptions<Meal> options =
                new FirebaseRecyclerOptions.Builder<Meal>()
                        .setQuery(query, Meal.class)
                        .build();

        mFirebaseAdapter = new FirebaseMealListAdapter(options,query,this,getActivity());



        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mFirebaseAdapter);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mFirebaseAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void onStart() {
        super.onStart();
        mFirebaseAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mFirebaseAdapter!= null) {
            mFirebaseAdapter.stopListening();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.stopListening();
    }

    public void onStartDrag(RecyclerView.ViewHolder viewHolder){
        mItemTouchHelper.startDrag(viewHolder);
    }

}
