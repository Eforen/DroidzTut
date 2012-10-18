package net.obviam.droidz;

import net.obviam.droidz.model.Droid;
import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainGamePanel extends SurfaceView implements
SurfaceHolder.Callback {
	private static final String TAG = MainGamePanel.class.getSimpleName();

	private MainThread thread;
	private Droid droid;

	public MainGamePanel(Context context) {
		super(context);
		// adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);

		// create droid and load bitmap
		droid = new Droid(BitmapFactory.decodeResource(getResources(), R.drawable.droid_1), 50, 50);

		// create the game loop thread
		thread = new MainThread(getHolder(), this);

		// make the GamePanel focusable so it can handle events
		setFocusable(true);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		thread.setRunning(true);
		thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// tell the thread to shut down and wait for it to finish
		// this is a clean shutdown
		boolean retry = true;
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// try again shutting down the thread
			}
		}
		Log.d(TAG, "Thread was shut down cleanly");
	}


	@Override
	public boolean onTouchEvent(MotionEvent event) {
		System.out.println("MainGamePanel.onTouchEvent(): "+event.toString());
		Log.d(TAG, event.toString());
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			// delegating event handling to the droid
			droid.handleActionDown((int)event.getX(), (int)event.getY());
			
			// check if in the lower part of the screen we exit
			if (event.getY() > getHeight() - 50) {
				thread.setRunning(false);
				((Activity)getContext()).finish();
			} else {
				Log.d(TAG, "Coords: x=" + event.getX() + ",y=" + event.getY());
			}
		}
		if (event.getAction() == MotionEvent.ACTION_MOVE) {
			Log.d(TAG, "ACTION_MOVE");
			// the gestures
			if (droid.isTouched()) {
				// the droid was picked up and is being dragged
				droid.setX((int)event.getX());
				droid.setY((int)event.getY());
			}
		}
		if (event.getAction() == MotionEvent.ACTION_UP) {
			Log.d(TAG, "ACTION_UP");
			// touch was released
			if (droid.isTouched()) {
				droid.setTouched(false);
			}
		}

		return super.onTouchEvent(event);
	}


	@Override
	protected void onDraw(Canvas canvas) {
		//canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.droid_1), 10, 10, null);
		canvas.drawColor(Color.BLACK);
		droid.draw(canvas);
	}

}