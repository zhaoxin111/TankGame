package com.bn.object;

import com.bn.tank.MySurfaceView;
import com.bn.util.BitmapIOUtil;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class Award extends Thing			// c h p
{
	MySurfaceView father;
	Matrix m=new Matrix();
	Bitmap award[]=new Bitmap[3];
	int num;
	
	float propsCX;
	float propsCY;
	float propsHX;
	float propsHY;
	float propsPX;
	float propsPY;
	
	public Award(MySurfaceView father,int num)
	{
		this.father=father;
		this.num=num;
		award[0]=BitmapIOUtil.getBitmap("propsC.png",father.father.getResources());
		award[1]=BitmapIOUtil.getBitmap("propsH.png",father.father.getResources());
		award[2]=BitmapIOUtil.getBitmap("propsP.png",father.father.getResources());
		
		propsCX=award[0].getWidth()/2;
		propsCY=award[0].getHeight()/2;
		
	}
	@Override
	public void drawSelf(Canvas canvas, int tx, int ty, int angle)
	{
		m.reset();
		m.postTranslate(tx-propsCX, ty-propsCY);
		canvas.drawBitmap(award[num], m, null);
		
	}
	
}
