package net.ddns.suyashbakshi.bakenmake.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import net.ddns.suyashbakshi.bakenmake.R;

public class VideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        Log.v("instance_tag", "onCreate");

        VideoActivityFragment videoActivityFragment = new VideoActivityFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.video_container,videoActivityFragment)
                .commit();
    }

}
