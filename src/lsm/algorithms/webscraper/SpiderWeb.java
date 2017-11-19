package lsm.algorithms.webscraper;

import java.net.URI;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;

public class SpiderWeb {
    private final int maxDepth;
    private final HashMap<String, Integer> seen = new HashMap<>();
    private final HashSet<String> hosts = new HashSet<>();
    private final ArrayDeque<URL> urls = new ArrayDeque<>();

    SpiderWeb(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    synchronized URL get() {
        return urls.poll();
    }

    synchronized void add(int depth, String link) {
        try {
            URL url = new URL(link);
            new URI(link);
            link = url.toString();
            if(seen.containsKey(link)) return;
            urls.add(url);
            seen.put(link, depth);
            hosts.add(url.getHost());
            // Note.writenl("Found " + seen.size() + " urls");
        } catch (Exception ignored) { }
    }

    synchronized void add(String from, String link) {
        int prevDepth = seen.get(from);
        if (prevDepth >= maxDepth) return;
        add(prevDepth + 1, link);
    }

    synchronized int urlsLeft(){ return urls.size(); }

    synchronized String[] getSeen() {
        return seen.keySet().toArray(new String[seen.size()]);
    }
    synchronized String[] getHosts(){ return hosts.toArray(new String[hosts.size()]); }
}
