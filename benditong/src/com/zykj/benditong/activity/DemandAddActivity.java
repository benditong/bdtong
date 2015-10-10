package com.zykj.benditong.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.R;
import com.zykj.benditong.http.HttpErrorHandler;
import com.zykj.benditong.http.HttpUtils;
import com.zykj.benditong.http.UrlContants;
import com.zykj.benditong.utils.StringUtil;
import com.zykj.benditong.utils.TextUtil;
import com.zykj.benditong.utils.Tools;
import com.zykj.benditong.view.MyCommonTitle;
import com.zykj.benditong.view.MyRequestDailog;
import com.zykj.benditong.view.SegmentView;
import com.zykj.benditong.view.SegmentView.onSegmentViewClickListener;
import com.zykj.benditong.view.UIDialog;

public class DemandAddActivity extends BaseActivity implements onSegmentViewClickListener{

	private MyCommonTitle myCommonTitle;
	private SegmentView demand_type;
	private ImageView demand_image;
	private EditText demand_title,demand_caller,demand_tel,demand_info;
	private Button demand_submit;
	private int type;
	private File file;
	private String title, caller, tel, info;

	private String timeString;//上传头像的字段
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView(R.layout.ui_demand_add);
		
		type = getIntent().getIntExtra("type", 0);
		initView();
	}

	/**
	 * 加载页面
	 */
	private void initView() {
		myCommonTitle = (MyCommonTitle)findViewById(R.id.aci_mytitle);
		myCommonTitle.setTitle("信息发布");

		demand_type = (SegmentView)findViewById(R.id.demand_type);//供求
		demand_type.setSegmentText("供应", 0);
		demand_type.setSegmentText("求购", 1);
		demand_type.setSegmentStatus(type);
		demand_type.setBackgroundResource(R.drawable.demand_tab_left, R.drawable.demand_tab_right);
		demand_type.setTextColor(R.drawable.demand_tab_color);
		demand_type.setOnSegmentViewClickListener(this);
		demand_image = (ImageView)findViewById(R.id.demand_image);//图片
		demand_title = (EditText)findViewById(R.id.demand_title);//标题
		demand_caller = (EditText)findViewById(R.id.demand_caller);//联系人
		demand_tel = (EditText)findViewById(R.id.demand_tel);//联系电话
		demand_info = (EditText)findViewById(R.id.demand_info);//信息描述
		demand_submit = (Button)findViewById(R.id.demand_submit);//提交
		
		setListener(demand_image, demand_submit);
	}

	/**
	 * 切换信息分类
	 */
	@Override
	public void onSegmentViewClick(View v, int position) {
		switch (position) {
		case 0:type = 0;break;
		case 1:type = 1;break;
		default:break;
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.demand_image:
			UIDialog.ForThreeBtn(this, new String[]{"拍照", "从相册中选取", "取消"}, this);
			break;
		case R.id.demand_submit:
			title = StringUtil.toString(demand_title.getText());
			caller = StringUtil.toString(demand_caller.getText());
			tel = StringUtil.toString(demand_tel.getText());
			info = StringUtil.toString(demand_info.getText());
			if(StringUtil.isEmpty(title)){
				Tools.toast(this, "标题不能为空!");
			}else if(file == null){
				Tools.toast(this, "请上传图片!");
			}else if(StringUtil.isEmpty(caller)){
				Tools.toast(this, "联系人不能为空!");
			}else if(TextUtil.isPhone(tel)){
				Tools.toast(this, "手机号格式不对!");
			}else if(StringUtil.isEmpty(info)){
				Tools.toast(this, "描述不能为空!");
			}else{
				try {
					MyRequestDailog.showDialog(this, "");
					RequestParams params = new RequestParams();
					params.put("imgsrc", file);
					HttpUtils.uploadone(res_uploadone, params);//上传图片
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
			break;
		case R.id.dialog_modif_1:
			/*拍照*/
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
		case R.id.dialog_modif_2:
			/*从相册中选取*/
			UIDialog.closeDialog();
			Intent photoIntent = new Intent(Intent.ACTION_PICK, null);
			photoIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
			startActivityForResult(photoIntent, 1);
			break;
		case R.id.dialog_modif_3:
			UIDialog.closeDialog();
			break;
		default:
			break;
		}
	}
	
	private AsyncHttpResponseHandler res_uploadone = new HttpErrorHandler() {
		@Override
		public void onRecevieSuccess(JSONObject json) {
			String imgsrc = json.getJSONObject(UrlContants.jsonData).getJSONObject("imgsrc").getString("imgsrc");
			RequestParams params = new RequestParams();
			params.put("type", type+1);
			params.put("imgsrc[]", imgsrc);
			params.put("title", title);
			params.put("name", caller);
			params.put("mobile", tel);
			params.put("intro", info);
			HttpUtils.submitSupplyDemandInfo(new HttpErrorHandler() {
				@Override
				public void onRecevieSuccess(JSONObject json) {
					Tools.toast(DemandAddActivity.this, "信息发布成功");
					finish();
				}
			}, params);
		}
	};
	
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
			/*如果是调用相机拍照，图片设置名字和路径*/
			File temp = new File(Environment.getExternalStorageDirectory().getPath() + "/DCIM/Camera/" + timeString + ".jpg");
			startPhotoZoom(Uri.fromFile(temp));
			break;
		case 3:
			/*取得裁剪后的图片*/
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
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
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
		file = new File(filename);
		FileOutputStream fOut = null;
		try {
			file.createNewFile();
			fOut = new FileOutputStream(file);
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
		demand_image.setImageBitmap(bitmap);
		//uploadAvatar(file);
	}
}
