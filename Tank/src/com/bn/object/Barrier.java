package com.bn.object;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

import com.bn.tank.MySurfaceView;
import com.bn.util.BitmapIOUtil;

public class Barrier extends Thing
{
	MySurfaceView father;
	Matrix m=new Matrix();
	Bitmap barrier;
	
	float barrierX;
	float barrierY;
	
	public Barrier(MySurfaceView father)
	{
		this.father=father;
		barrier=BitmapIOUtil.getBitmap("barrier.png",father.father.getResources());
		
		barrierX=barrier.getWidth()/2;
		barrierY=barrier.getHeight()/2;
		
	}
	@Override
	public void drawSelf(Canvas canvas,int bx,int by,int t)
	{		
		m.reset();
		m.postTranslate(bx-barrierX, by-barrierY);
		canvas.drawBitmap(barrier, m, null);
	}
	
}
