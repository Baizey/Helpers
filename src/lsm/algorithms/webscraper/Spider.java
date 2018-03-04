package lsm.algorithms.webscraper;

import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;
import lsm.helpers.IO.write.text.console.Note;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Spider extends Thread {

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
