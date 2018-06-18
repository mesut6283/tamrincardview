package com.example.danesh.tamrincardview.Activity.Activity;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnTimedTextListener;
import android.media.MediaPlayer.TrackInfo;
import android.media.TimedText;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.danesh.tamrincardview.Modal.Utilities;
import com.example.danesh.tamrincardview.R;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;

public class MediaActivity extends Activity implements OnTimedTextListener,MediaPlayer.OnCompletionListener, SeekBar.OnSeekBarChangeListener {
    private ImageButton btnPlay;
    private ImageButton btnForward;
    private ImageButton btnBackward;
    private SeekBar songProgressBar;
    private TextView songTitleLabel;
    private TextView songCurrentDurationLabel;
    private TextView songTotalDurationLabel;
    private Handler mHandler = new Handler();;
    //private SongsManager songManager;
    private Utilities utils;
    private int seekForwardTime = 5000; // 5000 milliseconds
    private int seekBackwardTime = 5000; // 5000 milliseconds
    private int currentSongIndex = 0;

    private static final String TAG = "TimedTextTest";
    private TextView txtDisplay;
    TextView textview2;
    private static Handler handler = new Handler();

    MediaPlayer player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);

        btnPlay=(ImageButton) findViewById(R.id.btnPlay);
        btnForward = (ImageButton) findViewById(R.id.btnForward);
        btnBackward = (ImageButton) findViewById(R.id.btnBackward);
        songProgressBar = (SeekBar) findViewById(R.id.songProgressBar);
        //songTitleLabel = (TextView) findViewById(R.id.songTitle);
        songCurrentDurationLabel = (TextView) findViewById(R.id.songCurrentDurationLabel);
        songTotalDurationLabel = (TextView) findViewById(R.id.songTotalDurationLabel);
        utils = new Utilities();


        player = MediaPlayer.create(this, R.raw.eslpod1229);
        try {
            player.addTimedTextSource(getSubtitleFile(R.raw.eslpod12290),
                    MediaPlayer.MEDIA_MIMETYPE_TEXT_SUBRIP);
            Log.d(TAG,"TEST getSubtitleFile(fileName) == "+getSubtitleFile(R.raw.eslpod12290));
            int textTrackIndex = findTrackIndexFor(
                    TrackInfo.MEDIA_TRACK_TYPE_TIMEDTEXT, player.getTrackInfo());
            Log.i("My Message", "textTrackIndex : " + textTrackIndex);
            if (textTrackIndex >= 0) {
                player.selectTrack(textTrackIndex);

            } else {
                Log.w(TAG, "Cannot find text track!");
            }

            player.setOnTimedTextListener(this);
           // player.start();

            btnPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String s = (String) btnPlay.getTag();
                    if (s.equals("pause")) {
                        play();
                    } else {
                        pause();
                    }
                }
            });
/**
 * Forward button click event
 * Forwards song specified seconds
 * */
            btnForward.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // get current song position
                    int currentPosition = player.getCurrentPosition();
                    // check if seekForward time is lesser than song duration
                    if(currentPosition + seekForwardTime <= player.getDuration()){
                        // forward song
                        player.seekTo(currentPosition + seekForwardTime);
                    }else{
                        // forward to end position
                        player.seekTo(player.getDuration());
                    }
                }
            });
            /**
             * Backward button click event
             * Backward song to specified seconds
             * */
            btnBackward.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // get current song position
                    int currentPosition = player.getCurrentPosition();
                    // check if seekBackward time is greater than 0 sec
                    if(currentPosition - seekBackwardTime >= 0){
                        // forward song
                        player.seekTo(currentPosition - seekBackwardTime);
                    }else{
                        // backward to starting position
                       player.seekTo(0);
                    }

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

        songProgressBar.setOnSeekBarChangeListener(this);
        player.setOnCompletionListener(this) ;

    }


    private void play() {

        try {
        player.start();
        btnPlay.setTag("play"); // next state should be 'pause'
        btnPlay.setBackgroundResource(R.drawable.btn_pause);
        songProgressBar.setProgress(0);
        songProgressBar.setMax(100);
            updateProgressBar();
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

    }

    private void pause() {
        player.pause();
        btnPlay.setTag("pause"); // next state should be 'pause'
        btnPlay.setBackgroundResource(R.drawable.btn_play);
          }

    public void updateProgressBar() {
       // mHandler.postDelayed(mUpdateTimeTask, 100);
        handler.postDelayed(mUpdateTimeTask, 100);
        Log.i("My Message", "age beshe khub mishe  ");
    }

    /**
     * Background Runnable thread
     * */
    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            long totalDuration = player.getDuration();
            long currentDuration = player.getCurrentPosition();

            // Displaying Total Duration time
           songTotalDurationLabel.setText(""+utils.milliSecondsToTimer(totalDuration));
            // Displaying time completed playing
            songCurrentDurationLabel.setText(""+utils.milliSecondsToTimer(currentDuration));

            // Updating progress bar
            int progress = (int)(utils.getProgressPercentage(currentDuration, totalDuration));
            //Log.d("Progress", ""+progress);
            songProgressBar.setProgress(progress);

            // Running this thread after 100 milliseconds
            //mHandler.postDelayed(this, 100);
            handler.postDelayed(this, 100);
        }
    };

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
       // mHandler.removeCallbacks(mUpdateTimeTask);
        handler.removeCallbacks(mUpdateTimeTask);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
       // mHandler.removeCallbacks(mUpdateTimeTask);
        handler.removeCallbacks(mUpdateTimeTask);
        int totalDuration = player.getDuration();
        int currentPosition = utils.progressToTimer(seekBar.getProgress(), totalDuration);

        // forward or backward to certain seconds
        player.seekTo(currentPosition);

        // update timer progress again
        updateProgressBar();

    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        pause();
    }


    private int findTrackIndexFor(int mediaTrackType, TrackInfo[] trackInfo) {
        int index = -1;
        for (int i = 0; i < trackInfo.length; i++) {
            if (trackInfo[i].getTrackType() == mediaTrackType) {
                return i;
            }
        }
        return index;
    }

    private String getSubtitleFile(int resId) {
        String fileName = getResources().getResourceEntryName(resId);
        File subtitleFile = getFileStreamPath(fileName);
        if (subtitleFile.exists()) {
            Log.d(TAG, "Subtitle already exists");
            return subtitleFile.getAbsolutePath();
        }
        Log.d(TAG, "Subtitle does not exists, copy it from res/raw");

        // Copy the file from the res/raw folder to your app folder on the
        // device
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = getResources().openRawResource(resId);
            outputStream = new FileOutputStream(subtitleFile, false);
            copyFile(inputStream, outputStream);
            return subtitleFile.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeStreams(inputStream, outputStream);
        }
        return "";
    }

    private void copyFile(InputStream inputStream, OutputStream outputStream)
            throws IOException {
        final int BUFFER_SIZE = 1024;
        byte[] buffer = new byte[BUFFER_SIZE];
        int length = -1;
        while ((length = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, length);
        }
    }

    // A handy method I use to close all the streams
    private void closeStreams(Closeable... closeables) {
        if (closeables != null) {
            for (Closeable stream : closeables) {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void onTimedText(final MediaPlayer mp, final TimedText text) {
        final WebView view = (WebView) findViewById(R.id.textContent);
        view.loadData(getString(R.string.mytextwebview), "text/html; charset=utf-8", "utf-8");

                                        //textview2=(TextView)findViewById(R.id.textDisplay2);
        Log.i("My Message", "inside onTimedText Listener text = "+text);
        if (text != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    int seconds = mp.getCurrentPosition() / 1000;
                    Log.i("My Message", "timed Text:" + text.getText());
//					txtDisplay.setText("[" + secondsToDuration(seconds) + "] "
//							+ text.getText());
                    //String myText = textview2.getText().toString();
                                      /*String myText = view.toString();
                                      int start = 0;
                                      int end =0;

                                      String b=text.getText().trim();
                                      start = myText.indexOf(b);
                                      end =start+ b.length();


                                       Toast.makeText(MediaActivity.this,b+" start:"+start+" end:"+end, Toast.LENGTH_LONG).show();


                                         SpannableString sps = new SpannableString(myText);
                                       sps.setSpan(new BackgroundColorSpan(getResources().getColor(R.color.green)), start,end , 0);
                                       textview2.setText(sps);*/


//					Spanned s = (Spanned) txtDisplay.getText();
//					int start = s.getSpanStart(this);
//					int end = s.getSpanEnd(this);

                    String b=text.getText().trim();
                    view.findAllAsync(b);


                }
            });
        }
    }

    // To display the seconds in the duration format 00:00:00
    public String secondsToDuration(int seconds) {
        return String.format("%02d:%02d:%02d", seconds / 3600,
                (seconds % 3600) / 60, (seconds % 60), Locale.US);
    }
    @Override
    protected void onPause() {
        super.onPause();
       player.pause();
    }
}
