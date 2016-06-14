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
	public float ratio;			//��ͬ�ֱ���X����ϵ��
	public float screenWidth;	//��Ļ���
	public float screenHeight;	//��Ļ�߶�
	public float screenLeft=0;	//��Ļ�������
	public float screenTop=0;	//��Ļ�߶�����
	int yaoganFlag=0;
	int pointNumber[]={-1,-1};
	RectF rectBackground;		//�������ƾ���
	Bitmap back;				//����ͼ��
	
	MenuView menu;				//�˵�
	WelcomeView down;				//���ؽ���
	SelectView select;			//ѡ�����
	GameViewDrawThread gameViewDrawThread;
	public Yaogan yaogan;	
	MainTank mainTank;
	MainBullet mainBullet; 
	Explosion explosion;
	Boss boss;
	OtherView about;			//����ֵ������,��ͣ����
	Thing thing[];
	
	public int gameState=1;
	


	public MySurfaceView(TankActivity activity,int width,int height)	
	{
		super(activity);
		this.father=activity;		//��ȡActivity����
		getHolder().addCallback(this);//ע��ص��ӿ�
		
		screenWidth=width;			//��¼��Ļ�Ŀ��
		screenHeight=height;		//��¼��Ļ�ĸ߶�
		if(screenWidth>screenHeight*16/9)		//�������ֻ���Ļ����
		{
			ratio=screenHeight/GameData.baseHeight;	//��ȡ��Ļ�ߵı���
			screenHeight=GameData.baseHeight;		//������Ļ��ʾ�ĸ߶�
			screenLeft=(screenWidth/ratio-GameData.baseWidth)/2; //������Ļ�������׵ĳ���
			screenWidth=GameData.baseWidth;		//������Ļ��ʾ�Ŀ��
		}else									//�������ֻ���Ļ����
		{
			ratio=screenWidth/GameData.baseWidth;	//��ȡ��Ļ��ı���
			screenWidth=GameData.baseWidth;	//������Ļ�Ŀ��
			screenTop=(screenHeight/ratio-GameData.baseHeight)/2;	//������Ļ�������׵ĳ���
			screenHeight=GameData.baseHeight;	//������Ļ�ĸ߶�		
		}
		gd.ratio=ratio;							//��¼��Ļ�ı���
		
		rectBackground = new RectF(0, 0, screenWidth, screenHeight); 	//���ƾ��ε����	
		back=BitmapIOUtil.getBitmap("gameBackground.png", activity.getResources());//���Ʊ���ͼ
		Matrix matrix = new Matrix();			//�������
		matrix.setScale(1.2f, 1.2f);			//�Ŵ���󳤿��1.2��
		back=Bitmap.createBitmap(back, 0, 0, back.getWidth(), back.getHeight(), matrix, true);//�Ŵ󱳾������1.2
		initObject();			//��ʼ��ˢ֡�߳�������
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

	public void openNetwork() 	//��Android�˽��������߳�
	{
		nt=new NetworkThread(MySurfaceView.this);
		nt.start();
	}

	@Override
	protected void onDraw(Canvas canvas) 		//������Ϸ����
	{
		canvas.scale(ratio, ratio);				//�Ŵ󻭲�
		canvas.save();
		canvas.translate(screenLeft, screenTop);//ƽ�Ƶ��µ�ԭ��
		canvas.clipRect(rectBackground);		//���òü���
		
		int gameState=GameData.viewState;	//��ǰ״̬
		
		switch(gameState)
		{
		case GameData.Game_load:			//����
			down.draw(canvas);
			break;
		case GameData.Game_menu:			//�˵�
			menu.draw(canvas);
			break;
		case GameData.Game_palying:			//��Ϸ��ʼ
			drawSelf(canvas);
			break;
		case GameData.Game_select:
			select.drawSelf(canvas);
			break;
		}
    	canvas.restore();								//�ָ�����
	}
	
	@Override
    public boolean onTouchEvent(MotionEvent event){
		int gameState=GameData.viewState;								//��ȡ��Ϸ״̬
		switch(gameState){
			case GameData.Game_load:									//��Ϸ���ؽ���
				break;
			case GameData.Game_menu:									//��Ϸ�˵�����
				menu.onTouchEvent(event);								//��Ӳ˵���������
				break;
			case GameData.Game_palying:									//��Ϸ�е�״��
				mainTouch(event);										//��Ϸ�м���
				break;
			case GameData.Game_pause:									//��Ϸ��ͣ״̬
				break;
			case GameData.Game_select:									//��Ϸѡ�ؽ���
				select.onTouchEvent(event);								//���ѡ�ؼ���
				break;
		}
    	return true;
    }

	private void drawSelf(Canvas canvas){				//��  ��Ϸ�� ����  
		int offset;										//����ƫ����
		synchronized(gd.lock){							//�����ݿ���в��������
			offset=gd.offset;							//��ȡ��Ϸ�Ľ���
		}
		canvas.save();									//������ͼƬ
		canvas.drawBitmap(back, 0, offset-GameData.baseHeight, null);//������Ϸ����
		canvas.drawBitmap(back, 0, offset, null);					//������Ϸ����
		canvas.restore();									//��������
		int []tempMap;									//��ͼ����
		int []tempTree;									//��
		int []tempTank;									//̹��
		int []tempAward;								//��������
		synchronized(gd.lock){							//�����ݿ���в��������
			tempMap=gd.mapData;							//��ͼ����
			tempTree=gd.mapTree;						//������
			tempTank=gd.mapTank;						//̹������
			tempAward=gd.award;							//��������
		}
		if(tempMap!=null){	    						//�����ͼ��Ϊ��	
			int count=0;								//���������
			while(count<tempMap.length){				//���� ��ͼ����
				thing[tempMap[count++]].drawSelf(canvas,
						tempMap[count++],tempMap[count++],0); //���÷������Ƶ�ͼ
		}}
		if(tempTank!=null){	    						//���̹�����鲻Ϊ��
			int count=0;								//���������
			while(count<tempTank.length){				//����̹������
				thing[tempTank[count++]].drawSelf(canvas,tempTank[count++],
						tempTank[count++],tempTank[count++]); 	//���÷�������̹��
		}}
		if(tempAward!=null)
		{
			int count=0;
			while(count<tempAward.length)
			{
				thing[tempAward[count++]].drawSelf(canvas, tempAward[count++], tempAward[count++],0);
			}
		}
		boss.drawSelf(canvas,0,0,0);					//����BOSS
		mainBullet.drawSelf(canvas,0,0,0);					//���ӵ�
		mainTank.drawSelf(canvas,0,0,0);						//�����̹��
		

		if(tempTree!=null)							//����
		{	    		
			int count=0;
			while(count<tempTree.length)
			{
				thing[tempTree[count++]].drawSelf(canvas,tempTree[count++],tempTree[count++],0); 
			}
		}
		explosion.drawSelf(canvas,0,0,0);				//����ը
		yaogan.drawSelf(canvas);						//��ҡ��
		about.drawSelf(canvas);							//��������������ͣ�Ļ���  /////////////////////////
	}

	private void mainTouch(MotionEvent event){								//��Ϸ�е�ҡ�˴�������
		int index=event.getActionIndex();									//��ȡ��ǰaction����ֵ
		int id=event.getPointerId(index);									//��ȡ��ǰ������������ֵ
		int pCount=event.getPointerCount();									//��ȡ��ǰ��������
		float x=changeTouchX(event.getX(index));							//��ȡ������X����
    	float y=changeTouchY(event.getY(index));							//��ȡ������Y����
    	switch (event.getAction()&MotionEvent.ACTION_MASK){     			//��㴥��      
            case MotionEvent.ACTION_DOWN:									//���㴥��
        	case MotionEvent.ACTION_POINTER_DOWN:							//��㴥��	
            	if(GameData.state==4){										//��ϷΪ��ͣ״̬
            		about.pauseTouchDown(x, y);								//�����ͣ�ļ���
            	}		
            	if(GameData.state<3&&about.isPause(x, y)){					//������ͣͼƬ
            		GameData.state=4;										//ת����ͣ״̬
            	}
            	int temp1=yaogan.isYaoGan(x,y);								//�Ƿ�����ҡ��			
            	if(temp1!=0){												//��������ҡ��
            		pointNumber[temp1-1]=id;								//��ҡ�˸�����ֵ
            	}
            break; 
            case MotionEvent.ACTION_MOVE:									//�������ƶ�	
            	if(GameData.state==4){										//��ͣ״̬
            		about.pauseTouchDown(x, y);								//��ͣ��������
            	}
            	for(int i=0;i<pCount;i++){									//����������
            		int pid=event.getPointerId(i);							//��ȡ����������ֵ
            		float xTemp=changeTouchX(event.getX(i));				//��ȡ����Xֵ
                	float yTemp=changeTouchY(event.getY(i));				//��ȡ����Yֵ
            		if(pointNumber[0]==pid){								//���������ҡ��
                		yaogan.changeLeftYaoGan(xTemp, yTemp);				//�ı�����ҡ�˵�λ��
                	}else if(pointNumber[1]==pid){							//���������ҡ��
                		yaogan.changeRightYaoGan(xTemp, yTemp);				//�ı�����ҡ��λ��
                }}
            break; 
            case MotionEvent.ACTION_UP:										//̧������
            case MotionEvent.ACTION_POINTER_UP:								//̧���㴥��
            	if(GameData.state==4){										//��ͣ״̬
            		about.pauseTouchUp(x, y);								//��ͣ״̬
            	}
            	if(pointNumber[0]==id){										//�������ҡ��
            		pointNumber[0]=-1;										//��ҡ������ֵ��Ϊ-1
                	yaogan.leftOffsetX=0;									//��ҡ��Xλ������Ϊ0
                	yaogan.leftOffsetY=0;									//��ҡ��Yλ������Ϊ0
            	}else if(pointNumber[1]==id){								//�������ҡ��
            		pointNumber[1]=-1;										//��ҡ������ֵ��Ϊ-1
                	yaogan.rightOffsetX=0;									//��ҡ��Xλ������Ϊ0
                	yaogan.rightOffsetY=0;									//��ҡ��Yλ������Ϊ0
            	}
            break;
    }}
	
	public float changeTouchX(float x)		//����ĻX���껻��Ϊ��Ϸ��������
	{
		return x/ratio-screenLeft;
	}
	
	public float changeTouchY(float y)		//����ĻY���껻��Ϊ��Ϸ��������
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
