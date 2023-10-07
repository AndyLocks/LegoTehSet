package rebrickableAPI;

import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import rebrickableAPI.returned_objects.Set;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RebrickableAPIGetter {

    private List<Set> getSearchResultFromUrl(String urlSearch) {

        Dotenv config = Dotenv.configure().load();
        String rebrickableApiKey = config.get("REBRICKABLE_API_KEY");

        StringBuffer response;

        try{
            URL url = new URL(urlSearch);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "key " + rebrickableApiKey);

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<Set> arrayListOfLegoSets = new ArrayList<>();

        JSONObject jsonResponseObject = new JSONObject(response.toString());

        JSONArray jsonSets = jsonResponseObject.getJSONArray("results");

        for (int i = 0; i < jsonSets.length(); i++) {
            Set set = new Set();

            JSONObject jsonSet = jsonSets.getJSONObject(i);

            set.setSetNum(jsonSet.getString("set_num"));
            set.setName(jsonSet.getString("name"));
            set.setYear(jsonSet.getInt("year"));
            set.setThemeId(jsonSet.getInt("theme_id"));
            set.setNumParts(jsonSet.getInt("num_parts"));
            try{
                set.setSetImageUrl(jsonSet.getString("set_img_url"));
            }
            catch (JSONException e) {
                set.setSetImageUrl(null);
            }
            set.setSetUrl(jsonSet.getString("set_url"));
            set.setLastModifiedDate(jsonSet.getString("last_modified_dt"));
            arrayListOfLegoSets.add(set);
        }

        return arrayListOfLegoSets;
    }

    public Set getRundomSet() {
        Dotenv config = Dotenv.configure().load();
        String rebrickableApiKey = config.get("REBRICKABLE_API_KEY");

        StringBuffer response;

        try{
            URL url = new URL("https://rebrickable.com/api/v3/lego/sets/?page=1&page_size=1");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "key " + rebrickableApiKey);

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        JSONObject jsonResponseObject = new JSONObject(response.toString());

        int setsCount = jsonResponseObject.getInt("count");

        int randomSetNum = (int) ((Math.random() * (setsCount - 1)) + 1);

        StringBuffer response2;

        try{
            URL url = new URL(new StringBuilder("https://rebrickable.com/api/v3/lego/sets/?page=").append(randomSetNum).append("&page_size=1").toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "key " + rebrickableApiKey);

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            response2 = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response2.append(inputLine);
            }
            in.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        JSONObject jsonResponseObject2 = new JSONObject(response2.toString());
        JSONArray jsonSets = jsonResponseObject2.getJSONArray("results");

        Set set = new Set();

        JSONObject jsonSet = jsonSets.getJSONObject(0);

        set.setSetNum(jsonSet.getString("set_num"));
        set.setName(jsonSet.getString("name"));
        set.setYear(jsonSet.getInt("year"));
        set.setThemeId(jsonSet.getInt("theme_id"));
        set.setNumParts(jsonSet.getInt("num_parts"));
        try{
            set.setSetImageUrl(jsonSet.getString("set_img_url"));
        }
        catch (JSONException e) {
            set.setSetImageUrl(null);
        }
        set.setSetUrl(jsonSet.getString("set_url"));
        set.setLastModifiedDate(jsonSet.getString("last_modified_dt"));

        return set;
    }

    public List<Set> getSearchResult(String search) {

        return this.getSearchResultFromUrl("https://rebrickable.com/api/v3/lego/sets/?search=" + search);
    }

    public List<Set> getSearchResult(String search, OrderingType orderingType) {

        return this.getSearchResultFromUrl(new StringBuilder("https://rebrickable.com/api/v3/lego/sets/?ordering=")
                .append(orderingType.getJsonProperty())
                .append("&search=")
                .append(search).
                toString());
    }

    public List<Set> getPage(int page, int setCount) {

        return this.getSearchResultFromUrl(new StringBuilder("https://rebrickable.com/api/v3/lego/sets/?page=")
                .append(page)
                .append("&page_size=")
                .append(setCount).toString());
    }

    public List<Set> getPageWithFiveSets(int page) {
        return this.getPage(page, 5);
    }
}
