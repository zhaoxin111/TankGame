package com.bn.util;
import java.io.InputStream;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapIOUtil 
{
	static Bitmap bp=null;
	public  static Bitmap getBitmap(String picName,Resources r)
	{
		System.out.println(picName);
		try
		{
			String path="game/"+picName;
			InputStream in=r.getAssets().open(path);
		    bp = BitmapFactory.decodeStream(in);
		}
		catch(Exception e)
		{
			System.out.println("≥ˆœ÷“Ï≥££°£°");
		}
		return bp;
	}
}
