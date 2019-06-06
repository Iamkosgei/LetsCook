package com.kosgei.letscook.ui;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.kosgei.letscook.Constants;
import com.kosgei.letscook.R;
import com.kosgei.letscook.adapters.RecipeListAdapter;
import com.kosgei.letscook.models.Category;
import com.kosgei.letscook.models.Meal;
import com.kosgei.letscook.services.EdamamService;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeListFragment extends Fragment {
    @BindView(R.id.recipeListRecycler)
    RecyclerView recyclerView;

    private RecipeListAdapter recipeListAdapter;
    public ArrayList<Meal> mMeals = new ArrayList<>();

    ArrayList<Meal> recipes;

    ArrayList<String> mealIds;

    Category category;

//    @BindView(R.id.image)
//    ImageView image;

//    @BindView(R.id.description)
//    TextView description;

//    @BindView(R.id.title)
//    TextView title;

    @BindView(R.id.shimmer_view_container)
    ShimmerFrameLayout mShimmerViewContainer;



    public RecipeListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        ButterKnife.bind(this,view);

//        category = Parcels.unwrap(getIntent().getParcelableExtra("category"));
//
//        description.setText(category.getCategory());
//        title.setText(category.getName());
//        Picasso.get().load(category.getUrl()).into(image);

        // Inflate the layout for this fragment
        getRecipes(Constants.CATEGORY);
        return view;
    }

    private void getRecipes(String category)
    {
        final EdamamService edamamService = new EdamamService();
        EdamamService.findRecipes(category, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                mealIds = edamamService.processFindRecipeResults(response);

                for(String id: mealIds)
                {
                    EdamamService.findDetails(id, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            recipes = edamamService.processMealsResults(response);

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    recipeListAdapter = new RecipeListAdapter(getActivity(),recipes);
                                    recyclerView.setAdapter(recipeListAdapter);
                                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                                    recyclerView.setLayoutManager(layoutManager);
                                    recyclerView.setHasFixedSize(true);

                                    mShimmerViewContainer.stopShimmer();
                                    mShimmerViewContainer.setVisibility(View.GONE);
                                }
                            });


                        }
                    });
                }
            }
        });

    }

    @Override
    // Method is now void, menu inflater is now passed in as argument:
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        // Call super to inherit method from parent:
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_search, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
//                addToSharedPreferences(query);
                getRecipes(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
