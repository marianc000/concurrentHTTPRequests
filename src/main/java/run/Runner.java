/*
 * Here should be licence
 */
package run;

import static shared.UrlSource.getUrlsFromUrl;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import shared.UrlTxt;
 
public abstract class Runner {

    abstract CompletableFuture<List<UrlTxt>> requestManyUrls(List<String> urls) throws Exception;

    void run() throws Exception {
        var urls = getUrlsFromUrl("https://www.bbc.com/news/world");
        System.out.println("url to fetch count: " + urls.size());

        var start = System.currentTimeMillis();
        var contents = requestManyUrls(urls).get();
        var time = System.currentTimeMillis() - start;

        // contents.forEach(o->System.out.println(o));
        var totalLength = contents.stream()
                .mapToInt(o -> o.txt().length())
                .reduce((a, b) -> a + b).getAsInt();
        System.out.println("fetched " + totalLength + " bytes from " + urls.size() + " urls in " + time + " ms");
    }
}
