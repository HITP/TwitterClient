package com.android.TwitetrClient.example2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import twitter4j.ResponseList;
import twitter4j.Status;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class TwitterClient_Ver2Activity extends ListActivity {
    /** Called when the activity is first created. */

	private ArrayList<TLItemBean> list=null;
	private TwitterAdapter adapter;
	SharedPreferences pref;

    @Override
    /*public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		Button btn=(Button)findViewById(R.id.button2);
		btn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO 自動生成されたメソッド・スタブ
				startActivity(new Intent(TwitterClient_Ver2Activity.this,twitterPreference.class));
			}
		});

		pref=getSharedPreferences(AppConsts.PREFERENCES_NAME,MODE_PRIVATE);
		if(pref == null){
        	pref.edit().commit();
        }

		if(twitter_client_util.isConnect(pref.getString(AppConsts.PREF_STATUS, ""))){
        	try{
        	ResponseList<Status> TL;

    		TL=twitter_client_util.getTL(pref);
            createData(TL);

            setContentView(R.layout.main);
    		adapter=new TwitterAdapter(this,R.layout.list_at,list);

    		ListView listview=(ListView)findViewById(R.id.listView1);
    		listview.setAdapter(adapter);

        	}catch(Exception e){
        		Toast.makeText(this, "タイムラインを取得できませんでした", Toast.LENGTH_LONG).show();
        	}
        }else{
        	startActivity(new Intent(TwitterClient_Ver2Activity.this,twitterPreference.class));
        }
	}*/

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pref=getSharedPreferences(AppConsts.PREFERENCES_NAME,MODE_PRIVATE);


        if(pref == null){
        	pref.edit().commit();
        }

        if(twitter_client_util.isConnect(pref.getString(AppConsts.PREF_STATUS, ""))){
        	try{
        	ResponseList<Status> TL;

    		TL=twitter_client_util.getTL(pref);
            createData(TL);

            adapter=new TwitterAdapter(this,R.layout.list_at,list);
    		setListAdapter(adapter);

        	}catch(Exception e){
        		Toast.makeText(this, "タイムラインを取得できませんでした", Toast.LENGTH_LONG).show();
        	}
        }else{
        	startActivity(new Intent(TwitterClient_Ver2Activity.this,twitterPreference.class));
        }
    }

    private void createData(ResponseList<Status> TL){
    	this.list=new ArrayList<TLItemBean>();

    	SimpleDateFormat sdf=new SimpleDateFormat("MM'月'dd'日 'hh':'mm");


		for(Status status:TL){
			Long id=status.getId();
			String userName=status.getUser().getScreenName();
			String tweet=status.getText();
			String url=status.getUser().getProfileImageURL().toString();
			String create_at=sdf.format(status.getCreatedAt());


			TLItemBean item=new TLItemBean();
			item.setScreenName(userName);
			item.setText(tweet);
			item.setUrl(url);
			item.setId(id);
			item.setCreateAt(create_at);

			list.add(item);
		}
    }
}