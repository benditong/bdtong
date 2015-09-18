package com.zykj.benditong.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.BaseApp;
import com.zykj.benditong.R;
import com.zykj.benditong.adapter.CommonAdapter;
import com.zykj.benditong.adapter.ViewHolder;
import com.zykj.benditong.http.HttpErrorHandler;
import com.zykj.benditong.http.HttpUtils;
import com.zykj.benditong.http.UrlContants;
import com.zykj.benditong.model.Order;
import com.zykj.benditong.utils.StringUtil;
import com.zykj.benditong.utils.Tools;
import com.zykj.benditong.view.MyCommonTitle;
import com.zykj.benditong.view.MyRequestDailog;
import com.zykj.benditong.view.UIDialog;

public class AssessActivity extends BaseActivity{

	private Order order;
	private int type;
	private int index;
	private MyCommonTitle myCommonTitle;
	private RatingBar restaurant_star;
	private EditText comment_content;
	private GridView noScrollgridview;
	private List<Bitmap> images = new ArrayList<Bitmap>();
	private List<File> files = new ArrayList<File>();
	private String imgs="";
	private CommonAdapter<Bitmap> adapter;
	private String timeString;//上传头像的字段
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView(R.layout.ui_order_assess);
		order = (Order)getIntent().getSerializableExtra("order");
		type = getIntent().getIntExtra("type", 3);
		
		initView();
		requestData();
	}

	private void initView() {
		myCommonTitle = (MyCommonTitle)findViewById(R.id.aci_mytitle);
		myCommonTitle.setTitle("提交评价");

		images.add(BitmapFactory.decodeResource(getResources(), R.drawable.add_photo));
		restaurant_star = (RatingBar) findViewById(R.id.restaurant_star);
		comment_content = (EditText) findViewById(R.id.comment_content);
		noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
		noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new CommonAdapter<Bitmap>(this, R.layout.ui_simple_image, images) {
			@Override
			public void convert(ViewHolder holder, Bitmap bitmap) {
				LayoutParams pageParms = holder.getView(R.id.assess_image).getLayoutParams();
				pageParms.width = (Tools.M_SCREEN_WIDTH - 40) / 5;
				pageParms.height = (Tools.M_SCREEN_WIDTH - 40) / 5;
				holder.setImageView(R.id.assess_image, bitmap);
			}
		};
		noScrollgridview.setAdapter(adapter);
		noScrollgridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View convertView, int position, long checkedid) {
				if (position == 0) {
					if(files.size() < 4){
						UIDialog.ForThreeBtn(AssessActivity.this, new String[]{"拍照", "从相册中选取", "取消"}, AssessActivity.this);
					}else{
						Tools.toast(AssessActivity.this, "最多上传四张图片");
					}
				}
			}
		});
		setListener(findViewById(R.id.reserve_go));
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.dialog_modif_1:
			/*拍照*/
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
			break;
		case R.id.dialog_modif_2:
			/*从相册中选取*/
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
		case R.id.dialog_modif_3:
			UIDialog.closeDialog();
			break;
		case R.id.reserve_go:
			if(StringUtil.isEmpty(comment_content.getText().toString().trim())){
				Tools.toast(this, "评论不能为空");
				return;
			}
			if(restaurant_star.getRating() < 1){
				Tools.toast(this, "整体评价最低一星");
				return;
			}
			try {
				MyRequestDailog.showDialog(this, "");
				index = 0;imgs = "";
				if(files.size() < 1){
					submitComments();
				}else{
					RequestParams params = new RequestParams();
					params.put("imgsrc", files.get(index));
					HttpUtils.uploadone(res_uploadone, params);//上传图片
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}
	
	private AsyncHttpResponseHandler res_uploadone = new HttpErrorHandler() {
		@Override
		public void onRecevieSuccess(JSONObject json) {
			try {
				String imgsrc = json.getJSONObject(UrlContants.jsonData).getJSONObject("imgsrc").getString("imgsrc");
				imgs += "&imgsrc[]="+imgsrc;
				index++;
				if(index < files.size()){
					RequestParams params = new RequestParams();
					params.put("imgsrc", files.get(index));
					HttpUtils.uploadone(res_uploadone, params);
				}else{
					submitComments();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	};
	
	private void submitComments(){
		String mType = type == 3 ? "shop" : type == 0 ? "restaurant" : "hotel";
		String parameter = "";
		parameter += "&type="+mType;
		parameter += "&uid="+BaseApp.getModel().getUserid();
		parameter += "&tid="+order.getTid();
		parameter += "&oid="+order.getId();
		parameter += "&golds="+restaurant_star.getRating();
		parameter += "&content="+comment_content.getText().toString().trim();
		parameter += "&pid="+(StringUtil.toString(order.getPid(), ""));
		parameter += imgs;
		HttpUtils.postComments(res_postComments, parameter.toString());
	}
	
	/**
	 * 请求提交评论
	 */
	private AsyncHttpResponseHandler res_postComments = new HttpErrorHandler() {
		@Override
		public void onRecevieSuccess(JSONObject json) {
			MyRequestDailog.closeDialog();
			Tools.toast(AssessActivity.this, json.getString("message"));
		}
		@Override
		public void onRecevieFailed(String status, JSONObject json) {
			Tools.toast(AssessActivity.this, json.getString("message"));
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
		images.add(bitmap);
		files.add(f);
		adapter.notifyDataSetChanged();
	}
	
	private void requestData() {
		/** 评论列表 */
//		RequestParams params = new RequestParams();
//		params.put("type", type);//restaurant餐厅(餐厅);hotel酒店;shop店铺
//		params.put("tid", pid);//评价的商家ID编号
//		params.put("nowpage", nowpage);//当前页
//		params.put("perpage", PERPAGE);//每页数量
//		HttpUtils.getCommentsList(res_getCommentsList, params);
	}
}
