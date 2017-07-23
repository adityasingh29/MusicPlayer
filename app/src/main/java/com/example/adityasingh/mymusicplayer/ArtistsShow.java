package com.example.adityasingh.mymusicplayer;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by adityasingh on 16/07/17.
 */

public class ArtistsShow extends ActionBarActivity {

    private ArrayList<Song> songList;
    private ListView artistsView;
    private ArrayList<String> artistsName=new ArrayList<String>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.artists_show);


        artistsView = (ListView) findViewById(R.id.artists_showList);
        songList = new ArrayList<Song>();
        getSongList();

        ArtistsAdapter artistsAdapter = new ArtistsAdapter(this, songList);
        artistsView.setAdapter(artistsAdapter);
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        Toast.makeText(this,"")
//    }

    //
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_artist,menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id=item.getItemId();
//        if(id==R.id.home){
//            Toast.makeText(this,"Home Page",Toast.LENGTH_SHORT).show();
//            Intent i=new Intent(this,MainActivity.class);
//            startActivity(i);
//        }
//        return super.onOptionsItemSelected(item);
//    }
    public void getSongList() {
        ContentResolver musicResolver = getContentResolver();
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);

        if(musicCursor!=null && musicCursor.moveToFirst()){
            //get columns
            int titleColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.ARTIST);
            int albumColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.ALBUM);
            //add songs to list
            do {
                long thisId = musicCursor.getLong(idColumn);
                if(!artistsName.contains(musicCursor.getString(artistColumn)) && musicCursor.getString(artistColumn)!="")
                {
                    String thisTitle = musicCursor.getString(titleColumn);
                    String thisArtist = musicCursor.getString(artistColumn);
                    songList.add(new Song(thisId, thisTitle, thisArtist));
                    artistsName.add(thisArtist);
                }
            }
            while (musicCursor.moveToNext());
        }
//        controller.show(0);
    }
}
