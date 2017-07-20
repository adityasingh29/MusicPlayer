package com.example.adityasingh.mymusicplayer;

import android.app.Activity;

/**
 * Created by adityasingh on 20/07/17.
 */

public class AlbumOver extends Activity {
    private long id;
    private String title;
    private String artist;
    private String album;

    public AlbumOver(long songID, String songTitle, String songArtist,String songAlbum) {
        id=songID;
        title=songTitle;
        artist=songArtist;
        album=songAlbum;
    }

    public long getID(){return id;}
    public String gettitle(){return title;}
    public String getartist(){return artist;}
    public String getAlbum(){return album;}
}
