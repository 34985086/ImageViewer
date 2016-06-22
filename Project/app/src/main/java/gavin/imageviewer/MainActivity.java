package gavin.imageviewer;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends Activity {

	ListView myList;
	LayoutInflater inflater;

	ArrayList<String> names = new ArrayList<>();
	ArrayList<String> descs = new ArrayList<>();
	ArrayList<String> fileNames = new ArrayList<>();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		inflater = LayoutInflater.from(MainActivity.this);

		getImagesAttributes();

		BaseAdapter adapter = new BaseAdapter() {
			@Override
			public int getCount() {
				return fileNames.size();
			}

			@Override
			public Object getItem(int position) {
				return null;
			}

			@Override
			public long getItemId(int position) {
				return position;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				//LinearLayout line = new LinearLayout(MainActivity.this);
				//line.setOrientation(LinearLayout.HORIZONTAL);

				//ImageView image = new ImageView(MainActivity.this);
				//image.setImageBitmap(BitmapFactory.decodeFile(fileNames.get(position)));
				//image.setScaleType(ImageView.ScaleType.MATRIX);
				//image.setIm
				/*
				image.setLayoutParams(new ViewGroup.LayoutParams(
						ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
				));*/
/*
				TextView text = new TextView(MainActivity.this);
				text.setText(names.get(position));
				text.setTextSize(20);
				text.setTextColor(Color.RED);
*/
				//line.addView(image);
				//line.addView(text);
				View layout = convertView;
				if (convertView == null) {
					layout = inflater.inflate(R.layout.prevview, null);
				}

				ImageView imageView = (ImageView) layout.findViewById(R.id.preView);
				imageView.setImageBitmap(BitmapFactory.decodeFile(fileNames.get(position)));
				return layout;
			}
		};

		myList = (ListView)findViewById(R.id.myList);
		myList.setAdapter(adapter);
		myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
			                        int position, long id) {
				System.out.println("click " + position + " item");
			}
		});
	}

	private void getImagesAttributes(){
		names.clear();
		descs.clear();
		fileNames.clear();

		Cursor cursor = getContentResolver().query(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);

		while(cursor.moveToNext()){
			String name = cursor.getString(cursor.getColumnIndex(
					MediaStore.Images.Media.DISPLAY_NAME));

			String desc = cursor.getString(cursor.getColumnIndex(
					MediaStore.Images.Media.DESCRIPTION ));

			byte[] data = cursor.getBlob(cursor.getColumnIndex(
					MediaStore.Images.Media.DATA ));

			names.add(name);
			descs.add(desc);
			fileNames.add(new String(data, 0, data.length - 1));
		}
	}
}
