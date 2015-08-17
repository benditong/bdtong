package com.zykj.benditong.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.BaseApp;
import com.zykj.benditong.R;
import com.zykj.benditong.http.HttpErrorHandler;
import com.zykj.benditong.http.HttpUtils;
import com.zykj.benditong.utils.CommonUtils;
import com.zykj.benditong.utils.StringUtil;
import com.zykj.benditong.utils.Tools;
import com.zykj.benditong.view.RoundImageView;
import com.zykj.benditong.view.UIDialog;

public class UserActivity extends BaseActivity {

	private String timeString;//上传头像的字段
	private RelativeLayout rl_me_top;
	private RoundImageView rv_me_avatar;
	private TextView tv_me_mobile,user_login,user_money,user_integral;
	private LinearLayout user_store,app_about,app_explain,reset_password,app_version;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView(R.layout.ui_user_activity);
		
		initView();
		requestData();
	}
	
	/**
	 * 加载页面
	 */
	private void initView(){
		rl_me_top = (RelativeLayout)findViewById(R.id.rl_me_top);//用户信息
		rv_me_avatar = (RoundImageView)findViewById(R.id.rv_me_avatar);//用户头像
		tv_me_mobile = (TextView)findViewById(R.id.tv_me_mobile);//用户手机
		user_login = (TextView)findViewById(R.id.user_login);//用户登录
		
		user_money = (TextView)findViewById(R.id.user_money);//我的钱包
		user_integral = (TextView)findViewById(R.id.user_integral);//积分

		user_store = (LinearLayout)findViewById(R.id.user_store);//我的收藏
		app_about = (LinearLayout)findViewById(R.id.app_about);//关于我们
		app_explain = (LinearLayout)findViewById(R.id.app_explain);//应用说明
		reset_password = (LinearLayout)findViewById(R.id.reset_password);//重置密码
		app_version = (LinearLayout)findViewById(R.id.app_version);//版本更新

		LayoutParams pageParms = rl_me_top.getLayoutParams();
		pageParms.width = Tools.M_SCREEN_WIDTH;
		pageParms.height = Tools.M_SCREEN_WIDTH * 10 / 29;

		LayoutParams imgParms = rv_me_avatar.getLayoutParams();
		imgParms.width = Tools.M_SCREEN_WIDTH * 2 / 11;
		imgParms.height = Tools.M_SCREEN_WIDTH * 2 / 11;
		
		setListener(user_login, rv_me_avatar, user_money, user_integral, user_store, app_about, app_explain, reset_password, app_version);
	}
	
	/**
	 * 请求服务器数据---首页
	 */
	private void requestData(){
		if(CommonUtils.CheckLogin()){
			tv_me_mobile.setVisibility(View.VISIBLE);
			rv_me_avatar.setVisibility(View.VISIBLE);
			user_login.setVisibility(View.GONE);
			rl_me_top.setOnClickListener(this);
			String avatar = BaseApp.getModel().getAvatar();
			tv_me_mobile.setText(StringUtil.isEmpty(BaseApp.getModel().getMobile())?BaseApp.getModel().getUsername():BaseApp.getModel().getMobile());//默认账户
			ImageLoader.getInstance().displayImage(avatar, rv_me_avatar);//用户头像
		}else{
			tv_me_mobile.setVisibility(View.GONE);
			rv_me_avatar.setVisibility(View.GONE);
			user_login.setVisibility(View.VISIBLE);
			rl_me_top.setOnClickListener(null);
			
			user_money.setText("￥0.00");//用户余额
			user_integral.setText("0");//用户资产
			rv_me_avatar.setImageResource(R.drawable.user_head_img);;//用户头像
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 1:
			/*如果是直接从相册获取*/
			try {
				startPhotoZoom(data.getData());
			} catch (Exception e) {
				Toast.makeText(this, "您没有选择任何照片", Toast.LENGTH_LONG).show();
			}
			break;
		case 2:
			/*如果是调用相机拍照时
			File temp = new File(Environment.getExternalStorageDirectory() + "/xiaoma.jpg");
			给图片设置名字和路径*/
			File temp = new File(Environment.getExternalStorageDirectory()
					.getPath() + "/DCIM/Camera/" + timeString + ".jpg");
			startPhotoZoom(Uri.fromFile(temp));
			break;
		case 3:
			/**
			 * 取得裁剪后的图片
			 * 非空判断大家一定要验证，如果不验证的话， 在剪裁之后如果发现不满意，要重新裁剪，丢弃
			 * 当前功能时，会报NullException，小马只 在这个地方加下，大家可以根据不同情况在合适的 地方做判断处理类似情况
			 */
			if (data != null) {
				setPicToView(data);
			}
			break;
		case 11:
			/*成功登陆之后*/
			if (data != null) {requestData();}
			break;
		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.user_login:
			/* 用户登录 */
			startActivityForResult(new Intent(this, UserLoginActivity.class), Activity.DEFAULT_KEYS_DISABLE);
			break;
		case R.id.rl_me_top:
			/* 用户信息 */
			break;
		case R.id.rv_me_avatar:
			/* 用户头像 */
			UIDialog.ForThreeBtn(this, new String[]{"相册", "拍照", "取消"}, this);
			break;
		case R.id.user_money:
			isLogin();
			break;
		case R.id.user_integral:
			isLogin();
			break;
		case R.id.user_store:
			isLogin();
			break;
		case R.id.app_about:
			break;
		case R.id.app_explain:
			break;
		case R.id.reset_password:
			isLogin();
			break;
		case R.id.app_version:
			break;
		case R.id.dialog_modif_1:
			/*选择相册*/
			UIDialog.closeDialog();
			Intent photoIntent = new Intent(Intent.ACTION_PICK, null);
			photoIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
			startActivityForResult(photoIntent, 1);
			break;
		case R.id.dialog_modif_2:
			/*点击拍照*/
			UIDialog.closeDialog();
			Date date = new Date(System.currentTimeMillis());
			SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMddHHmmss", new Locale("zh", "CN"));
			timeString = dateFormat.format(date);
			createSDCardDir();
			Intent shootIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			shootIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory()
							+ "/DCIM/Camera", timeString + ".jpg")));
			startActivityForResult(shootIntent, 2);
			break;
		case R.id.dialog_modif_3:
			UIDialog.closeDialog();
			break;
		default:
			break;
		}
	}


	/**
	 * 裁剪图片方法实现
	 * @param uri
	 */
	public void createSDCardDir() {
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			File sdcardDir = Environment.getExternalStorageDirectory();
			String path = sdcardDir.getPath() + "/DCIM/Camera";
			File path1 = new File(path);
			if (!path1.exists()) {
				path1.mkdirs();
			}
		}
	}

	/**
	 * 裁剪图片方法实现
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {
		/**
		 * 至于下面这个Intent的ACTION是怎么知道的，大家可以看下自己路径下的如下网页
		 * yourself_sdk_path/docs/reference/android/content/Intent.html
		 * 直接在里面Ctrl+F搜：CROP ，之前小马没仔细看过，其实安卓系统早已经有自带图片裁剪功能, 是直接调本地库的，小马不懂C C++
		 * 这个不做详细了解去了，有轮子就用轮子，不再研究轮子是怎么 制做的了...吼吼
		 */
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 3);
	}

	/**
	 * 保存裁剪之后的图片数据
	 * @param picdata
	 */
	private void setPicToView(Intent picdata) {
		Bundle extras = picdata.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			//Drawable drawable = new BitmapDrawable(photo);
			/*下面注释的方法是将裁剪之后的图片以Base64Coder的字符方式上 传到服务器，QQ头像上传采用的方法跟这个类似*/
			savaBitmap(photo);
			// avatar_head_image.setBackgroundDrawable(drawable);
		}
	}

	/**
	 * 将剪切后的图片保存到本地图片上！
	 */
	public void savaBitmap(Bitmap bitmap) {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMddHHmmss", new Locale("zh", "CN"));
		String cutnameString = dateFormat.format(date);
		String filename = Environment.getExternalStorageDirectory().getPath() + "/" + cutnameString + ".jpg";
		File f = new File(filename);
		FileOutputStream fOut = null;
		try {
			f.createNewFile();
			fOut = new FileOutputStream(f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);// 把Bitmap对象解析成流
		try {
			fOut.flush();
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		rv_me_avatar.setImageBitmap(bitmap);
		uploadAvatar(f);
	}

	/**
	 * 更新服务器头像
	 */
	private void uploadAvatar(File file) {
		try {
			RequestParams params = new RequestParams();
			params.put("id", BaseApp.getModel().getUserid());
			params.put("imgURL", file);
			HttpUtils.postUserAvatar(new HttpErrorHandler() {
				@Override
				public void onRecevieSuccess(JSONObject json) {
					Tools.toast(UserActivity.this, "上传成功!");
				}
			}, params);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//退出操作
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		CommonUtils.exitkey(keyCode, this);
		return super.onKeyDown(keyCode, event);
	}
	
	private void isLogin(){
		if(!CommonUtils.CheckLogin()){ Tools.toast(this, "请先登录"); return; }
	}
}
