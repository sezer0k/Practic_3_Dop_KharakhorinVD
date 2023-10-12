package ru.mirea.kharakhorin.rim;

import com.fasterxml.jackson.databind.json.JsonMapper;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;


import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RimClient {
    public static void main(String[] args) throws IOException, SQLException {
        Retrofit client = new Retrofit
                .Builder()
                .baseUrl("https://rickandmortyapi.com")
                .addConverterFactory(JacksonConverterFactory.create(new JsonMapper()))
                .build();

        RimService rimService = client.create(RimService.class);

        Response<DTO> response = rimService
                .getSeries(1).execute();

        DTO dto = response.body();

        List<Episode> episodes = dto.getEpisodes().stream()
                .sorted(Comparator.comparing(Episode::getAmountOfCharacters).reversed())
                .collect(Collectors.toList());

        System.out.println(episodes.get(0).getTitle());

    }
}
