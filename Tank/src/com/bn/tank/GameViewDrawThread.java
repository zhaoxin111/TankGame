package com.bn.tank;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameViewDrawThread extends Thread{
	boolean flag = true;										//开启线程标志位
	int sleepSpan = 20;											//睡眠的时间
	MySurfaceView mView;										//定义SurfaceView对象
	SurfaceHolder surfaceHolder;								//定义接口
	public GameViewDrawThread(MySurfaceView mView){
		this.mView = mView;										//获取SurfaceView的引用
		this.surfaceHolder = mView.getHolder();					//注册回调接口
	}
	public void run(){											//开启绘制线程
		Canvas c=null;											//定义画布
        while (this.flag) {										//进入线程
            try {
                c = this.surfaceHolder.lockCanvas(null);		// 锁定整个画布，在内存要求比较高的情况下，建议参数不要为null
                synchronized (this.surfaceHolder) {				//同步方法
                	if(this.flag){								//如果标志位为true
                    	mView.onDraw(c);						//绘制界面
            }}}finally {
                if (c != null) {								//如果画布不为空
                    this.surfaceHolder.unlockCanvasAndPost(c);	//并释放锁
            }}try{
            	Thread.sleep(sleepSpan);						//睡眠指定毫秒数				
            }catch(Exception e){
            	e.printStackTrace();							//打印堆栈信息
}}}}
