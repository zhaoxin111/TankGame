package com.bn.tank;

import com.bn.data.GameData;
import com.bn.object.Award;
import com.bn.object.Barrier;
import com.bn.object.Boss;
import com.bn.object.Explosion;
import com.bn.object.MainBullet;
import com.bn.object.MainTank;
import com.bn.object.Mark;
import com.bn.object.OtherTank;
import com.bn.object.Thing;
import com.bn.object.Tower;
import com.bn.object.Tree;
import com.bn.util.BitmapIOUtil;
import com.bn.util.NetworkThread;
import com.bn.util.Yaogan;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback
{
	public GameData gd=new GameData();
	public NetworkThread nt;
	
	public TankActivity father;
	public float ratio;			//不同分辨率X方向系数
	public float screenWidth;	//屏幕宽度
	public float screenHeight;	//屏幕高度
	public float screenLeft=0;	//屏幕宽度留白
	public float screenTop=0;	//屏幕高度留白
	int yaoganFlag=0;
	int pointNumber[]={-1,-1};
	RectF rectBackground;		//背景绘制矩形
	Bitmap back;				//背景图标
	
	MenuView menu;				//菜单
	WelcomeView down;				//加载界面
	SelectView select;			//选择界面
	GameViewDrawThread gameViewDrawThread;
	public Yaogan yaogan;	
	MainTank mainTank;
	MainBullet mainBullet; 
	Explosion explosion;
	Boss boss;
	OtherView about;			//生命值，分数,暂停界面
	Thing thing[];
	
	public int gameState=1;
	


	public MySurfaceView(TankActivity activity,int width,int height)	
	{
		super(activity);
		this.father=activity;		//获取Activity对象
		getHolder().addCallback(this);//注册回调接口
		
		screenWidth=width;			//记录屏幕的宽度
		screenHeight=height;		//记录屏幕的高度
		if(screenWidth>screenHeight*16/9)		//如果玩家手机屏幕过宽
		{
			ratio=screenHeight/GameData.baseHeight;	//获取屏幕高的比例
			screenHeight=GameData.baseHeight;		//设置屏幕显示的高度
			screenLeft=(screenWidth/ratio-GameData.baseWidth)/2; //设置屏幕左面留白的长度
			screenWidth=GameData.baseWidth;		//设置屏幕显示的宽度
		}else									//如果玩家手机屏幕过高
		{
			ratio=screenWidth/GameData.baseWidth;	//获取屏幕宽的比例
			screenWidth=GameData.baseWidth;	//设置屏幕的宽度
			screenTop=(screenHeight/ratio-GameData.baseHeight)/2;	//设置屏幕上面留白的长度
			screenHeight=GameData.baseHeight;	//设置屏幕的高度		
		}
		gd.ratio=ratio;							//记录屏幕的比例
		
		rectBackground = new RectF(0, 0, screenWidth, screenHeight); 	//绘制矩形的面积	
		back=BitmapIOUtil.getBitmap("gameBackground.png", activity.getResources());//绘制背景图
		Matrix matrix = new Matrix();			//定义矩阵
		matrix.setScale(1.2f, 1.2f);			//放大矩阵长宽各1.2倍
		back=Bitmap.createBitmap(back, 0, 0, back.getWidth(), back.getHeight(), matrix, true);//放大背景长宽各1.2
		initObject();			//初始化刷帧线程与物体
	}
	private void initObject(){						//
		Tree t1=new Tree(this,0);										
		Tree t2=new Tree(this,1);
		Mark m1=new Mark(this,0);
		Mark m2=new Mark(this,1);
		Barrier b=new Barrier(this);
		Tower t=new Tower(this);
		OtherTank et1=new OtherTank(this,0);
		OtherTank et2=new OtherTank(this,1);
		OtherTank et3=new OtherTank(this,2);
		OtherTank et4=new OtherTank(this,3);
		Award ac=new Award(this,0);					//12
		Award ah=new Award(this,1);
		Award ap=new Award(this,2);
		thing=new Thing[]{t1,t2,m1,m2,b,b,t,t,et1,et2,et3,et4,ac,ah,ap};
		mainBullet=new MainBullet(this);
		mainTank=new MainTank(this);
		yaogan=new Yaogan(this);
		explosion=new Explosion(this);
		about=new OtherView(this);
		boss=new Boss(this);
		menu=new MenuView(this);
		down=new WelcomeView(this);
		select=new SelectView(this);
	}

	public void openNetwork() 	//打开Android端接收数据线程
	{
		nt=new NetworkThread(MySurfaceView.this);
		nt.start();
	}

	@Override
	protected void onDraw(Canvas canvas) 		//绘制游戏画面
	{
		canvas.scale(ratio, ratio);				//放大画布
		canvas.save();
		canvas.translate(screenLeft, screenTop);//平移到新的原点
		canvas.clipRect(rectBackground);		//设置裁剪区
		
		int gameState=GameData.viewState;	//当前状态
		
		switch(gameState)
		{
		case GameData.Game_load:			//加载
			down.draw(canvas);
			break;
		case GameData.Game_menu:			//菜单
			menu.draw(canvas);
			break;
		case GameData.Game_palying:			//游戏开始
			drawSelf(canvas);
			break;
		case GameData.Game_select:
			select.drawSelf(canvas);
			break;
		}
    	canvas.restore();								//恢复画面
	}
	
	@Override
    public boolean onTouchEvent(MotionEvent event){
		int gameState=GameData.viewState;								//获取游戏状态
		switch(gameState){
			case GameData.Game_load:									//游戏加载界面
				break;
			case GameData.Game_menu:									//游戏菜单界面
				menu.onTouchEvent(event);								//添加菜单触摸监听
				break;
			case GameData.Game_palying:									//游戏中的状体
				mainTouch(event);										//游戏中监听
				break;
			case GameData.Game_pause:									//游戏暂停状态
				break;
			case GameData.Game_select:									//游戏选关界面
				select.onTouchEvent(event);								//添加选关监听
				break;
		}
    	return true;
    }

	private void drawSelf(Canvas canvas){				//画  游戏中 界面  
		int offset;										//画面偏移量
		synchronized(gd.lock){							//对数据库进行操作添加锁
			offset=gd.offset;							//获取游戏的进度
		}
		canvas.save();									//画背景图片
		canvas.drawBitmap(back, 0, offset-GameData.baseHeight, null);//绘制游戏背景
		canvas.drawBitmap(back, 0, offset, null);					//绘制游戏背景
		canvas.restore();									//保留画布
		int []tempMap;									//地图数组
		int []tempTree;									//树
		int []tempTank;									//坦克
		int []tempAward;								//奖励数组
		synchronized(gd.lock){							//对数据库进行操作添加锁
			tempMap=gd.mapData;							//地图数组
			tempTree=gd.mapTree;						//树数组
			tempTank=gd.mapTank;						//坦克数组
			tempAward=gd.award;							//奖励数组
		}
		if(tempMap!=null){	    						//如果地图不为空	
			int count=0;								//定义计数器
			while(count<tempMap.length){				//遍历 地图数组
				thing[tempMap[count++]].drawSelf(canvas,
						tempMap[count++],tempMap[count++],0); //调用方法绘制地图
		}}
		if(tempTank!=null){	    						//如果坦克数组不为空
			int count=0;								//定义计数器
			while(count<tempTank.length){				//遍历坦克数组
				thing[tempTank[count++]].drawSelf(canvas,tempTank[count++],
						tempTank[count++],tempTank[count++]); 	//调用方法绘制坦克
		}}
		if(tempAward!=null)
		{
			int count=0;
			while(count<tempAward.length)
			{
				thing[tempAward[count++]].drawSelf(canvas, tempAward[count++], tempAward[count++],0);
			}
		}
		boss.drawSelf(canvas,0,0,0);					//绘制BOSS
		mainBullet.drawSelf(canvas,0,0,0);					//画子弹
		mainTank.drawSelf(canvas,0,0,0);						//画玩家坦克
		

		if(tempTree!=null)							//画树
		{	    		
			int count=0;
			while(count<tempTree.length)
			{
				thing[tempTree[count++]].drawSelf(canvas,tempTree[count++],tempTree[count++],0); 
			}
		}
		explosion.drawSelf(canvas,0,0,0);				//画爆炸
		yaogan.drawSelf(canvas);						//画摇杆
		about.drawSelf(canvas);							//分数、生命、暂停的绘制  /////////////////////////
	}

	private void mainTouch(MotionEvent event){								//游戏中的摇杆触摸监听
		int index=event.getActionIndex();									//获取当前action索引值
		int id=event.getPointerId(index);									//获取当前触摸坐标索引值
		int pCount=event.getPointerCount();									//获取当前触摸数量
		float x=changeTouchX(event.getX(index));							//获取触摸点X坐标
    	float y=changeTouchY(event.getY(index));							//获取触摸点Y坐标
    	switch (event.getAction()&MotionEvent.ACTION_MASK){     			//多点触控      
            case MotionEvent.ACTION_DOWN:									//单点触摸
        	case MotionEvent.ACTION_POINTER_DOWN:							//多点触摸	
            	if(GameData.state==4){										//游戏为暂停状态
            		about.pauseTouchDown(x, y);								//添加暂停的监听
            	}		
            	if(GameData.state<3&&about.isPause(x, y)){					//触摸暂停图片
            		GameData.state=4;										//转到暂停状态
            	}
            	int temp1=yaogan.isYaoGan(x,y);								//是否点击到摇杆			
            	if(temp1!=0){												//如果点击到摇杆
            		pointNumber[temp1-1]=id;								//给摇杆赋索引值
            	}
            break; 
            case MotionEvent.ACTION_MOVE:									//触摸点移动	
            	if(GameData.state==4){										//暂停状态
            		about.pauseTouchDown(x, y);								//暂停触摸监听
            	}
            	for(int i=0;i<pCount;i++){									//遍历触摸点
            		int pid=event.getPointerId(i);							//获取触摸点索引值
            		float xTemp=changeTouchX(event.getX(i));				//获取坐标X值
                	float yTemp=changeTouchY(event.getY(i));				//获取坐标Y值
            		if(pointNumber[0]==pid){								//如果是左面摇杆
                		yaogan.changeLeftYaoGan(xTemp, yTemp);				//改变左面摇杆的位置
                	}else if(pointNumber[1]==pid){							//如果是右面摇杆
                		yaogan.changeRightYaoGan(xTemp, yTemp);				//改变右面摇杆位置
                }}
            break; 
            case MotionEvent.ACTION_UP:										//抬起触摸点
            case MotionEvent.ACTION_POINTER_UP:								//抬起多点触控
            	if(GameData.state==4){										//暂停状态
            		about.pauseTouchUp(x, y);								//暂停状态
            	}
            	if(pointNumber[0]==id){										//如果是左摇杆
            		pointNumber[0]=-1;										//左摇杆索引值置为-1
                	yaogan.leftOffsetX=0;									//左摇杆X位移量置为0
                	yaogan.leftOffsetY=0;									//左摇杆Y位移量置为0
            	}else if(pointNumber[1]==id){								//如果是右摇杆
            		pointNumber[1]=-1;										//右摇杆索引值置为-1
                	yaogan.rightOffsetX=0;									//右摇杆X位移量置为0
                	yaogan.rightOffsetY=0;									//右摇杆Y位移量置为0
            	}
            break;
    }}
	
	public float changeTouchX(float x)		//将屏幕X坐标换算为游戏世界坐标
	{
		return x/ratio-screenLeft;
	}
	
	public float changeTouchY(float y)		//将屏幕Y坐标换算为游戏世界坐标
	{
		return y/ratio-screenTop;
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

	@Override
	public void surfaceCreated(SurfaceHolder holder){
        gameViewDrawThread=new GameViewDrawThread(this);
        gameViewDrawThread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		father.exit();
	}
}
