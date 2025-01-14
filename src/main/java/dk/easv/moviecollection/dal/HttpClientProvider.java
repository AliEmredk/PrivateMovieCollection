package dk.easv.moviecollection.dal;

import java.net.URI;
import java.net.http.HttpClient;

public abstract class HttpClientProvider
{
  private static final HttpClient client = HttpClient.newHttpClient();
  public static String apiKeyPleaseDoNotSteal = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4ZjJjOWFhNDZlNWUzMmIyYWZhYTUxMTZhYmE5ZDg3NyIsIm5iZiI6MTczNjg5MTg1OS40NjIwMDAxLCJzdWIiOiI2Nzg2ZGRkMzhjMjMwOWNlYWZiYjkwZTEiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.Vwr_SMqM9FcTiZwxkR7GLFoy8xE_gQ2taF9X4cfe5GI";
  public static String apiMovieSearchUrl = "https://api.themoviedb.org/3/search/movie?query=";
  public static String apiMovieAttributes = "&include_adult=false&language=en-US&page=1";



  public static HttpClient getClient() {
    return client;
  }
}
