package net.ddns.suyashbakshi.bakenmake.Activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import net.ddns.suyashbakshi.bakenmake.Adapters.MainRecipeListAdapter;
import net.ddns.suyashbakshi.bakenmake.NetworkServices.FetchRecipe;
import net.ddns.suyashbakshi.bakenmake.R;
import net.ddns.suyashbakshi.bakenmake.Utils.Utility;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public static MainRecipeListAdapter mAdapter;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        Log.v("instance_tag","onCreateView");
        mAdapter = new MainRecipeListAdapter(getContext(),0,new ArrayList<String>());
        GridView recipeListView = (GridView)rootView.findViewById(R.id.recipeListView);
        recipeListView.setAdapter(mAdapter);

        recipeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String recipe = mAdapter.getItem(i).toString();
                Intent detailIntent = new Intent(getContext(),RecipeDetails.class).putExtra(Intent.EXTRA_TEXT,recipe);
                startActivity(detailIntent);
            }
        });
        if(Utility.isOnline(getContext()))
            updateRecipeList();
        else
            Toast.makeText(getContext(),getString(R.string.no_network),Toast.LENGTH_LONG).show();
        return rootView;
    }

    private void updateRecipeList() {
        FetchRecipe fetch = new FetchRecipe(getContext(),mAdapter);
        fetch.execute();
    }
}
