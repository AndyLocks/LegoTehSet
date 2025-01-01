package org.lts.lego_teh_set.rebrickableAPI;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.lts.lego_teh_set.rebrickableAPI.dto.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Random;

/// Makes requests to [Rebrickable API](https://rebrickable.com/api/v3/docs/).
public class RebrickableAPIRepository {

    private final Logger LOG = LoggerFactory.getLogger(RebrickableAPIRepository.class);
    private final String token;
    private final RestClient restClient = RestClient.create("https://rebrickable.com/api/v3");
    private final Random random = new Random();

    public RebrickableAPIRepository(String token) {
        this.token = token;
    }

    /// Random set from the database.
    ///
    /// @return random set
    /// @see Set
    public Set randomSet() {
        int count = restClient.get()
                .uri("/lego/sets/?page=1&page_size=1")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "key " + token)
                .retrieve()
                .toEntity(Count.class)
                .getBody()
                .count();

        return restClient.get()
                .uri("/lego/sets/?page={page}&page_size=1", random.nextInt(count + 1))
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "key " + token)
                .retrieve()
                .toEntity(Results.class)
                .getBody().sets().get(0);
    }

    /// Random set from the database.
    ///
    /// @param theme lego set theme
    /// @return random lego set
    /// @see Theme
    /// @see Set
    public Set randomSet(Theme theme) {
        int count = restClient.get()
                .uri("/lego/sets/?page=1&page_size=1&theme_id={theme}", theme.getThemeId())
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "key " + token)
                .retrieve()
                .toEntity(Count.class)
                .getBody()
                .count();

        return restClient.get()
                .uri("/lego/sets/?page={page}&page_size=1&theme_id={theme}", random.nextInt(count + 1), theme.getThemeId())
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "key " + token)
                .retrieve()
                .toEntity(Results.class)
                .getBody().sets().get(0);
    }

    /// All sets found by request.
    ///
    /// @param request a search request
    /// @return set list
    /// @see Set
    public List<Set> search(String request) {
        return restClient.get()
                .uri("/lego/sets/?search={request}", request)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "key " + token)
                .retrieve()
                .toEntity(Results.class)
                .getBody()
                .sets();
    }

    /// All sets found by request.
    ///
    /// @param request      a search request
    /// @param orderingType sort type
    /// @return set list
    /// @see Set
    /// @see OrderingType
    public List<Set> search(String request, OrderingType orderingType) {
        return restClient.get()
                .uri("/lego/sets/?search={request}&ordering={ordering}", request, orderingType.getJsonProperty())
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "key " + token)
                .retrieve()
                .toEntity(Results.class)
                .getBody()
                .sets();
    }

    /// Searches for exactly one set by its "rebrickable" id
    ///
    /// @param setNum specific identifier of "rebrickable" id
    /// @return null if set was not found
    public Set setFromId(String setNum) {
        try {
            return restClient.get()
                    .uri("/lego/sets/{setNum}/", setNum)
                    .accept(MediaType.APPLICATION_JSON)
                    .header("Authorization", "key " + token)
                    .retrieve()
                    .toEntity(Set.class)
                    .getBody();
        } catch (HttpClientErrorException.NotFound _) {
            return null;
        }
    }

    private record Count(@JsonProperty("count") int count) {
    }

    private record Results(@JsonProperty("results") List<Set> sets) {
    }

}
