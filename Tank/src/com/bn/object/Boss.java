package com.bn.object;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.bn.tank.MySurfaceView;
import com.bn.util.BitmapIOUtil;

public class Boss extends Thing
{
	MySurfaceView father;
	Bitmap boss1[]=new Bitmap[6];
	Bitmap boss2[]=new Bitmap[6];
	
	int bossX1;
	int bossY1;
	int bossX2;
	int bossY2;
	int count;
	public Boss(MySurfaceView father)
	{
		this.father=father;
		Bitmap temp1=BitmapIOUtil.getBitmap("boss3.png", father.father.getResources());
		Bitmap temp2=BitmapIOUtil.getBitmap("boss5.png", father.father.getResources());
		int width1=temp1.getWidth()/3;		//每一个boss的宽度
		int height1=temp1.getHeight()/2;		//每一个boss的高度
		int width2=temp2.getWidth()/3;
		int height2=temp2.getHeight()/2;
		
		bossX1=width1/2;		
		bossY1=height1/2;
		bossX2=width2/2;
		bossY2=height2/2;
		
		for(int i=0;i<2;i++)	//取出6个boss的图片
		{
			for(int j=0;j<3;j++)
			{
				boss1[3*i+j]=Bitmap.createBitmap(temp1, width1*j,height1*i,width1,height1);
				boss2[3*i+j]=Bitmap.createBitmap(temp2, width2*j,height2*i,width2,height2);
			}
		}
	}

	@Override
	public void drawSelf(Canvas canvas, int integer, int integer2, int integer3) {
		int bossX,bossY,bossNum;
		boolean bossFlag;	
		synchronized(father.gd.lock)
		{
			bossX=father.gd.bossX;
			bossY=father.gd.bossY;
			bossFlag=father.gd.bossFlag;
			bossNum=father.gd.bossNum;
		}
		count=(count+1)%60;	//boss变化的时间
		if(bossFlag)		//如果有boss
		{
			if(bossNum==1)
			{
				canvas.drawBitmap(boss1[count/10], bossX-this.bossX1, bossY-this.bossY1, null);
			}else if(bossNum==2)
			{
				canvas.drawBitmap(boss2[count/10], bossX-this.bossX2, bossY-this.bossY2, null);
			}
			
		}
	}
}