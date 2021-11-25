/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package shared;


import static shared.HttpUtils.get;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class UrlSource {

    public static List<String> getUrlsFromUrl(String url) throws Exception {
        return Pattern.compile("href=\"(https:[^\"]+)\"")
                .matcher(get(url))
                .results()
                .map(r -> r.group(1))
                .collect(Collectors.toList());
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException, Exception {
        getUrlsFromUrl("https://www.bbc.com/news/world").forEach(r -> System.out.println(r));
    }
}
