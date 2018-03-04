package lsm.algorithms.webscraper;

import lsm.helpers.IO.write.text.TextWriter;
import lsm.helpers.IO.write.text.console.Note;

import java.io.BufferedWriter;

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

