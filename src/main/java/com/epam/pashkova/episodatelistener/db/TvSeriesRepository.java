package com.epam.pashkova.episodatelistener.db;

import com.epam.pashkova.episodatelistener.db.table.TvSeries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TvSeriesRepository extends JpaRepository<TvSeries, Long> {
    TvSeries findByTitle(String title);
}
