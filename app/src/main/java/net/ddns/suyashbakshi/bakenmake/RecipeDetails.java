package net.ddns.suyashbakshi.bakenmake;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class RecipeDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecipeDetailsFragment fragment = new RecipeDetailsFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.recipe_detail_container,fragment)
                .commit();
    }

}
