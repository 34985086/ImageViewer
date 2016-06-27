package gavin.imageviewer;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by syang9 on 2016-06-23.
 */
public class ImageBundle implements Serializable {
	private int position;
	private ArrayList<String> names;
	//Matrix matrix;

	public ImageBundle(ArrayList<String>names, int position){
		this.position = position;
		this.names = names;
	}

	public Drawable getDrawable(Resources res){
		DisplayMetrics dm = res.getDisplayMetrics();

		Bitmap bitmap = BitmapFactory.decodeFile(names.get(position));
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		Matrix matrix = new Matrix();
		float scaleX = (float) dm.widthPixels / width;
		float scaleY = (float) dm.heightPixels / height;
		float scale = scaleX < scaleY ? scaleX : scaleY;
		matrix.setScale(scale, scale);

		Bitmap bitmap1 = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
		BitmapDrawable drawable = new BitmapDrawable(res, bitmap1);

		return drawable;
	}

	public Boolean next(){
		if(position < names.size() - 1)
		{
			position++;
			return true;
		}else{
			return false;
		}
	}

	public Boolean prev(){
		if(position > 0){
			position--;
			return true;
		}else{
			return false;
		}
	}
}
