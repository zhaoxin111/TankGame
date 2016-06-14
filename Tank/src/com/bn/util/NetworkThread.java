package com.bn.util;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import com.bn.data.GameData;
import com.bn.tank.MySurfaceView;
import com.bn.tank.TankActivity;

public class NetworkThread extends Thread
{
	MySurfaceView father;
	Socket sc;
	DataInputStream din;
	public DataOutputStream dout;
	public boolean flag=true;
	
	public NetworkThread(MySurfaceView father)
	{
		this.father=father;		
	}

	public void run()
	{
		
		try
		{
	//		sc=new Socket("172.27.35.1",9999);
	//		sc=new Socket("192.168.1.106",9999);
	//		sc=new Socket("10.16.189.30",9999);
	//		sc=new Socket("192.168.1.103",9999);
	//		sc=new Socket("192.168.155.1",9999);
			sc=new Socket();
			sc.connect(new InetSocketAddress("192.168.1.120",9999), 10000);
			din=new DataInputStream(sc.getInputStream());
			dout=new DataOutputStream(sc.getOutputStream());
			dout.writeUTF("<#CONNECT#>");
		}
		catch(Exception e)
		{
			TankActivity.handler.sendEmptyMessage(1);
			GameData.viewState=GameData.Game_menu;
			return;
		}
		
		while(flag)
		{
			try
			{
				String msg=din.readUTF();
				if(msg.startsWith("<#OK#>"))
				{					
					System.out.println("Connect ok...");
					int redOrGreen=din.readInt();
					int level=din.readInt();
					System.out.println(level);
					synchronized(father.gd.lock)
					{
						father.gd.levelNumber=level;
						GameData.redOrGreen=redOrGreen;
						GameData.state=GameData.STATE_WAITING;	
					}					
				}
				else if(msg.startsWith("<#FULL#>"))
				{
					System.out.println("Full...");
					break;
				}
				else if(msg.startsWith("<#SELECT2#>"))
				{
					int level=din.readInt();
					father.gd.levelNumber=level;
				}
				else if(msg.startsWith("<#GAME_BULLET#>"))		//XXX 玩家子弹
				{
					int redGunState=(din.readInt()==0)?0:1;
					int greenGunState=(din.readInt()==0)?0:1;
					
					int length=din.readInt();
					float[] temp=new float[length];
					
					for(int i=0;i<length;i++)
					{
						temp[i]=din.readFloat();
					}
					
					synchronized(father.gd.lock)
					{
						father.gd.redGunState=redGunState;
						father.gd.greenGunState=greenGunState;
						father.gd.mainBullet=new ArrayList<Float>();
						for(int i=0;i<length;i++)
						{
							father.gd.mainBullet.add(temp[i]);
						}
					}
				}
				else if(msg.startsWith("<#GAME_MISSILE#>"))		//XXX 玩家火箭
				{
					boolean missileFlag=din.readBoolean();
					int length=din.readInt();
					float[] temp=new float[length];
					for(int i=0;i<length;i++)
					{
						temp[i]=din.readFloat();
					}
					if(missileFlag)
					{
						father.father.playSound(GameData.SHOOT, 0);
					}
					synchronized(father.gd.lock)
					{
						father.gd.mainMissile=new ArrayList<Float>();
						for(int i=0;i<length;i++)
						{
							father.gd.mainMissile.add(temp[i]);
						}
					}
				}
				else if(msg.startsWith("<#GAME_OTHERBULLET#>"))	//XXX 敌方子弹
				{
					int OtherGunState=(din.readInt()==0)?0:1;
					
					int length=din.readInt();
					float[] temp=new float[length];
					
					for(int i=0;i<length;i++)
					{
						temp[i]=din.readFloat();
					}
					
					synchronized(father.gd.lock)
					{
						father.gd.State=OtherGunState;
						father.gd.otherBullet=new ArrayList<Float>();
						for(int i=0;i<length;i++)
						{
							father.gd.otherBullet.add(temp[i]);
						}
	//					father.gd.otank=true;
					}
				}
				else if(msg.startsWith("<#GAME_MAP#>"))			//XXX 地图
				{
					int length=din.readInt();
					int[] temp=new int[length];
					
					for(int i=0;i<length;i++)
					{
						temp[i]=din.readInt();
					}
					
					synchronized(father.gd.lock)
					{
						father.gd.mapData=new int[length-1];
						father.gd.offset=temp[0];
						for(int i=1;i<length;i++)
						{
							father.gd.mapData[i-1]=temp[i];
						}
					}
				}
				else if(msg.startsWith("<#GAME_DATA#>"))			//XXX玩家生命值
				{
					int redHealth=din.readInt();
					int greenHealth=din.readInt();
					int score=din.readInt();
					synchronized(father.gd.lock)
					{
						father.gd.redHealth=redHealth;
						father.gd.greenHealth=greenHealth;
						father.gd.score=score;
					}
				}
				else if(msg.startsWith("<#GAME_BOSS#>"))			//XXX boss
				{
					int bossNum=din.readInt();
					int bossX=din.readInt();
					int bossY=din.readInt();
					boolean bossFlag=din.readBoolean();
					boolean bulletFlag=din.readBoolean();
					if(bulletFlag)
					{
						int length=din.readInt();
						float[] bossbullet=new float[length];
						for(int i=0;i<length;i++)
						{
							bossbullet[i]=din.readFloat();
						}
						
						
						synchronized(father.gd.lock)
						{
							father.gd.bossbullet=new ArrayList<Float>();
							for(int i=0;i<length;i++)
							{
								father.gd.bossbullet.add(bossbullet[i]);
							}
						}
						System.out.println("boss length "+length);
					}
					synchronized(father.gd.lock)
					{
						father.gd.bossX=bossX;
						father.gd.bossY=bossY;
						father.gd.bossFlag=bossFlag;
						father.gd.bossNum=bossNum;
					}
				}
				else if(msg.startsWith("<#GAME_GAMESTATE#>"))			//XXX 游戏状态
				{
					int gameState=din.readInt();
					if(gameState==2)
					{
						GameData.viewState=GameData.Game_palying;
					}
					if(gameState==3)
					{
						father.father.playSound(GameData.LOSE, 0);
					}
					synchronized(father.gd.lock)
					{
						GameData.state=gameState;
					}
				}
				else if(msg.startsWith("<GAME_TREE>"))			//XXX 树
				{
					int length=din.readInt();
					int[] temp=new int[length];
					
					for(int i=0;i<length;i++)
					{
						temp[i]=din.readInt();
					}
					
					synchronized(father.gd.lock)
					{
						father.gd.mapTree=new int[length];
						for(int i=0;i<length;i++)
						{
							father.gd.mapTree[i]=temp[i];
						}
					}
				}
				else if(msg.startsWith("<#GAME_EXPLOSION#>"))	//XXX 爆炸
				{
					int length=din.readInt();
					int[] temp=new int[length];
					for(int i=0;i<length;i++)
					{
						temp[i]=din.readInt();
						if(i%3==0&&(temp[i]==1||temp[i]==26))
						{
							System.out.println("ddd");
							father.father.playSound(GameData.EXPLOSION, 0);
						}
					}
					
					synchronized(father.gd.lock)
					{
						father.gd.explosion=new int[length];
						for(int i=0;i<length;i++)
						{
							father.gd.explosion[i]=temp[i];
						}
					}
				}
				else if(msg.startsWith("<GAME_OTANK>"))			//XXX 敌方坦卡
				{
					int length=din.readInt();
					int[] temp=new int[length];
					
					for(int i=0;i<length;i++)
					{
						temp[i]=din.readInt();
					}
					
					synchronized(father.gd.lock)
					{
						father.gd.mapTank=new int[length];
						for(int i=0;i<length;i++)
						{
							father.gd.mapTank[i]=temp[i];
						}
					}
				}
				else if(msg.equals("<#GAME_TANK#>"))			//XXX 玩家坦克
				{
					int redX=din.readInt();
					int redY=din.readInt();
					int greenX=din.readInt();
					int greenY=din.readInt();
					float redTankAngle=din.readFloat();
					float greenTankAngle=din.readFloat();
					float redGunAngle=din.readFloat();
					float greenGunAngle=din.readFloat();
					
					synchronized(father.gd.lock)
					{
						father.gd.redX=redX;
						father.gd.redY=redY;
						father.gd.greenX=greenX;
						father.gd.greenY=greenY;
						father.gd.redTankAngle=redTankAngle;
						father.gd.greenTankAngle=greenTankAngle;
						father.gd.redGunAngle=redGunAngle;
						father.gd.greenGunAngle=greenGunAngle;
					}
				}
				else if(msg.equals("<GAME_AWARD>"))					//XXX 收集爆炸后的奖励
				{
					int length=din.readInt();
					int temp[]=new int[length];
					for(int i=0;i<length;i++)
					{
						temp[i]=din.readInt();
					}
					
					synchronized(father.gd.lock)
					{
						father.gd.award=temp;
					}
				}
				else if(msg.equals("<#EXIT#>"))						//XXX 发送推出信息
				{
					GameData.state=GameData.STATE_UNCONNECTED;
					GameData.viewState=GameData.Game_menu;
					father.nt.flag=false;
				}
				else if(msg.equals("<#VICTORY#>"))					//XXX 胜利
				{
					synchronized(father.gd.lock)
					{
						GameData.state=GameData.STATE_UNCONNECTED;
						GameData.viewState=GameData.Game_menu;
						father.nt.flag=false;
					}
				}else if(msg.equals("<#CLEAR#>"))					//XXX 清除boss子弹
				{
					synchronized(father.gd.lock)
					{
						father.gd.bossbullet=null;
					}
				}
			}
			catch(Exception e)
			{
				GameData.state=0;
				GameData.viewState=GameData.Game_menu;
				father.gd=new GameData();
				TankActivity.handler.sendEmptyMessage(2);
				return;
			}
			
		}
		try
		{			
			din.close();
			dout.close();
			sc.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}