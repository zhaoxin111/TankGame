package com.bn.object;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.bn.tank.MySurfaceView;
import com.bn.util.BitmapIOUtil;

public class Explosion extends Thing
{
	MySurfaceView father;
	Bitmap explosion1;
	Bitmap explosion2;
	Bitmap explosion[]=new Bitmap[50];
	int size[]=new int[2];
	public Explosion(MySurfaceView father)
	{
		this.father=father;
		explosion1=BitmapIOUtil.getBitmap("explosion1.png", father.father.getResources());
		explosion2=BitmapIOUtil.getBitmap("explosion2.png", father.father.getResources());
		size[0]=explosion1.getWidth()/5;
		size[1]=explosion2.getWidth()/5;
		for(int i=0;i<50;i++)
		{
			int j=i%25;
			if(i/25==0)
			{
				explosion[i]=Bitmap.createBitmap(explosion1, (j%5)*size[0], (j/5)*size[0], size[0], size[0]);
			}else
			{
				explosion[i]=Bitmap.createBitmap(explosion2, (j%5)*size[1], (j/5)*size[1], size[1], size[1]);
			}
		}
	}

	@Override
	public void drawSelf(Canvas canvas, int x, int y, int angle) {
		if(father.gd.explosion==null)
		{
			return;
		}
		int temp[];
		int count=0;
		synchronized(father.gd.lock)
		{
			temp=new int[father.gd.explosion.length];
			while(count<father.gd.explosion.length)
			{
				temp[count]=father.gd.explosion[count++];
			}
		}
		count=0;
		while(count<temp.length)
		{
			int flag=temp[count]/25;
			canvas.drawBitmap(explosion[temp[count++]], temp[count++]-size[flag]/2, temp[count++]-size[flag]/2, null);
		}
	}
}