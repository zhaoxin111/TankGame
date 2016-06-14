package com.bn.tank;

import java.io.IOException;
import java.util.HashMap;

import com.bn.data.GameData;
import com.bn.util.KeyThread;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;

public class TankActivity extends Activity {

	public MySurfaceView mySurfaceView;
	public KeyThread kt;
	SoundPool soundPool;
	MediaPlayer mediaPlayer;
	HashMap<Integer, Integer> soundPoolMap;
	public static Handler handler;
	int completeFlag=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
        final int width = metric.widthPixels;
        final int height = metric.heightPixels;
		mySurfaceView =  new MySurfaceView(this,width,height);
        kt=new KeyThread(mySurfaceView);
        kt.start();
		initSounds();
		setContentView(mySurfaceView);
        soundPool.setOnLoadCompleteListener
        (
        	new SoundPool.OnLoadCompleteListener() 
        	{
				@Override
				public void onLoadComplete(SoundPool soundPool, int sampleId, int status) 
				{
					completeFlag++;
					if(completeFlag==5)
					{
						try {
							Thread.sleep(3000);
						} catch (Exception e){
							e.printStackTrace();
						}
						GameData.viewState=2;
					}
				}
			}
        );
        handler=new Handler()
        {
        	@Override
        	public void handleMessage(Message msg)
        	{
        		switch(msg.what)
        		{
        		case 1:
        			Toast.makeText(TankActivity.this, "连接失败", Toast.LENGTH_SHORT).show();
        		case 2:
        			Toast.makeText(TankActivity.this, "连接断开", Toast.LENGTH_SHORT).show();
        		}
        	}
        };
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)		//物理按键监听
	{
		int gameState=GameData.viewState;
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			switch(gameState)
			{
			case GameData.Game_load:
				break;
			case GameData.Game_menu:
				return mySurfaceView.menu.onKeyDown(keyCode, event);
			case GameData.Game_palying:
				if(GameData.state==4)
				{
					GameData.state=2;
					return true;
				}else if(GameData.state==2)
				{
					GameData.state=4;
					return true;
				}
				break;
			case GameData.Game_select:
				GameData.viewState=GameData.Game_menu;
				break;
			}
		}
		return false;
	}
	
	private void initSounds() 
	{
		AssetManager assetManager = getAssets(); 
		AssetFileDescriptor temp;
		mediaPlayer=new MediaPlayer();
		try
		{
			temp=assetManager.openFd("sound/background.mp3");
			mediaPlayer.setDataSource
			(
				temp.getFileDescriptor(), 
				temp.getStartOffset(), 
				temp.getLength()
			);
			mediaPlayer.setLooping(true);
			mediaPlayer.prepare();
//			mediaPlayer.setDataSource(context, uri);
//			
//			mediaPlayer.setDataSource(path);
			
		//	Uri uri = Uri.parse("file:///sdcard/background.mp3");
//			mediaPlayer.setDataSource("assets/sound/background.mp3");
//			mediaPlayer.setda
//		//	mediaPlayer.setDataSource(TankActivity.this, uri);
//			mediaPlayer.setLooping(true);
//			mediaPlayer.prepare();
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		soundPool=new SoundPool
		(
			6,
			AudioManager.STREAM_MUSIC,
			100
		);
		soundPoolMap=new HashMap<Integer, Integer>();
        AssetFileDescriptor descriptor[]=new AssetFileDescriptor[5];
        String path[]=
        {
    		"sound/eatprops.wav",
    		"sound/grenada.ogg",
    		"sound/lose.mp3",
    		"sound/rocket_shoot2.ogg",
    		"sound/select.wav"
        };
		try 
		{
			for(int i=0;i<5;i++)
			{
				descriptor[i] = assetManager.openFd(path[i]);
				soundPoolMap.put(i+1, soundPool.load(descriptor[i], 1));
			}
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		mediaPlayer.start();
	}
	
	//播放背景音乐
	public void playBackground()
	{
		AssetManager assetManager = getAssets(); 	
		AssetFileDescriptor temp;
		mediaPlayer.release();
		if(!GameData.GAME_SOUND)
		{
			return;
		}
		mediaPlayer=new MediaPlayer();
		try
		{
			temp=assetManager.openFd("sound/background.mp3");	//加载背景音乐
			mediaPlayer.setDataSource
			(
				temp.getFileDescriptor(), 
				temp.getStartOffset(), 
				temp.getLength()
			);
			mediaPlayer.setLooping(true);
			mediaPlayer.prepare();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		mediaPlayer.start();
	}
	
	public void playSound(int sound, int loop) 
	{	   
		if(GameData.GAME_EFFECT)
		{
			AudioManager mgr = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);   
		    float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);   
		    float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);       
		    float volume =1.5f*(streamVolumeCurrent/streamVolumeMax);   
		    
		    soundPool.play
		    (
	    		soundPoolMap.get(sound), //声音资源id
	    		volume, 				 //左声道音量
				volume, 				 //右声道音量
				1, 						 //优先级				 
				loop, 					 //循环次数 -1带表永远循环
				0.5f					 //回放速度0.5f～2.0f之间
		    );
		}
	}

	@Override
	public void onResume()
	{
		super.onResume();
	}
	
	@Override	
	public void onPause()
	{
		super.onPause();
	}
	
	public void exit()
	{
		kt.broadcastExit();
		mediaPlayer.release();
		System.exit(0);
	}
}
