/*
 * BootstrapManager.java
 *
 * Created on 13. November 2006, 12:45
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package overload.gnutella2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import overload.globals.GlobalConstants;

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
    
    public synchronized WebNode getWebNode() {
        if (webCache.size() == 0)
        {
            File f = new File(System.getenv("user.home") + "/.overloadp2p/default_webcaches");
            int added = 0;
            if (f.exists() && f.canRead()) {
                String line = null;
                try {
                    BufferedReader in = new BufferedReader(new FileReader(f));
                    while((line = in.readLine()) != null) {
                        try {
                            WebNode n = new WebNode(line);
                            addWebNode(n);
                            added++;
                        } catch(NodeException e) {
                            continue;
                        }
                    }
                    in.close();
                } catch(IOException e) {
                    
                }
            }
            
            if(added == 0 || !f.exists()) {
                for(String s : defaultNodes) {
                    try {
                        WebNode n = new WebNode(s);
                        addWebNode(n);
                    } catch(NodeException e) {
                        continue;
                    }
                }
            }
            
            long curTime = System.currentTimeMillis();
            for(WebNode n : webCache) {
                if (curTime - n.getRequestTime() > 3600000) return n;
            }
            
            try {
                BufferedWriter out = new BufferedWriter(new FileWriter(f));
                for(WebNode n : webCache)
                    out.write(n.getUrl() + '\n');
                out.close();
            } catch(IOException e) {
                
            }
            
        }
        return null; //should never happen
    }
    
    private synchronized boolean DoWebcacheRequest() {
        int gotHosts = 0;
        int retries  = 0;
        String requestParams = "?get=1&net=gnutella2&client=" + GlobalConstants.CLIENT_CODE_VERSION;
        while(gotHosts == 0 && retries < GlobalConstants.MAX_WEBREQUEST_RETRIES) {
            WebNode n = getWebNode();
            try {
                URL url = new URL(n.getUrl() + requestParams);
                URLConnection urlc = url.openConnection();
                urlc.setConnectTimeout(30000);
                urlc.setReadTimeout(10000);
                String line = null;
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                while((line = in.readLine()) != null)
                {
                    Pattern hostPattern = Pattern.compile("^h\\|(.)+", Pattern.CASE_INSENSITIVE);
                    Pattern urlPattern  = Pattern.compile("^u\\|(.)+", Pattern.CASE_INSENSITIVE);
                    
                    Matcher m;
                    m = hostPattern.matcher(line);
                    
                    String[] parts = line.split("\\|");
                    if (m.matches()) {
                        try {
                            PeerNode pn = new PeerNode(parts[1]);
                            addPeerNode(pn);
                            gotHosts++;
                        } catch(NodeException e) {
                            continue;
                        }
                    }
                    
                    m = urlPattern.matcher(parts[1]);
                    if (m.matches()) {
                        try {
                            WebNode wn = new WebNode(parts[1]);
                            addWebNode(wn);
                        } catch(NodeException e) {
                            continue;
                        }
                    }
                }
                in.close();
            } catch(MalformedURLException e) {
                webCache.remove(n);
                retries++;
                continue;
            } catch(IOException e) {
                webCache.remove(n);
                retries++;
                continue;
            }
            n.updateRequestTime();
        }
        
        return retries < GlobalConstants.MAX_WEBREQUEST_RETRIES ? true : false;
    }
    
    public synchronized PeerNode getPeerNode() {
        if (peerCache.size() == 0) {
            boolean requestSuccess = DoWebcacheRequest();
            if (!requestSuccess) return null;
        }
        
        return peerCache.remove();
    }
}
