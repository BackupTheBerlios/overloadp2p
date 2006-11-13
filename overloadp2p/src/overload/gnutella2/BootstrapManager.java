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
    private final String[] defaultNodes = {
        "http://gwcrab2.sarcastro.com:8002",
        "http://silvers.servehttp.com/g2/bazooka.php",
        "http://bazooka1.servehttp.com/g2/bazooka.php",
        "http://g2.sbicomputing.com/g2/bazooka.php",
        "http://g2.wow-toj.be/bazooka.php"
    };
    
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
