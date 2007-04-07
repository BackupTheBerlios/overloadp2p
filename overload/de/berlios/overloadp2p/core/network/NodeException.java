package de.berlios.overloadp2p.core.network;

/**
 * Thrown by node types.
 * @author Dmitri Bachtin
 */
public class NodeException extends Exception {
	private static final long serialVersionUID = 3442172200208267019L;
	public NodeException() {
		super();
	}
	
	public NodeException(String msg) {
		super(msg);
	}
}
