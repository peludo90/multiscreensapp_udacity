package com.example.android.miwok.fragments;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.miwok.R;
import com.example.android.miwok.adapter.AdapterListWords;
import com.example.android.miwok.entity.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by obed.gonzalez on 16/02/2017.
 */
public class FragmentNumbers extends Fragment {

    private List<Word> listWords;
    private AdapterListWords adapterListWords;
    private MediaPlayer.OnCompletionListener completionListener;
    MediaPlayer mediaPlayer;
    private AudioManager.OnAudioFocusChangeListener audioFocusChangeListener;
    AudioManager audioManager;

    private ListView lvNumbers;

    public FragmentNumbers() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_numbers, container, false);
        listWords = new ArrayList<>();
        listWords.add(new Word("One", "lutti", R.drawable.number_one, R.raw.number_one));
        listWords.add(new Word("Two", "otiiko", R.drawable.number_two, R.raw.number_two));
        listWords.add(new Word("three", "tolookosu", R.drawable.number_three, R.raw.number_three));
        listWords.add(new Word("Four", "oyyisa", R.drawable.number_four, R.raw.number_four));
        listWords.add(new Word("Five", "massokka", R.drawable.number_five, R.raw.number_five));
        listWords.add(new Word("Six", "temmokka", R.drawable.number_six, R.raw.number_six));
        listWords.add(new Word("Seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven));
        listWords.add(new Word("Eight", "kawinta", R.drawable.number_eight, R.raw.number_eight));
        listWords.add(new Word("Nine", "wo’e", R.drawable.number_nine, R.raw.number_nine));
        listWords.add(new Word("Ten", "na’aacha", R.drawable.number_ten, R.raw.number_ten));

        getComponents(rootView);
        setInfo();
        setListeners();

        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private void getComponents(View view) {
        lvNumbers = (ListView) view.findViewById(R.id.act_numbers_lv_numbers);
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
    }

    private void setInfo() {

        adapterListWords = new AdapterListWords(getActivity(), listWords, R.color.category_numbers);
        lvNumbers.setAdapter(adapterListWords);
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


        lvNumbers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listWords.get(position).hasSound()) {
                    releaseMediaPlayer();

                    int result = audioManager.requestAudioFocus(audioFocusChangeListener,
                            AudioManager.STREAM_MUSIC,
                            AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                    if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                        mediaPlayer = MediaPlayer.create(getActivity(), listWords.get(position).getSoundResourceId());
                        mediaPlayer.start();
                        mediaPlayer.setOnCompletionListener(completionListener);
                    } else {
                        Toast.makeText(getActivity(), R.string.no_focus_gained, Toast.LENGTH_LONG).show();
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
