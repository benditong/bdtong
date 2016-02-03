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

import android.app.Activity;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.R;
import com.zykj.benditong.adapter.CommonAdapter;
import com.zykj.benditong.adapter.ViewHolder;
import com.zykj.benditong.http.HttpErrorHandler;
import com.zykj.benditong.http.HttpUtils;
import com.zykj.benditong.http.UrlContants;
import com.zykj.benditong.utils.StringUtil;
import com.zykj.benditong.utils.TextUtil;
import com.zykj.benditong.utils.Tools;
import com.zykj.benditong.view.MyCommonTitle;
import com.zykj.benditong.view.MyRequestDailog;
import com.zykj.benditong.view.PickDialog;
import com.zykj.benditong.view.PickDialog.PickDialogListener;
import com.zykj.benditong.view.SegmentView;
import com.zykj.benditong.view.SegmentView.onSegmentViewClickListener;
import com.zykj.benditong.view.UIDialog;

public class HouseAddActivity extends BaseActivity implements
		onSegmentViewClickListener {
	private MyCommonTitle myCommonTitle;
	private SegmentView rent_type;
	private GridView house_image;
	private EditText house_title, house_price, house_room, house_square,
			house_infloor, house_totalfloor, house_plot, house_address,
			house_intro, house_contacts, house_mobile;
	private TextView house_decoration;
	private Button btn_submit;
	private CommonAdapter<Bitmap> adapter;
	private List<Bitmap> images = new ArrayList<Bitmap>();
	private List<File> files = new ArrayList<File>();
	private String imgs = "";
	private String timeString;// 上传头像的字段
	private int type;
	private int index;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_house_add);
		type = getIntent().getIntExtra("type", 0);
		initView();
	}

	private void initView() {
		myCommonTitle = (MyCommonTitle) findViewById(R.id.aci_mytitle);
		myCommonTitle.setTitle("房产发布");
		/**
		 * 初始化控件
		 */
		rent_type = (SegmentView) findViewById(R.id.house_type);
		rent_type.setSegmentText("售房", 0);
		rent_type.setSegmentText("租房", 1);
		rent_type.setSegmentStatus(type);
		rent_type.setBackgroundResource(R.drawable.demand_tab_left,
				R.drawable.demand_tab_right);
		rent_type.setTextColor(R.drawable.demand_tab_color);
		rent_type.setOnSegmentViewClickListener(this);

		house_title = (EditText) findViewById(R.id.house_title);
		house_price = (EditText) findViewById(R.id.house_price);
		house_room = (EditText) findViewById(R.id.house_room);
		house_square = (EditText) findViewById(R.id.house_square);
		house_decoration = (TextView) findViewById(R.id.house_add_decoration);
		house_infloor = (EditText) findViewById(R.id.house_infloor);
		house_totalfloor = (EditText) findViewById(R.id.house_totalfloor);
		house_plot = (EditText) findViewById(R.id.house_plot);
		house_address = (EditText) findViewById(R.id.house_address);
		house_intro = (EditText) findViewById(R.id.house_intro);
		house_contacts = (EditText) findViewById(R.id.house_contacts);
		house_mobile = (EditText) findViewById(R.id.house_mobile);
       
//		house_decoration.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				AlertDialog.Builder builder = new AlertDialog.Builder(
//						HouseAddActivity.this);
//				builder.setIcon(R.drawable.ic_house);
//				
//				builder.setTitle("请选择装修情况");
//				// 指定下拉列表的显示数据
//				final String fitments[] = { "毛坯装修", "一般装修", "精装修", "豪华装修" };
//				// 设置一个下拉的列表选择项
//				builder.setItems(fitments,
//						new DialogInterface.OnClickListener() {
//							@Override
//							public void onClick(DialogInterface dialog,
//									int which) {
//								house_decoration.setText(fitments[which]);
//							}
//						});
//				builder.show();
//			}
//		});

		btn_submit = (Button) findViewById(R.id.house_submit);
		images.add(BitmapFactory.decodeResource(getResources(),
				R.drawable.add_photo));
		house_image = (GridView) findViewById(R.id.fc_add_house_image);
		house_image.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new CommonAdapter<Bitmap>(this, R.layout.ui_simple_image,
				images) {
			@Override
			public void convert(ViewHolder holder, Bitmap bitmap) {
				LayoutParams pageParms = holder.getView(R.id.assess_image)
						.getLayoutParams();
				pageParms.width = (Tools.M_SCREEN_WIDTH - 40) / 5;
				pageParms.height = (Tools.M_SCREEN_WIDTH - 40) / 5;
				holder.setImageView(R.id.assess_image, bitmap);
			}
		};
		house_image.setAdapter(adapter);
		house_image.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View convertView,
					int position, long checkedid) {
				if (position == 0) {
					if (files.size() < 5) {
						UIDialog.ForThreeBtn(HouseAddActivity.this,
								new String[] { "拍照", "从相册中选取", "取消" },
								HouseAddActivity.this);
					} else {
						Tools.toast(HouseAddActivity.this, "最多上传五张图片");
					}
				}
			}
		});
		setListener(btn_submit,house_decoration);
	}

	/**
	 * SegmentView整租、合租 切换
	 */
	@Override
	public void onSegmentViewClick(View view, int position) {
		switch (position) {
		case 0:
			type = 0;
			break;
		case 1:
			type = 1;
			break;
		default:
			break;
		}
	}

	@Override
	public void onClick(View view) {
		super.onClick(view);
		switch (view.getId()) {
		case R.id.dialog_modif_1:
			/* 拍照 */
			UIDialog.closeDialog();
			/**
			 * 下面这句还是老样子，调用快速拍照功能，至于为什么叫快速拍照，大家可以参考如下官方
			 * 文档，you_sdk_path/docs/guide/topics/media/camera.html
			 * 我刚看的时候因为太长就认真看，其实是错的，这个里面有用的太多了，所以大家不要认为
			 * 官方文档太长了就不看了，其实是错的，这个地方小马也错了，必须改正
			 */
			Date date = new Date(System.currentTimeMillis());
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"'IMG'_yyyyMMddHHmmss", new Locale("zh", "CN"));
			timeString = dateFormat.format(date);
			createSDCardDir();
			Intent shootIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			shootIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri
					.fromFile(new File(Environment
							.getExternalStorageDirectory() + "/DCIM/Camera",
							timeString + ".jpg")));
			startActivityForResult(shootIntent, 2);
			break;
		case R.id.dialog_modif_2:
			/* 从相册中选取 */
			UIDialog.closeDialog();
			/**
			 * 刚开始，我自己也不知道ACTION_PICK是干嘛的，后来直接看Intent源码，
			 * 可以发现里面很多东西，Intent是个很强大的东西，大家一定仔细阅读下
			 */
			Intent photoIntent = new Intent(Intent.ACTION_PICK, null);
			/**
			 * 下面这句话，与其它方式写是一样的效果，如果： intent.setData(MediaStore.Images
			 * .Media.EXTERNAL_CONTENT_URI); intent.setType(""image/*");设置数据类型
			 * 如果朋友们要限制上传到服务器的图片类型时可以直接写如 ："image/jpeg 、 image/png等的类型"
			 * 这个地方小马有个疑问，希望高手解答下：就是这个数据URI与类型为什么要分两种形式来写呀？有什么区别？
			 */
			photoIntent.setDataAndType(
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
			startActivityForResult(photoIntent, 1);
			break;
		case R.id.dialog_modif_3:
			UIDialog.closeDialog();
			break;
		case R.id.house_add_decoration:
			final List<String> lists=new ArrayList<String>();
			lists.add("毛坯装修");
			lists.add("一般装修");
			lists.add("精装修");
			lists.add("豪华装修");
			new PickDialog(HouseAddActivity.this, "请选择装修情况", lists, new PickDialogListener() {
				
				@Override
				public void onListItemClick(int position, String string) {
					house_decoration.setText(lists.get(position));
				}
			}).show();
			break;
		case R.id.house_submit:
			/**
			 * 设置控件的输入条件
			 */
			if (files.size() <= 0) {
				Tools.toast(this, "请上传图片");
			} else if (StringUtil.isEmpty(house_title.getText().toString()
					.trim())) {
				Tools.toast(this, "标题不能为空");
			} else if (StringUtil.isEmpty(house_price.getText().toString()
					.trim())) {
				Tools.toast(this, "价格不能为空");
			} else if (Integer
					.parseInt(house_price.getText().toString().trim()) < 0) {
				Tools.toast(this, "价格不能为负数");
			} else if (StringUtil.isEmpty(house_room.getText().toString()
					.trim())) {
				Tools.toast(this, "庁室不能为空");
			} else if (StringUtil.isEmpty(house_square.getText().toString()
					.trim())) {
				Tools.toast(this, "面积不能为空");
			} else if (Integer.parseInt(house_square.getText().toString()
					.trim()) <= 0) {
				Tools.toast(this, "面积不能为0或负数");
			} else if (StringUtil.isEmpty(house_decoration.getText().toString()
					.trim())) {
				Tools.toast(this, "内部装修不能为空");
			} else if (StringUtil.isEmpty(house_infloor.getText().toString()
					.trim())) {
				Tools.toast(this, "所在楼层不能为空");
			} else if (StringUtil.isEmpty(house_totalfloor.getText().toString()
					.trim())) {
				Tools.toast(this, "总楼层不能为空");
			} else if (Integer.parseInt(house_infloor.getText().toString()
					.trim()) > Integer.parseInt(house_totalfloor.getText()
					.toString().trim())) {
				Tools.toast(this, "房屋所在楼层数大于总楼层数,请重新输入");
			} else if (StringUtil.isEmpty(house_plot.getText().toString()
					.trim())) {
				Tools.toast(this, "小区名称不能为空");
			} else if (StringUtil.isEmpty(house_address.getText().toString()
					.trim())) {
				Tools.toast(this, "小区地址不能为空");
			} else if (StringUtil.isEmpty(house_intro.getText().toString()
					.trim())) {
				Tools.toast(this, "房源简介不能为空");
			} else if (StringUtil.isEmpty(house_contacts.getText().toString()
					.trim())) {
				Tools.toast(this, "联系人不能为空");
			} else if (!TextUtil.isMobile(house_mobile.getText().toString()
					.trim())) {
				Tools.toast(this, "手机格式不正确");
			} else {
				try {
					MyRequestDailog.showDialog(this, "");
					index = 0;
					imgs = "";
					RequestParams params = new RequestParams();
					params.put("imgsrc", files.get(index));
					HttpUtils.uploadone(res_uploadone, params);// 上传图片
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
			break;
		default:
			break;
		}
	}

	/**
	 * 单张图片上传
	 */
	private AsyncHttpResponseHandler res_uploadone = new HttpErrorHandler() {
		@Override
		public void onRecevieSuccess(JSONObject json) {
			try {
				String imgsrc = json.getJSONObject(UrlContants.jsonData)
						.getJSONObject("imgsrc").getString("imgsrc");
				imgs += "&imgsrc[]=" + imgsrc;
				index++;
				if (index < files.size()) {
					RequestParams params = new RequestParams();
					params.put("imgsrc", files.get(index));
					HttpUtils.uploadone(res_uploadone, params);
				} else {
					submitData();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	};

	private void submitData() {
		String parameter = "";
		parameter += "&title=" + house_title.getText().toString().trim();// 标题
		parameter += "&price=" + house_price.getText().toString().trim();// 价格
		parameter += "&tingshi=" + house_room.getText().toString().trim();// 厅室
		parameter += "&area=" + house_square.getText().toString().trim();// 面积
		parameter += "&fitment=" + house_decoration.getText().toString().trim();// 面积
		parameter += "&floor=" + house_infloor.getText().toString().trim();// 所在楼层
		parameter += "&allfloor="
				+ house_totalfloor.getText().toString().trim();// 总楼层数
		parameter += "&plot=" + house_plot.getText().toString().trim();// 小区名称
		parameter += "&plotaddress="
				+ house_address.getText().toString().trim();// 小区地址
		parameter += "&intro=" + house_intro.getText().toString().trim();// 房源简介
		parameter += "&type=" + (type + 1);// 租住方式：合租房：1，整租房：2
		parameter += "&name=" + house_contacts.getText().toString().trim();// 联系人
		parameter += "&mobile=" + house_mobile.getText().toString().trim();// 联系电话
		parameter += imgs;// 图片地址，上传好的图片路径
		HttpUtils.submitHouseInfo(new HttpErrorHandler() {
			@Override
			public void onRecevieSuccess(JSONObject json) {
				MyRequestDailog.closeDialog();
				Tools.toast(HouseAddActivity.this, "房源发布成功");
				setResult(Activity.RESULT_OK);
				finish();
			}

			@Override
			public void onRecevieFailed(String status, JSONObject json) {
				super.onRecevieFailed(status, json);
				Tools.toast(HouseAddActivity.this, "房源发布失败");
			}
		}, parameter.toString());
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 1:
			/* 如果是直接从相册获取 */
			try {
				startPhotoZoom(data.getData());
			} catch (Exception e) {
				Toast.makeText(this, "您没有选择任何照片", Toast.LENGTH_LONG).show();
			}
			break;
		case 2:
			/*
			 * 如果是调用相机拍照时 File temp = new
			 * File(Environment.getExternalStorageDirectory() + "/xiaoma.jpg");
			 * 给图片设置名字和路径
			 */
			File temp = new File(Environment.getExternalStorageDirectory()
					.getPath() + "/DCIM/Camera/" + timeString + ".jpg");
			startPhotoZoom(Uri.fromFile(temp));
			break;
		case 3:
			/**
			 * 取得裁剪后的图片 非空判断大家一定要验证，如果不验证的话， 在剪裁之后如果发现不满意，要重新裁剪，丢弃
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
	 * 
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
		intent.putExtra("aspectX", 0.6);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 250);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 3);
	}

	/**
	 * 保存裁剪之后的图片数据
	 * 
	 * @param picdata
	 */
	private void setPicToView(Intent picdata) {
		Bundle extras = picdata.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			// Drawable drawable = new BitmapDrawable(photo);
			/* 下面注释的方法是将裁剪之后的图片以Base64Coder的字符方式上 传到服务器，QQ头像上传采用的方法跟这个类似 */
			savaBitmap(photo);
			// avatar_head_image.setBackgroundDrawable(drawable);
		}
	}

	/**
	 * 将剪切后的图片保存到本地图片上！
	 */
	public void savaBitmap(Bitmap bitmap) {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMddHHmmss", new Locale("zh", "CN"));
		String cutnameString = dateFormat.format(date);
		String filename = Environment.getExternalStorageDirectory().getPath()
				+ "/" + cutnameString + ".jpg";
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
}
