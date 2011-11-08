package com.android.TwitetrClient.example2;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TwitterAdapter extends ArrayAdapter<TLItemBean>{

	private ArrayList<TLItemBean> items;
	private LayoutInflater inflater;

	public TwitterAdapter(Context context, int textViewResourceId,
			ArrayList<TLItemBean> items) {
		super(context, textViewResourceId, items);
		// TODO 自動生成されたコンストラクター・スタブ
		this.items=items;
		this.inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position,View convertView,ViewGroup parent){
		View view=convertView;

		if(view==null){
			view=inflater.inflate(R.layout.list_at,null);
			view.setBackgroundResource(R.drawable.back);
		}

		TLItemBean item=(TLItemBean)items.get(position);

		if(item!=null){
			TextView id=(TextView)view.findViewById(R.id.id);
			TextView user=(TextView)view.findViewById(R.id.textView1);
			TextView text=(TextView)view.findViewById(R.id.textView2);
			TextView url=(TextView)view.findViewById(R.id.url);
			TextView date=(TextView)view.findViewById(R.id.create_at);
			ImageView icon=(ImageView)view.findViewById(R.id.icon);

			if(id!=null && user!=null && text!=null && url!=null && date!=null){
				id.setText(Long.toString(item.getId()));
				user.setText(item.getScreenName());
				text.setText(item.getText());
				url.setText(item.getUrl());
				date.setText(item.getCreateAt());

				try {
					URL urlstr=new URL(item.getUrl());
					InputStream is=urlstr.openStream();
					Bitmap bm=BitmapFactory.decodeStream(is);
					icon.setImageBitmap(bm);
				} catch (Exception e) {
					// TODO 自動生成された catch ブロック
					icon.setImageResource(R.drawable.x);
				}
			}
		}
		return view;
	}

}
