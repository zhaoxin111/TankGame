package com.bn.gp.server;

import java.io.*;
import java.net.*;
import java.util.*;

import com.bn.gp.action.Action;
import com.bn.gp.data.GameData;

public class ServerAgent extends Thread
{
	//客户端计数器
	static int count=0;
	//客户端列表
	static List<ServerAgent> ulist=new ArrayList<ServerAgent>();
	//动作队列
	public static Queue<Action> aq=new LinkedList<Action>();
	
	Socket sc;
	static ServerThread st;
	DataInputStream din;
	DataOutputStream dout;
	int redOrGreen;
	static boolean flag=true;
	int i=0;
	
	public static void broadcastMap(ArrayList<Integer> tempMap)
	{
		for(ServerAgent sa:ulist)
		{
			try
			{
				synchronized(GameData.lock)
				{
					sa.dout.writeUTF("<#GAME_MAP#>");
					sa.dout.writeInt(tempMap.size());
					for(int j=0;j<tempMap.size();j++)
					{
						sa.dout.writeInt(tempMap.get(j));
					}
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public static void broadcastAward()							//发送爆炸后的奖励坐标
	{
		for(ServerAgent sa:ulist)
		{
			try
			{
				synchronized(GameData.lock)
				{
					sa.dout.writeUTF("<GAME_AWARD>");
					sa.dout.writeInt(GameData.award.size());
					for(int i=0;i<GameData.award.size();i++)
					{
						sa.dout.writeInt(GameData.award.get(i));
					}
				}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public static void broadcastExplosion()						//发送爆炸坐标
	{
		for(ServerAgent sa:ulist)
		{
			try
			{
				synchronized(GameData.lock)
				{
					sa.dout.writeUTF("<#GAME_EXPLOSION#>");
					sa.dout.writeInt(GameData.explosion.size());
					for(int i=0;i<GameData.explosion.size();i++)
					{
						sa.dout.writeInt(GameData.explosion.get(i));
					}
				}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public static void broadcastData() 
	{
		for(ServerAgent sa:ulist)
		{
			try
			{
				synchronized(GameData.lock)
				{
					sa.dout.writeUTF("<#GAME_DATA#>");
					sa.dout.writeInt(GameData.redHealth);
					sa.dout.writeInt(GameData.greenHealth);
					sa.dout.writeInt(GameData.score);
				}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	public static void broadcastGameState() 
	{
		for(ServerAgent sa:ulist)
		{
			try
			{
				synchronized(GameData.lock)
				{
					sa.dout.writeUTF("<#GAME_GAMESTATE#>");
					sa.dout.writeInt(GameData.gameState);
				}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}	
	
	public static void broadcastTree(ArrayList<Integer> tempTree)	//发送树的地图
	{
		for(ServerAgent sa:ulist)
		{
			try
			{
				synchronized(GameData.lock)
				{
					sa.dout.writeUTF("<GAME_TREE>");
					sa.dout.writeInt(tempTree.size());
					for(int k=0;k<tempTree.size();k++)
					{
						sa.dout.writeInt(tempTree.get(k));
					}
				}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	public static void broadcastTank(ArrayList<Integer> tempTank)	//发送敌军坦克的地图
	{
		for(ServerAgent sa:ulist)
		{
			try
			{
				synchronized(GameData.lock)
				{
					sa.dout.writeUTF("<GAME_OTANK>");
					sa.dout.writeInt(tempTank.size());
					for(int k=0;k<tempTank.size();k++)
					{
						sa.dout.writeInt(tempTank.get(k));
					}
				}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	public static void broadcastMissile() 
	{
		for(ServerAgent sa:ulist)
		{
			try
			{
				boolean flag=false;
				if(GameData.redMissileCount==1||GameData.greenMissileCount==1)
				{
					flag=true;
				}
				synchronized(GameData.lock)
				{
					sa.dout.writeUTF("<#GAME_MISSILE#>");
					sa.dout.writeBoolean(flag);
					sa.dout.writeInt(GameData.mainMissile.size());
					for(int i=0;i<GameData.mainMissile.size();i++)
					{
						sa.dout.writeFloat(GameData.mainMissile.get(i));
					}
					
				}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	public static void broadcastBullet()								//子弹
	{
		for(ServerAgent sa:ulist)
		{
			try
			{
				synchronized(GameData.lock)
				{
					sa.dout.writeUTF("<#GAME_BULLET#>");
					sa.dout.writeInt(GameData.redState);
					sa.dout.writeInt(GameData.greenState);
					sa.dout.writeInt(GameData.mainBullet.size());
					for(int i=0;i<GameData.mainBullet.size();i++)
					{
						sa.dout.writeFloat(GameData.mainBullet.get(i));
					}
					
				}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	public static void broadcastOtherBullet()
	{
		for(ServerAgent sa:ulist)
		{
			try
			{
				synchronized(GameData.lock)
				{
					sa.dout.writeUTF("<#GAME_OTHERBULLET#>");
					sa.dout.writeInt(GameData.otherState);
					sa.dout.writeInt(GameData.otherBullet.size());
					for(int i=0;i<GameData.otherBullet.size();i++)
					{
						sa.dout.writeFloat(GameData.otherBullet.get(i));
					}
					
				}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
	}
	
	public static void broadcastTank()
	{
		for(ServerAgent sa:ulist)
		{
			try
			{
				synchronized(GameData.lock)
				{
					sa.dout.writeUTF("<#GAME_TANK#>");
					sa.dout.writeInt(GameData.redX);
					sa.dout.writeInt(GameData.redY);
					sa.dout.writeInt(GameData.greenX);
					sa.dout.writeInt(GameData.greenY);
					sa.dout.writeFloat(GameData.redTankAngle);
					sa.dout.writeFloat(GameData.greenTankAngle);
					sa.dout.writeFloat(GameData.redGunAngle);
					sa.dout.writeFloat(GameData.greenGunAngle);
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public static void broadcastBoss()
	{
		for(ServerAgent sa:ulist)
		{
			try
			{
				synchronized(GameData.lock)
				{
					boolean b=(GameData.bossBullet!=null);
					sa.dout.writeUTF("<#GAME_BOSS#>");
					sa.dout.writeInt(GameData.bossNum);
					sa.dout.writeInt(GameData.bossX);
					sa.dout.writeInt(GameData.bossY);
					sa.dout.writeBoolean(GameData.bossFlag);
					sa.dout.writeBoolean(b);
					if(b)
					{
						int length=GameData.bossBullet.size();
						sa.dout.writeInt(length);
						for(int i=0;i<length;i++)
						{
							sa.dout.writeFloat(GameData.bossBullet.get(i));
						}
					}
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public static void broadcastClear() 
	{
		for(ServerAgent sa:ulist)
		{
			try
			{
				synchronized(GameData.lock)
				{
					sa.dout.writeUTF("<#CLEAR#>");
				}
			}
			catch(Exception e)
			{
				//e.printStackTrace();
			}
		}
	}
	public ServerAgent(Socket sc)
	{
		this.sc=sc;
		try
		{
			din=new DataInputStream(sc.getInputStream());
			dout=new DataOutputStream(sc.getOutputStream());
		}
		catch(Exception e)
		{
			//e.printStackTrace();
		}
	}
	
	public ServerAgent(ServerThread st,Socket sc)
	{
		this.sc=sc;
		ServerAgent.st=st;
		try
		{
			din=new DataInputStream(sc.getInputStream());
			dout=new DataOutputStream(sc.getOutputStream());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void run()
	{
		while(flag)
		{
			try
			{
				String msg=din.readUTF();
				if(msg.startsWith("<#CONNECT#>"))
				{
//					count++;
					if(count==0)
					{
						dout.writeUTF("<#OK#>");
						redOrGreen=0;
						dout.writeInt(redOrGreen);
						dout.writeInt(GameData.level);
						ulist.add(this);
						count++;
						System.out.println("red connect...");
					}
					else if(count==1)
					{
						dout.writeUTF("<#OK#>");
						redOrGreen=1;
						dout.writeInt(redOrGreen);
						dout.writeInt(GameData.level);
						ulist.add(this);
						count++;
						System.out.println("green connect...");
					}
					else
					{
						dout.writeUTF("<#FULL#>");
						break;
					}
				}
				else if(msg.startsWith("<#SELECT#>"))
				{
					int level=din.readInt();
					System.out.println("level"+level);
					try
					{
						for(ServerAgent sa:ulist)
						{
							synchronized(GameData.lock)
							{
								sa.dout.writeUTF("<#SELECT2#>");
								sa.dout.writeInt(level);
							}
							System.out.println("level----------"+level);
						}
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
				else if(msg.startsWith("<#LEVEL#>"))
				{
					int level=din.readInt();
					System.out.println("start");	
					synchronized(GameData.lock)
					{
						GameData.level=level;
					}
					System.out.println("broadcast data  pre");
					broadcastData();
					System.out.println("broadcast data  next");
					initStart();
				}
				else if(msg.startsWith("<#KEY#>"))
				{
					float leftOffsetX,leftOffsetY,rightOffsetX,rightOffsetY;
					leftOffsetX=din.readFloat();
					leftOffsetY=din.readFloat();
					rightOffsetX=din.readFloat();
					rightOffsetY=din.readFloat();
					MainTank m=new MainTank(redOrGreen,leftOffsetX,leftOffsetY,rightOffsetX,rightOffsetY);
					synchronized(GameData.lock)
					{
						aq.offer(m);
					}
				}
				else if(msg.startsWith("<#STATE#>"))
				{
					int gameState=din.readInt();
					synchronized(GameData.lock)
					{
						GameData.gameState=gameState;
					}
				}else if(msg.startsWith("<#EXIT#>"))
				{
					closeGame();
				}
			}
			catch(Exception e)
			{
				closeGame();
				break;
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
	
	public static void closeGame() 
	{
		LevelChange.resetLevel();
		flag=false;
		st.flag=false;
		ulist.clear();
		try 
		{
			st.ss.close();
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		new ServerThread().start();
	}

	private void initStart() 
	{
		System.out.println("initstart....");
		GameData.threadFlag=true;
		new MainBulletThread().start();
		new MainMissileThread().start();
		new OtherBulletThread().start();
		new ExplosionThread().start();
		new MapThread().start();
		new UpdateThread().start();
		LevelChange.changeLevel();
		GameData.gameState=2;
		MainTank m=new MainTank(0,0,0,0,0);
		synchronized(GameData.lock)
		{
			aq.offer(m);
		}
		System.out.println(" inistart end/....");
	}

	public static void broadcastVictory() 
	{
		for(ServerAgent sa:ulist)
		{
			try
			{
				synchronized(GameData.lock)
				{
					sa.dout.writeUTF("<#VICTORY#>");
				}
			}
			catch(Exception e)
			{
				//e.printStackTrace();
			}
		}
	}
}