package com.android.TwitetrClient.example2;


import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import android.content.SharedPreferences;

public class twitter_client_util{

	/*接続チェック*/
	public static boolean isConnect(String twitterStatus){
		if(twitterStatus != null && twitterStatus.equalsIgnoreCase(AppConsts.STATUS_AVAILABLE)){
			return true;
		}else{
			return false;
		}
	}

	public static ResponseList<Status> getTL(SharedPreferences pref){
		ResponseList<Status> TL = null;

		String oauthToken=pref.getString("oauth_token","");
		String oauthTokenSecret=pref.getString("oauth_token_secret","");
		AccessToken accessToken=new AccessToken(oauthToken,oauthTokenSecret);
		Twitter twitter=new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(AppConsts.CONSUMER_KEY,AppConsts.CONSUMER_SECRET);
		twitter.setOAuthAccessToken(accessToken);

		try {
			TL=twitter.getHomeTimeline();
		} catch (TwitterException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return TL;
	}

	/*切断*/
	public static void disconnect(SharedPreferences pref){
		SharedPreferences.Editor editor=pref.edit();
		editor.remove("oauth_token");
		editor.remove("oauth_token_secret");
		editor.remove("status");
		editor.commit();
	}


	/*ツイート*/
	public void tweet(String str,SharedPreferences pref){
		String oauthToken=pref.getString("oauth_token","");
		String oauthTokenSecret=pref.getString("oauth_token_secret","");
		AccessToken accessToken=new AccessToken(oauthToken,oauthTokenSecret);

		Twitter twitter=new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(AppConsts.CONSUMER_KEY,AppConsts.CONSUMER_SECRET);
		twitter.setOAuthAccessToken(accessToken);

		try{
			twitter.updateStatus(str);
			/*Edit_Text.setText("");*/
		}catch(TwitterException e){
			//Toast.makeText(R.layout.main,"ツイート出来ませんでした",Toast.LENGTH_LONG).show();
		}
	}
}