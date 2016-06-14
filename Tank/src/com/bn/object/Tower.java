package com.bn.object;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

import com.bn.tank.MySurfaceView;
import com.bn.util.BitmapIOUtil;

public class Tower extends Thing
{
	MySurfaceView father;
	Matrix m=new Matrix();
	
	Bitmap tower;
	float towerX;
	float towerY;
	
	public Tower(MySurfaceView father)
	{
		this.father=father;
		tower=BitmapIOUtil.getBitmap("tower.png",father.father.getResources());
		
		towerX=tower.getWidth()/2;
		towerY=tower.getHeight()/2;
	}
	@Override
	public void drawSelf(Canvas canvas,int tx,int  ty,int t)
	{		
		m.reset();
		m.postTranslate(tx-towerX, ty-towerY);
		canvas.drawBitmap(tower, m, null);
	}
	
}
