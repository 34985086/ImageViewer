package gavin.imageviewer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by syang9 on 2016-06-23.
 */
public class ImageBundle implements Serializable {
	private int position;
	private ArrayList<String> names;

	public ImageBundle(ArrayList<String>names, int position){
		this.position = position;
		this.names = names;
	}

	public Drawable getDrawable(){

		Bitmap bitmap = BitmapFactory.decodeFile(names.get(position));
		return new BitmapDrawable(null, bitmap);
	}

	public void next(){
		if(position < names.size() - 1)
		{
			position++;
		}
	}

	public void prev(){
		if(position > 0){
			position--;
		}
	}
}
