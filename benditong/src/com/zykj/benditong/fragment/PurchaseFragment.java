package com.zykj.benditong.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.zykj.benditong.R;

/**
 * @author Administrator
 * 团购订单分类
 */
public class PurchaseFragment extends Fragment implements OnCheckedChangeListener,OnPageChangeListener{

    private RadioGroup tab_order;
    private ViewPager order_viewpager;
	private PurchaseListFragment[] fragments;
	private FragmentPagerAdapter mPagerAdapter;
	private RadioButton order_tab1,order_tab2,order_tab3,order_tab4;
    private PurchaseListFragment orderFragment1,orderFragment2,orderFragment3,orderFragment4;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ui_order_purchase, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        initview();
    }

    /**
     * 初始化页面
     */
    public void initview() {
    	tab_order = (RadioGroup)getView().findViewById(R.id.tab_order);
    	tab_order.setOnCheckedChangeListener(this);
    	order_tab1 = (RadioButton)getView().findViewById(R.id.order_tab1);
    	order_tab2 = (RadioButton)getView().findViewById(R.id.order_tab2);
    	order_tab3 = (RadioButton)getView().findViewById(R.id.order_tab3);
    	order_tab4 = (RadioButton)getView().findViewById(R.id.order_tab4);
    	
    	order_viewpager = (ViewPager)getView().findViewById(R.id.order_viewpager);

    	orderFragment1 = PurchaseListFragment.getInstance(0);//未付款
    	orderFragment2 = PurchaseListFragment.getInstance(1);//未消费
    	orderFragment3 = PurchaseListFragment.getInstance(2);//已消费
    	orderFragment4 = PurchaseListFragment.getInstance(3);//已退款
    	
    	fragments = new PurchaseListFragment[]{orderFragment1,orderFragment2,orderFragment3,orderFragment4};
    	mPagerAdapter = new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
			@Override
			public int getCount() { return fragments.length; }
			@Override
			public Fragment getItem(int position) { return fragments[position]; }
		};
		order_viewpager.setAdapter(mPagerAdapter);
		order_viewpager.setOnPageChangeListener(this);
    }

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.order_tab1:
			order_viewpager.setCurrentItem(0, true);
			break;
		case R.id.order_tab2:
			order_viewpager.setCurrentItem(1, true);
			break;
		case R.id.order_tab3:
			order_viewpager.setCurrentItem(2, true);
			break;
		case R.id.order_tab4:
			order_viewpager.setCurrentItem(3, true);
			break;
		default:
			break;
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {}

	@Override
	public void onPageSelected(int position) {
		switch (position) {
		case 0:
			order_tab1.setChecked(true);
			break;
		case 1:
			order_tab2.setChecked(true);
			break;
		case 2:
			order_tab3.setChecked(true);
			break;
		case 3:
			order_tab4.setChecked(true);
			break;
		}
	}
}
