package gavin.imageviewer;

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

	public int getPosition(){
		return position;
	}

	public String getName(int position){
		if(position < names.size()){
			return names.get(position);
		}else{
			return null;
		}
	}
}
