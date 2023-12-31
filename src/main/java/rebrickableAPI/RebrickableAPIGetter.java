package rebrickableAPI;

import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import rebrickableAPI.returned_objects.Set;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Делает запросы к серверам rebrickable
 * <p>
 * Является бином spring framework
 * <a href="https://rebrickable.com/api/v3/docs/">Документация rebrickable API</a>
 */
@Component
public class RebrickableAPIGetter {

    private JSONObject getJSONObjectFromUrl(String urlSearch) {
        Dotenv config = Dotenv.configure().load();
        String rebrickableApiKey = config.get("REBRICKABLE_API_KEY");

        StringBuilder response;

        try{
            URL url = new URL(urlSearch);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "key " + rebrickableApiKey);

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            response = new StringBuilder();
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

    /**
     * Случайный набор из базы данных
     *
     * @return случайный набор
     * @see Set
     */
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

    /**
     * Случайный набор из базы данных
     *
     * @param theme тематика набора
     * @return случайный набор
     * @see Theme
     * @see Set
     */
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

    /**
     * Все наборы найденные по запросу
     *
     * @param search запрос для поиска
     * @return список всех наборов
     * @see Set
     */
    public List<Set> getSearchResult(String search) {

        return this.getSearchResultFromUrl("https://rebrickable.com/api/v3/lego/sets/?search=" + search);
    }

    /**
     * Все наборы найденные по запросу
     *
     * @param search запрос для поиска
     * @param orderingType тип для сортировки
     * @return список всех наборов
     * @see Set
     * @see OrderingType
     */
    public List<Set> getSearchResult(String search, OrderingType orderingType) {

        return this.getSearchResultFromUrl(new StringBuilder("https://rebrickable.com/api/v3/lego/sets/?ordering=")
                .append(orderingType.getJsonProperty())
                .append("&search=")
                .append(search).
                toString());
    }
}
