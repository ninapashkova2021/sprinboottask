package com.epam.pashkova.episodatelistener.db.table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TvSeries {
    @Id
    @SequenceGenerator(name = "tv_series_sequence", sequenceName = "tv_series_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tv_series_sequence")
    private Long id;
    private String title;
    private String year;
    private Integer seasonsCount;
    private LocalDate nextEpisodeDate;
    private Double rating;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_tv_series",
            joinColumns = @JoinColumn(name = "tv_series_id"),
            inverseJoinColumns = @JoinColumn(name = "user_login"))
    private Set<SubscriberUser> subscribersUserSet = new HashSet<>();
}
