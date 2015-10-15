package com.zykj.benditong.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Spanned;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zykj.benditong.utils.ImageUtil;

public class ViewHolder {
	
	private SparseArray<View> mViews;
	private int mPosition;
	private View mConvertView;
	public View btn_sign_up;
	public TextView tv_orign;
	public TextView tv_destination;
	public TextView tv_departure_time;
	public TextView tv_remain_seats;
	public TextView tv_price;
	
	public ViewHolder(Context context, ViewGroup parent, int layoutId, int position){
		this.mPosition = position;
		this.mViews = new SparseArray<View>();
		mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
		mConvertView.setTag(this);
	}
	

	public ViewHolder() {
		// TODO Auto-generated constructor stub
	}


	public static ViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position){
		if(convertView == null){
			return new ViewHolder(context, parent, layoutId, position);
		}else{
			ViewHolder holder = (ViewHolder)convertView.getTag();
			holder.mPosition = position;
			return holder;
		}
	}
	
	/**
	 * 通过ViewId获得控件
	 * @param viewId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends View> T getView(int viewId){
		View view = mViews.get(viewId);
		if(view == null){
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T)view;
	}
	
	public int getPosition() {
		return mPosition;
	}

	public View getConvertView() {
		return mConvertView;
	}
	
	public ViewHolder setText(int viewId, String text){
		TextView tv = getView(viewId);
		tv.setText(text);
		return this;
	}
	
	public ViewHolder setText(int viewId, Spanned spanned){
		TextView tv = getView(viewId);
		tv.setText(spanned);
		return this;
	}
	
	public ViewHolder setText(int viewId, String text, int flag){
		TextView tv = getView(viewId);
		tv.setText(text);
		tv.getPaint().setFlags(flag);
		return this;
	}
	
	public ViewHolder setImageView(int viewId, int resId){
		ImageView view = getView(viewId);
		view.setImageResource(resId);
		return this;
	}
	
	public ViewHolder setImageView(int viewId, Bitmap bitmap){
		ImageView view = getView(viewId);
		view.setImageBitmap(bitmap);
		return this;
	}
	
	public ViewHolder setImageUrl(int viewId, String uri){
		ImageView view = getView(viewId);
		ImageLoader.getInstance().displayImage(uri, view);
		return this;
	}
	
	public ViewHolder setImageUrl(int viewId, String uri, float roundPx){
		ImageView view = getView(viewId);
        ImageUtil.displayImage2Circle(view, uri, roundPx, null);//图片
		return this;
	}
	
	public ViewHolder setVisibility(int viewId, boolean flag){
		getView(viewId).setVisibility(flag?View.VISIBLE:View.GONE);
		return this;
	}
	
	public ViewHolder setRating(int viewId, float value){
		RatingBar view = getView(viewId);
		view.setRating(value);
		return this;
	}


	public ViewHolder setButton(int viewId, String text, OnClickListener listener){
		Button view = getView(viewId);
		view.setText(text);
		view.setOnClickListener(listener);
		return this;
	}
}
