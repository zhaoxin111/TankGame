package com.bn.util;

import com.bn.tank.MySurfaceView;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Yaogan
{
	MySurfaceView father;
	Bitmap direction;
	Bitmap center;
	public int directionR;//摇杆盘半径
	public int centerR;//摇杆半径
	//direction中心点坐标
	public float leftDirectionX;
	public float leftDirectionY;
	public float rightDirectionX;
	public float rightDirectionY;
	//center中心点坐标
	float leftCenterX;
    float leftCenterY;
    float rightCenterX;
    float rightCenterY;
	//center偏移量
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
		//左摇杆
		canvas.drawBitmap(direction, 30, leftDirectionY-directionR, null);//绘制摇杆1
		leftCenterX=leftDirectionX+leftOffsetX;
		leftCenterY=leftDirectionY+leftOffsetY;
    	float leftYaoganX=leftCenterX-centerR;
    	float leftYaoganY=leftCenterY-centerR;
    	canvas.drawBitmap(center,leftYaoganX,leftYaoganY, null);//绘制摇杆2
    	//右摇杆
    	canvas.drawBitmap(direction, father.screenWidth-2*directionR-30, rightDirectionY-directionR, null);//绘制摇杆1
    	rightCenterX=rightDirectionX+rightOffsetX;
		rightCenterY=rightDirectionY+rightOffsetY;
    	float rightYaoganX=rightCenterX-centerR;
    	float rightYaoganY=rightCenterY-centerR;
    	canvas.drawBitmap(center,rightYaoganX,rightYaoganY, null);//绘制摇杆2
	}
	
	public void changeRightYaoGan(float tx, float ty) 
	{
		//获取摇杆盘中心坐标，摇杆盘半径与摇杆半径的差（用来达到摇杆与摇杆盘内切的效果）
		float x=rightDirectionX,y=rightDirectionY,r=directionR-centerR;
		float dir=(tx-x)*(tx-x)+(ty-y)*(ty-y);
		if(dir<r*r)//勾股定理 触控位置没超过摇杆盘
		{
			rightOffsetX=tx-x;//触控位置与摇杆盘中心点X偏移量----Δx
			rightOffsetY=ty-y;//触控位置与摇杆盘中心点Y偏移量----Δy
		}
		else
		{
			float angle=(float) Math.atan((ty-y)/(tx-x));//根据Δx与Δy计算角度
			if(tx-x>0)
			{
				rightOffsetX=(float) (r*Math.cos(angle));//根据半径、角度计算x偏移量
				rightOffsetY=(float) (r*Math.sin(angle));//根据半径、角度计算y偏移量
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
		//获取摇杆盘中心坐标，摇杆盘半径与摇杆半径的差（用来达到摇杆与摇杆盘内切的效果）
		float x=leftDirectionX,y=leftDirectionY,r=directionR-centerR;
		float dir=(tx-x)*(tx-x)+(ty-y)*(ty-y);
		if(dir<r*r)//勾股定理 触控位置没超过摇杆盘
		{
			leftOffsetX=tx-x;//触控位置与摇杆盘中心点X偏移量----Δx
			leftOffsetY=ty-y;//触控位置与摇杆盘中心点Y偏移量----Δy
		}
		else
		{
			float angle=(float) Math.atan((ty-y)/(tx-x));//根据Δx与Δy计算角度
			if(tx-x>0)
			{
				leftOffsetX=(float) (r*Math.cos(angle));//根据半径、角度计算x偏移量
				leftOffsetY=(float) (r*Math.sin(angle));//根据半径、角度计算y偏移量
			}
			else
			{
				leftOffsetX=(float) (-r*Math.cos(angle));
				leftOffsetY=(float) (-r*Math.sin(angle));
			}
		}
	}
	
	public int isYaoGan(float tx,float ty)//检测是否点击到摇杆盘
	{
		//获取摇杆盘中心坐标，摇杆盘半径
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