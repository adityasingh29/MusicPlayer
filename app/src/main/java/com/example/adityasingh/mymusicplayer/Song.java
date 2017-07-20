package com.example.adityasingh.mymusicplayer;

import android.app.Activity;

/**
 * Created by adityasingh on 15/07/17.
 */

public class Song extends Activity {
    private long id;
    private String title;
    private String artist;

    public Song(long songID, String songTitle, String songArtist) {
        id=songID;
        title=songTitle;
        artist=songArtist;
    }

    public long getID(){return id;}
    public String gettitle(){return title;}
    public String getartist(){return artist;}
}
