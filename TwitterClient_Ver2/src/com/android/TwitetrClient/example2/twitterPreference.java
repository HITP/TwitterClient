package com.android.TwitetrClient.example2;

import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationContext;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.widget.Toast;

public class twitterPreference extends PreferenceActivity {

	String twitterStatus="";
	SharedPreferences pref;
	OAuthAuthorization oauth;
	RequestToken reqToken=null;

	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.setting);

		pref=getSharedPreferences(AppConsts.PREFERENCES_NAME,MODE_PRIVATE);

		if(pref!=null){
			twitterStatus=pref.getString(AppConsts.PREF_STATUS,"");
		}

		setTwitterMenu();
	}

	private void setTwitterMenu(){
		PreferenceScreen twitterScreen=(PreferenceScreen)findPreference(AppConsts.PREFERENCE_TWITTER_SETTING);

		if(twitter_client_util.isConnect(twitterStatus)){
			twitterScreen.setSummary("接続");
		}else{
			twitterScreen.setSummary("切断");
		}
	}

	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,Preference preference){
		//if(AppConsts.PREFERENCE_TWITTER_SETTING.equals(preference.getKey())){
			twitterSetting();

			if(!twitter_client_util.isConnect(twitterStatus)){
				Intent intent=new Intent(this,twitterLogin.class);
				intent.putExtra("auth_url", reqToken.getAuthorizationURL());
				this.startActivityForResult(intent, AppConsts.REQUEST_CODE);
			}else{
				twitter_client_util.disconnect(pref);
			}
		//}
		return super.onPreferenceTreeClick(preferenceScreen, preference);
	}

	protected void onActivityResult(int requestCode,int resultCode,Intent intent){
		if(resultCode==RESULT_OK){
			super.onActivityResult(requestCode, resultCode, intent);

			AccessToken accessToken=null;

			try{
				accessToken=oauth.getOAuthAccessToken(reqToken,intent.getExtras().getString("oauth_verifier"));

				SharedPreferences.Editor editor=pref.edit();
				editor.putString("oauth_token",accessToken.getToken());
				editor.putString("oauth_token_secret",accessToken.getTokenSecret());
				editor.putString("status","available");
				editor.commit();

				finish();

			}catch(TwitterException e){
				Toast.makeText(this,"Access_Token Error!",Toast.LENGTH_LONG).show();
			}
		}
	}

	private void twitterSetting(){
		Configuration conf=ConfigurationContext.getInstance();
		oauth=new OAuthAuthorization(conf);
		oauth.setOAuthConsumer(AppConsts.CONSUMER_KEY, AppConsts.CONSUMER_SECRET);

		try{
			reqToken=oauth.getOAuthRequestToken(AppConsts.CALLBACK_URL);
		}catch(TwitterException e){
			Toast.makeText(this,"Oauth Error!",Toast.LENGTH_LONG).show();
		}
	}
}
