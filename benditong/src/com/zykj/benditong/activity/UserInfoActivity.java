package com.zykj.benditong.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
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
import com.zykj.benditong.utils.StringUtil;
import com.zykj.benditong.view.MyCommonTitle;
import com.zykj.benditong.view.MyDialog;
import com.zykj.benditong.view.RoundImageView;
import com.zykj.benditong.view.UIDialog;

public class UserInfoActivity extends BaseActivity{

	private String timeString;//上传头像的字段
	private MyCommonTitle myCommonTitle;
	private RoundImageView rv_me_avatar;
	private TextView add_name, mobile_number;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_user_info);
		
		initView();
		requestData();
	}

	/**
	 * 初始化页面
	 */
	private void initView() {
		myCommonTitle = (MyCommonTitle)findViewById(R.id.aci_mytitle);
		myCommonTitle.setTitle("修改信息");
		myCommonTitle.setEditTitle("保存");
		myCommonTitle.setLisener(this, this);

		rv_me_avatar = (RoundImageView)findViewById(R.id.rv_me_avatar);//头像
		add_name = (TextView)findViewById(R.id.add_name);//昵称
		mobile_number = (TextView)findViewById(R.id.mobile_number);//性别
		
		setListener(add_name, rv_me_avatar);
	}

	/**
	 * 请求服务器数据---首页
	 */
	private void requestData(){
		ImageLoader.getInstance().displayImage(BaseApp.getModel().getAvatar(), rv_me_avatar);//用户头像
		String name = BaseApp.getModel().getUsername();
		add_name.setHint(StringUtil.isEmpty(name)?"添加昵称":name);
		String mobile = BaseApp.getModel().getMobile();
		if(!StringUtil.isEmpty(mobile)){
			mobile_number.setText(mobile.substring(0, 3)+"****"+mobile.substring(mobile.length()-4));
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.aci_back_btn:
			/* 返回 */
			setResult(Activity.RESULT_OK);
			finish();
			break;
		case R.id.add_name:
			new MyDialog(this, R.style.MyDialog, "昵称", "确定", "取消",
					new MyDialog.DialogClickListener() {
						@Override
						public void onRightBtnClick(Dialog dialog) {
							dialog.dismiss();
						}
						@Override
						public void onLeftBtnClick(Dialog dialog) {
							EditText tv_edit = (EditText)dialog.findViewById(R.id.tv_edit);
							if(!StringUtil.isEmpty(tv_edit.getText().toString().trim())){
								add_name.setText(tv_edit.getText().toString().trim());
							}
							dialog.dismiss();
						}
					}).show();
			break;
		case R.id.aci_edit_btn:
			/* 保存 */
			break;
		case R.id.rv_me_avatar:
			UIDialog.ForThreeBtn(this, new String[]{"相册", "拍照", "取消"}, this);
			break;
		case R.id.dialog_modif_1:
			/*选择相册*/
			UIDialog.closeDialog();
			/**
			 * 刚开始，我自己也不知道ACTION_PICK是干嘛的，后来直接看Intent源码，
			 * 可以发现里面很多东西，Intent是个很强大的东西，大家一定仔细阅读下
			 */
			Intent photoIntent = new Intent(Intent.ACTION_PICK, null);
			/**
			 * 下面这句话，与其它方式写是一样的效果，如果：
			 * intent.setData(MediaStore.Images
			 * .Media.EXTERNAL_CONTENT_URI);
			 * intent.setType(""image/*");设置数据类型
			 * 如果朋友们要限制上传到服务器的图片类型时可以直接写如
			 * ："image/jpeg 、 image/png等的类型"
			 * 这个地方小马有个疑问，希望高手解答下：就是这个数据URI与类型为什么要分两种形式来写呀？有什么区别？
			 */
			photoIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
			startActivityForResult(photoIntent, 1);
			break;
		case R.id.dialog_modif_2:
			/*点击拍照*/
			UIDialog.closeDialog();
			/**
			 * 下面这句还是老样子，调用快速拍照功能，至于为什么叫快速拍照，大家可以参考如下官方
			 * 文档，you_sdk_path/docs/guide/topics/media/camera.html
			 * 我刚看的时候因为太长就认真看，其实是错的，这个里面有用的太多了，所以大家不要认为
			 * 官方文档太长了就不看了，其实是错的，这个地方小马也错了，必须改正
			 */
			Date date = new Date(System.currentTimeMillis());
			SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMddHHmmss", new Locale("zh", "CN"));
			timeString = dateFormat.format(date);
			createSDCardDir();
			Intent shootIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			shootIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory()
							+ "/DCIM/Camera", timeString + ".jpg")));
			startActivityForResult(shootIntent, 2);
			// 下面这句指定调用相机拍照后的照片存储的路径
			/*
			 * intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri
			 * .fromFile(new File(Environment
			 * .getExternalStorageDirectory()+"/DCIM",
			 * "testpic.jpg"))); startActivityForResult(intent, 2);
			 */
			break;
		case R.id.dialog_modif_3:
			UIDialog.closeDialog();
			break;
		default:
			break;
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
		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void createSDCardDir() {
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			// 创建一个文件夹对象，赋值为外部存储器的目录
			File sdcardDir = Environment.getExternalStorageDirectory();
			// 得到一个路径，内容是sdcard的文件夹路径和名字
			String path = sdcardDir.getPath() + "/DCIM/Camera";
			File path1 = new File(path);
			if (!path1.exists()) {
				// 若不存在，创建目录，可以在应用启动的时候创建
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
				}
			}, params);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
