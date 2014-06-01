package com.mangal.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * An {@link android.widget.ImageView} layout that maintains a consistent width to height aspect ratio.
 */
public class DynamicHeightImageView extends ImageView {

    private double mHeightRatio;
    private double mImageHeight = 0;
    private double mImageWidth = 0;

    public DynamicHeightImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DynamicHeightImageView(Context context) {
        super(context);
    }

    public void setHeightRatio(double ratio) {
        if (ratio != mHeightRatio) {
            mHeightRatio = ratio;
            requestLayout();
        }
    }

    public double getHeightRatio() {
        return mHeightRatio;
    }
    
    

	/**
	 * 输入原图宽高，按给定的定值宽缩放长度
	 * 
	 * @param mImageWidth 原图的宽度
	 * @param mImageHeight 原图的高度
	 */
	public void setWH2Scale(double mImageWidth, double mImageHeight) {
		this.mImageWidth = mImageWidth;
		this.mImageHeight = mImageHeight;
		requestLayout();
	}

	@Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mHeightRatio > 0.0) {
            // set the image views size
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = (int) (width * mHeightRatio);
            setMeasuredDimension(width, height);
        }else if (mImageHeight!=0 && mImageWidth!=0) {
        	int width = MeasureSpec.getSize(widthMeasureSpec);
        	double scale = mImageWidth/width;
        	int height = (int) (mImageHeight/scale);
        	setMeasuredDimension(width, height);
		}
        else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
