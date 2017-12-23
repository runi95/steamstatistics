package com.steamstatistics.data;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SuggestionRepository extends CrudRepository<SuggestionEntity, Long> {

    @Override
    List<SuggestionEntity> findAll();

    List<SuggestionEntity> findAllByCategory(String category);

    List<SuggestionEntity> findAllBySteamid(long steamid);
}
