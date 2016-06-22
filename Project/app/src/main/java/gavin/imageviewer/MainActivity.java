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
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends Activity {

	ListView myList;
	LayoutInflater inflater;

	ArrayList<String> names = new ArrayList<>();
	ArrayList<String> descs = new ArrayList<>();
	ArrayList<String> fileNames = new ArrayList<>();
	ArrayList<Long> dateTaken = new ArrayList<>();


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
				View layout = convertView;
				if (convertView == null) {
					layout = inflater.inflate(R.layout.prevview, null);
				}

				ImageView imageView = (ImageView) layout.findViewById(R.id.preView);
				imageView.setImageBitmap(BitmapFactory.decodeFile(fileNames.get(position)));

				TextView name = (TextView)layout.findViewById(R.id.name);
				TextView time = (TextView)layout.findViewById(R.id.dateTaken);
				name.setText(names.get(position));
				time.setText(getDateTime("yy-MM-dd HH:mm:ss", dateTaken.get(position)));
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

		for(String name:cursor.getColumnNames()){
			System.out.println(name);
		}
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

			dateTaken.add(cursor.getLong(cursor.getColumnIndex(
					MediaStore.Images.Media.DATE_TAKEN)));
		}
	}

	public static String getDateTime(String pattern, long mseconds){
		SimpleDateFormat date = new SimpleDateFormat(pattern);
		return date.format(new Date(mseconds));
	}
}
