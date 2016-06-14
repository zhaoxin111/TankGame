package com.bn.object;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Canvas;
import com.bn.tank.MySurfaceView;
import com.bn.util.BitmapIOUtil;

public class Tree extends Thing
{
	MySurfaceView father;
	Matrix m=new Matrix();				//¾ØÕó
	Bitmap tree[]=new Bitmap[2];
	int num;
	float TreeX;
	float TreeY;
	
	public Tree(MySurfaceView father,int a)		
	{
		this.father=father;
		this.num=a;
		tree[0]=BitmapIOUtil.getBitmap("tree1.png",father.father.getResources());	
		tree[1]=BitmapIOUtil.getBitmap("tree2.png",father.father.getResources());
		TreeX=tree[0].getWidth()/2;
		TreeY=tree[0].getHeight()/2;
		
	}
	@Override
	public void drawSelf(Canvas canvas,int t1x,int t1y,int t)
	{		
		m.reset();
//		m.postScale(Tree1X*2*father.ratioX, Tree1Y*2*father.ratioY);
		m.postTranslate(t1x-TreeX, t1y-TreeY);
		canvas.drawBitmap(tree[num],m, null);			//»­Ê÷1
		
//		m.reset();
//		m.postTranslate(t2x,t2y);
//	//	m.postScale(200, 200);
//		canvas.drawBitmap(tree2,m,null);			//»­Ê÷2
		
		
	}

}
