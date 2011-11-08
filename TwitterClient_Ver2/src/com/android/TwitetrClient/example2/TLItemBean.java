package com.android.TwitetrClient.example2;

public class TLItemBean{
	private String ScreenName;
	private String Text;
	private String Url;
	private Long Id;
	private String create_at;

	public String getScreenName(){
		return ScreenName;
	}

	public void setScreenName(String ScreenName){
		this.ScreenName=ScreenName;
	}

	public String getText(){
		return Text;
	}

	public void setText(String Text){
		this.Text=Text;
	}

	public String getUrl(){
		return Url;
	}

	public void setUrl(String Url){
		this.Url=Url;
	}

	public Long getId(){
		return Id;
	}

	public void setId(Long Id){
		this.Id=Id;
	}

	public String getCreateAt(){
		return create_at;
	}

	public void setCreateAt(String create_at){
		this.create_at=create_at;
	}
}