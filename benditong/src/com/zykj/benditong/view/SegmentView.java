package com.zykj.benditong.view;

import org.xmlpull.v1.XmlPullParser;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zykj.benditong.R;

public class SegmentView extends LinearLayout {
	
    private TextView textView1;
    private TextView textView2;
    private onSegmentViewClickListener listener;
    
    public SegmentView(Context context) {
        super(context);
        init();
    }
    
    public SegmentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
	
	public SegmentView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
        init();
	}
	
	private void init() {
//      this.setLayoutParams(new LinearLayout.LayoutParams(dp2Px(getContext(), 60), LinearLayout.LayoutParams.WRAP_CONTENT));
        textView1 = new TextView(getContext());
        textView2 = new TextView(getContext());
        textView1.setLayoutParams(new LayoutParams(0, LayoutParams.MATCH_PARENT, 1));
        textView2.setLayoutParams(new LayoutParams(0, LayoutParams.MATCH_PARENT, 1));
        textView1.setText("SEG1");
        textView2.setText("SEG2");
        XmlPullParser xrp = getResources().getXml(R.drawable.tab_seg_color);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(), xrp);
            textView1.setTextColor(csl);
            textView2.setTextColor(csl);
          } catch (Exception e) {
        }
        textView1.setGravity(Gravity.CENTER);
        textView2.setGravity(Gravity.CENTER);
        textView1.setPadding(3, 6, 3, 6);
        textView2.setPadding(3, 6, 3, 6);
        setSegmentTextSize(16);
        textView1.setBackgroundResource(R.drawable.tab_seg_left);
        textView2.setBackgroundResource(R.drawable.tab_seg_right);
        textView1.setSelected(true);
        this.removeAllViews();
        this.addView(textView1);
        this.addView(textView2);
        this.invalidate();
        
        textView1.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                if (textView1.isSelected()) {
                    return;
                }
                textView1.setSelected(true);
                textView2.setSelected(false);
                if (listener != null) {
                    listener.onSegmentViewClick(textView1, 0);
                }
            }
        });
        textView2.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                if (textView2.isSelected()) {
                    return;
                }
                textView2.setSelected(true);
                textView1.setSelected(false);
                if (listener != null) {
                    listener.onSegmentViewClick(textView2, 1);
                }
            }
        });
    }
    /** 
     * 设置字体大小 单位dip 
     * <p>2014年7月18日</p> 
     * @param dp 
     * @author RANDY.ZHANG 
     */  
    public void setSegmentTextSize(int dp) {  
        textView1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dp);  
        textView2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dp);  
    }  
      
    public void setOnSegmentViewClickListener(onSegmentViewClickListener listener) {  
        this.listener = listener;  
    }  
    
	/**
	 * 设置文字
	 * 2014年7月18日
	 * @param text
	 * @param position
	 * @author RANDY.ZHANG
	 */
	public void setSegmentText(CharSequence text, int position) {
		if (position == 0) {
			textView1.setText(text);
		}
		if (position == 1) {
			textView2.setText(text);
		}
	} 
  
	/** 
	 * 设置默认状态
	 * <p>2014年7月18日</p> 
	 * @param position 0默认左 1默认右
	 * @author RANDY.ZHANG 
	 */  
	public void setSegmentStatus(int position) {
	    if (position == 0) {
            textView1.setSelected(true);
            textView2.setSelected(false);
	    }
	    if (position == 1) {
            textView2.setSelected(true);
            textView1.setSelected(false);
	    }
	}
	
	public void setBackgroundResource(int leftResid, int RightResid){
        textView1.setBackgroundResource(leftResid);
        textView2.setBackgroundResource(RightResid);
	}
	
	public void setTextColor(int id){
        XmlPullParser xrp = getResources().getXml(id);
		try {
			ColorStateList csl = ColorStateList.createFromXml(getResources(), xrp);
            textView1.setTextColor(csl);
            textView2.setTextColor(csl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
      
    public static interface onSegmentViewClickListener{  
        /** 
         *  
         * <p>2014年7月18日</p> 
         * @param v 
         * @param position 0-左边 1-右边 
         * @author RANDY.ZHANG 
         */  
        public void onSegmentViewClick(View v,int position);  
    }  
}
