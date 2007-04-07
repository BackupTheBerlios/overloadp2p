package de.berlios.overloadp2p.core.network;

import java.io.Serializable;

/**
 * Represents a participant of the Gnutella2 network.
 * @author Dmitri Bachtin
 *
 */
public class PeerNode implements Serializable {
	private static final long serialVersionUID = 2415055661318027233L;
	private String host;
	private int port;
	
	private void init(String host, int port) throws NodeException {
		if (host == null)
			throw new NodeException("Hostname is a null reference");
		if (port < 1 || port > 65535)
			throw new NodeException("Port is out of range");
		if (host.length() < 3)
			throw new NodeException("Hostname is too short");
		this.host = host.toLowerCase();
		this.port = port;
	}
	
	/**
	 * Creates a new instance of PeerNode
	 * @param host host address of the node 
	 * @param port port on which the gnutella2 service is running
	 * @throws NodeException In case host is null or shorter than 3 chars or port is
	 *         not in 1..65535
	 */
	public PeerNode(String host, int port) throws NodeException {
		init(host, port);
	}
	
	/**
	 * Creates a new instance of PeerNode
	 * @param hostPort full address of the node in form address:port
	 * @throws NodeException In case hostPort is misformatted or host is too short or
	 *         port is not in 1..65535
	 */
	public PeerNode(String hostPort) throws NodeException {
		if (hostPort == null)
			throw new NodeException("Host:Port is a null reference");
		String[] parts = hostPort.split(":");
		if (parts.length != 2)
			throw new NodeException("Host:Port is misformatted");
		int port = 0;
		try {
			port = Integer.parseInt(parts[1]);
		} catch(NumberFormatException e) {
			throw new NodeException("Port is not a number");
		}
		
		init(parts[0], port);
	}

	/**
	 * PeerNode == PeerNode if Hosts and Ports are equal. HoST is same as hOSt.
	 */
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof PeerNode))
			return false;
		PeerNode n = (PeerNode) obj;
		return n.port == this.port && n.host.equals(this.host);
	}

	/**
	 * returns host.hashCode() ^ port
	 */
	public int hashCode() {
		return this.host.hashCode() ^ this.port;
	}

	/**
	 * Returns the full address of the node in form host:port
	 */
	public String toString() {
		return this.host + ":" + this.port;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}
	
	
}
