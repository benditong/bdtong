package com.zykj.benditong.activity;

import org.apache.http.Header;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;
import com.pingplusplus.android.PaymentActivity;
import com.zykj.benditong.BaseActivity;
import com.zykj.benditong.BaseApp;
import com.zykj.benditong.R;
import com.zykj.benditong.http.AbstractHttpHandler;
import com.zykj.benditong.http.HttpUtils;
import com.zykj.benditong.model.Order;
import com.zykj.benditong.utils.Tools;
import com.zykj.benditong.view.MyCommonTitle;
import com.zykj.benditong.view.MyRequestDailog;

public class PayActivity extends BaseActivity {

	private MyCommonTitle myCommonTitle;
    public static final int REQUEST_CODE_PAYMENT = 1;
	
	private TextView order_name,order_price,user_wellet,pay_price,pay_alipay,pay_weixin;
	private ImageView sel_alipay,sel_weixin;
	private Button reserve_go;
	
	private Order order;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView(R.layout.ui_pay_activity);
		
		initView();
		addValues();
	}

	private void addValues() {
		order = (Order) getIntent().getSerializableExtra("order");
		String money = BaseApp.getModel().getMoney();
		
		order_name.setText(order.getTitle());
		order_price.setText(order.getInprice());
		user_wellet.setText(money);
		float shouldpay = Float.valueOf(order.getInprice())-Float.valueOf(money) > 0 ? Float.valueOf(order.getInprice())-Float.valueOf(money) : 0;
		pay_price.setText(shouldpay+"");
		sel_alipay.setSelected(true);
	}

	private void initView() {
		myCommonTitle = (MyCommonTitle)findViewById(R.id.aci_mytitle);
		myCommonTitle.setTitle("支付订单");
		
		order_name = (TextView)findViewById(R.id.order_name);//订单名称
		order_price = (TextView)findViewById(R.id.order_price);//订单价格
		user_wellet = (TextView)findViewById(R.id.user_wellet);//钱包余额
		pay_price = (TextView)findViewById(R.id.pay_price);//还需支付
		pay_alipay = (TextView)findViewById(R.id.pay_alipay);//支付宝支付
		pay_weixin = (TextView)findViewById(R.id.pay_weixin);//微信支付
		pay_alipay.setText(Html.fromHtml("支付宝支付<br><small><font color=#707070>推荐有支付宝账户的用户使用</font></small>"));
		pay_weixin.setText(Html.fromHtml("微信支付<br><small><font color=#707070>推荐安装微信5.0及以上版本的使用</font></small>"));
		
		sel_alipay = (ImageView)findViewById(R.id.sel_alipay);//选择支付宝
		sel_weixin = (ImageView)findViewById(R.id.sel_weixin);//选择微信
		reserve_go = (Button)findViewById(R.id.reserve_go);//确认支付
		setListener(sel_alipay, sel_weixin, reserve_go);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.sel_alipay:
			sel_alipay.setSelected(true);
			sel_weixin.setSelected(false);
			break;
		case R.id.sel_weixin:
			sel_alipay.setSelected(false);
			sel_weixin.setSelected(true);
			break;
		case R.id.reserve_go:
			//reserve_go.setOnClickListener(null);
			RequestParams params = new RequestParams();
			params.put("channel", sel_alipay.isSelected()?"alipay":"wx");
			params.put("amount", order.getInprice());//订单金额
			params.put("ordernum", order.getOid());//订单编号
			params.put("subject", order.getTitle());//商品标题
			params.put("body", sel_alipay.isSelected()?"支付宝支付":"维信支付");//详情说明
			MyRequestDailog.showDialog(this, "");
			HttpUtils.orderPay(new AbstractHttpHandler() {
				@Override
				public void onJsonSuccess(JSONObject json) {
		            new PaymentTask().execute(json);
				}
				@Override
				public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
					MyRequestDailog.closeDialog();
	                Tools.toast(PayActivity.this, "支付失败");
				}
			}, params);;
			break;
		default:
			break;
		}
	}
	
    class PaymentTask extends AsyncTask<JSONObject, Void, String> {
        @Override
        protected void onPreExecute() {}
        @Override
        protected String doInBackground(JSONObject... pr) {
            JSONObject json = pr[0];
            return json.toString();
        }
        @Override
        protected void onPostExecute(String data) {
            Log.i("charge", data);
            Intent intent = new Intent();
            String packageName = getPackageName();
            ComponentName componentName = new ComponentName(packageName, packageName + ".wxapi.WXPayEntryActivity");
            intent.setComponent(componentName);
            intent.putExtra(PaymentActivity.EXTRA_CHARGE, data);
            startActivityForResult(intent, REQUEST_CODE_PAYMENT);
        }
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
        //支付页面返回处理
    	if (requestCode == REQUEST_CODE_PAYMENT) {
    		if (resultCode == Activity.RESULT_OK) {
    			MyRequestDailog.closeDialog();
    			String result = data.getExtras().getString("pay_result");
                Tools.toast(this, result.equals("success")?"支付成功":"支付失败");
    			/* 处理返回值
    			 * "success" - payment succeed
    			 * "fail"    - payment failed
    			 * "cancel"  - user canceld
    			 * "invalid" - payment plugin not installed
    			 *
    			 * 如果是银联渠道返回 invalid，调用 UPPayAssistEx.installUPPayPlugin(this); 安装银联安全支付控件。
    			 */
    			//String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
    			//String extraMsg = data.getExtras().getString("extra_msg"); // 错误信息
    		} else if (resultCode == Activity.RESULT_CANCELED) {
				MyRequestDailog.closeDialog();
                Tools.toast(this, "User canceled");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
				MyRequestDailog.closeDialog();
                Tools.toast(this, "An invalid Credential was submitted.");
            }
    	}
    }
}
