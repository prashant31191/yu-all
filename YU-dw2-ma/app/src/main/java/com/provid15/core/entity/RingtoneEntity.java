package com.provid15.core.entity;

public class RingtoneEntity {
	public long currentPosition;
	public boolean Selected;
	public long ModifiedDate;
	public String donwloadUrl;
	public int mDuration;
	public String mName;
	private String mOriginUrl;
	private String videoId;


	public enum PLAYSTATE {
		INIT_STATE, PLAY_STATE, PAUSE_STATE, LOADING_STATE
	};

	private PLAYSTATE playState = PLAYSTATE.INIT_STATE; // updatePlayState playInterrupt



	public boolean mPlaying = false;

	private String desc;

	public RingtoneEntity() {
		initParams();
	}

	private void initParams() {
		playState = PLAYSTATE.INIT_STATE;
		mPlaying = false;
		currentPosition = 0;
	}

	public void setId(int id) {
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setPlayState(PLAYSTATE initState) {
		playState = initState;
	}

	public String getDownloadUrl() {
		return donwloadUrl;
	}

	public void setDownloadUrl(String url) {
		donwloadUrl = url;
	}

	public void setDuration(int duration) {
		mDuration = duration;
	}

	public int getDuration() {
		return mDuration;
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		mName = name;
	}

	public PLAYSTATE getPlayState() {
		return playState;
	}

	public String getOriginUrl() {
		return mOriginUrl;
	}

	public void setOriginUrl(String mOriginUrl) {
		this.mOriginUrl = mOriginUrl;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}
}
