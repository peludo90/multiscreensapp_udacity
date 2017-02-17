package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.miwok.adapter.AdapterListWords;
import com.example.android.miwok.entity.Word;

import java.util.ArrayList;
import java.util.List;

public class ActivityColors extends AppCompatActivity {

    private List<Word> listWords;
    private AdapterListWords adapterListWords;
    private MediaPlayer.OnCompletionListener completionListener;
    MediaPlayer mediaPlayer;
    private AudioManager.OnAudioFocusChangeListener audioFocusChangeListener;
    AudioManager audioManager;

    private ListView lvColors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colors);

        listWords = new ArrayList<>();
        listWords.add(new Word("Red", "weṭeṭṭi", R.drawable.color_red, R.raw.color_red));
        listWords.add(new Word("Green", "chokokki", R.drawable.color_green, R.raw.color_green));
        listWords.add(new Word("Brown", "ṭakaakki", R.drawable.color_brown, R.raw.color_brown));
        listWords.add(new Word("Gray", "ṭopoppi", R.drawable.color_gray, R.raw.color_gray));
        listWords.add(new Word("Black", "kululli", R.drawable.color_black, R.raw.color_black));
        listWords.add(new Word("White", "kelelli", R.drawable.color_white, R.raw.color_white));
        listWords.add(new Word("Dusty yellow", "ṭopiisә", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        listWords.add(new Word("Mustard yellow", "chiwiiṭә", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));
        getComponents();
        setInfo();
        setListeners();
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private void getComponents() {
        lvColors = (ListView) findViewById(R.id.act_colors_lv_colors);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
    }

    private void setInfo() {

        adapterListWords = new AdapterListWords(this, listWords, R.color.category_colors);
        lvColors.setAdapter(adapterListWords);
    }

    private void setListeners() {

        audioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int focusChange) {
                switch (focusChange) {
                    case AudioManager.AUDIOFOCUS_GAIN:
                        if (mediaPlayer != null)
                            mediaPlayer.start();
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS:
                        releaseMediaPlayer();
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.pause();
                            mediaPlayer.seekTo(0);
                        }
                        break;
                    default:
                        break;

                }
            }
        };

        completionListener = new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                releaseMediaPlayer();
                audioManager.abandonAudioFocus(audioFocusChangeListener);

            }
        };

        lvColors.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listWords.get(position).hasSound()) {
                    releaseMediaPlayer();
                    int result = audioManager.requestAudioFocus(audioFocusChangeListener,
                            AudioManager.STREAM_MUSIC,
                            AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                    if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                        mediaPlayer = MediaPlayer.create(ActivityColors.this, listWords.get(position).getSoundResourceId());
                        mediaPlayer.start();
                        mediaPlayer.setOnCompletionListener(completionListener);
                    } else {
                        Toast.makeText(ActivityColors.this, R.string.no_focus_gained, Toast.LENGTH_LONG).show();
                    }

                }
            }
        });
    }

    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            //  mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
            audioManager.abandonAudioFocus(audioFocusChangeListener);
        }
    }
}
