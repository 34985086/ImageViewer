package gavin.imageviewer;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.content.Intent;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.widget.ImageView;

import java.util.ArrayList;

public class ViewActivity extends Activity{

	private GestureDetector detector;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view);

		detector = new GestureDetector(this, new OnGestureListener() {
			@Override
			public boolean onDown(MotionEvent e) {
				System.out.println("onDown");
				return false;
			}

			@Override
			public void onShowPress(MotionEvent e) {
				System.out.println("onShowPress");
			}

			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				finish();
				return true;
			}

			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
				System.out.println("onScroll");
				return false;
			}

			@Override
			public void onLongPress(MotionEvent e) {
				System.out.println("onLongPress");
			}

			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
				System.out.println("onFling");
				return false;
			}
		});

		Intent intent = getIntent();
		ImageBundle imageBundle = (ImageBundle)intent.getSerializableExtra("ImageBundle");

		ImageView imageView = (ImageView) findViewById(R.id.imageView);
		imageView.setImageBitmap(BitmapFactory.decodeFile(
				imageBundle.getName(imageBundle.getPosition())));
	}

	@Override
	public boolean onTouchEvent(MotionEvent e){
		return detector.onTouchEvent(e);
	}
}
