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

    public List<Set> getSearchResult(String search) {

        Dotenv config = Dotenv.configure().load();
        String rebrickableApiKey = config.get("REBRICKABLE_API_KEY");

        StringBuffer response;

        try{
            URL url = new URL("https://rebrickable.com/api/v3/lego/sets/?search=" + search);
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
}
