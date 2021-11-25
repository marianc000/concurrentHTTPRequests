/*
 * Here should be licence
 */
package shared;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {
    
    public static String get(String url) throws Exception {
         var con = (HttpURLConnection) new URL(url).openConnection();
        con.setInstanceFollowRedirects(false);
        if (con.getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND) {
            return ""; // 404 throws FileNotFoundException 
        }
        try ( BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            var response = new StringBuilder();
            String line;
            
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            
            return response.toString();
        }
    }
}
