package com.bn.tank;

import com.bn.util.BitmapIOUtil;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class WelcomeView 
{
	public float ratio;//��ͬ�ֱ���X����ϵ��
	public float screenWidth;//��Ļ���
	public float screenHeight;//��Ļ�߶�
	public float screenLeft=0;//��Ļ�������
	public float screenTop=0;//��Ļ�߶�����
	
	Bitmap back;		//����ͼ��
	Bitmap decoration;	//
	Bitmap load;
	
	int Downtime=0;
	MySurfaceView father;
	public WelcomeView(MySurfaceView father)
	{
		this.father=father;
		back=BitmapIOUtil.getBitmap("gameBackground.png", father.getResources());
		decoration=BitmapIOUtil.getBitmap("decoration.png", father.getResources());	
		load=BitmapIOUtil.getBitmap("load.png", father.getResources());
		
		Matrix matrix = new Matrix(); 
		matrix.setScale(1.2f, 1.2f);					//����1.2��
		
		ratio=father.gd.ratio;
		
		back=Bitmap.createBitmap(back, 0, 0, back.getWidth(), back.getHeight(), matrix, true);
		decoration=Bitmap.createBitmap(decoration, 0, 0, decoration.getWidth(), decoration.getHeight(), matrix, true);
		load=Bitmap.createBitmap(load, 0, 0, load.getWidth(), load.getHeight(), matrix, true);
		
	}
	
	protected void draw(Canvas canvas)
	{
		canvas.save();												//������ͼƬ
		canvas.drawBitmap(back, 0, 0, null);
		canvas.restore();
		
		canvas.save();							
		canvas.translate(260,50);									//�����������ͼƬ
		canvas.drawBitmap(decoration, 0, 0, null);
		canvas.restore();
		
		
		if(father.gd.loadTime%50>0&&father.gd.loadTime%50<25)
		{
			canvas.save();
			canvas.translate(400,420);
			canvas.drawBitmap(load,0,0, null);
			canvas.restore();
		}
		father.gd.loadTime++;
	}
}
