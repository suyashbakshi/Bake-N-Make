package net.ddns.suyashbakshi.bakenmake.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import net.ddns.suyashbakshi.bakenmake.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A placeholder fragment containing a simple view.
 */
public class VideoActivityFragment extends Fragment {

    JSONObject data;

    @Override
    public void onStop() {
        super.onStop();
        player.release();
    }

    private SimpleExoPlayerView videoView;
    private SimpleExoPlayer player;

    private TextView descTv;
    private Button play_next, play_previous;

    TrackSelector trackSelector;
    LoadControl loadControl;
    int position = 0, i;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.v("instance_tag", "onConfigChanged");
        if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            //Toast.makeText(getContext(), "Portrait", Toast.LENGTH_SHORT).show();
            descTv.setVisibility(View.VISIBLE);
            play_next.setVisibility(View.VISIBLE);
            play_previous.setVisibility(View.VISIBLE);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            videoView.setLayoutParams(layoutParams);
        }
        else if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            descTv.setVisibility(View.GONE);
            play_next.setVisibility(View.GONE);
            play_previous.setVisibility(View.GONE);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            );
            videoView.setLayoutParams(layoutParams);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        player.release();
    }

    public VideoActivityFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v("instance_tag", "onResumeFragment");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_video, container, false);
        Log.v("instance_tag", "onCreateView");
        videoView = (SimpleExoPlayerView) rootView.findViewById(R.id.videoView);
        descTv = (TextView) rootView.findViewById(R.id.video_description_tv);
        play_next = (Button) rootView.findViewById(R.id.play_next_button);
        play_previous = (Button) rootView.findViewById(R.id.play_previous_button);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.WRAP_CONTENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT
//        );
//        videoView.setLayoutParams(layoutParams);
        if(player == null) {
            trackSelector = new DefaultTrackSelector();
            loadControl = new DefaultLoadControl();
            player = ExoPlayerFactory.newSimpleInstance(getContext(),trackSelector,loadControl);
            videoView.setPlayer(player);

            player.addListener(new ExoPlayer.EventListener() {
                @Override
                public void onTimelineChanged(Timeline timeline, Object manifest) {

                }

                @Override
                public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

                }

                @Override
                public void onLoadingChanged(boolean isLoading) {

                }

                @Override
                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

                }

                @Override
                public void onPlayerError(ExoPlaybackException error) {
                    Toast.makeText(getContext(),getString(R.string.no_video),Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onPositionDiscontinuity() {

                }

                @Override
                public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

                }
            });
        }

        Intent intent = getActivity().getIntent();
        Bundle args = getArguments();

        //if (intent != null) {

            try {
                if(!RecipeDetails.mTwoPane)
                    data = new JSONObject(intent.getStringExtra(Intent.EXTRA_TEXT));
                else {
                    if(args!=null)
                        data = new JSONObject(args.getString("DATA"));
                    else
                        return rootView;
                }
                if (savedInstanceState != null) {
                    position = savedInstanceState.getInt("position");
                    data = new JSONObject(RecipeDetailsFragment.mAdapter.getItem(savedInstanceState.getInt("item")).toString());
                }
                i = data.getInt("id");
                Log.v("test_tag", data.toString());
                descTv.setText(data.getString("description"));
                String mUriStr = data.get("videoURL").toString();
                if (mUriStr.equals("")) {
                    mUriStr = data.get("thumbnailURL").toString();
                }
                Uri uri = Uri.parse(mUriStr);

                final String userAgent = Util.getUserAgent(getContext(), "BakeNMake");
                MediaSource mediaSource = new ExtractorMediaSource(uri, new DefaultDataSourceFactory(
                        getContext(), userAgent), new DefaultExtractorsFactory(), null, null);

                player.prepare(mediaSource);
                player.setPlayWhenReady(true);
                play_next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            //progressDialog.show();
                            position=0;
                            JSONObject next = new JSONObject(RecipeDetailsFragment.mAdapter.getItem(++i).toString());
                            Log.v("test_tag_next", next.toString());

                            player.prepare(new ExtractorMediaSource(Uri.parse(next.getString("videoURL")),
                                    new DefaultDataSourceFactory(getContext(),userAgent),
                                    new DefaultExtractorsFactory(),
                                    null,
                                    null));

                            descTv.setText(next.getString("description"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IndexOutOfBoundsException e) {
                            Toast.makeText(getContext(), getString(R.string.last_step_msg), Toast.LENGTH_SHORT).show();
                            //getActivity().onBackPressed();
                        }
                    }
                });

                play_previous.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            //progressDialog.show();
                            position=0;
                            JSONObject next = new JSONObject(RecipeDetailsFragment.mAdapter.getItem(--i).toString());
                            Log.v("test_tag_next", next.toString());

                            player.prepare(new ExtractorMediaSource(Uri.parse(next.getString("videoURL")),
                                    new DefaultDataSourceFactory(getContext(),userAgent),
                                    new DefaultExtractorsFactory(),
                                    null,
                                    null));

                            descTv.setText(next.getString("description"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IndexOutOfBoundsException e) {
                            Toast.makeText(getContext(), getString(R.string.first_step_msg), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        //}
        return rootView;
    }
}
