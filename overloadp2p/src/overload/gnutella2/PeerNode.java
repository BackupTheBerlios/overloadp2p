/*
 * PeerNode.java
 *
 * Created on 13. November 2006, 10:02
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package overload.gnutella2;

import java.io.Serializable;

/**
 * Represents a (bootstrapable) participant of the network
 * @author Bachtin Dmitri
 */
public class PeerNode implements Serializable {
    
    private static final long serialVersionUID = -7364589938032522154L;
    private String host;
    private int port;
    
    private void init(String host, int port) throws NodeException {
        if (port < 1 || port > 65535) throw new NodeException();
        if (null == host) throw new NodeException();
        if (host.length() < 3) throw new NodeException();
        this.host = host.toLowerCase();
        this.port = port;
    }
    
    /** Creates a new instance of PeerNode */
    public PeerNode(String host, int port) throws NodeException {
        init(host, port);
    }
    
    public PeerNode(String hostPort) throws NodeException {
        if (hostPort == null) throw new NodeException();
        String[] parts = hostPort.split(":");
        if (parts.length != 2) throw new NodeException();
        int port = 0;
        try {
            port = Integer.parseInt(parts[1]);
        } catch(NumberFormatException e) {
            throw new NodeException();
        }
        init(parts[0], port);
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
    
    public int hashCode() {
        return host.hashCode() ^ port;
    }
    
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null) return false;
        if (!(o instanceof PeerNode)) return false;
        PeerNode n = (PeerNode) o;
        return n.port == port && n.host.equals(host);
    }
    
    public String toString() {
        return host + ":" + port;
    }
}
