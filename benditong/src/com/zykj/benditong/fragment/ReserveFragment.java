package com.zykj.benditong.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.zykj.benditong.R;

/**
 * @author Administrator
 * 团购订单分类
 */
public class ReserveFragment extends Fragment{
    
    private RadioGroup tab_reserve;
    private ReserveListFragment reserveFragment1,reserveFragment2;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ui_order_reserve, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        initview();
		initFragment();
    }

    /**
     * 初始化页面
     */
    public void initview() {
    	tab_reserve = (RadioGroup)getView().findViewById(R.id.tab_order);

    	reserveFragment1 = ReserveListFragment.getInstance(0);//餐厅预订
    	reserveFragment2 = ReserveListFragment.getInstance(1);//酒店预订

    	tab_reserve.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == R.id.reserve_tab1) {
					getActivity().getSupportFragmentManager().beginTransaction().show(reserveFragment1).hide(reserveFragment2).commit();
				} else if (checkedId == R.id.reserve_tab2) {
					getActivity().getSupportFragmentManager().beginTransaction().hide(reserveFragment1).show(reserveFragment2).commit();
				}
			}
		});
    }

	/**
	 * 请求服务器数据---首页
	 */
	private void initFragment(){
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.reserve_framelayout,reserveFragment1).
	        add(R.id.reserve_framelayout,reserveFragment2)
	        .show(reserveFragment1).hide(reserveFragment2).commit();
	}
}
