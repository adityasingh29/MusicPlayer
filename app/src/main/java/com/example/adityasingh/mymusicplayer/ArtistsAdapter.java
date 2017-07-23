package com.example.adityasingh.mymusicplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by adityasingh on 16/07/17.
 */

public class ArtistsAdapter extends BaseAdapter{

    private ArrayList<Song> songs;
    private LayoutInflater songInf;
    private ArrayList<String> artistsName=new ArrayList<String>();

    public ArtistsAdapter(Context c, ArrayList<Song> theSongs){
        songs=theSongs;
        songInf=LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        return songs.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LinearLayout songLay = (LinearLayout)songInf.inflate(R.layout.artists,viewGroup, false);
        //get title and artist views
        //get song using position
        Song currSong = songs.get(i);
        //get title and artist strings
        if(!artistsName.contains(currSong.getartist()) && currSong.getartist()!=""){
            TextView artistView = (TextView)songLay.findViewById(R.id.song_artist_artists);
            artistView.setVisibility(View.VISIBLE);
            artistView.setText(currSong.getartist());
            artistsName.add(currSong.getartist());
        }

            //set position as tag
        songLay.setTag(i);
        return songLay;
    }
}


