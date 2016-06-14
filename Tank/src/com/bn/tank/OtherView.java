package com.bn.tank;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Typeface;

import com.bn.data.GameData;
import com.bn.util.BitmapIOUtil;

public class OtherView
{
	MySurfaceView father;
	Bitmap healthBitmap;
	Bitmap scoreBitmap;
	Bitmap loseBitmap;
	Bitmap pauseBackground;
	Bitmap pauseDefault;
	Bitmap pauseSelect;
	Bitmap continueDefault;
	Bitmap continueSelect;
	Bitmap backDefault;
	Bitmap backSelect;
	Bitmap exitDefault;
	Bitmap exitSelect;
	Bitmap soundsetDefault;
	Bitmap soundsetSelect;
	Bitmap effectDefault[]=new Bitmap[2];
	Bitmap effectSelect[]=new Bitmap[2];
	Bitmap musicDefault[]=new Bitmap[2];
	Bitmap musicSelect[]=new Bitmap[2];
	Bitmap nums[]=new Bitmap[10];
	
	float pauseX;
	float pauseY;
	int screenX=(int) (GameData.baseWidth/2);
	int screenY=(int) (GameData.baseHeight/2);
	int pauseHeight;
	int pauseWidth;
	int backgroundHeight;
	int backgroundWidth;
	int loseHeight;
	int loseWidth;
	int paddingX=110;//4按键距中心的距离
	int paddingY=55;
	int buttonHeight=50;//按钮标准高度宽度
	int buttonWidth=180;
	public int buttonFlag=0;// 第一位  返回游戏 -- 第二位 设置音乐 -- 第三位 返回菜单 -- 第四位  退出游戏  
	public boolean soundFlag=false; //false--暂停界面  true--音乐设置界面
	public int soundsetFlag=0;// 第一位  
	Paint paint=new Paint();
	
	public OtherView(MySurfaceView father)
	{
		this.father=father;
		Bitmap numsTemp=BitmapIOUtil.getBitmap("nums.png",father.father.getResources());
		int numsHeight=numsTemp.getHeight();
		int numsWidth=numsTemp.getWidth()/10;
		for(int i=0;i<10;i++)
		{
			nums[i]=Bitmap.createBitmap(numsTemp, i*numsWidth, 0, numsWidth, numsHeight);
		}
		healthBitmap=BitmapIOUtil.getBitmap("redbox.png", father.father.getResources());
		scoreBitmap=BitmapIOUtil.getBitmap("score_icon.png", father.father.getResources());
		loseBitmap=BitmapIOUtil.getBitmap("lose.png", father.father.getResources());
		pauseBackground=BitmapIOUtil.getBitmap("pausebackground.png", father.father.getResources());
		pauseDefault=BitmapIOUtil.getBitmap("pause.png", father.father.getResources());
		pauseSelect=BitmapIOUtil.getBitmap("pause_select.png", father.father.getResources());
		continueDefault=BitmapIOUtil.getBitmap("continue_game.png", father.father.getResources());
		continueSelect=BitmapIOUtil.getBitmap("continue_game_select.png", father.father.getResources());
		backDefault=BitmapIOUtil.getBitmap("back_menu.png", father.father.getResources());
		backSelect=BitmapIOUtil.getBitmap("back_menu_select.png", father.father.getResources());
		exitDefault=BitmapIOUtil.getBitmap("exit_game.png", father.father.getResources());
		exitSelect=BitmapIOUtil.getBitmap("exit_game_select.png", father.father.getResources());
		soundsetDefault=BitmapIOUtil.getBitmap("soundset.png", father.father.getResources());
		soundsetSelect=BitmapIOUtil.getBitmap("soundset_select.png", father.father.getResources());
		effectDefault[0]=BitmapIOUtil.getBitmap("close_effect.png", father.father.getResources());
		effectSelect[0]=BitmapIOUtil.getBitmap("close_effect_select.png", father.father.getResources());
		musicDefault[0]=BitmapIOUtil.getBitmap("close_music.png", father.father.getResources());
		musicSelect[0]=BitmapIOUtil.getBitmap("close_music_select.png", father.father.getResources());
		effectDefault[1]=BitmapIOUtil.getBitmap("open_effect.png", father.father.getResources());
		effectSelect[1]=BitmapIOUtil.getBitmap("open_effect_select.png", father.father.getResources());
		musicDefault[1]=BitmapIOUtil.getBitmap("open_music.png", father.father.getResources());
		musicSelect[1]=BitmapIOUtil.getBitmap("open_music_select.png", father.father.getResources());
		
		backgroundHeight=pauseBackground.getHeight();
		backgroundWidth=pauseBackground.getWidth();
		pauseHeight=pauseDefault.getHeight();
		pauseWidth=pauseDefault.getWidth();
		loseHeight=loseBitmap.getHeight();
		loseWidth=loseBitmap.getWidth();
		pauseX=father.screenWidth-30-pauseWidth/2;
		pauseY=20+pauseHeight/2;
		
		Typeface face=Typeface.createFromAsset(father.father.getAssets(), "Font/FZKATJW.ttf");
		paint.setAntiAlias(true);
		paint.setStrokeCap(Cap.ROUND);  
		paint.setTypeface(face);
		paint.setStrokeJoin(Join.ROUND);
		paint.setFakeBoldText(true);
		paint.setStrokeWidth(3);
		paint.setTextSize(80);
		paint.setTextAlign(Align.CENTER);
	}
	 	

	public void drawSelf(Canvas canvas){			//绘制方法
		if(GameData.state==1){						//如果准备游戏状态
			canvas.save();							//保存画布
			canvas.translate(screenX, screenY);		//移动画布
			paint.setStyle(Style.FILL);				//设置画笔样式
			paint.setColor(Color.WHITE);			//设置画笔颜色
			canvas.drawText("等待其他用户进入", 0, 0, paint);//绘制等待信息
			paint.setStyle(Style.STROKE);			//设置画笔样式
			paint.setColor(Color.BLACK);			//设置画笔颜色
			canvas.drawText("等待其他用户进入", 0, 0, paint);	//绘制等待信息
			canvas.restore();						//还原画布
		}else if(GameData.state>=2){				//游戏进行中，画生命和得分
			drawHealth(canvas);						//绘制玩家生命值方法
			drawScore(canvas);						//绘制玩家分数方法
		}else if(GameData.state==4){				//暂停状态
			if(!soundFlag){							//判断是那个界面
				pauseMenu1(canvas);					//暂停界面
			}else{
				pauseMenu2(canvas);					//设置音乐界面
			}
		}else if(GameData.state==3){				//游戏失败界面
			canvas.save();							//保存画布
			canvas.translate(screenX-loseWidth/2,screenY-loseHeight/2);	//移动画布
			canvas.drawBitmap(loseBitmap, 0, 0, null);	//绘制失败图片
			canvas.restore();						//还原画布
		}
		canvas.save();								//保存画布
		canvas.translate(pauseX-pauseWidth/2,pauseY-pauseHeight/2);//移动画布
		canvas.drawBitmap(pauseSelect, 0, 0, null);	//绘制暂停按钮
		canvas.restore();							//还原画布
	}

	
	private void pauseMenu2(Canvas canvas) 
	{
		canvas.save();
		canvas.translate(screenX, screenY);
		canvas.drawBitmap(pauseBackground, -backgroundWidth/2, -backgroundHeight/2, null);
		
		canvas.save();
		canvas.translate(-paddingX, -paddingY);
		int effect=GameData.GAME_EFFECT?0:1;
		canvas.drawBitmap(((soundsetFlag&4)!=4)?effectDefault[effect]:effectSelect[effect], 
				-buttonWidth/2, -buttonHeight/2, null);
		canvas.restore();
		
		canvas.save();
		canvas.translate(paddingX, -paddingY);
		int sound=GameData.GAME_SOUND?0:1;
		canvas.drawBitmap(((soundsetFlag&2)!=2)?musicDefault[sound]:musicSelect[sound], 
				-buttonWidth/2, -buttonHeight/2, null);
		canvas.restore();
		
		canvas.save();
		canvas.translate(paddingX, paddingY);
		canvas.drawBitmap(((soundsetFlag&1)!=1)?backDefault:backSelect, 
				-buttonWidth/2, -buttonHeight/2, null);
		canvas.restore();
		canvas.restore();
	}

	private void pauseMenu1(Canvas canvas){							//绘制暂停界面
		canvas.save();												//保存画布
		canvas.translate(screenX, screenY);							//移动画布
		canvas.drawBitmap(pauseBackground, -backgroundWidth/2,	
				-backgroundHeight/2, null);							//绘制暂停背景
		canvas.save();												//保存画布
		canvas.translate(-paddingX, -paddingY);						//移动画布
		canvas.drawBitmap(((buttonFlag&8)!=8)?continueDefault:continueSelect, 
				-buttonWidth/2, -buttonHeight/2, null);				//绘制
		canvas.restore();											//还原画布
		canvas.save();												//保存画布
		canvas.translate(paddingX, -paddingY);						//移动画布
		canvas.drawBitmap(((buttonFlag&4)!=4)?soundsetDefault:soundsetSelect, 
				-buttonWidth/2, -buttonHeight/2, null);				//绘制音声选型按钮
		canvas.restore();											//还原画布
		canvas.save();												//保存画布
		canvas.translate(-paddingX, paddingY);						//移动画布
		canvas.drawBitmap(((buttonFlag&2)!=2)?backDefault:backSelect,
				-buttonWidth/2, -buttonHeight/2, null);				//绘制返回按钮
		canvas.restore();											//还原画布
		canvas.save();												//保存画布
		canvas.translate(paddingX, paddingY);						//移动画布
		canvas.drawBitmap(((buttonFlag&1)!=1)?exitDefault:exitSelect, 
				-buttonWidth/2, -buttonHeight/2, null);				//绘制退出按钮
		canvas.restore();											//还原画布
	}

	private void drawScore(Canvas canvas){					//绘制玩家分数
		int score;											//定义一个分数
		synchronized(father.gd.lock){						//同步方法
			score=father.gd.score;							//获取玩家分数
		}
		if(score<0){return;}								//如果分数为0，返回
		canvas.save();										//保存画布
		canvas.translate(400, 30);							//移动画布
		canvas.drawBitmap(scoreBitmap, 0, 0, null);			//绘制分数图片
		canvas.translate(30, 0);							//移动画布
		String scoreStr=String.valueOf(score);				//分数转换成字符串
		for(int i=0;i<scoreStr.length();i++){				//遍历分数字符串
			int temp=Integer.parseInt(scoreStr.charAt(i)+"");//取出一位字符
			canvas.translate(30, 0);						//移动画布
			canvas.drawBitmap(nums[temp], 0, 0, null);		//绘制分数
		}
		canvas.restore();									//还原画布
	}
	private void drawHealth(Canvas canvas){					//绘制生命值
		int health;
		synchronized(father.gd.lock){						//同步方法
			health=(GameData.redOrGreen==0)?father.gd.redHealth:father.gd.greenHealth;
		}
		if(health<0){	return;}							//如果生命值为0
		canvas.save();										//保存画布
		canvas.translate(40, 30);							//移动位置
		canvas.drawBitmap(healthBitmap, 0, 0, null);		//绘制帮助图片
		canvas.translate(30, 0);							//移动位置
		String healthStr=String.valueOf(health);			//生命值类型转换为字符串
		for(int i=0;i<healthStr.length();i++){				//遍历生命值字符串
			int temp=Integer.parseInt(healthStr.charAt(i)+"");	//取出一位数字
			canvas.translate(30, 0);							//移动位置
			canvas.drawBitmap(nums[temp], 0, 0, null);			//绘制生命值
		}
		canvas.restore();										//还原画布
	}
	
	public boolean isPause(float tx, float ty)
	{
		float d1=(tx-pauseX)*(tx-pauseX)+(ty-pauseY)*(ty-pauseY);
		float d2=pauseWidth*pauseWidth/4;
		if(d1<d2)
		{
			return true;
		}
		return false;
	}
	
	public void pauseTouchUp(float tx, float ty){
		if(!soundFlag){
			if(Math.abs(tx-(screenX-paddingX))<buttonWidth/2
				&&Math.abs(ty-(screenY-paddingY))<buttonHeight/2){
				father.father.playSound(GameData.SELECT,0);
				GameData.state=2;
			}else if(Math.abs(tx-(screenX+paddingX))<buttonWidth/2
					&&Math.abs(ty-(screenY-paddingY))<buttonHeight/2){
				father.father.playSound(GameData.SELECT,0);
				soundFlag=true;
			}else if(Math.abs(tx-(screenX-paddingX))<buttonWidth/2
					&&Math.abs(ty-(screenY+paddingY))<buttonHeight/2){
				father.father.playSound(GameData.SELECT,0);
				father.father.kt.broadcastExit();
				father.nt.flag=false;
			}else if(Math.abs(tx-(screenX+paddingX))<buttonWidth/2
					&&Math.abs(ty-(screenY+paddingY))<buttonHeight/2){
				father.father.playSound(GameData.SELECT,0);
				father.father.exit();
			}
			buttonFlag=0;
		}else
		{
			if(Math.abs(tx-(screenX-paddingX))<buttonWidth/2&&Math.abs(ty-(screenY-paddingY))<buttonHeight/2)
			{
				GameData.GAME_EFFECT=!GameData.GAME_EFFECT;
				father.father.playSound(GameData.SELECT,0);
			}else if(Math.abs(tx-(screenX+paddingX))<buttonWidth/2&&Math.abs(ty-(screenY-paddingY))<buttonHeight/2)
			{
				father.father.playSound(GameData.SELECT,0);
				GameData.GAME_SOUND=!GameData.GAME_SOUND;
				father.father.playBackground();
			}else if(Math.abs(tx-(screenX+paddingX))<buttonWidth/2&&Math.abs(ty-(screenY+paddingY))<buttonHeight/2)
			{
				father.father.playSound(GameData.SELECT,0);
				soundFlag=!soundFlag;
			}
			soundsetFlag=0;
		}
	}
	
	public void pauseTouchDown(float tx, float ty){
		if(!soundFlag){
			if(Math.abs(tx-(screenX-paddingX))<buttonWidth/2
					&&Math.abs(ty-(screenY-paddingY))<buttonHeight/2){
				buttonFlag=buttonFlag|8;
			}else if(Math.abs(tx-(screenX+paddingX))<buttonWidth/2
					&&Math.abs(ty-(screenY-paddingY))<buttonHeight/2){
				buttonFlag=buttonFlag|4;
			}else if(Math.abs(tx-(screenX-paddingX))<buttonWidth/2
					&&Math.abs(ty-(screenY+paddingY))<buttonHeight/2){
				buttonFlag=buttonFlag|2;
			}else if(Math.abs(tx-(screenX+paddingX))<buttonWidth/2&&
					Math.abs(ty-(screenY+paddingY))<buttonHeight/2){
				buttonFlag=buttonFlag|1;	
			}else{
				buttonFlag=0;
			}
		}else
		{
			if(Math.abs(tx-(screenX-paddingX))<buttonWidth/2&&Math.abs(ty-(screenY-paddingY))<buttonHeight/2)
			{
				soundsetFlag=soundsetFlag|4;
			}else if(Math.abs(tx-(screenX+paddingX))<buttonWidth/2&&Math.abs(ty-(screenY-paddingY))<buttonHeight/2)
			{
				soundsetFlag=soundsetFlag|2;
			}else if(Math.abs(tx-(screenX+paddingX))<buttonWidth/2&&Math.abs(ty-(screenY+paddingY))<buttonHeight/2)
			{
				soundsetFlag=soundsetFlag|1;
			}else
			{
				soundsetFlag=0;
			}
		}
	}
}