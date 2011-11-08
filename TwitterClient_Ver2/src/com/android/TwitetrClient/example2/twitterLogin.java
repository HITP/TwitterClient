package com.android.TwitetrClient.example2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class twitterLogin extends Activity {

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.twitterlogin);

		WebView wv=(WebView)findViewById(R.id.webView1);
		wv.setWebViewClient(new WebViewClient(){

			public void onPageFinished(WebView view,String url){
				super.onPageFinished(view, url);

				if(url!=null && url.startsWith(AppConsts.CALLBACK_URL)){
					String[] urlParameters=url.split("\\?")[1].split("&");

					String oauthToken="";
					String oauthVerifier="";

					if(urlParameters[0].startsWith("oauth_token")){
						oauthToken=urlParameters[0].split("=")[1];
					}else if(urlParameters[1].startsWith("oauth_token")){
						oauthToken=urlParameters[1].split("=")[1];
					}

					if(urlParameters[0].startsWith("oauth_verifier")){
						oauthToken=urlParameters[0].split("=")[1];
					}else if(urlParameters[1].startsWith("oauth_verifier")){
						oauthToken=urlParameters[1].split("=")[1];
					}

					Intent intent=getIntent();
					intent.putExtra("oauth_token", oauthToken);
					intent.putExtra("oauth_verifier", oauthVerifier);

					setResult(Activity.RESULT_OK,intent);
					finish();
				}
			}
		});

		wv.loadUrl(this.getIntent().getExtras().getString("auth_url"));
	}
}
