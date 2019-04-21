package com.t3h.buoi13;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.IntDef;

import com.t3h.buoi13.model.Song;

import java.util.ArrayList;

public class MediaManager implements MediaPlayer.OnCompletionListener {

    public static final int NEXT = 1;
    public static final int PREV = -1;
    @IntDef({NEXT,PREV})
    @interface INCREASE{
    }

    private MediaPlayer player;
    private ArrayList<Song> arrSong;
    private Context context;
    private int currentIndex;

    public MediaManager(ArrayList<Song> arrSong, Context context) {
        this.arrSong = arrSong;
        this.context = context;
    }

    public void create(int index){
        currentIndex = index;
        release();//Giai phong bai hat truoc do dang chay
        String data = arrSong.get(index).getData();
        player = MediaPlayer.create(context, Uri.parse(data));
        start();
        player.setOnCompletionListener(this);//chuyen sang bai moi sau khi het
    }


    //start cung chinh la resume
    public void start(){
        if(player!=null){
            player.start();
        }
    }

    public void stop(){
        if(player!=null){
            player.stop();
        }
    }

    public void pause(){
        if(player!=null){
            player.pause();
        }
    }

    public void release(){
        if(player!=null){
            player.release();
        }
    }

    public void seek(int position){
        if(player!=null){
            player.seekTo(position);
        }
    }

    public void loop(boolean isLooping){
        if(player!=null){
            player.setLooping(isLooping);
        }
    }

    public int getDuration(){
        if(player!=null){
            return player.getDuration();
        }
        return 0;
    }

    public int getCurrentPosition(){
        if(player!=null){
            return player.getCurrentPosition();
        }
        return 0;
    }


    //increase se chi nhan NEXT HOAC PREV(rang buoc du lieu)
    public void changeSong(@INCREASE int increase){
        currentIndex += increase;
        if(currentIndex>=arrSong.size()){
            currentIndex=0;
        }else if (currentIndex<0){
            currentIndex=arrSong.size()-1;
        }
        create(currentIndex);
    }


    @Override
    public void onCompletion(MediaPlayer mp) {

        changeSong(NEXT);

    }


}
