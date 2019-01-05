package qenawi.panda.movie_search_list;

/**
 * Created by Andorid-win on 7/18/2018.
 */

public class Constants {
    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String MOVIES_TABLE_NAME = "movies";
    public static final String MOVIES_TABLE_NAME_DETAILS = "moviesdetails";
    public static final String DATABASE_NAME = "StackOverflow.db";
    public static final String TMDP_API_KEY = "7d1dd3f8f443ecde27c323485a170bdc";
    public static final String CONST_SEARCH_TEST_TITLE = "transformers";
    public static final String INVALID_INDEX = "Invalid index";
    public static final String UNSUPPORTED_OPERATION = "Unsupported operation";
    public static final String QUESTIONS_END_POINT = "questions?site=stackoverflow";
    public static final String QUESTIONS_END_POINT_QUERY = "tagged";

    public static class Movies {
        private static final String ImageBaseUrl = "https://image.tmdb.org/t/p/";
        public static final String PopularURL = "movie/popular";
        public static final String Top_RatedURL = "movie/top_rated";
        public static final String Movie_Detail = "movie/";

        public static String getImageBaseUrl(String RequiredWidth)
        {
            return ImageBaseUrl + RequiredWidth + "/";
        }

        public static String getMovieDetailUrl(int MovieID) {
            return Movie_Detail + String.valueOf(MovieID);
        }
    }
    public static class BundleFlags {
        public static final String MOVIE_ID = "MOVIE_ID";
    }
}
