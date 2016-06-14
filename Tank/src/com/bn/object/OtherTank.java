package com.bn.object;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

import com.bn.tank.MySurfaceView;
import com.bn.util.BitmapIOUtil;

public class OtherTank extends Thing
{
	MySurfaceView father;
	Matrix m=new Matrix();
	Bitmap tankbody[]=new Bitmap[4];
	Bitmap tankgun[]=new Bitmap[2];
	Bitmap tankGunTemp[]=new Bitmap[4];
	
	float tankbodyX;
	float tankbodyY;
	float tankgunX;
	float tankgunY;
	int num;
	
	public OtherTank(MySurfaceView father,int num)
	{
		this.num=num;
		this.father=father;
		tankbody[0]=BitmapIOUtil.getBitmap("enemybody1.png",father.father.getResources());
		tankbody[1]=BitmapIOUtil.getBitmap("enemybody2.png",father.father.getResources());
		tankbody[2]=BitmapIOUtil.getBitmap("enemybody3.png",father.father.getResources());
		tankbody[3]=BitmapIOUtil.getBitmap("enemybody4.png",father.father.getResources());

		tankGunTemp[0]=BitmapIOUtil.getBitmap("enemygun1.png", father.father.getResources());
		tankGunTemp[1]=BitmapIOUtil.getBitmap("enemygun2.png", father.father.getResources());
		tankGunTemp[2]=BitmapIOUtil.getBitmap("enemygun3.png", father.father.getResources());
		tankGunTemp[3]=BitmapIOUtil.getBitmap("enemygun4.png", father.father.getResources());
		
		int width=tankGunTemp[num].getWidth()/2;
		int height=tankGunTemp[num].getHeight();
		
		tankgun[0]=Bitmap.createBitmap(tankGunTemp[num], 0, 0, width, height);
		tankgun[1]=Bitmap.createBitmap(tankGunTemp[num], width, 0, width, height);
		
		tankbodyX=tankbody[0].getWidth()/2;
		tankbodyY=tankbody[0].getHeight()/2;
		tankgunX=tankgun[0].getWidth()/2;
		tankgunY=tankgun[1].getHeight()*23/32;
		
	}
	
	public void drawSelf(Canvas canvas,int tank1X,int tank1Y,int tankAngle)
	{
		int ggs;
		
		synchronized(father.gd.lock)
		{
			ggs=father.gd.State;
		}

	//	tankAngle=toAngle(dx,dy);
		
		canvas.save();
		canvas.translate(tank1X-tankbodyX,tank1Y-tankbodyY);
		canvas.drawBitmap(tankbody[num], 0, 0, null);
		canvas.restore();
		
		canvas.save();
		canvas.translate(tank1X-tankgunX,tank1Y-tankgunY);
		canvas.rotate(tankAngle, tankgunX,tankgunY);
		canvas.drawBitmap(tankgun[ggs], 0, 0, null);
		canvas.restore();
		
	}
//	public float toAngle(float dx,float dy)
//	{
//		float tankAngle=(float) Math.toDegrees(Math.atan(dy/dx));
//		if(dx==0)
//		{
//			if(dy>0)
//			{
//				tankAngle=180;
//			}else
//			{
//				tankAngle=0;
//			}
//		}
//		else if(dx>0)
//		{	
//			tankAngle=(float)Math.toDegrees(Math.atan(dy/dx));
//			tankAngle=90+tankAngle;
//		}else if(dx<0)
//		{	
//			tankAngle=(float)Math.toDegrees(Math.atan(dy/dx));
//			tankAngle=270+tankAngle;
//		}
//		return tankAngle;
//	}
}
