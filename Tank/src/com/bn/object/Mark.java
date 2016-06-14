package com.bn.object;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

import com.bn.tank.MySurfaceView;
import com.bn.util.BitmapIOUtil;

public class Mark extends Thing
{
		MySurfaceView father;
		Matrix m=new Matrix();		
		
		Bitmap mark1;
		Bitmap []mark=new Bitmap[2];
		
		float mark1X;
		float mark1Y;
		int size;
		
		public  Mark(MySurfaceView father, int size)
		{
			this.father=father;
			this.size=size+1;
			mark1=BitmapIOUtil.getBitmap("mark1.png",father.father.getResources());
			mark1X=mark1.getWidth()/2;
			mark1Y=mark1.getHeight()/2;
			
		}
		@Override
		public void drawSelf(Canvas canvas,int t1x,int t1y,int t)
		{			
			m.reset();
			m.setScale(size, size);
			m.postTranslate(t1x-mark1X*size, t1y-mark1Y*size);
			canvas.drawBitmap(mark1,m, null);			//»­Ê÷1
		}
}
