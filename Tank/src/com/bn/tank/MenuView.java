package com.bn.tank;

import com.bn.data.GameData;
import com.bn.util.BitmapIOUtil;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class MenuView{
	public float ratio;//��ͬ�ֱ���X����ϵ��
	
	Bitmap back;		//����ͼ��
	Bitmap decoration;	//
	Bitmap title;		//���� (̹�˴�ս)
	Bitmap helpbg;		//��������
	Bitmap back_Menu;	//���ز˵�
	Bitmap Game_Music;	//�ر�����
	Bitmap Game_Effect;	//�ر���Ч

	Bitmap back_menu[]=new Bitmap[2];	
	Bitmap close_music[]=new Bitmap[2];
	Bitmap close_effect[]=new Bitmap[2];
	Bitmap open_music[]=new Bitmap[2];
	Bitmap open_effect[]=new Bitmap[2];
	Bitmap helpProgress;
	Bitmap helpProgress_select;
	public int num=5;
	Bitmap helpprogress;
	Bitmap help_back;
	Bitmap help[]=new Bitmap[num];
	
	Bitmap[] game=new Bitmap[4];
	Bitmap start_game[]=new Bitmap[4];
	Bitmap[] start_game_select=new Bitmap[4];

	int menuX=680;				//���˵�  �ĸ���ť
	int menuY=120;
	int distance=80;			//��ť�ľ���
	
	int menu_help_backX=700;	//���������� ���ز˵�
	int menu_help_backY=480;
	
	int menu_sound_backX=20;	//����ѡ���� ���ز˵�
	int menu_sound_backY=470;
	
	int close_musicX=680;	//�ر�����
	int close_musicY=170;
	int close_effectX=680;	//�ر���Ч
	int close_effectY=300;
	
	public int offsetX=0;		//���� X  ƫ����
	public int offsetY=0;		//���� Y ƫ����
	public boolean anim=false;

	public MySurfaceView father;

	
	public final int MENU_START=0;	//���˵�
	public final int MENU_SOUND=1;	//��������
	public final int MENU_HELP=2;	//��Ϸ����
	public int MenuState=MENU_START;	//�˵���״̬
	public int size;
	
	public MenuView(MySurfaceView father) 
	{
		this.father=father;
		
		back=BitmapIOUtil.getBitmap("gameBackground.png", father.getResources());
		decoration=BitmapIOUtil.getBitmap("decoration.png", father.getResources());	
		title=BitmapIOUtil.getBitmap("title.png", father.getResources());
		helpbg=BitmapIOUtil.getBitmap("helpbg2.png", father.getResources());
		back_menu[0]=BitmapIOUtil.getBitmap("back_menu.png", father.getResources());
		back_menu[1]=BitmapIOUtil.getBitmap("back_menu_select", father.getResources());
		
		close_music[0]=BitmapIOUtil.getBitmap("close_music.png",father.getResources());		//�ر�����
		close_music[1]=BitmapIOUtil.getBitmap("close_music_select.png",father.getResources());
		
		close_effect[0]=BitmapIOUtil.getBitmap("close_effect.png", father.getResources());	//�ر���Ч
		close_effect[1]=BitmapIOUtil.getBitmap("close_effect_select.png", father.getResources());
		
		open_music[0]=BitmapIOUtil.getBitmap("open_music.png", father.getResources());
		open_music[1]=BitmapIOUtil.getBitmap("open_music.select.png", father.getResources());
		
		open_effect[0]=BitmapIOUtil.getBitmap("open_effect.png", father.getResources());
		open_effect[1]=BitmapIOUtil.getBitmap("open_effect.select.png", father.getResources());
		
		start_game[0]=BitmapIOUtil.getBitmap("start_game.png", father.getResources());
		start_game[1]=BitmapIOUtil.getBitmap("soundset.png", father.getResources());
		start_game[2]=BitmapIOUtil.getBitmap("help.png", father.getResources());
		start_game[3]=BitmapIOUtil.getBitmap("exit_game.png", father.getResources());
		
		start_game_select[0]=BitmapIOUtil.getBitmap("start_game_select.png", father.getResources());
		start_game_select[1]=BitmapIOUtil.getBitmap("soundset_select.png", father.getResources());
		start_game_select[2]=BitmapIOUtil.getBitmap("help_select.png", father.getResources());
		start_game_select[3]=BitmapIOUtil.getBitmap("exit_game_select.png", father.getResources());
		
		help[0]=BitmapIOUtil.getBitmap("help111.png", father.getResources());
		help[1]=BitmapIOUtil.getBitmap("help222.png", father.getResources());
		help[2]=BitmapIOUtil.getBitmap("help333.png", father.getResources());
		help[3]=BitmapIOUtil.getBitmap("help444.png", father.getResources());
		help[4]=BitmapIOUtil.getBitmap("help555.png", father.getResources());
		
		helpprogress=BitmapIOUtil.getBitmap("levels.png",father.getResources());
		help_back=BitmapIOUtil.getBitmap("help_back.png", father.getResources());

		helpProgress=Bitmap.createBitmap(helpprogress, 20, 0, 20, 20);	
		helpProgress_select=Bitmap.createBitmap(helpprogress, 0, 0, 20, 20);	
				
		Matrix matrix = new Matrix(); 
		matrix.setScale(1.2f, 1.2f);					//����1.2��
		
		ratio=father.gd.ratio;
		
		back=Bitmap.createBitmap(back, 0, 0, back.getWidth(), back.getHeight(), matrix, true);
		helpbg=Bitmap.createBitmap(helpbg,0,0,helpbg.getWidth(),helpbg.getHeight(),matrix,true);
		helpprogress=Bitmap.createBitmap(helpprogress,0,0,helpprogress.getWidth(),helpprogress.getHeight(),matrix,true);
		for(int i=0;i<2;i++)
		{
			back_menu[i]=Bitmap.createBitmap(back_menu[i],0,0,back_menu[i].getWidth(),back_menu[i].getHeight(),matrix,true);
			close_music[i]=Bitmap.createBitmap(close_music[i],0,0,close_music[i].getWidth(),close_music[i].getHeight(),matrix,true);
			close_effect[i]=Bitmap.createBitmap(close_effect[i],0,0,close_effect[i].getWidth(),close_effect[i].getHeight(),matrix,true);
			open_music[i]=Bitmap.createBitmap(open_music[i], 0, 0, open_music[i].getWidth(), open_music[i].getHeight(),matrix,true);
			open_effect[i]=Bitmap.createBitmap(open_effect[i], 0, 0, open_effect[i].getWidth(), open_effect[i].getHeight(),matrix,true);
			
		}
		Game_Music=close_music[0];
		Game_Effect=close_effect[0];
		back_Menu=back_menu[0];
		
		helpProgress=Bitmap.createBitmap(helpProgress, 0, 0, helpProgress.getWidth(), helpProgress.getHeight(),matrix,true);
		helpProgress_select=Bitmap.createBitmap(helpProgress_select, 0, 0, helpProgress_select.getWidth(), helpProgress_select.getHeight(),matrix,true);
		
		for(int i=0;i<4;i++)
		{
			game[i]=start_game[i]=Bitmap.createBitmap(start_game[i], 0, 0, start_game[i].getWidth(), start_game[i].getHeight(),matrix,true);
			start_game_select[i]=Bitmap.createBitmap(start_game_select[i], 0, 0, start_game_select[i].getWidth(), start_game_select[i].getHeight(),matrix,true);
		}
	}
	
	protected void draw(Canvas canvas){								//���Ʋ˵�����
		canvas.save();												//���滭��
		canvas.drawBitmap(back, 0, 0, null);						//������ͼƬ
		canvas.restore();											//��ԭ����
		switch(MenuState){											//�жϲ˵�״̬
			case MENU_START:										//��ʼ���˵�����
				canvas.save();										//���滭��
				canvas.translate(140,50);							//��������λ��
				canvas.drawBitmap(title, 0, 0, null);				//���Ʊ���
				canvas.restore();									//��ԭ����
				canvas.save();										//���滭��				
				canvas.translate(100,180);							//��������λ��
				canvas.drawBitmap(decoration, 0, 0, null);			//�����������ͼƬ
				canvas.restore();									//��ԭ����
				for(int i=0;i<4;i++){								
					canvas.save();									//��������
					canvas.translate(menuX,menuY+i*distance);		//��������λ��
					canvas.drawBitmap(game[i], 0, 0, null);			//���˵���
					canvas.restore();								//��ԭ����
				}
				break;
			case MENU_SOUND:										//����
				
				canvas.save();							
				canvas.translate(140,50);									//������
				canvas.drawBitmap(title, 0, 0, null);
				canvas.restore();
				
				canvas.save();							
				canvas.translate(100,180);									//�����������ͼƬ
				canvas.drawBitmap(decoration, 0, 0, null);
				canvas.restore();
				
				canvas.save();
				canvas.translate(menu_sound_backX, menu_sound_backY);
				canvas.drawBitmap(back_Menu, 0, 0, null);
				canvas.restore();
				
				
				canvas.save();
				canvas.translate(close_musicX,close_musicY);
				canvas.drawBitmap(Game_Music,0,0, null);
				canvas.restore();
				
				canvas.save();
				canvas.translate(close_effectX,close_effectY);
				canvas.drawBitmap(Game_Effect, 0, 0, null);
				canvas.restore();
				
				break;
			case MENU_HELP:											//���ư�������
				int end,start,offsetX,position;						//����
				int distanceX=90;									//X�����λ��
				int distanceY=30;									//Y������λ��
	            synchronized(father.gd.lock){						//ͬ������
	            	end=GameData.end;
	            	start=GameData.start;
	            	offsetX=GameData.offsetX;
	            	position=GameData.position;
	            	num=GameData.helpNum;
	            }
				if(!anim){											//���û�л�����ʱ��
					if(end!=0&&start!=0){							//�����ʼ���յ����겻Ϊ0
						offsetX=end-start;							//��ȡ����ƫ����
					}else{
						offsetX=0;									//ƫ������Ϊ0
				}}
				int viewX=offsetX;									//���廭�������
				if(position==0){									//����ǵ�һ��ͼ
					if(offsetX>0){									//������һ���
						viewX=0;									//����ƫ����Ϊ0
				}}else if(position==num-1){							//��������һ��ͼ
					if(offsetX<0){									//������󻬶�
						viewX=0;									//����ƫ����Ϊ0
				}}
				canvas.save();										//���滭��
				canvas.translate(viewX+distanceX, distanceY);		//ƽ�ƻ���
				canvas.drawBitmap(help[position], 0, 0, null);		//���ư���ͼƬ
				canvas.restore();									//��ԭ����
				if(offsetX<0)										//���󻬶�
				{
					if(position<num-1){								//����������һ��ͼ
						canvas.save();								//���滭��
						canvas.drawBitmap(help[position+1],offsetX+GameData.baseWidth+distanceX,
									distanceY,null);				//�������������ͼƬ
						canvas.restore();							//��ԭ����
				}}else if(offsetX>0){								//������һ���
					if(position>0){									//������ǵ�һ��
						canvas.save();								//���滭��
						canvas.drawBitmap(help[position-1], offsetX-GameData.baseWidth+distanceX,
									distanceY, null);				//���ư���ͼƬ
						canvas.restore();							//��ԭ����
				}}
				for(int i=0;i<5;i++){
					if(i==position){								//���ƶ��İ�������
						canvas.save();								//���滭��
						canvas.drawBitmap(helpProgress_select,350+i*50,500,null);//��ѡ��ĵ�
						canvas.restore();							//��ԭ����
						continue;									
					}
					canvas.save();									//���滭��
					canvas.drawBitmap(helpProgress,350+i*50,500,null);	//�������ĵ�
					canvas.restore();								//��ԭ����
				}
				canvas.save();										//���滭��
				canvas.drawBitmap(help_back,0,0, null);				//�������˵���ť
				canvas.restore();									//��ԭ����
				synchronized(father.gd.lock){						//ͬ������
	            	GameData.offsetX=offsetX;						//����λ����
	            	GameData.position=position;						//���ĵ�ǰ�İ���λ��
	            }
				break;												
	}}
	
	public boolean onTouchEvent(MotionEvent event){
		float x=father.changeTouchX(event.getX());
		float y=father.changeTouchY(event.getY());
		switch(event.getAction()){
			case MotionEvent.ACTION_DOWN:
				if(MenuState==MENU_HELP){
					synchronized(father.gd.lock){
						GameData.start=(int) x;
				}}
				break;
			case MotionEvent.ACTION_MOVE:
				switch(MenuState){
					case MENU_START:
						if(x>menuX&&x<menuX+200){
							if(y>menuY&&y<menuY+game[0].getHeight()){
								game[0]=start_game_select[0];
							}else if(y>(menuY+distance)
									&&y<(menuY+distance+game[0].getHeight())){
								game[1]=start_game_select[1];
							}else if(y>(menuY+distance*2)
									&&y<(menuY+distance*2+game[0].getHeight())){
								game[2]=start_game_select[2];
							}else if(y>(menuY+distance*3)
									&&y<(menuY+distance*3+game[0].getHeight())){
								game[3]=start_game_select[3];
							}else{
								for(int i=0;i<4;i++){
									game[i]=start_game[i];
						}}}
						break;
						case MENU_SOUND:	
						if(x>menu_sound_backX
								&&x<menu_sound_backX+back_Menu.getWidth()){
							if(y>menu_sound_backY
									&&y<menu_sound_backY+back_Menu.getHeight()){
								back_Menu=back_menu[1];
							}else{
								back_Menu=back_menu[0];
						}}
						if(x>close_musicX&&x<close_musicX+Game_Music.getWidth()){
							if(y>close_musicY
									&&y<close_musicY+Game_Music.getHeight()){
								if(GameData.GAME_SOUND){
									Game_Music=close_music[1];
								}else{
									Game_Music=open_music[0];
							}}
							if(y>close_effectY
									&&y<close_effectY+Game_Music.getHeight()){
								if(GameData.GAME_EFFECT){
									Game_Effect=close_effect[1];
								}else{
									Game_Effect=open_effect[0];
						}}}
				break;
				case MENU_HELP:
					synchronized(father.gd.lock){
						GameData.end=(int) x;
					}
			    	onFling();
					break;
				}
			break;
			case MotionEvent.ACTION_UP:		//̧��
			switch(MenuState){
					case MENU_START:
						if(x>menuX&&x<menuX+200){
							if(y>menuY&&y<menuY+game[0].getHeight()){
								game[0]=start_game[0];
								GameData.viewState=GameData.Game_select;
								father.openNetwork();
							}else if(y>(menuY+distance)&&y<(menuY+distance+game[0].getHeight())){
								game[1]=start_game[1];
								MenuState=MENU_SOUND;
							}else if(y>(menuY+distance*2)&&y<(menuY+distance*2+game[0].getHeight())){
								game[2]=start_game[2];
								MenuState=MENU_HELP;
							}else if(y>(menuY+distance*3)&&y<(menuY+distance*3+game[0].getHeight())){
								game[3]=start_game[3];
								father.father.exit();
							}
							father.father.playSound(GameData.SELECT,0);
						}
						break;
					case MENU_SOUND:
						if(x>menu_sound_backX&&x<menu_sound_backX+back_Menu.getWidth()){
							if(y>menu_sound_backY&&y<menu_sound_backY+back_Menu.getHeight()){
								back_Menu=back_menu[0];
								MenuState=MENU_START;
								father.father.playSound(GameData.SELECT,0);
						}}
						if(x>close_musicX&&x<close_musicX+Game_Music.getWidth()){
							if(y>close_musicY&&y<close_musicY+Game_Music.getHeight()){
								if(GameData.GAME_SOUND){
									Game_Music=open_music[0];
									GameData.GAME_SOUND=false;
								}else{
									Game_Music=close_music[0];
									GameData.GAME_SOUND=true;
								}
								father.father.playBackground();
								father.father.playSound(GameData.SELECT,0);
							}
							if(y>close_effectY&&y<close_effectY+Game_Music.getHeight()){
								if(GameData.GAME_EFFECT){
									Game_Effect=open_effect[0];
									GameData.GAME_EFFECT=false;
								}else{
									Game_Effect=close_effect[0];
									GameData.GAME_EFFECT=true;
								}
								father.father.playSound(GameData.SELECT,0);
						}}
						break;
					case MENU_HELP:
					if(x>0&&x<80){
						if(y>0&&y<60){
							MenuState=MENU_START;
							father.father.playSound(GameData.SELECT,0);
					}}
					synchronized(father.gd.lock){
						GameData.start=0;
						GameData.end=0;
					}
					break;
			}
			break;	
		}
		return true;
	}
	
	public void onFling() 
	{ 
		int end,start,offsetX,position,num;
		synchronized(father.gd.lock)
        {
        	end=GameData.end;
        	start=GameData.start;
        	offsetX=GameData.offsetX;
        	position=GameData.position;
        	num=GameData.helpNum;
        }
		if(end!=0&&start!=0)
		{
			offsetX=end-start;
		}else
		{
			offsetX=0;
		}
	    if (offsetX >GameData.baseWidth /6) 			//��
	    { 
	        if (position>0) 
	        { 
	            
	            anim=true;
	            while(offsetX<GameData.baseWidth)
	            {
	            	offsetX*=1.1;
	            	synchronized(father.gd.lock)
	            	{
	            		GameData.offsetX=offsetX;
	            	}
	            	try {
						Thread.sleep(40);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
	            	
	            }
	            synchronized(father.gd.lock)
	            {
	            	GameData.position--;
	            	GameData.end=0;
	            	GameData.start=0;
	            	GameData.offsetX=0;
	            	
	            }
	            anim=false;
	           
	        } 
	    } else if(offsetX<-GameData.baseWidth/6)		//��
	    { 
	        if (position<num-1) 
	        { 
	             
	            anim=true;
	            while(offsetX>-GameData.baseWidth)
	            {
	            	offsetX*=1.1;
	            	synchronized(father.gd.lock)
	            	{
	            		GameData.offsetX=offsetX;
	            	}
	            	try {
						Thread.sleep(40);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
	            }
	            synchronized(father.gd.lock)
	            {
	            	GameData.position++;
	            	GameData.end=0;
	            	GameData.start=0;
	            	GameData.offsetX=0;
	            }
	            anim=false;
	        } 
	    } 
	    try {
			Thread.sleep(50);
		} catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
    
}


	public boolean onKeyDown(int keyCode,KeyEvent event)
	{
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			switch(MenuState)
			{
				case MENU_START:
					break;
				case MENU_SOUND:
					MenuState=MENU_START;
					break;
				case MENU_HELP:
					MenuState=MENU_START;
					break;
			}
		}
		return true;
	}
}
