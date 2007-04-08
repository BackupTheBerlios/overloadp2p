package de.berlios.overloadp2p.core.network;

import java.util.ArrayList;

/**
 * Maintains caches of PeerNodes and WebNodes.
 * 
 * @author Dmitri Bachtin
 */
public final class NodeCache {
	private final int MAX_PEERCACHE_SIZE = 800;

	private final int MAX_WEBCACHE_SIZE = 150;

	private ArrayList<PeerNode> peerCache = new ArrayList<PeerNode>();

	private ArrayList<WebNode> webCache = new ArrayList<WebNode>();

	private static NodeCache instance = null;

	private NodeCache() {

	}

	public synchronized NodeCache getInstance() {
		if (instance == null)
			instance = new NodeCache();
		return instance;
	}

	/**
	 * Adds a peer node to the internal peer cache unless it is null or the
	 * count of saved peer nodes exceeds the maximum.
	 * 
	 * @param n
	 *            the node to be added.
	 */
	public synchronized void addPeerNode(PeerNode n) {
		if (n == null)
			return;
		if (peerCache.size() >= MAX_PEERCACHE_SIZE)
			return;
		peerCache.add(n);
	}

	/**
	 * Adds a web node to the internal web cache unless it is null or the count
	 * of saved web nodes exceeds the maximum or a node with the same URL
	 * already exists.
	 * 
	 * @param n
	 *            the node to be added.
	 */
	public synchronized void addWebNode(WebNode n) {
		if (n == null)
			return;
		if (webCache.size() >= MAX_WEBCACHE_SIZE)
			return;
		if (webCache.contains(n))
			return;
		webCache.add(n);
	}

	public synchronized void addPeerNode(String host, int port) {
		try {
			PeerNode n = new PeerNode(host, port);
			addPeerNode(n);
		} catch (NodeException e) {
			return;
		}
	}

	public synchronized void addPeerNode(String hostPort) {
		try {
			PeerNode n = new PeerNode(hostPort);
			addPeerNode(n);
		} catch (NodeException e) {
			return;
		}
	}

	public synchronized void addWebNode(String url) {
		try {
			WebNode n = new WebNode(url);
			addWebNode(n);
		} catch (NodeException e) {
			return;
		}
	}

	public synchronized void addWebNode(String url, long lastRequestTime) {
		try {
			WebNode n = new WebNode(url, lastRequestTime);
			addWebNode(n);
		} catch (NodeException e) {
			return;
		}
	}
}
