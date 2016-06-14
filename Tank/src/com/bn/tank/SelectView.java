package com.bn.tank;

import com.bn.data.GameData;
import com.bn.util.BitmapIOUtil;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;

public class SelectView
{
	MySurfaceView father;
	int picX;
	int picY;
	int fontX;
	int fontY;
	int backX;
	int backY;
	int startX;
	int startY;
	int waitX;
	int waitY;
	Bitmap levelPic[][]=new Bitmap[2][2];
	Bitmap levelFont[]=new Bitmap[2];
	Bitmap startGame[]=new Bitmap[2];
	Bitmap backMenu[]=new Bitmap[2];
	Bitmap wait;
	float eventX=0;
	int number=0;
	int buttonFlag=0;//第一位 后退  第二位 开始游戏(左数)
	boolean touchFlag=false;
	
	public SelectView(MySurfaceView father)
	{
		this.father=father;
		levelPic[0][0]=BitmapIOUtil.getBitmap("oneP.png", father.father.getResources());
		levelPic[1][0]=BitmapIOUtil.getBitmap("twoP.png", father.father.getResources());
		levelPic[0][1]=BitmapIOUtil.getBitmap("oneP_select.png", father.father.getResources());
		levelPic[1][1]=BitmapIOUtil.getBitmap("twoP_select.png", father.father.getResources());
		levelFont[0]=BitmapIOUtil.getBitmap("one.png", father.father.getResources());
		levelFont[1]=BitmapIOUtil.getBitmap("two.png", father.father.getResources());
		startGame[0]=BitmapIOUtil.getBitmap("start_game.png", father.getResources());
		startGame[1]=BitmapIOUtil.getBitmap("start_game_select.png", father.getResources());
		backMenu[0]=BitmapIOUtil.getBitmap("back_menu.png", father.father.getResources());
		backMenu[1]=BitmapIOUtil.getBitmap("back_menu_select.png", father.father.getResources());
		wait=BitmapIOUtil.getBitmap("wait.png", father.father.getResources());
		
		picX=levelPic[0][0].getWidth()/2;
		picY=levelPic[0][0].getHeight()/2;;
		fontX=levelFont[0].getWidth()/2;
		fontY=levelFont[0].getHeight()/2;
		startX=startGame[0].getWidth()/2;
		startY=startGame[0].getHeight()/2;
		backX=backMenu[0].getWidth()/2;
		backY=backMenu[0].getHeight()/2;
		waitX=wait.getWidth()/2;
		waitY=wait.getHeight()/2;
	}
	
	public void drawSelf(Canvas canvas)
	{
		canvas.save();
		number=father.gd.levelNumber;
		canvas.drawBitmap(father.back, 0, 0, null);
		canvas.drawBitmap(backMenu[((buttonFlag&2)==2)?1:0], 130-backX, 65-backY, null);
		if(GameData.state==0)
		{
			canvas.drawBitmap(wait, father.screenWidth/2-waitX, father.screenHeight/2-waitY, null);
		}
		if(GameData.state==1)
		{
			if(GameData.redOrGreen==1)
			{
				canvas.drawBitmap(startGame[((buttonFlag&1)==1)?1:0], 830-startX, 65-startY, null);
			}
			canvas.drawBitmap(levelPic[0][(number==0)?1:0], 270-picX, 260-picY, null);
			canvas.drawBitmap(levelFont[0], 270-fontX, 430-fontY, null);
			canvas.drawBitmap(levelPic[1][(number==1)?1:0], 690-picX, 260-picY, null);
			canvas.drawBitmap(levelFont[1], 690-fontX, 430-fontY, null);
			
		}
		canvas.restore();
	}
	
	public void onTouchEvent(MotionEvent event)
	{
		float x=father.changeTouchX(event.getX());
		float y=father.changeTouchY(event.getY());
		switch(event.getAction())
		{
		case MotionEvent.ACTION_DOWN:
			if(GameData.redOrGreen==1)
			{
				if(Math.abs(x-270)<picX&&Math.abs(y-250)<picY)
				{
					father.father.kt.broadcastSelect(0);
				}else if(Math.abs(x-270)<fontX&&Math.abs(y-450)<fontY)
				{
					father.father.kt.broadcastSelect(0);
				}else if(Math.abs(x-690)<picX&&Math.abs(y-250)<picY)
				{
					father.father.kt.broadcastSelect(1);
				}else if(Math.abs(x-690)<fontX&&Math.abs(y-450)<fontY)
				{
					father.father.kt.broadcastSelect(1);
				}else if(Math.abs(x-830)<startX&&Math.abs(y-65)<startY)
				{
					touchFlag=true;
				}
			}
			if(Math.abs(x-130)<backX&&Math.abs(y-65)<backY)
			{
				touchFlag=true;
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if(touchFlag)
			{
				if(Math.abs(x-130)<backX&&Math.abs(y-65)<backY)
				{
					buttonFlag=2;
				}else if(Math.abs(x-830)<startX&&Math.abs(y-65)<startY)
				{
					buttonFlag=1;
				}else
				{
					buttonFlag=0;
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			if(touchFlag)
			{
				if(Math.abs(x-130)<backX&&Math.abs(y-65)<backY)
				{
					GameData.viewState=GameData.Game_menu;
					father.father.kt.broadcastExit();
					father.nt.flag=false;
				}else if(Math.abs(x-830)<startX&&Math.abs(y-65)<startY)
				{
					father.father.kt.broadcastLevel(number);
					GameData.viewState=GameData.Game_palying;
				}
				buttonFlag=0;
				touchFlag=false;
			}
			break;
		}
	}
}