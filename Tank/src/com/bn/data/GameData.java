package com.bn.data;

import java.util.ArrayList;

public class GameData
{
	public static int state=0;//0--δ����  1---�ɹ�����  2--��Ϸ��ʼ  3--��Ϸʧ��   4--��ͣ״̬
	
	public Object lock=new Object();
	
	public int redX=-50;							//���̹�˵�����
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
	
	public float redTankAngle=0;					//���̹�˵���Ͳ�Ƕ�
	public float greenTankAngle=0;
	public float redGunAngle=0;
	public float greenGunAngle=0;
	
	public int State=0;								//�з�̹��״̬
	
	public ArrayList<Float> mainBullet;
	public ArrayList<Float> otherBullet;
	public ArrayList<Float> mainMissile;
	public ArrayList<Float> bossbullet;				//boss�ӵ�
	public int[] mapData;
	public int[] mapTree;
	public int[] mapTank;
	public int[] explosion;							//��ըλ������
	public int[] award;								//��ը�Ľ���
	public int offset=0;							//��ͼƫ����

	public boolean bossFlag=false;
	public int bossX=480;
	public int bossY=270;
	public int bossNum=1;

	public float ratio=1;							//��Ļ����

	public static int redOrGreen;
	
	public final static float baseWidth=960;		//�����ֱ��ʿ��
	public final static float baseHeight=540;		//�����ֱ��ʸ߶�
	
	public final static int STATE_UNCONNECTED=0;
	public final static int STATE_WAITING=1;
	public final static int STATE_CONNECTED=2;
	
	public final static int EAT=1;
	public final static int EXPLOSION=2;
	public final static int LOSE=3;
	public final static int SHOOT=4;
	public final static int SELECT=5;
	public final static int BACKGROUND=6;
	
	
	public final static int Game_load=1;	//����
	public final static int Game_menu=2;	//��Ϸ�˵�
	public final static int Game_palying=3;	//��Ϸ��ʼ
	public final static int Game_pause=4;	//��Ϸ��ͣ
	public final static int Game_select=5;	//ѡ��ؿ�
	
	public static int viewState=Game_load;	//��Ϸ״̬
	
	public static boolean GAME_SOUND=true;	//��Ϸ����
	public static boolean GAME_EFFECT=true;	//��Ϸ��Ч
	
	public static int start;
	public static int end;
	public static int offsetX;
	public static int position=0;

	public static int helpNum=5;
}