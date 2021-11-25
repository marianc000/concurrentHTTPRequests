/*
 * Here should be licence
 */
package run;

import static shared.HttpUtils.get;
import shared.UrlTxt;
import static shared.UrlSource.getUrlsFromUrl;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;
 
public class URLRequests extends Runner {

    CompletableFuture<UrlTxt> load(String url) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return new UrlTxt(url, get(url));
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        });
    }

    @Override
    CompletableFuture<List<UrlTxt>> requestManyUrls(List<String> urls) throws InterruptedException, ExecutionException {
        CompletableFuture<UrlTxt>[] requests = urls
                .stream().map(url -> load(url)).toArray(i -> new CompletableFuture[i]);

        return CompletableFuture.allOf(requests)
                .thenApply(v -> {
                    return Stream.of(requests)
                            .map(future -> future.join())
                            .collect(Collectors.toList());
                });
    }

    public static void main(String[] args) throws Exception {
        new URLRequests().run();
    }
}
