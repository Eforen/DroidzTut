package net.obviam.droidz.model;

import net.obviam.droidz.MainGamePanel;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

public class Droid {

	private static final String TAG = Droid.class.getSimpleName();
	
	private Bitmap bitmap; // the actual bitmap
	private int x;   // the X coordinate
	private int y;   // the Y coordinate
	private boolean touched; // if droid is touched/picked up

	public Droid(Bitmap bitmap, int x, int y) {
		this.bitmap = bitmap;
		this.x = x;
		this.y = y;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	public boolean isTouched() {
		return touched;
	}
	
	public void setTouched(boolean touched) {
		this.touched = touched;
		Log.d(TAG, "Bitmap Touched: "+String.valueOf(touched));
	}
	
	public void draw(Canvas canvas) {
		canvas.drawBitmap(bitmap, x - (bitmap.getWidth()/2), y - (bitmap.getHeight()/2), null);
	}
	
	public void handleActionDown(int eventX, int eventY){
		if (eventX >= (x - bitmap.getWidth() / 2) && (eventX <= (x + bitmap.getWidth()/2))) {
			if (eventY >= (y - bitmap.getHeight() / 2) && (y <= (y + bitmap.getHeight() / 2))) {
				// droid touched
				setTouched(true);
			} else {
				setTouched(false);
			}
		} else {
			setTouched(false);
		}
	}
}
