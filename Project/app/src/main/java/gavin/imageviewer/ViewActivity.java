package gavin.imageviewer;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

public class ViewActivity extends Activity{

	final int FLIP_DISTANCE = 50;
	private GestureDetector detector;
	private ImageSwitcher switcher;
	private ImageBundle images;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view);

		images = (ImageBundle)getIntent().getSerializableExtra("ImageBundle");

		switcher = (ImageSwitcher)findViewById(R.id.switcher);
		switcher.setFactory(new ViewSwitcher.ViewFactory() {
			@Override
			public View makeView() {
				LayoutInflater inflater = LayoutInflater.from(ViewActivity.this);
				View view = inflater.inflate(R.layout.image, null);
				ImageView imageView = (ImageView)view.findViewById(R.id.imageView);
				DisplayMetrics dm = ViewActivity.this.getResources().getDisplayMetrics();
				imageView.setMinimumHeight(dm.heightPixels);
                return view;
			}
		});
		switcher.setImageDrawable(images.getDrawable(ViewActivity.this.getResources()));

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
				if(e1.getX() - e2.getX() > FLIP_DISTANCE){
					System.out.println("From right to left");
					if(images.next()) {
						switcher.setInAnimation(ViewActivity.this, R.anim.slide_in_right);
						switcher.setOutAnimation(ViewActivity.this, R.anim.slide_out_left);
						switcher.setImageDrawable(images.getDrawable(ViewActivity.this.getResources()));
					}
					return true;
				}else if(e2.getX() - e1.getX() > FLIP_DISTANCE){
					System.out.println("From left to right");
					if(images.prev()) {
						switcher.setInAnimation(ViewActivity.this, R.anim.slide_in_left);
						switcher.setOutAnimation(ViewActivity.this, R.anim.slide_out_right);
						switcher.setImageDrawable(images.getDrawable(ViewActivity.this.getResources()));
					}
					return true;
				}else{
					return false;
				}
			}
		});
	}
	@Override
	public boolean onTouchEvent(MotionEvent e){
		return detector.onTouchEvent(e);
	}
}
