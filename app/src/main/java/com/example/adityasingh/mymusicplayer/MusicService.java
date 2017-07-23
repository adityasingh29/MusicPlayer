package com.example.adityasingh.mymusicplayer;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by adityasingh on 15/07/17.
 */

public class MusicService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener {

    private String songTitle="";
    private boolean shuffle=false;
    private Random rand;
    private static final int NOTIFY_ID=1;

    private MediaPlayer player;
    private ArrayList<Song> songs;
    private int songPosn;
    private final IBinder musicBind = new MusicBinder();
//   AudioManager audioManager;

    @Override
    public void onCreate() {
        super.onCreate();
        songPosn=0;
        player = new MediaPlayer();
        initMusicPlayer();
        rand=new Random();

//      to implement audio changes with other apps
//
//       audioManager=new AudioManager();
//        audioManager.requestAudioFocus();
    }

    public void setList(ArrayList<Song> theSongs){
        songs=theSongs;
    }

//    @Override
//    public void onAudioFocusChange(int i) {
//
//    }

    public class MusicBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }

    public void initMusicPlayer(){

        player.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);

        player.setOnPreparedListener(this);
        player.setOnCompletionListener(this);
        player.setOnErrorListener(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return musicBind;
    }
    @Override
    public boolean onUnbind(Intent intent){
        player.stop();
        player.release();
        return false;
    }

//default onCompletion
//    @Override
//    public void onCompletion(MediaPlayer mediaPlayer) {
//
//    }
//
//    default onError method
//    @Override
//    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
//        return false;
//    }

    @Override
    public void onPrepared(MediaPlayer mp) {

        mp.start();

        Intent notIntent = new Intent(this, MainActivity.class);
        notIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendInt = PendingIntent.getActivity(this, 0,
                notIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(this);

        builder.setContentIntent(pendInt)
                .setSmallIcon(R.drawable.play)
                .setTicker(songTitle)
                .setOngoing(true)
                .setContentTitle("Playing")
                .setContentText(songTitle);
        Notification not = builder.build();

        startForeground(NOTIFY_ID, not);

        Intent onPreparedIntent = new Intent("MEDIA_PLAYER_PREPARED");
        LocalBroadcastManager.getInstance(this).sendBroadcast(onPreparedIntent);
    }

    public String showSongName(){
        Song playSong = songs.get(songPosn);
        songTitle=playSong.gettitle();
        return songTitle;
    }

    public void playSong(){

        player.reset();
        Song playSong = songs.get(songPosn);
        songTitle=playSong.gettitle();
//get id
        long currSong = playSong.getID();
//set uri
        Uri trackUri = ContentUris.withAppendedId(
                android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                currSong);
        try{
            player.setDataSource(getApplicationContext(), trackUri);
        }
        catch(Exception e){
            Log.e("MUSIC SERVICE", "Error setting data source", e);
        }

        player.prepareAsync();
    }
    public void setSong(int songIndex){
        songPosn=songIndex;
    }

    public int getPosn(){
        return player.getCurrentPosition();
    }

    public int getDur(){
        return player.getDuration();
    }

    public boolean isPng(){
        return player.isPlaying();
    }

    public void pausePlayer(){
        player.pause();

    }

    public void seek(int posn){
        player.seekTo(posn);
    }

    public void go(){
        player.start();
    }

    public void playPrev(){
        songPosn--;
        if(songPosn<=0) songPosn=songs.size()-1;
        playSong();
    }

    //    playNext without shuffle
//
//    public void playNext(){
//        songPosn++;
//        if(songPosn>=songs.size()) songPosn=0;
//        playSong();
//    }
    @Override
    public void onDestroy() {
        stopForeground(true);
    }


//    for shuffle button

    public void setShuffle(){

        if(shuffle) {
            shuffle = false;
            Toast.makeText(this,"Shuffle set OFF",Toast.LENGTH_SHORT).show();
        }
        else {
            shuffle = true;
            Toast.makeText(this,"Songs Playing in Shuffle",Toast.LENGTH_SHORT).show();
        }
    }
//playnext for shuffle

    public void playNext(){
        if(shuffle){
            int newSong = songPosn;
            while(newSong==songPosn){
                newSong=rand.nextInt(songs.size());
            }
            songPosn=newSong;
        }
        else{
            songPosn++;
            if(songPosn>=songs.size()) songPosn=0;
        }
        playSong();
    }


    //    updated onError method
    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        mp.reset();
        return false;
    }

    //    updated onCompletion
    @Override
    public void onCompletion(MediaPlayer mp) {
        if(player.getCurrentPosition()>0){
            mp.reset();
            playNext();
        }
    }

}