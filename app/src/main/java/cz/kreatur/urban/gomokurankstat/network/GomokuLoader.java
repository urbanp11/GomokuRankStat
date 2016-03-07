package cz.kreatur.urban.gomokurankstat.network;

import android.os.AsyncTask;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.kreatur.urban.gomokurankstat.ladder.model.Player;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpException;

/**
 * TODO: implement lazy loading
 * Created by Petr Urban on 07.03.16.
 */
public class GomokuLoader {
    private static GomokuLoader instance = new GomokuLoader();
    private static int sum = 10000;
    private static int shift = 0;
    private static String mode = GomokuRestClient.MODE_CUSTOM;

    private GomokuDownloaderCallback callback;

    private GomokuLoader() {}

    public static GomokuLoader getInstance() {
        return instance;
    }

    public void downloadData(final GomokuDownloaderCallback callback) {
        this.callback = callback;
        callback.onStart();

        GomokuRestClient client = new GomokuRestClient();

        String url = "sum=" + sum + "&shift=" + shift + "&mode=" + mode;
        client.getListOfPlayers(url, null, new JsonHttpResponseHandler() {

            @Override
            public void onCancel() {
                callback.onError(null);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                callback.onError(new HttpException(throwable.getMessage()));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                callback.onError(new HttpException(throwable.getMessage()));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                callback.onError(new HttpException(throwable.getMessage()));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                new BindDataToListTask(response).execute();
            }
        });

    }

    public void cancel(boolean b) {
        //TODO make loader cancelable
    }

    // parsing from json takes some time - better do in other thread
    private class BindDataToListTask extends AsyncTask<Void, Void, List<Player>> {
        private final JSONArray jsonArray;

        public BindDataToListTask(JSONArray jsonArray) {
            this.jsonArray = jsonArray;
        }

        @Override
        protected List<Player> doInBackground(Void... params) {
            List<Player> players = new ArrayList<>();

            int arrayLength = jsonArray.length();
            for (int i = 0; i < arrayLength; i++) {
                try {

                    JSONObject jsonObjectPlayer =  jsonArray.getJSONObject(i);

                    String name = jsonObjectPlayer.getString(GomokuRestClient.COLUMN_PLAYER_NAME);
                    int elo = jsonObjectPlayer.getInt(GomokuRestClient.COLUMN_PLAYER_ELO);
                    String code = jsonObjectPlayer.getString(GomokuRestClient.COLUMN_COUNTRY_CODE);

                    Player player = new Player(code, elo, name);
                    players.add(player);

                    Log.d("Player", player.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return players;
        }

        //update thread after binding
        @Override
        protected void onPostExecute(List<Player> players) {
            callback.onSuccess(players);
        }
    }

    public interface GomokuDownloaderCallback {
        void onStart();
        void onSuccess(List<Player> countries);
        void onError(Exception e);
    }
}
