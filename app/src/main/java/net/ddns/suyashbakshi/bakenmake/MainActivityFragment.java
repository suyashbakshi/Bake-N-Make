package net.ddns.suyashbakshi.bakenmake;

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

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private MainRecipeListAdapter mAdapter;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        Log.v("instance_tag","onCreateView");
        mAdapter = new MainRecipeListAdapter(getContext(),0,new ArrayList<String>());
        ListView recipeListView = (ListView)rootView.findViewById(R.id.recipeListView);
        recipeListView.setAdapter(mAdapter);

        recipeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String recipe = mAdapter.getItem(i).toString();
                Intent detailIntent = new Intent(getContext(),RecipeDetails.class).putExtra(Intent.EXTRA_TEXT,recipe);
                startActivity(detailIntent);
            }
        });
        FetchRecipe fetch = new FetchRecipe(getContext(),mAdapter);
        fetch.execute();

        return rootView;
    }
}
