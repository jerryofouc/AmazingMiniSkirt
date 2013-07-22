package com.netease.amazing.util;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

/**
 * 
 * @author Huang Xiao Jun
 * Class Description:
 *    ͨ��url��ȡͼƬ������ʾ��imgView��
 */
public class InitImageView extends AsyncTask<String,String,Bitmap> {
	private ImageView imgView;
	public InitImageView(ImageView imgView){
		this.imgView = imgView;
	}

	@Override
	protected Bitmap doInBackground(String... arg0) {			
		return ReturnBitmapFromInternet.returnBitMap(arg0[0]);
	}
	
	@Override
	protected void onPostExecute(Bitmap result) {
		super.onPostExecute(result);
		imgView.setImageBitmap(result);
	}
	
}
