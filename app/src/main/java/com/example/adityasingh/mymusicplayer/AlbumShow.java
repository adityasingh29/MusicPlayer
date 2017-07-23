package com.example.adityasingh.mymusicplayer;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by adityasingh on 20/07/17.
 */

public class AlbumShow extends ActionBarActivity {

    private ArrayList<AlbumOver> songList;
    private ListView albumsView;
    private ArrayList<String> albumsName=new ArrayList<String>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_show);


        albumsView = (ListView) findViewById(R.id.albums_showList);
        songList = new ArrayList<AlbumOver>();
        getSongList();

        AlbumAdapter albumAdapter = new AlbumAdapter(this, songList);
        albumsView.setAdapter(albumAdapter);
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
                if(!albumsName.contains(musicCursor.getString(albumColumn)) && musicCursor.getString(albumColumn)!="")
                {
                    String thisTitle = musicCursor.getString(titleColumn);
                    String thisArtist = musicCursor.getString(artistColumn);
                    String thisAlbum= musicCursor.getString(albumColumn);
                    songList.add(new AlbumOver(thisId, thisTitle,thisArtist,thisAlbum));
                    albumsName.add(thisAlbum);
                }
            }
            while (musicCursor.moveToNext());
        }
//        controller.show(0);
    }
}