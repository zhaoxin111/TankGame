package com.bn.data;

import java.util.ArrayList;

public class GameData
{
	public static int state=0;//0--未连接  1---成功连接  2--游戏开始  3--游戏失败   4--暂停状态
	
	public Object lock=new Object();
	
	public int redX=-50;							//玩家坦克的坐标
	public int redY=-50;
	public int greenX=-50;
	public int greenY=-50;
	public int redGunState=0;
	public int greenGunState=0;
	public int redHealth=-1;
	public int greenHealth=-1;
	
	public int score=-1;

	public int levelNumber=0;
	public int loadTime=0;
	
	public float redTankAngle=0;					//玩家坦克的炮筒角度
	public float greenTankAngle=0;
	public float redGunAngle=0;
	public float greenGunAngle=0;
	
	public int State=0;								//敌方坦克状态
	
	public ArrayList<Float> mainBullet;
	public ArrayList<Float> otherBullet;
	public ArrayList<Float> mainMissile;
	public ArrayList<Float> bossbullet;				//boss子弹
	public int[] mapData;
	public int[] mapTree;
	public int[] mapTank;
	public int[] explosion;							//爆炸位置数组
	public int[] award;								//爆炸的奖励
	public int offset=0;							//地图偏移量

	public boolean bossFlag=false;
	public int bossX=480;
	public int bossY=270;
	public int bossNum=1;

	public float ratio=1;							//屏幕比例

	public static int redOrGreen;
	
	public final static float baseWidth=960;		//基础分辨率宽度
	public final static float baseHeight=540;		//基础分辨率高度
	
	public final static int STATE_UNCONNECTED=0;
	public final static int STATE_WAITING=1;
	public final static int STATE_CONNECTED=2;
	
	public final static int EAT=1;
	public final static int EXPLOSION=2;
	public final static int LOSE=3;
	public final static int SHOOT=4;
	public final static int SELECT=5;
	public final static int BACKGROUND=6;
	
	
	public final static int Game_load=1;	//加载
	public final static int Game_menu=2;	//游戏菜单
	public final static int Game_palying=3;	//游戏开始
	public final static int Game_pause=4;	//游戏暂停
	public final static int Game_select=5;	//选择关卡
	
	public static int viewState=Game_load;	//游戏状态
	
	public static boolean GAME_SOUND=true;	//游戏音乐
	public static boolean GAME_EFFECT=true;	//游戏音效
	
	public static int start;
	public static int end;
	public static int offsetX;
	public static int position=0;

	public static int helpNum=5;
}