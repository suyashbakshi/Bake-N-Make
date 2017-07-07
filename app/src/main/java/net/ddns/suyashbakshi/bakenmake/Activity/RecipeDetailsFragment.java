package net.ddns.suyashbakshi.bakenmake.Activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import net.ddns.suyashbakshi.bakenmake.Adapters.RecipeStepListAdapter;
import net.ddns.suyashbakshi.bakenmake.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class RecipeDetailsFragment extends Fragment {

    public static RecipeStepListAdapter mAdapter;

    public RecipeDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        Intent intent = getActivity().getIntent();
        StringBuffer buff = new StringBuffer();
        mAdapter = new RecipeStepListAdapter(new ArrayList(), getContext());
        try {
            if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {

                TextView ingredients_tv = (TextView) rootView.findViewById(R.id.ingredients_tv);
                RecyclerView stepListView = (RecyclerView) rootView.findViewById(R.id.recipe_step_list);
                stepListView.setAdapter(mAdapter);
                stepListView.setLayoutManager(new GridLayoutManager(getContext(),1));

                JSONObject det = new JSONObject(intent.getStringExtra(Intent.EXTRA_TEXT));
                JSONArray ingredients = det.getJSONArray("ingredients");
                JSONArray steps = det.getJSONArray("steps");

                for (int i = 0; i < ingredients.length(); i++) {
                    JSONObject item = ingredients.getJSONObject(i);
                    buff.append("-> " + item.getString("ingredient") + "\n    Quantity : " + item.getString("quantity") + " " + item.getString("measure") + "\n\n");
                }
                for (int i = 0; i < steps.length(); i++) {
                    JSONObject step = steps.getJSONObject(i);
                    mAdapter.addItem(step.toString());
                }

                stepListView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        ((Callback) getActivity())
                                .onItemSelected(mAdapter.getItem(position));
                    }
                }));

                ingredients_tv.setText(buff.toString());
                mAdapter.notifyDataSetChanged();
            }
        } catch (JSONException ex) {
        }

        return rootView;
    }

    public interface Callback {

        public void onItemSelected(String data);
    }
}
