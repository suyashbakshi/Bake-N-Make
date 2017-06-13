package net.ddns.suyashbakshi.bakenmake;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A placeholder fragment containing a simple view.
 */
public class VideoActivityFragment extends Fragment {

    VideoView videoView;
    private ProgressDialog progressDialog;
    private MediaController mediaController;
    TextView descTv;
    Button play_next,play_previous;
    int position = 0,i;
    public VideoActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_video, container, false);
        videoView = (VideoView) rootView.findViewById(R.id.videoView);
        descTv = (TextView) rootView.findViewById(R.id.video_description_tv);
        play_next = (Button) rootView.findViewById(R.id.play_next_button);
        play_previous = (Button) rootView.findViewById(R.id.play_previous_button);
        if (mediaController == null) {
            mediaController = new MediaController(getContext());
        }

        videoView.setMediaController(mediaController);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();

        Intent intent = getActivity().getIntent();
        if (intent != null) {

            try {
                final JSONObject data = new JSONObject(intent.getStringExtra(Intent.EXTRA_TEXT));
                i = data.getInt("id");
                Log.v("test_tag", data.toString());
                descTv.setText(data.getString("description"));
                videoView.setVideoURI(Uri.parse(data.get("videoURL").toString()));

                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        progressDialog.dismiss();
                        videoView.seekTo(position);

                        if (position == 0)
                            videoView.start();
                        else
                            videoView.pause();
                    }
                });

                videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                    @Override
                    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), getString(R.string.no_video), Toast.LENGTH_SHORT).show();
                        getActivity().onBackPressed();
                        return false;
                    }
                });

                videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        //getActivity().onBackPressed();
                    }
                });

                play_next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            progressDialog.show();
                            JSONObject next = new JSONObject(RecipeDetailsFragment.mAdapter.getItem(++i).toString());
                            Log.v("test_tag_next",next.toString());
                            videoView.setVideoURI(Uri.parse(next.getString("videoURL")));
                            descTv.setText(next.getString("description"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        catch (IndexOutOfBoundsException e){
                            Toast.makeText(getContext(),"All steps finish. Enjoy!",Toast.LENGTH_SHORT).show();
                            getActivity().onBackPressed();
                        }
                    }
                });

                play_previous.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            progressDialog.show();
                            JSONObject next = new JSONObject(RecipeDetailsFragment.mAdapter.getItem(--i).toString());
                            Log.v("test_tag_next",next.toString());
                            videoView.setVideoURI(Uri.parse(next.getString("videoURL")));
                            descTv.setText(next.getString("description"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        catch (IndexOutOfBoundsException e){
                            Toast.makeText(getContext(),"All steps finish. Enjoy!",Toast.LENGTH_SHORT).show();
                            getActivity().onBackPressed();
                        }
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        return rootView;
    }
}
