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

    private JSONObject getJSONObjectFromUrl(String urlSearch) {
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
        return new JSONObject(response.toString());
    }

    private List<Set> getSearchResultFromUrl(String urlSearch) {
        List<Set> arrayListOfLegoSets = new ArrayList<>();

        JSONObject jsonResponseObject = this.getJSONObjectFromUrl(urlSearch);

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
        JSONObject jsonResponseObject = this.getJSONObjectFromUrl("https://rebrickable.com/api/v3/lego/sets/?page=1&page_size=1");

        int setsCount = jsonResponseObject.getInt("count");

        int randomSetNum = (int) ((Math.random() * (setsCount - 1)) + 1);

        JSONObject jsonResponseObject2 = this.getJSONObjectFromUrl(new StringBuilder("https://rebrickable.com/api/v3/lego/sets/?page=").append(randomSetNum).append("&page_size=1").toString());
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

    public Set getRundomSet(Theme theme) {
        String themeUrl = new StringBuilder("https://rebrickable.com/api/v3/lego/sets/?page=1&page_size=1").append("&theme_id=").append(theme.getThemeId()).toString();
        JSONObject jsonObject = this.getJSONObjectFromUrl(themeUrl);
        int setsCount = jsonObject.getInt("count");
        int randomSetNum = (int) ((Math.random() * (setsCount - 1)) + 1);

        JSONObject jsonObject1 = this.getJSONObjectFromUrl(
                new StringBuilder("https://rebrickable.com/api/v3/lego/sets/?page=").append(randomSetNum).append("&page_size=1&theme_id=").append(theme.getThemeId()).toString()
        );

        JSONArray jsonSets = jsonObject1.getJSONArray("results");

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
