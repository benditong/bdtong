package com.zykj.benditong.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.zykj.benditong.R;
import com.zykj.benditong.adapter.CommonAdapter;
import com.zykj.benditong.adapter.ViewHolder;

public class PickDialog extends Dialog{
	private Context context;
	private String title;
	//private LinearLayout blend_dialog_preview;
	private ListView blend_dialog_nextview;
	private List<String> items=new ArrayList<String>();
	private PickDialogListener pickDialogListener;

	public PickDialog(Context context,String title,List<String> items,PickDialogListener pickDialogListener) {
		super(context, R.style.blend_theme_dialog);
		this.context=context;
		this.title=title;
		this.items = items;
		this.pickDialogListener=pickDialogListener;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		LayoutInflater inflater =LayoutInflater.from(context);
		LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.dialog_listview, null);

		TextView titleTextview = (TextView) layout.findViewById(R.id.blend_dialog_title);
		titleTextview.setText(title);
		TextView cancleTextView = (TextView) layout.findViewById(R.id.blend_dialog_cancle_btn);
		cancleTextView.setText("取消");
		//blend_dialog_preview = (LinearLayout) layout.findViewById(R.id.blend_dialog_preview);
		blend_dialog_nextview = (ListView) layout.findViewById(R.id.blend_dialog_nextview);
		

		this.setCanceledOnTouchOutside(true);
		this.setOnCancelListener(new DialogInterface.OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				dismiss();
			}
		});
		cancleTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dismiss();
			}
		});
		this.setContentView(layout);
		initListViewData();
	}
	
	public void initListViewData(){
		blend_dialog_nextview.setAdapter(new CommonAdapter<String>(context, R.layout.ui_item_dialog, items) {
			@Override
			public void convert(ViewHolder holder, String str) {
				holder.setText(R.id.dialog_item_textview, str);
			}
		});
		blend_dialog_nextview.setOnItemClickListener(new ListView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				dismiss();
				if(pickDialogListener!=null){
					pickDialogListener.onListItemClick(position, items.get(position));
				}
			}
		});
	}
	
	public interface PickDialogListener {
		public void onListItemClick(int position, String string);
	}
}
