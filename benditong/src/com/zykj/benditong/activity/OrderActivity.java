package com.zykj.benditong.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;

import com.zykj.benditong.R;
import com.zykj.benditong.fragment.PurchaseFragment;
import com.zykj.benditong.fragment.ReserveFragment;
import com.zykj.benditong.utils.CommonUtils;
import com.zykj.benditong.view.SegmentView;
import com.zykj.benditong.view.SegmentView.onSegmentViewClickListener;

public class OrderActivity extends FragmentActivity implements onSegmentViewClickListener{
	
	private SegmentView order_seg;
    private PurchaseFragment purchaseFragment;//团购订单
    private ReserveFragment reserveFragment;//预定订单
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_order_activity);
		
		initView();
		initFragment();
	}
	
	/**
	 * 加载页面
	 */
	private void initView(){
		order_seg = (SegmentView)findViewById(R.id.order_seg);
		order_seg.setSegmentText("团购订单", 0);
		order_seg.setSegmentText("预定订单", 1);
		order_seg.setOnSegmentViewClickListener(this);
	}

	/**
	 * 初始化Fragment
	 */
	private void initFragment(){
		purchaseFragment = new PurchaseFragment();//团购订单
		reserveFragment = new ReserveFragment();//预定订单
        getSupportFragmentManager().beginTransaction().add(R.id.order_framelayout,purchaseFragment).
                add(R.id.order_framelayout,reserveFragment)
                .show(purchaseFragment).hide(reserveFragment).commit();
	}
	
	//退出操作
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		CommonUtils.exitkey(keyCode, this);
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onSegmentViewClick(View view, int position) {
		switch (position) {
		case 0:
			break;
		case 1:
			break;
		default:
			break;
		}
	}
}
