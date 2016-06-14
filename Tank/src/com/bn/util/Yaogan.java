package com.bn.util;

import com.bn.tank.MySurfaceView;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Yaogan
{
	MySurfaceView father;
	Bitmap direction;
	Bitmap center;
	public int directionR;//ҡ���̰뾶
	public int centerR;//ҡ�˰뾶
	//direction���ĵ�����
	public float leftDirectionX;
	public float leftDirectionY;
	public float rightDirectionX;
	public float rightDirectionY;
	//center���ĵ�����
	float leftCenterX;
    float leftCenterY;
    float rightCenterX;
    float rightCenterY;
	//centerƫ����
    public float leftOffsetX=0;
    public float leftOffsetY=0;
    public float rightOffsetX=0;
    public float rightOffsetY=0;
    
	public Yaogan(MySurfaceView father)
	{
		this.father=father;
		direction=BitmapIOUtil.getBitmap("direction.png", father.father.getResources());
		center=BitmapIOUtil.getBitmap("center.png", father.father.getResources());
		directionR=direction.getWidth()/2;
		centerR=center.getWidth()/2;
		leftDirectionX=30+directionR;
		leftDirectionY=father.screenHeight-(30+directionR);
		rightDirectionX=father.screenWidth-30-directionR;
		rightDirectionY=father.screenHeight-(30+directionR);
	}
	
	public void drawSelf(Canvas canvas)
	{
		//��ҡ��
		canvas.drawBitmap(direction, 30, leftDirectionY-directionR, null);//����ҡ��1
		leftCenterX=leftDirectionX+leftOffsetX;
		leftCenterY=leftDirectionY+leftOffsetY;
    	float leftYaoganX=leftCenterX-centerR;
    	float leftYaoganY=leftCenterY-centerR;
    	canvas.drawBitmap(center,leftYaoganX,leftYaoganY, null);//����ҡ��2
    	//��ҡ��
    	canvas.drawBitmap(direction, father.screenWidth-2*directionR-30, rightDirectionY-directionR, null);//����ҡ��1
    	rightCenterX=rightDirectionX+rightOffsetX;
		rightCenterY=rightDirectionY+rightOffsetY;
    	float rightYaoganX=rightCenterX-centerR;
    	float rightYaoganY=rightCenterY-centerR;
    	canvas.drawBitmap(center,rightYaoganX,rightYaoganY, null);//����ҡ��2
	}
	
	public void changeRightYaoGan(float tx, float ty) 
	{
		//��ȡҡ�����������꣬ҡ���̰뾶��ҡ�˰뾶�Ĳ�����ﵽҡ����ҡ�������е�Ч����
		float x=rightDirectionX,y=rightDirectionY,r=directionR-centerR;
		float dir=(tx-x)*(tx-x)+(ty-y)*(ty-y);
		if(dir<r*r)//���ɶ��� ����λ��û����ҡ����
		{
			rightOffsetX=tx-x;//����λ����ҡ�������ĵ�Xƫ����----��x
			rightOffsetY=ty-y;//����λ����ҡ�������ĵ�Yƫ����----��y
		}
		else
		{
			float angle=(float) Math.atan((ty-y)/(tx-x));//���ݦ�x�릤y����Ƕ�
			if(tx-x>0)
			{
				rightOffsetX=(float) (r*Math.cos(angle));//���ݰ뾶���Ƕȼ���xƫ����
				rightOffsetY=(float) (r*Math.sin(angle));//���ݰ뾶���Ƕȼ���yƫ����
			}
			else
			{
				rightOffsetX=(float) (-r*Math.cos(angle));
				rightOffsetY=(float) (-r*Math.sin(angle));
			}
		}
	}
	
	public void changeLeftYaoGan(float tx, float ty) 
	{
		//��ȡҡ�����������꣬ҡ���̰뾶��ҡ�˰뾶�Ĳ�����ﵽҡ����ҡ�������е�Ч����
		float x=leftDirectionX,y=leftDirectionY,r=directionR-centerR;
		float dir=(tx-x)*(tx-x)+(ty-y)*(ty-y);
		if(dir<r*r)//���ɶ��� ����λ��û����ҡ����
		{
			leftOffsetX=tx-x;//����λ����ҡ�������ĵ�Xƫ����----��x
			leftOffsetY=ty-y;//����λ����ҡ�������ĵ�Yƫ����----��y
		}
		else
		{
			float angle=(float) Math.atan((ty-y)/(tx-x));//���ݦ�x�릤y����Ƕ�
			if(tx-x>0)
			{
				leftOffsetX=(float) (r*Math.cos(angle));//���ݰ뾶���Ƕȼ���xƫ����
				leftOffsetY=(float) (r*Math.sin(angle));//���ݰ뾶���Ƕȼ���yƫ����
			}
			else
			{
				leftOffsetX=(float) (-r*Math.cos(angle));
				leftOffsetY=(float) (-r*Math.sin(angle));
			}
		}
	}
	
	public int isYaoGan(float tx,float ty)//����Ƿ�����ҡ����
	{
		//��ȡҡ�����������꣬ҡ���̰뾶
		if(tx<father.screenWidth/2)
		{
			float x=leftDirectionX,y=leftDirectionY,r=directionR;
			float dir=(tx-x)*(tx-x)+(ty-y)*(ty-y);
			if(dir<r*r)
			{
				leftOffsetX=tx-x;
				leftOffsetY=ty-y;
				return 1;
			}
		}else
		{
			float x=rightDirectionX,y=rightDirectionY,r=directionR;
			float dir=(tx-x)*(tx-x)+(ty-y)*(ty-y);
			if(dir<r*r)
			{
				rightOffsetX=tx-x;
				rightOffsetY=ty-y;
				return 2;
			}
		}
		return 0;
	}
}