package lsm.algorithms.webscraper;

import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;
import lsm.helpers.IO.write.text.TextWriter;
import lsm.helpers.Note;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebCrawler {

    public static void main(String... args) throws Exception {
        setPopulation(100);
        WebCrawler.crawl(3, "https://www.google.com");
    }

    private static int population = 10;
    private synchronized static void setPopulation(int i){
        if(i <= 0) return;
        population = i;
    }

    private static void crawl(int maxDepth, String... urls) throws Exception {
        SpiderWeb web = new SpiderWeb(maxDepth);
        for(String url : urls) web.add(0, url);

        int reserve = population;
        Spider[] spiders = new Spider[reserve];

        Spider boss;
        (boss = spiders[0] = new Spider(web)).start();
        Thread.sleep(1000);
        int used = 1;

        while(used < reserve && boss.getLivingWorkers() > 0)
            if(web.urlsLeft() > boss.getLivingWorkers())
                (spiders[used++] = new Spider(web)).start();

        for(int i = 0; i < used; i++)
            spiders[i].join();

        String[] crawled = web.getHosts();
        BufferedWriter writer = TextWriter.getWriter("reddit", "txt", true);
        for(String url : crawled) {
            writer.write(url + "\n");
            Note.writenl(url);
        }
        writer.flush();
        writer.close();
    }
}

class SpiderWeb {
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

class Spider extends Thread {

    private final HttpClient client = HttpClient.newHttpClient();
    private final SpiderWeb home;

    Spider(SpiderWeb home) {
        this.home = home;
    }

    private static int livingWorkers = 0;
    synchronized private void changeLivingWorkers(int i){
        livingWorkers += i;
        Note.writenl("Spiders alive: " + livingWorkers);
    }
    synchronized int getLivingWorkers(){ return livingWorkers; }

    @Override
    public void run() {
        changeLivingWorkers(1);
        URL url;
        while((url = home.get()) != null)
            try { runOnce(url); } catch (Exception ignored) {}
        changeLivingWorkers(-1);
    }

    private static Pattern link = Pattern.compile("(https?://www\\.(\\w|[./?=,&#%\\-])+)");
    private void runOnce(URL url) throws URISyntaxException, IOException, InterruptedException {
        String body = client.send(
                HttpRequest.newBuilder()
                        .uri(url.toURI())
                        .setHeader("User-Agent", "spoderbot")
                        .GET()
                        .build(),
                HttpResponse.BodyHandler.asString()
        ).body();
        Matcher matcher = link.matcher(body);
        String from = url.toString();
        while(matcher.find())
            home.add(from, matcher.group(1));
    }

}