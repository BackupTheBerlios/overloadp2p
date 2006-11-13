/*
 * BootstrapManager.java
 *
 * Created on 13. November 2006, 12:45
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package overload.gnutella2;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author Bachtin Dmitri
 */
public class BootstrapManager {
    
    private ArrayList<WebNode> webCache;
    private LinkedList<PeerNode> peerCache;
    private int maxPeerCacheSize = 500;
    private int maxWebCacheSize  = 150;
    private static BootstrapManager instance;
    
    /** Creates a new instance of BootstrapManager */
    private BootstrapManager() {
        webCache = new ArrayList<WebNode>();
        peerCache = new LinkedList<PeerNode>();
    }
    
    public static synchronized BootstrapManager getInstance() {
        if (instance == null) instance = new BootstrapManager();
        return instance;
    }
    
    public synchronized void addPeerNode(PeerNode n) {
        if (n == null) return;
        if (peerCache.size() >= maxPeerCacheSize) return;
        peerCache.add(n);
    }
    
    public synchronized void addWebNode(WebNode n) {
        if (n == null) return;
        if (webCache.size() >= maxWebCacheSize) return;
        if (webCache.contains(n)) return;
        webCache.add(n);
    }
    
}
