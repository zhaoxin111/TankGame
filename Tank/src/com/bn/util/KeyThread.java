package com.bn.util;

import com.bn.data.GameData;
import com.bn.tank.MySurfaceView;

public class KeyThread extends Thread
{
	static final int TIME_SPAN=50; 
	MySurfaceView father;
	public boolean flag=true;
	int gameState=0;
	int testCount=0;
	
	public KeyThread(MySurfaceView father)
	{
		this.father=father;
		gameState=GameData.state;
	}
	
	public void broadcastExit()
	{
		father.gd=new GameData();
		try
		{
			synchronized(father.gd.lock)
			{
				father.nt.dout.writeUTF("<#EXIT#>");
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public void broadcastState(int state)
	{
		try
		{
			synchronized(father.gd.lock)
			{
				father.nt.dout.writeUTF("<#STATE#>");
				father.nt.dout.writeInt(state);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	public void broadcastLevel(int number) 
	{
		try
		{
			synchronized(father.gd.lock)
			{
				father.nt.dout.writeUTF("<#LEVEL#>");
				father.nt.dout.writeInt(number);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	public void broadcastSelect(int number) 
	{
		try
		{
			synchronized(father.gd.lock)
			{
				father.nt.dout.writeUTF("<#SELECT#>");
				father.nt.dout.writeInt(number);
			}
		}
		catch (Exception e) 
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
				if(gameState!=GameData.state)
				{
					gameState=GameData.state;
					broadcastState(gameState);
				}
				if(GameData.state==2)
				{
					if(father.yaogan.leftOffsetX!=0||father.yaogan.leftOffsetY!=0
							||father.yaogan.rightOffsetX!=0||father.yaogan.rightOffsetY!=0)
					{
						synchronized(father.gd.lock)
						{
							father.nt.dout.writeUTF("<#KEY#>");
							father.nt.dout.writeFloat(father.yaogan.leftOffsetX);
							father.nt.dout.writeFloat(father.yaogan.leftOffsetY);
							father.nt.dout.writeFloat(father.yaogan.rightOffsetX);
							father.nt.dout.writeFloat(father.yaogan.rightOffsetY);
						}
					}
				}
				testCount=(testCount+1)%20;
				if(testCount==0)
				{
					father.nt.dout.writeUTF("<#TEST#>");
				}
				Thread.sleep(TIME_SPAN);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}