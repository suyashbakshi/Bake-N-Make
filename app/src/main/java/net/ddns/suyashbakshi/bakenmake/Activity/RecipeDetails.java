package net.ddns.suyashbakshi.bakenmake.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import net.ddns.suyashbakshi.bakenmake.R;

public class RecipeDetails extends AppCompatActivity implements RecipeDetailsFragment.Callback{

    private final String STEPFRAGMENT_TAG = "STAG";
    private final String VIDEOFRAGMENT_TAG = "VTAG";

    public static boolean mTwoPane;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(findViewById(R.id.video_container) != null) {
            mTwoPane = true;

            if(mTwoPane)
                Log.v("PROBLEM_TWOPANE","TRUE");

            if(savedInstanceState == null){
                getSupportFragmentManager().beginTransaction()
                        .add(new VideoActivityFragment(),VIDEOFRAGMENT_TAG)
                        .commit();
            }
        }
        else {
            mTwoPane = false;
        }
    }

    @Override
    public void onItemSelected(String data) {

        if(mTwoPane){

            Bundle args = new Bundle();
            args.putString("DATA",data);

            VideoActivityFragment videoActivityFragment = new VideoActivityFragment();
            videoActivityFragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.video_container,videoActivityFragment,VIDEOFRAGMENT_TAG)
                    .commit();
        }else {


            Intent intent = new Intent(this, VideoActivity.class);
            intent.putExtra(Intent.EXTRA_TEXT, data);
            startActivity(intent);
        }
    }
}
