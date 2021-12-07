/*
 * Here should be licence
 */
package run;

import shared.UrlTxt;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HttpClientRequests extends Runner {
    private final HttpClient client = HttpClient.newHttpClient();

    @Override
    public CompletableFuture<List<UrlTxt>> requestManyUrls(List<String> urls) throws InterruptedException, ExecutionException {

        CompletableFuture<HttpResponse<String>>[] requests = urls.stream()
                .map(url -> URI.create(url))
                .map(uri -> HttpRequest.newBuilder(uri)
                    .setHeader("accept-encoding", "gzip,deflate,br")
                    .build())
                .map(request -> client.sendAsync(request, BodyHandlers.ofString()))
                .toArray(CompletableFuture[]::new);

        return CompletableFuture.allOf(requests)
                .thenApply(v -> {
                    return Stream.of(requests)
                            .map(future -> future.join())
                            .map(response -> new UrlTxt(response.uri().toString(), response.body()))
                            .collect(Collectors.toList());
                });
    }

    public static void main(String[] args) throws Exception {
        new HttpClientRequests().run();
    }
}
