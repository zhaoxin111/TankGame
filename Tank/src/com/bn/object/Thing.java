package com.bn.object;

import android.graphics.Canvas;

public abstract class Thing
{
	public abstract void drawSelf(Canvas canvas,int x,int y,int angle);
			//0 °µÊ÷	Tree
			//1ÁÁÊ÷	Tree
			//2¿Ó	mark
			//3É³°ü 	barrier
			//4Ëş 	tower
			//5     tank1
			//6 	tank2
			//7		tank3
			//8		tank4
}