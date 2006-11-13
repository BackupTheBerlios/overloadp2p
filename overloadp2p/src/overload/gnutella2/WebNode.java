/*
 * WebNode.java
 *
 * Created on 13. November 2006, 11:34
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package overload.gnutella2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a bootstrappable GWebCache
 * @author Bachtin Dmitri
 */
public class WebNode {
   
    private String url;
    private long requestTime;
    private static final long serialVersionUID = 5349987826566852046L;
    
    private void init(String url, long requestTime) throws NodeException {
        if (requestTime < 0 || requestTime > System.currentTimeMillis())
            throw new NodeException();
        if (url == null) throw new NodeException();
        if (url.length() < 10) throw new NodeException();
        Pattern p = Pattern.compile("^http://", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(url);
        if (!m.matches()) throw new NodeException();
        String hostname = url.substring(0, url.indexOf(8, '/')).toLowerCase();
        String path     = url.substring(url.indexOf(8,'/'));
        p = Pattern.compile("/index\\.(php|asp|jsp|cgi|aspx)");
        m = p.matcher(path);
        if (m.matches())
            path = path.substring(0, path.lastIndexOf('/'));
        this.url = "http://" + hostname + path;
        this.requestTime = requestTime;
    }
    
    /** Creates a new instance of WebNode */
    public WebNode(String url, long requestTime) throws NodeException {
        init(url, requestTime);
    }
    
    public WebNode(String url) throws NodeException {
        init(url, 0);
    }

    public String getUrl() {
        return url;
    }

    public long getRequestTime() {
        return requestTime;
    }
    
    public int hashCode() {
        return url.hashCode() ^ 77;
    }
    
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null) return false;
        if (!(o instanceof WebNode)) return false;
        WebNode n = (WebNode) o;
        return n.url.equals(url);
    }
}
