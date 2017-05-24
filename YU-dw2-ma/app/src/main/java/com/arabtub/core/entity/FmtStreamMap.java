package com.arabtub.core.entity;


public class FmtStreamMap {
	public String fallbackHost;

	public String s;
	public String itag;
	public String type;

	public String quality;

	public String url;

	public String sig;

	public String title;

	public String mediatype;

	public boolean encrypted;

	public String extension;

	public Resolution resolution;

	public String html5playerJS;

	public CharSequence videoid;

	public String realUrl;

	@Override
	public String toString() {
		return "FmtStreamMap [fallbackHost=" + fallbackHost + ", s=" + s + ", itag=" + itag + ", type=" + type + ", quality=" + quality
				+ ", url=" + url + ", sig=" + sig + ", title=" + title + ", mediatype=" + mediatype + ", encrypted=" + encrypted
				+ ", extension=" + extension + ", resolution=" + resolution + ", html5playerJS=" + html5playerJS + ", videoid=" + videoid
				+ "]";
	}

	public String getStreamString() {
		if (resolution != null) {
			return String.format("%s (%s)", extension, resolution.resolution);
		} else {
			return null;
		}
	}
}