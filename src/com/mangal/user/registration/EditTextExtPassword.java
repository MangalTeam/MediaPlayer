package com.mangal.user.registration;

import com.mangal.mediaplayer.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.EditText;

public class EditTextExtPassword extends EditText {
	private Bitmap bitmap;

	public EditTextExtPassword(Context context, AttributeSet attrs) {
		super(context, attrs);
		bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.user_input_password);
	}

	@Override
	protected void onDraw(Canvas canvas) {

		canvas.drawBitmap(bitmap, (getHeight() - bitmap.getHeight()) / 2, (getHeight() - bitmap.getHeight()) / 2,
				new Paint());

		super.onDraw(canvas);
	}
}
