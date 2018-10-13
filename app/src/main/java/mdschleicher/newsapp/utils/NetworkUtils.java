package mdschleicher.newsapp.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    final static String NEWSAPI_BASE_URL = "https://newsapi.org/v1/articles";
    final static String PARAM_SOURCE = "source";
    final static String source = "the-next-web";
    final static String PARAM_SORT = "sortBy";
    final static String sortBy = "latest";
    final static String PARAM_APIKEY = "apiKey";
    final static String apiKey = "4d8af6f380b446c9b942b0a2d6170246";

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            if(scanner.hasNext())
                return scanner.next();
            else
                return null;

        } finally {
            urlConnection.disconnect();
        }
    }
}
