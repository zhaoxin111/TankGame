package com.bn.object;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.bn.tank.MySurfaceView;
import com.bn.util.BitmapIOUtil;

public class MainTank extends Thing
{
	MySurfaceView father;
	Bitmap mainBodyr;
	Bitmap mainBodyg;
	Bitmap mainGun[]=new Bitmap[2];
	//tankÍ¼Æ¬ÖÐÐÄµã
    float mainTankX;
    float mainTankY;
    float mainGunX;
    float mainGunY;
	public MainTank(MySurfaceView father)
	{
		this.father=father;
		mainBodyr=BitmapIOUtil.getBitmap("mainbodyr.png", father.father.getResources());
		mainBodyg=BitmapIOUtil.getBitmap("mainbodyg.png", father.father.getResources());
		Bitmap mainGunTemp=BitmapIOUtil.getBitmap("maingun.png", father.father.getResources());
		int width=mainGunTemp.getWidth()/2;
		int height=mainGunTemp.getHeight();
		mainGun[0]=Bitmap.createBitmap(mainGunTemp, 0, 0, width, height);
		mainGun[1]=Bitmap.createBitmap(mainGunTemp, width, 0, width, height);
	//	System.out.println(mainBodyr.getWidth()+"||"+mainBodyr.getHeight());
		mainTankX=mainBodyr.getWidth()/2;
		mainTankY=mainBodyr.getHeight()/2;
		mainGunX=mainGun[0].getWidth()/2+1;
		mainGunY=mainGun[0].getHeight()*23/32;
	}
	@Override
	public void drawSelf(Canvas canvas, int x, int y, int angle) {
		int rx=0;
		int gx=0;
		int ry=0;
		int gy=0;
		int rgs=0;
		int ggs=0;
		int rh=0;
		int gh=0;
		float rta=0;
		float gta=0;
		float rga=0;
		float gga=0;
		
		synchronized(father.gd.lock)
		{
			rx=father.gd.redX;
			ry=father.gd.redY;
			gx=father.gd.greenX;
			gy=father.gd.greenY;
			rh=father.gd.redHealth;
			gh=father.gd.greenHealth;
			rgs=father.gd.redGunState;
			ggs=father.gd.greenGunState;
			rta=father.gd.redTankAngle;
			gta=father.gd.greenTankAngle;
			rga=father.gd.redGunAngle;
			gga=father.gd.greenGunAngle;
		}
		
		if(rh>0)
		{
			canvas.save();
			canvas.translate(rx-mainTankX,ry-mainTankY);
			canvas.rotate(rta, mainTankX, mainTankY);
			canvas.drawBitmap(mainBodyr, 0, 0, null);
			canvas.restore();
			
			canvas.save();
			canvas.translate(rx-mainGunX, ry-mainGunY);
			canvas.rotate(rga, mainGunX, mainGunY);
			canvas.drawBitmap(mainGun[rgs], 0, 0, null);
			canvas.restore();
		}
		if(gh>0)
		{
			canvas.save();
			canvas.translate(gx-mainTankX, gy-mainTankY);
			canvas.rotate(gta, mainTankX, mainTankY);
			canvas.drawBitmap(mainBodyg, 0, 0, null);
			canvas.restore();		
			
			canvas.save();
			canvas.translate(gx-mainGunX, gy-mainGunY);
			canvas.rotate(gga, mainGunX, mainGunY);
			canvas.drawBitmap(mainGun[ggs], 0, 0, null);
			canvas.restore();
		}		
	}
}