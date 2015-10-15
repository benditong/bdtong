package com.zykj.benditong.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.zykj.benditong.R;
import com.zykj.benditong.utils.Tools;


/**
 * 自定义dialog
 * 
 * @author zhousheng
 * 
 */
public class MyDialog extends Dialog implements
		android.view.View.OnClickListener {
	private DialogClickListener listener;
	Context context;
	private TextView tv_title;
	private TextView dialog_textViewID;
	private TextView dialog_textViewID1;
	private String leftBtnText;
	private String rightBtnText;
	private String content;

	public MyDialog(Context context) {
		super(context);
		this.context = context;
	}

	/**
	 * 自定义dialog
	 * 
	 * @param context
	 * @param theme
	 *            主题
	 * @param content
	 *            主体文字
	 * @param leftBtnText
	 *            左按钮文字，若为""则隐藏
	 * @param rightBtnText
	 *            右按钮文字，若为""则隐藏
	 * @param listener
	 *            回调接口
	 */
	public MyDialog(Context context, int theme, String content,
			String leftBtnText, String rightBtnText,
			DialogClickListener listener) {
		super(context, theme);
		this.context = context;
		this.content = content;
		this.leftBtnText = leftBtnText;
		this.rightBtnText = rightBtnText;
		this.listener = listener;
	}

	public void setTextSize(int size) {
		dialog_textViewID.setTextSize(size);
		dialog_textViewID1.setTextSize(size);
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dialog_input);
		tv_title = (TextView) findViewById(R.id.tv_title);
		dialog_textViewID1 = (TextView) findViewById(R.id.dialog_textViewID1);
		dialog_textViewID = (TextView) findViewById(R.id.dialog_textViewID);
		dialog_textViewID.setOnClickListener(this);
		dialog_textViewID1.setOnClickListener(this);
		initView();
		initDialog(context);
	}

	/**
	 * 设置dialog的宽为屏幕的3分之1
	 * 
	 * @param context
	 */
	private void initDialog(Context context) {
//		setCanceledOnTouchOutside(false);
//		setOnKeyListener(new OnKeyListener() {
//			@Override
//			public boolean onKey(DialogInterface dialog, int keyCode,
//					KeyEvent event) {
//				if (keyCode == KeyEvent.KEYCODE_BACK
//						&& event.getRepeatCount() == 0) {
//					return true;
//				} else {
//					return false;
//				}
//			}
//		});
		//WindowManager windowManager = this.getWindow().getWindowManager();
		//Display display = windowManager.getDefaultDisplay();
		WindowManager.LayoutParams lp = this.getWindow().getAttributes();
		lp.width = (int) (Tools.M_SCREEN_WIDTH / 6 * 5); // 设置宽度
		this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		this.getWindow().setAttributes(lp);
	}

	private void initView() {
		tv_title.setText(content);
		if (leftBtnText.equals(""))
			dialog_textViewID.setVisibility(View.GONE);
		else
			dialog_textViewID.setText(leftBtnText);
		if (rightBtnText.equals(""))
			dialog_textViewID1.setVisibility(View.GONE);
		else
			dialog_textViewID1.setText(rightBtnText);
	}

	public interface DialogClickListener {
		void onLeftBtnClick(Dialog dialog);
		void onRightBtnClick(Dialog dialog);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dialog_textViewID:
			listener.onLeftBtnClick(this);
			break;
		case R.id.dialog_textViewID1:
			listener.onRightBtnClick(this);
			break;
		default:
			break;
		}
	}
}