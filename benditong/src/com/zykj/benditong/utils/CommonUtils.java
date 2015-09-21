package com.zykj.benditong.utils;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zykj.benditong.BaseApp;
import com.zykj.benditong.R;
import com.zykj.benditong.adapter.NumericWheelAdapter;
import com.zykj.benditong.adapter.OnWheelChangedListener;
import com.zykj.benditong.view.WheelView;

public class CommonUtils {

	private static final double EARTH_RADIUS = 6378137;//地球半径
	/**
	* 动态设置ListView的高度
	* @param listView
	*/
	public static void setListViewHeightBasedOnChildren(ListView listView) { 
	    if(listView == null) return;

	    ListAdapter listAdapter = listView.getAdapter(); 
	    if (listAdapter == null) { 
	        // pre-condition 
	        return; 
	    } 

	    int totalHeight = 0; 
	    for (int i = 0; i < listAdapter.getCount(); i++) { 
	        View listItem = listAdapter.getView(i, null, listView); 
	        listItem.measure(0, 0); 
	        totalHeight += listItem.getMeasuredHeight(); 
	    } 

	    ViewGroup.LayoutParams params = listView.getLayoutParams(); 
	    params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1)); 
	    listView.setLayoutParams(params); 
	}
	
	private static double rad(double d){
		return d * Math.PI / 180.0;
	}
	
	/**
	 * 根据两个经纬度计算距离
	 * @param lat1
	 * @param lng1
	 * @param lat2
	 * @param lng2
	 * @return
	 */
	public static double GetDistance(double lat1, double lng1, double lat2, double lng2){
	   double radLat1 = rad(lat1);
	   double radLat2 = rad(lat2);
	   double a = radLat1 - radLat2;
	   double b = rad(lng1) - rad(lng2);

	   double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +
	   Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
	   s = s * EARTH_RADIUS;
	   s = Math.round(s * 10000) / 10000;
	   return s;
	}
	
	public static boolean CheckLogin(){
		return !StringUtil.isEmpty(BaseApp.getModel().getUserid());
	}
	
	public static void showPic(String name,ImageView imageview){
		if(!StringUtil.isEmpty(name)){
			ImageLoader.getInstance().displayImage(name, imageview);
		}
	}
	
	//退出操作
	public static void exitkey(int keyCode, Context context) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {// 返回按钮
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setTitle("提示").setMessage("您确定退出当前应用").setNegativeButton("取消", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			}).setPositiveButton("确定", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					try {
						// 判断是否存在临时创建的文件
						File temp_file = new File(Environment.getExternalStorageDirectory() + File.separator + "heyi_dir");
						Tools.Log("文件是否存在：" + temp_file.exists());
						if (temp_file.exists()) {
							File[] file_detail = temp_file.listFiles();
							for (File file_del : file_detail) {
								file_del.delete();
							}
							temp_file.delete();
						}
					} catch (Exception e) {
					}
					System.exit(0);
				}
			}).setOnCancelListener(new DialogInterface.OnCancelListener() {
				public void onCancel(DialogInterface dialog) {
					dialog.dismiss();
				}

			}).show();
		}
	}
	

	public static void showShare(Context context, String title, String content, String url) {
		 ShareSDK.initSDK(context);
		 OnekeyShare oks = new OnekeyShare();
		 //关闭sso授权
		 oks.disableSSOWhenAuthorize(); 
		 // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		 oks.setTitle(title);
		 // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		 oks.setTitleUrl(url);
		 // text是分享文本，所有平台都需要这个字段
		 oks.setText(content);
		 // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		 oks.setImagePath("http://dashboard.mob.com/Uploads/db95c30283c2aa827e6831170d70808d.png");//确保SDcard下面存在此张图片
		 // url仅在微信（包括好友和朋友圈）中使用
		 oks.setUrl(url);
		 // comment是我对这条分享的评论，仅在人人网和QQ空间使用
		 oks.setComment("content");
		 // site是分享此内容的网站名称，仅在QQ空间使用
		 oks.setSite(title);
		 // siteUrl是分享此内容的网站地址，仅在QQ空间使用
		 oks.setSiteUrl(url);

		// 启动分享GUI
		 oks.show(context);
	}
	
	/**
	 * @param context
	 * @param tv_time
	 * 调用时间控件,格式:yyyy-MM-dd HH:mm
	 */
	public static void showDateTimePicker(Context context, final TextView tv_time){
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DATE);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);

		// 添加大小月月份并将其转换为list,方便之后的判断
		String[] months_big = { "1", "3", "5", "7", "8", "10", "12" };
		String[] months_little = { "4", "6", "9", "11" };

		final List<String> list_big = Arrays.asList(months_big);
		final List<String> list_little = Arrays.asList(months_little);

		final Dialog dialog = new Dialog(context);
		dialog.setTitle("请选择日期与时间");
		// 找到dialog的布局文件
		LayoutInflater inflater = (LayoutInflater)context.getSystemService("layout_inflater");
		View view = inflater.inflate(R.layout.time_layout, null);

		// 年
		final WheelView wv_year = (WheelView) view.findViewById(R.id.year);
		wv_year.setAdapter(new NumericWheelAdapter(1990, 2100));// 设置"年"的显示数据
		wv_year.setCyclic(true);// 可循环滚动
		wv_year.setLabel("年");// 添加文字
		wv_year.setCurrentItem(year - 1990);// 初始化时显示的数据

		// 月
		final WheelView wv_month = (WheelView) view.findViewById(R.id.month);
		wv_month.setAdapter(new NumericWheelAdapter(1, 12));
		wv_month.setCyclic(true);
		wv_month.setLabel("月");
		wv_month.setCurrentItem(month);

		// 日
		final WheelView wv_day = (WheelView) view.findViewById(R.id.day);
		wv_day.setCyclic(true);
		// 判断大小月及是否闰年,用来确定"日"的数据
		if (list_big.contains(String.valueOf(month + 1))) {
			wv_day.setAdapter(new NumericWheelAdapter(1, 31));
		} else if (list_little.contains(String.valueOf(month + 1))) {
			wv_day.setAdapter(new NumericWheelAdapter(1, 30));
		} else {
			// 闰年
			if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
				wv_day.setAdapter(new NumericWheelAdapter(1, 29));
			else
				wv_day.setAdapter(new NumericWheelAdapter(1, 28));
		}
		wv_day.setLabel("日");
		wv_day.setCurrentItem(day - 1);

		// 时
		final WheelView wv_hours = (WheelView) view.findViewById(R.id.hour);
		wv_hours.setAdapter(new NumericWheelAdapter(0, 23));
		wv_hours.setCyclic(true);
		wv_hours.setCurrentItem(hour);

		// 分
		final WheelView wv_mins = (WheelView) view.findViewById(R.id.mins);
		wv_mins.setAdapter(new NumericWheelAdapter(0, 59, "%02d"));
		wv_mins.setCyclic(true);
		wv_mins.setCurrentItem(minute);

		// 添加"年"监听
		OnWheelChangedListener wheelListener_year = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int year_num = newValue + 1990;
				// 判断大小月及是否闰年,用来确定"日"的数据
				if (list_big.contains(String
						.valueOf(wv_month.getCurrentItem() + 1))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 31));
				} else if (list_little.contains(String.valueOf(wv_month
						.getCurrentItem() + 1))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 30));
				} else {
					if ((year_num % 4 == 0 && year_num % 100 != 0)
							|| year_num % 400 == 0)
						wv_day.setAdapter(new NumericWheelAdapter(1, 29));
					else
						wv_day.setAdapter(new NumericWheelAdapter(1, 28));
				}
			}
		};
		// 添加"月"监听
		OnWheelChangedListener wheelListener_month = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int month_num = newValue + 1;
				// 判断大小月及是否闰年,用来确定"日"的数据
				if (list_big.contains(String.valueOf(month_num))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 31));
				} else if (list_little.contains(String.valueOf(month_num))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 30));
				} else {
					if (((wv_year.getCurrentItem() + 1990) % 4 == 0 && (wv_year
							.getCurrentItem() + 1990) % 100 != 0)
							|| (wv_year.getCurrentItem() + 1990) % 400 == 0)
						wv_day.setAdapter(new NumericWheelAdapter(1, 29));
					else
						wv_day.setAdapter(new NumericWheelAdapter(1, 28));
				}
			}
		};
		wv_year.addChangingListener(wheelListener_year);
		wv_month.addChangingListener(wheelListener_month);

		// 根据屏幕密度来指定选择器字体的大小
		int textSize = 0;

		textSize = 28;

		wv_day.TEXT_SIZE = textSize;
		wv_hours.TEXT_SIZE = textSize;
		wv_mins.TEXT_SIZE = textSize;
		wv_month.TEXT_SIZE = textSize;
		wv_year.TEXT_SIZE = textSize;

		Button btn_sure = (Button) view.findViewById(R.id.btn_datetime_sure);
		Button btn_cancel = (Button) view
				.findViewById(R.id.btn_datetime_cancel);
		// 确定
		btn_sure.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 如果是个数,则显示为"02"的样式
				String parten = "00";
				DecimalFormat decimal = new DecimalFormat(parten);
				// 设置日期的显示
				 tv_time.setText((wv_year.getCurrentItem() + 1990) + "-"
				 + decimal.format((wv_month.getCurrentItem() + 1)) + "-"
				 + decimal.format((wv_day.getCurrentItem() + 1)) + " "
				 + decimal.format(wv_hours.getCurrentItem()) + ":"
				 + decimal.format(wv_mins.getCurrentItem()));

				dialog.dismiss();
			}
		});
		// 取消
		btn_cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});
		// 设置dialog的布局,并显示
		dialog.setContentView(view);
		dialog.show();
	}
}
