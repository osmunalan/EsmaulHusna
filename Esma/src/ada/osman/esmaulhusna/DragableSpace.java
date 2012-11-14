package ada.osman.esmaulhusna;

import ada.osman.esmaulhusna.R;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class DragableSpace extends ViewGroup {
	private Scroller mScroller;
	private VelocityTracker mVelocityTracker;

	private int mScrollX = 0;
	private Context c;
	private float mLastMotionX;

	private static final String LOG_TAG = "DragableSpace";
	private static final int SNAP_VELOCITY = 500;
	private final static int TOUCH_STATE_REST = 0;

	private int mTouchState = TOUCH_STATE_REST;

	public DragableSpace(Context context) {
		super(context);
		mScroller = new Scroller(context);
		c = context;
		this.setLayoutParams(new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.FILL_PARENT));
	}

	public DragableSpace(Context context, AttributeSet attrs) {
		super(context, attrs);
		c = context;
		mScroller = new Scroller(context);
		this.setLayoutParams(new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.FILL_PARENT));
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (mVelocityTracker == null) {
			mVelocityTracker = VelocityTracker.obtain();
		}
		mVelocityTracker.addMovement(event);

		final int action = event.getAction();
		final float x = event.getX();

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			Log.i(LOG_TAG, "event : down");
			/*
			 * If being flinged and user touches, stop the fling. isFinished
			 * will be false if being flinged.
			 */
			if (!mScroller.isFinished()) {
				mScroller.abortAnimation();
			}

			// Remember where the motion event started
			mLastMotionX = x;
			break;

		case MotionEvent.ACTION_UP:
			Log.i(LOG_TAG, "event : up");
			// if (mTouchState == TOUCH_STATE_SCROLLING) {
			final VelocityTracker velocityTracker = mVelocityTracker;
			velocityTracker.computeCurrentVelocity(1000);
			int velocityX = (int) velocityTracker.getXVelocity();
			ViewFlipper vf = (ViewFlipper) findViewById(R.id.details);
			if (velocityX > SNAP_VELOCITY) {
				// ViewFlipper vf = (ViewFlipper) findViewById(R.id.details);
				// Set an animation from res/anim: I pick push left out

				vf.setAnimation(AnimationUtils.loadAnimation(c,
						R.anim.slide_right));
				// vf.showPrevious();
				MainActivity.setA(MainActivity.getA() - 1);
				showImage();

			} else if (velocityX < -SNAP_VELOCITY) {
				// Fling hard enough to move right
				// ViewFlipper vf = (ViewFlipper) findViewById(R.id.details);
				// Set an animation from res/anim: I pick push left out
				// int a = getId();
				vf.setAnimation(AnimationUtils.loadAnimation(c,
						R.anim.slide_left));
				// vf.showPrevious();
				MainActivity.setA(MainActivity.getA() + 1);
				showImage();
			}

			if (mVelocityTracker != null) {
				mVelocityTracker.recycle();
				mVelocityTracker = null;
			}
			// }
			mTouchState = TOUCH_STATE_REST;
			break;
		case MotionEvent.ACTION_CANCEL:
			Log.i(LOG_TAG, "event : cancel");
			mTouchState = TOUCH_STATE_REST;
		}
		mScrollX = this.getScrollX();

		return true;
	}

	public void showImage() {
		int a = MainActivity.getA();

		if (a <= 0)
			a = 99;
		else if (a >= 100)
			a = 1;
		MainActivity.setA(a);
		String uri = "drawable/i" + a;
		String uri2 = "string/n" + a;
		String uri3 = "string/s" + a;
		
		// int imageResource = R.drawable.icon;
		int imageResource = getResources().getIdentifier(uri, null,
				"ada.osman.esmaulhusna");

		ImageView imageView = (ImageView) findViewById(R.id.im1);
		Drawable image = getResources().getDrawable(imageResource);
		imageView.setImageDrawable(image);

		TextView t = (TextView) findViewById(R.id.syc);
		t.setText("" + a);

		int nameres = getResources().getIdentifier(uri2, null,
				"ada.osman.esmaulhusna");
		String name = getResources().getString(nameres);
		TextView t2 = (TextView) findViewById(R.id.baslik);
		t2.setText(name);

		nameres = getResources().getIdentifier(uri3, null,
				"ada.osman.esmaulhusna");
		String aciklama = getResources().getString(nameres);
		TextView t3 = (TextView) findViewById(R.id.aciklama);
		t3.setText(aciklama);
		((ScrollView) findViewById(R.id.scrollview)).scrollTo(0, 0);

	}

	/*
	 * public void fillBlanks(){ Log.d("habara","hubara"); ImageView imageView;
	 * String imageUri="drawable/i"; int imageResource =
	 * getResources().getIdentifier(imageUri+a, null, "ada.osman.esmaulhusna");
	 * Drawable image = getResources().getDrawable(imageResource);
	 * 
	 * imageView = (ImageView) findViewById(R.id.im1);
	 * imageView.setImageDrawable(image);
	 * 
	 * TextView t = (TextView) findViewById(R.id.hab1); String s[] =
	 * getStringResourceByName("string/s1").split("#"); t.setText(s[0]);
	 * 
	 * if(a%2==0) { ViewFlipper vf = (ViewFlipper) findViewById(R.id.details);
	 * // Set an animation from res/anim: I pick push left out vf.showNext(); }
	 * 
	 * }
	 */
	/*
	 * public String getStringResourceByName(String aString) {
	 * 
	 * int resId = getResources().getIdentifier(aString, "string",
	 * "ada.osman.esmaulhusna"); return getString(resId); }
	 */

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int childLeft = 0;

		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			final View child = getChildAt(i);
			if (child.getVisibility() != View.GONE) {
				final int childWidth = child.getMeasuredWidth();
				child.layout(childLeft, 0, childLeft + childWidth,
						child.getMeasuredHeight());
				childLeft += childWidth;
			}
		}

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		if (widthMode != MeasureSpec.EXACTLY) {
			throw new IllegalStateException("error mode.");
		}

		final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		if (heightMode != MeasureSpec.EXACTLY) {
			throw new IllegalStateException("error mode.");
		}

		// The children are given the same width and height as the workspace
		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
		}

	}

}
