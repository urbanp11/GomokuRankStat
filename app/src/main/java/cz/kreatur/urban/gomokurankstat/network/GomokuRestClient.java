package cz.kreatur.urban.gomokurankstat.network;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by Petr Urban on 07.03.16.
 */
public class GomokuRestClient {

    public static final String MODE_CUSTOM = "custom";

    public static final String COLUMN_PLAYER_NAME = "player_name";
    public static final String COLUMN_PLAYER_ELO = "player_elo";
    public static final String COLUMN_COUNTRY_CODE = "country_code";

    private static final String BASE_URL = "http://kreatur.cz/gomokuonline/api/?";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void getListOfPlayers(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
