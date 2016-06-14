package com.bn.object;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.bn.tank.MySurfaceView;
import com.bn.util.BitmapIOUtil;

public class MainBullet	extends Thing
{
	MySurfaceView father;
	Bitmap mainBullet;
	Bitmap otherBullet1;
	Bitmap otherBullet2;
	Bitmap boss1Bullet;
	Bitmap boss2Bullet;
	
	float mainBulletX;
	float mainBulletY;
	
	float otherBulletX1;
	float otherBulletY1;
	
	float otherBulletX2;
	float otherBulletY2;
	
	float boss1BulletX;
	float boss1BulletY;
	
	float boss2BulletX;
	float boss2BulletY;
	
	public MainBullet(MySurfaceView father)
	{
		this.father=father;
		mainBullet=BitmapIOUtil.getBitmap("tankBullet.png", father.father.getResources());
		otherBullet1=BitmapIOUtil.getBitmap("enemyBullet.png",father.father.getResources());
		otherBullet2=BitmapIOUtil.getBitmap("enemyBazooka.png", father.father.getResources());
		boss1Bullet=BitmapIOUtil.getBitmap("bombboss.png", father.getResources());
		boss2Bullet=BitmapIOUtil.getBitmap("boss2bullet.png", father.getResources());
		
		mainBulletX=mainBullet.getWidth()/2;
		mainBulletY=mainBullet.getHeight()/2;
		
		otherBulletX1=otherBullet1.getWidth()/2;
		otherBulletY1=otherBullet1.getHeight()/2;
		
		otherBulletX2=otherBullet2.getWidth()/2;
		otherBulletY2=otherBullet2.getHeight()/2;
		
		boss1BulletX=boss1Bullet.getWidth()/2;
		boss1BulletY=boss1Bullet.getHeight()/2;
		
		boss2BulletX=boss2Bullet.getWidth()/2;
		boss2BulletY=boss2Bullet.getHeight()/2;
		
	}

	@Override
	public void drawSelf(Canvas canvas, int x, int y, int angle) {
		if(father.gd.mainBullet!=null)
		{
			float tempStr[];
			
			synchronized(father.gd.lock)
			{
				int length=father.gd.mainBullet.size();
				tempStr=new float[length];
				for(int i=0;i<length;i++)
				{
					tempStr[i]=father.gd.mainBullet.get(i);
				}
				
			}
			int count=0;
			while(count<tempStr.length)
			{
				canvas.drawBitmap(
						mainBullet, 
						tempStr[count++]-mainBulletX, 
						tempStr[count++]-mainBulletY, 
						null);
			}
		}
		if(father.gd.mainMissile!=null)
		{
			ArrayList<Float> temp;
			synchronized(father.gd.lock)
			{
				temp=new ArrayList<Float>(father.gd.mainMissile);
			}
			int count=0;
			while(count<temp.size())
			{
				canvas.save();
				canvas.translate(temp.get(count++)-otherBulletX2,temp.get(count++)-otherBulletY2);
				canvas.rotate(temp.get(count++),otherBulletX2,otherBulletY2);
				canvas.drawBitmap(otherBullet2, 0, 0, null);
				canvas.restore();
			}
		}
		
		if(father.gd.otherBullet!=null)
		{
			float tempStr2[];
			synchronized(father.gd.lock)
			{
				int length2=father.gd.otherBullet.size();
				tempStr2=new float[length2];
				for(int i=0;i<length2;i++)
				{
					tempStr2[i]=father.gd.otherBullet.get(i);
				}
			}
			int count=0;
			while(count<tempStr2.length)
			{
				System.out.println(" temp count "+tempStr2[count]);
				if(tempStr2[count++]==0)
				{
					canvas.save();
					canvas.translate(tempStr2[count++]-otherBulletX1,tempStr2[count++]-otherBulletY1);
					canvas.rotate(tempStr2[count++],otherBulletX1,otherBulletY1);
					canvas.drawBitmap(otherBullet1, 0, 0, null);
					canvas.restore();
				}else
				{
					canvas.save();
					canvas.translate(tempStr2[count++]-otherBulletX2,tempStr2[count++]-otherBulletY2);
					canvas.rotate(tempStr2[count++],otherBulletX2,otherBulletY2);
					canvas.drawBitmap(otherBullet2, 0, 0, null);
					canvas.restore();
				}
			}
		}
		
		if(father.gd.bossbullet!=null)
		{
			float tempStr3[];
			synchronized(father.gd.lock)
			{
				int length3=father.gd.bossbullet.size();
				tempStr3=new float[length3];
				for(int i=0;i<length3;i++)
				{
					tempStr3[i]=father.gd.bossbullet.get(i);
					
				}
			}
			int count=0;
			while(count<tempStr3.length)
			{
				System.out.println(" temp count "+tempStr3[count]);
				if(tempStr3[count]==0)
				{
					count++;
					canvas.save();
					canvas.translate(tempStr3[count++]-mainBulletX,tempStr3[count++]-mainBulletY);
					canvas.rotate(tempStr3[count++],otherBulletX1,otherBulletY1);
					canvas.drawBitmap(mainBullet, 0, 0, null);
					canvas.restore();
				}else if(tempStr3[count]==1)
				{
					count++;
					canvas.save();
					canvas.translate(tempStr3[count++]-boss1BulletX,tempStr3[count++]-boss1BulletX);
					canvas.rotate(tempStr3[count++],boss1BulletX,boss1BulletY);
					canvas.drawBitmap(boss1Bullet, 0, 0, null);
					canvas.restore();
				}else if(tempStr3[count]==2)
				{
					count++;
					canvas.save();
					canvas.translate(tempStr3[count++]-boss2BulletX,tempStr3[count++]-boss2BulletY);
					canvas.rotate(tempStr3[count++],boss2BulletX,boss2BulletY);
					canvas.drawBitmap(boss2Bullet, 0, 0, null);
					canvas.restore();
				}
			}
		}
	}
}