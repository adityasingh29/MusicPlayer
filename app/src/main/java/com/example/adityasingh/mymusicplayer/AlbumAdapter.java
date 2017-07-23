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
 * Created by adityasingh on 20/07/17.
 */

public class AlbumAdapter extends BaseAdapter {

    private ArrayList<AlbumOver> songs;
    private LayoutInflater songInf;
    private ArrayList<String> albumsName=new ArrayList<String>();

    public AlbumAdapter(Context c, ArrayList<AlbumOver> theSongs){
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
        LinearLayout songLay = (LinearLayout)songInf.inflate(R.layout.album,viewGroup, false);
        //get title and artist views
        //get song using position
        AlbumOver currSong = songs.get(i);
        //get title and artist strings
        if(!albumsName.contains(currSong.getAlbum()) && currSong.getAlbum()!=""){
            TextView albumView = (TextView)songLay.findViewById(R.id.song_artist_albums);
            albumView.setVisibility(View.VISIBLE);
            albumView.setText(currSong.getAlbum());
            albumsName.add(currSong.getAlbum());
        }

        //set position as tag
        songLay.setTag(i);
        return songLay;
    }
}
