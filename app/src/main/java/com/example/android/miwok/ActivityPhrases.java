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

public class ActivityPhrases extends AppCompatActivity {

    private List<Word> listWords;
    private AdapterListWords adapterListWords;
    private ListView lvPhrases;
    private MediaPlayer.OnCompletionListener completionListener;
    private AudioManager.OnAudioFocusChangeListener audioFocusChangeListener;
    AudioManager audioManager;
    MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phrases);
        listWords = new ArrayList<>();
        listWords.add(new Word("Where are you going?", "minto wuksus", R.raw.phrase_where_are_you_going));
        listWords.add(new Word("What is your name?", "tinnә oyaase'nә", R.raw.phrase_what_is_your_name));
        listWords.add(new Word("My name is...", "oyaaset...", R.raw.phrase_my_name_is));
        listWords.add(new Word("How are you feeling?", "michәksәs?", R.raw.phrase_how_are_you_feeling));
        listWords.add(new Word("I’m feeling good.", "kuchi achit", R.raw.phrase_im_feeling_good));
        listWords.add(new Word("Are you coming?", "әәnәs'aa?", R.raw.phrase_are_you_coming));
        listWords.add(new Word("Yes, I’m coming.", "hәә’ әәnәm", R.raw.phrase_yes_im_coming));
        listWords.add(new Word("I’m coming.", "әәnәm", R.raw.phrase_im_coming));
        listWords.add(new Word("Let’s go.", "yoowutis", R.raw.phrase_lets_go));
        listWords.add(new Word("Come here.", "әnni'nem", R.raw.phrase_come_here));
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
        lvPhrases = (ListView) findViewById(R.id.act_phrases_lv_phrases);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
    }

    private void setInfo() {

        adapterListWords = new AdapterListWords(this, listWords, R.color.category_phrases);
        lvPhrases.setAdapter(adapterListWords);
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

            }
        };


        lvPhrases.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listWords.get(position).hasSound()) {
                    releaseMediaPlayer();
                    int result = audioManager.requestAudioFocus(audioFocusChangeListener,
                            AudioManager.STREAM_MUSIC,
                            AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                    if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                        mediaPlayer = MediaPlayer.create(ActivityPhrases.this, listWords.get(position).getSoundResourceId());
                        mediaPlayer.start();
                        mediaPlayer.setOnCompletionListener(completionListener);
                    } else {
                        Toast.makeText(ActivityPhrases.this, R.string.no_focus_gained, Toast.LENGTH_LONG).show();
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
