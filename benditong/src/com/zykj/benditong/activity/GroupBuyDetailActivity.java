package com.zykj.benditong.activity;

import java.util.List;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.BaseApp;
import com.zykj.benditong.R;
import com.zykj.benditong.adapter.CommonAdapter;
import com.zykj.benditong.adapter.ViewHolder;
import com.zykj.benditong.http.EntityHandler;
import com.zykj.benditong.http.HttpErrorHandler;
import com.zykj.benditong.http.HttpUtils;
import com.zykj.benditong.http.UrlContants;
import com.zykj.benditong.model.Assess;
import com.zykj.benditong.model.Good;
import com.zykj.benditong.utils.CommonUtils;
import com.zykj.benditong.utils.ImageUtil;
import com.zykj.benditong.utils.Tools;
import com.zykj.benditong.view.AutoListView;
import com.zykj.benditong.view.MyShareAndStoreTitle;

public class GroupBuyDetailActivity extends BaseActivity {
	
	private Good good;
	private String shopId;
	private MyShareAndStoreTitle myCommonTitle;
	private TextView title_one,title_two,title_three;
	private TextView good_price,good_old_price,good_sell_num;
	private RatingBar restaurant_star;
	private ImageView header_background,img_store;
	private Button reserve_go;
	private AutoListView assess_list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView(R.layout.ui_groupbuy_detail);
		String goodId = getIntent().getStringExtra("goodId");
		shopId = getIntent().getStringExtra("shopId");
		
		initView();
		requestData(goodId);
	}
	
	private void requestData(String goodId) {
		HttpUtils.getGoods( new HttpErrorHandler() {
			@Override
			public void onRecevieSuccess(JSONObject json) {
				good = JSONObject.parseObject(json.getString(UrlContants.jsonData), Good.class);
				initializationDate();
			}
		}, goodId);
	}
	
	private void initView(){
		myCommonTitle = (MyShareAndStoreTitle)findViewById(R.id.aci_mytitle);
		myCommonTitle.setLisener(null, this);
		myCommonTitle.setTitle("团购详情");
		
		header_background = (ImageView)findViewById(R.id.header_background);//主图图片
		restaurant_star = (RatingBar)findViewById(R.id.restaurant_star);//评价星
		title_one = (TextView)findViewById(R.id.title_one);//有效期
		title_two = (TextView)findViewById(R.id.title_two);//使用时间
		title_three = (TextView)findViewById(R.id.title_three);//使用规则
		good_price = (TextView)findViewById(R.id.good_price);//现在
		good_old_price = (TextView)findViewById(R.id.good_old_price);//原价
		good_sell_num = (TextView)findViewById(R.id.good_sell_num);//已售数量
		reserve_go = (Button)findViewById(R.id.reserve_go);//立即抢购
		assess_list = (AutoListView)findViewById(R.id.assess_list);//评价列表
		img_store=(ImageView) findViewById(R.id.aci_store_btn);
		
		LayoutParams pageParms = header_background.getLayoutParams();
		pageParms.width = Tools.M_SCREEN_WIDTH;
		pageParms.height = Tools.M_SCREEN_WIDTH*2/5;
		
		setListener(reserve_go);
	}

	/**
	 * 请求餐厅、酒店、商铺商品
	 */
	private AsyncHttpResponseHandler res_getGoodsCommentsList = new EntityHandler<Assess>(Assess.class) {
		@Override
		public void onReadSuccess(List<Assess> list) {
			assess_list.setAdapter(new CommonAdapter<Assess>(GroupBuyDetailActivity.this, R.layout.ui_item_assess, list){
				@Override
				public void convert(ViewHolder holder, Assess assess) {
					holder.setText(R.id.res_assess_name, assess.getName())//
						.setRating(R.id.res_assess_star, Float.valueOf(assess.getGolds()))//
						.setText(R.id.res_assess_content, assess.getContent())//
						.setImageUrl(R.id.res_assess_img, assess.getAvatar(), 10f)//
						.setText(R.id.res_assess_time, assess.getAddtime());//
					GridView grid_images = holder.getView(R.id.grid_images);//
					SimpleAdapter adapt = new SimpleAdapter(GroupBuyDetailActivity.this, assess.getImglist(), 
							R.layout.ui_simple_image, new String[]{"imgsrc"}, new int[]{R.id.assess_image});
				 	adapt.setViewBinder(new ViewBinder(){
						@Override
						public boolean setViewValue(View view, Object data, String textRepresentation) { 
							if (view instanceof ImageView && data != null) {
			                    ImageView iv = (ImageView) view;
			                    LayoutParams pageParms = iv.getLayoutParams();
			    				pageParms.width = 80;
			    				pageParms.height = 80;
			    				ImageUtil.displayImage2Circle(iv, UrlContants.IMAGE_URL+data.toString(), 5f, null);
			                    return true;
			                }else{
				                return false;
			                }
						}  
				   	}); 
					grid_images.setAdapter(adapt);
				}
			});
		}
	};
	
	private void initializationDate(){
		ImageLoader.getInstance().displayImage(good.getImgsrc(), header_background);
		good_price.setText(Html.fromHtml("<big><big><font color=#25B6ED>"+good.getPrice()+"</font></big></big>元"));
		good_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
		good_old_price.setText("360元");
		good_sell_num.setText("已售1042");
		restaurant_star.setRating(3f);
		title_one.setText(good.getLasts());//有效期
		title_two.setText(good.getUsetime());//使用时间
		title_three.setText(good.getGuize());//使用规则
		RequestParams params = new RequestParams();
		params.put("tid", shopId);//商铺Id
		params.put("pid", good.getId());//产品Id
		
		HttpUtils.getGoodsCommentsList(res_getGoodsCommentsList, params);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.reserve_go:
			if(!CommonUtils.CheckLogin()){Tools.toast(this, "请先登录!");return;}
			startActivity(new Intent(GroupBuyDetailActivity.this, GroupBuyInputActivity.class)
				.putExtra("tid", shopId).putExtra("pid", good.getId())
				.putExtra("inprice", good.getPrice()).putExtra("goodname", good.getTitle()));
			break;
		case R.id.aci_store_btn:
			if(!CommonUtils.CheckLogin()){Tools.toast(this, "请先登录！");return;}
			RequestParams params=new RequestParams();
			params.put("uid", BaseApp.getModel().getUserid());
			params.put("type", good.getType());
			params.put("pid", good.getId());
			HttpUtils.addCollection(new HttpErrorHandler() {
				
				@Override
				public void onRecevieSuccess(JSONObject json) {
					Tools.toast(GroupBuyDetailActivity.this, "添加收藏成功");
					img_store.setImageResource(R.drawable.my_store_select);
				}
				@Override
				public void onRecevieFailed(String status, JSONObject json) {
					Tools.toast(GroupBuyDetailActivity.this, "添加收藏成功");
				}
			}, params);
		default:
			break;
		}
	}
}
