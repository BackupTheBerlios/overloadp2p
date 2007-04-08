package de.berlios.overloadp2p.core.network;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Represents a Gnutella GWebCache node. 
 * It stores an unparameterized URL and the timestamp of the last 
 * access to the node.
 * For sorting purposes the node can be compared using Comparable by its request time.
 * @author Dmitri Bachtin
 */
public class WebNode implements Serializable, Comparable<WebNode> {
	private static final long serialVersionUID = -6097444455047165661L;
	private String url;
	private long lastRequestTime;
	
	protected void init(String url, long lastRequestTime) throws NodeException {
		if (url == null)
			throw new NodeException("URL is a null reference");
		if (lastRequestTime < 0)
			lastRequestTime = 0;
		else if (lastRequestTime > System.currentTimeMillis())
			lastRequestTime = System.currentTimeMillis();
		
		URL _url;
		StringBuilder urlBuilder = new StringBuilder();
		try {
			_url = new URL(url);
		} catch(MalformedURLException e) {
			throw new NodeException("URL is misformatted");
		}
		
		if (!_url.getProtocol().equals("http"))
			throw new NodeException("URL is not HTTP");
		urlBuilder.append(_url.getProtocol());
		
		if (_url.getHost().length() < 2)
			throw new NodeException("URL hostname is too short");
		urlBuilder.append(_url.getHost().toLowerCase());
		
		if (_url.getPort() > 0 && _url.getPort() < 65536) {
			urlBuilder.append(':');
			urlBuilder.append(_url.getPort());
		}
		
		if (_url.getPath().equals(""))
			urlBuilder.append('/');
		else if (!_url.getPath().startsWith("/"))
			throw new NodeException("URL path is invalid");
		else {
			String path = _url.getPath();
			while(path.endsWith("/")) // trunk all the trailing /
				path = path.substring(1, path.lastIndexOf('/'));
			if (path.matches("index\\..+\\z"))
				path = path.substring(0, path.lastIndexOf("index"));
			urlBuilder.append(path);
		}
		
		this.url = urlBuilder.toString();
		this.lastRequestTime = lastRequestTime;
	}
	
	/**
	 * Creates a new instance of WebNode with lastRequestTime timestamp set to 0
	 * @param url
	 * @throws NodeException if the URL is invalid
	 */
	public WebNode(String url) throws NodeException {
		init(url, 0);
	}
	
	/**
	 * Creates a new instance of WebNode with lastRequestTime set to provided value or 
	 * 0 if the time is negative or current time if the time is in future.
	 * @param url
	 * @param lastRequestTime
	 * @throws NodeException if the URL is invalid
	 */
	public WebNode(String url, long lastRequestTime) throws NodeException {
		init(url, lastRequestTime);
	}
	
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}
	
	/**
	 * Equal if URLs of both objects are equal
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof WebNode))
			return false;
		final WebNode other = (WebNode) obj;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}
	
	public long getLastRequestTime() {
		return lastRequestTime;
	}
	public String getUrl() {
		return url;
	}
	
	/**
	 * Compares two WebNodes based on their lastRequestTime values.
	 * @return -1 if lastRequestTime is smaller than of obj, +1 if this is higher than of obj
	 * else 0.
	 */
	public int compareTo(WebNode obj) {
		if (obj == null)
			throw new NullPointerException();
		if (this.lastRequestTime < obj.lastRequestTime)
			return -1;
		else if (this.lastRequestTime > obj.lastRequestTime)
			return 1;
		return 0;
	}
	
	/**
	 * @return URL@requestTime
	 */
	public String toString() {
		return this.url + '@' + this.lastRequestTime;
	}
	
}
