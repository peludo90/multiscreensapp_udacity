package com.example.android.miwok.entity;

/**
 * Created by obed.gonzalez on 09/02/2017.
 */
public class Word {
    private String english;
    private String miwok;
    private int imageResourceId = NO_IMAGE_PROVIDED;
    private int soundResourceId = NO_IMAGE_PROVIDED;
    private static final int NO_IMAGE_PROVIDED = -1;

    public Word(String english, String miwok, int soundResourceId) {
        this.english = english;
        this.miwok = miwok;
        this.soundResourceId = soundResourceId;
    }

    public Word(String english, String miwok, int imageResourceId, int soundResourceId) {
        this.english = english;
        this.miwok = miwok;
        this.imageResourceId = imageResourceId;
        this.soundResourceId = soundResourceId;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getMiwok() {
        return miwok;
    }

    public void setMiwok(String miwok) {
        this.miwok = miwok;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    public int getSoundResourceId() {
        return soundResourceId;
    }

    public void setSoundResourceId(int soundResourceId) {
        this.soundResourceId = soundResourceId;
    }

    public boolean hasImage(){
        return imageResourceId != NO_IMAGE_PROVIDED;
    }

    public boolean hasSound(){
        return soundResourceId != NO_IMAGE_PROVIDED;
    }

    @Override
    public String toString() {
        return "Word{" +
                "english='" + english + '\'' +
                ", miwok='" + miwok + '\'' +
                ", soundResourceId=" + soundResourceId +
                ", soundResourceId=" + soundResourceId +
                '}';
    }
}
