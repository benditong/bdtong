package com.zykj.benditong.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import com.zykj.benditong.R;

public class MySurfaceView extends SurfaceView implements Callback, Runnable {
	
	private SurfaceHolder mHolder;
	private Canvas mCanvas;
	
	/**
	 * 用于绘制的线程
	 */
	private Thread t;
	
	/**
	 * 线程的控制开关
	 */
	private boolean isRunning;
	
	/** 盘块的奖项 */
	private String[] mStrs = new String[]{"单反相机","IPAD","恭喜发财","IPHONE","服装一套","恭喜发财"};

	/** 盘块的图片 */
	private int[] mImages = new int[]{R.drawable.f011,R.drawable.f012,R.drawable.f013,R.drawable.f014,R.drawable.f015,R.drawable.f016};

	/** 与图片对应的bitmap数组 */
	private Bitmap[] mImagesBitmaps;

	private Bitmap mBgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg2);
	
	/** 盘块的颜色 */
	private int[] mColor = new int[]{0xFFFFC300,0xFFF17E01,0xFFFFC300,0xFFF17E01,0xFFFFC300,0xFFF17E01};
	
	private int mItemCount = 6;
	
	/** 绘制盘块的画笔 */
	private Paint mArcPaint;

	/** 绘制文本的画笔 */
	private Paint mTextPaint;

	private float mTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 20, getResources().getDisplayMetrics());

	/** 整个盘块的范围 */
	private RectF mRange = new RectF();

	/** 整个盘块的直径 */
	private int mRadius;

	/** 转盘的中心位置 */
	private int mCenter;

	/** 这里我们的padding直接以paddingLeft为准 */
	private int mPadding;

	/** 滚动的速度 */
	private double mSpeed = 0;

	/** volatile 线程的可见性 */
	private volatile int mStartAngle;

	/** 判断是否点击了停止按钮 */
	private boolean isShouldEnd;
	
	public MySurfaceView(Context context) {
		this(context, null);
	}

	public MySurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mHolder = getHolder();
		mHolder.addCallback(this);
		
		//可获得焦点
		setFocusable(true);
		setFocusableInTouchMode(true);
		//设置常量
		setKeepScreenOn(true);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int width = Math.min(getMeasuredWidth(), getMeasuredHeight());
		
		mPadding = getPaddingLeft();
		//直径
		mRadius = width - mPadding * 2;
		//中心点
		mCenter = width / 2;
		
		setMeasuredDimension(width, width);
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		
		//初始化绘制盘块的画笔
		mArcPaint = new Paint();
		mArcPaint.setAntiAlias(true);
		mArcPaint.setDither(true);
		
		//初始化绘制文字的画笔
		mTextPaint = new Paint();
		mTextPaint.setColor(0xffffffff);
		mTextPaint.setTextSize(mTextSize);

		//初始化绘制盘块的范围
		mRange = new RectF(mPadding, mPadding, mPadding+mRadius, mPadding+mRadius);
		
		//初始化图片
		mImagesBitmaps = new Bitmap[mItemCount];
		for (int i = 0; i < mItemCount; i++) {
			mImagesBitmaps[i] = BitmapFactory.decodeResource(getResources(), mImages[i]);
		}
		
		isRunning = true;
		t = new Thread(this);
		t.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		isRunning = false;
	}

	@Override
	public void run() {
		//不断进行绘制
		while(isRunning){
			long start = System.currentTimeMillis();
			draw();
			long end = System.currentTimeMillis();
			
			if(end - start < 50){
				try {
					Thread.sleep(50-(end-start));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void draw() {
		try {
			mCanvas = mHolder.lockCanvas();
			if(mCanvas != null){
				//draw something
				//绘制背景
				drawBg();
				//绘制盘块
				
				float tmpAngle = mStartAngle;
				float sweepAngle = 360 / mItemCount;
				for (int i = 0; i < mItemCount; i++) {
					mArcPaint.setColor(mColor[i]);
					//绘制盘块
					mCanvas.drawArc(mRange, tmpAngle, sweepAngle, true, mArcPaint);
					//绘制文本
					drawText(tmpAngle,sweepAngle,mStrs[i]);
					//绘制Icon
					drawIcon(tmpAngle, mImagesBitmaps[i]);
					
					tmpAngle += sweepAngle;
				}
				mStartAngle += mSpeed;
				
				//如果点击了停止按钮
				if(isShouldEnd){
					mSpeed -= 1;
				}
				if(mSpeed <= 0){
					mSpeed = 0;
					isShouldEnd = false;
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
		} finally {
			if(mCanvas != null){
				mHolder.unlockCanvasAndPost(mCanvas);
			}
		}
	}
	
	/**
	 * 点击启动旋转
	 */
	public void luckyStart(){
		mSpeed = 50;
		isShouldEnd = false;
	}
	
	public void luckyEnd(){
		isShouldEnd = true;
	}
	
	/**
	 * 转盘是否在旋转
	 * @return
	 */
	public boolean isStart(){
		return mSpeed != 0;
	}
	
	public boolean isShouldEnd(){
		return isShouldEnd;
	}

	/**
	 * 绘制Icon
	 * @param tmpAngle
	 * @param bitmap
	 */
	private void drawIcon(float tmpAngle, Bitmap bitmap) {
		//设置图片的宽度为直径的1/8
		int imgWidth = mRadius / 8;
		
		//Math.PI/180
		float angle = (float)((tmpAngle + 360/mItemCount/2)*Math.PI/180);

		int x = (int)(mCenter + mRadius/2/2*Math.cos(angle));
		int y = (int)(mCenter + mRadius/2/2*Math.sin(angle));
		
		//确定那个图片位置
		Rect rect = new Rect(x-imgWidth/2,y-imgWidth/2,x+imgWidth/2,y+imgWidth/2);
		
		mCanvas.drawBitmap(bitmap, null, rect, null);
	}

	/**
	 * 绘制每个盘块的文本
	 * @param tmpAngle
	 * @param sweepAngle
	 * @param string
	 */
	private void drawText(float tmpAngle, float sweepAngle, String str) {
		Path path = new Path();
		path.addArc(mRange, tmpAngle, sweepAngle);
		
		//利用水平偏移量让文字居中
		float textWidth = mTextPaint.measureText(str);
		int hOffset = (int)(mRadius * Math.PI/mItemCount/2 - textWidth/2);
		int vOffset = mRadius/2/6;//垂直偏移量
		mCanvas.drawTextOnPath(str, path, hOffset, vOffset, mTextPaint);
	}

	private void drawBg() {
		mCanvas.drawColor(0xFFFFFFFF);
		mCanvas.drawBitmap(mBgBitmap, null, new Rect(mPadding/2, mPadding/2, getMeasuredWidth()-mPadding/2, getMeasuredHeight()-mPadding/2), null);
	}
}

























