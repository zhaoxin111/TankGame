package com.bn.tank;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameViewDrawThread extends Thread{
	boolean flag = true;										//�����̱߳�־λ
	int sleepSpan = 20;											//˯�ߵ�ʱ��
	MySurfaceView mView;										//����SurfaceView����
	SurfaceHolder surfaceHolder;								//����ӿ�
	public GameViewDrawThread(MySurfaceView mView){
		this.mView = mView;										//��ȡSurfaceView������
		this.surfaceHolder = mView.getHolder();					//ע��ص��ӿ�
	}
	public void run(){											//���������߳�
		Canvas c=null;											//���廭��
        while (this.flag) {										//�����߳�
            try {
                c = this.surfaceHolder.lockCanvas(null);		// �����������������ڴ�Ҫ��Ƚϸߵ�����£����������ҪΪnull
                synchronized (this.surfaceHolder) {				//ͬ������
                	if(this.flag){								//�����־λΪtrue
                    	mView.onDraw(c);						//���ƽ���
            }}}finally {
                if (c != null) {								//���������Ϊ��
                    this.surfaceHolder.unlockCanvasAndPost(c);	//���ͷ���
            }}try{
            	Thread.sleep(sleepSpan);						//˯��ָ��������				
            }catch(Exception e){
            	e.printStackTrace();							//��ӡ��ջ��Ϣ
}}}}
