package com.epam.pashkova.episodatelistener.db.table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PreRemove;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class SubscriberUser {
    @Id
    @SequenceGenerator(name = "subscriber_user_sequence", sequenceName = "subscriber_user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subscriber_user_sequence")
    private Long id;
    @Column(unique = true)
    private String login;
    private String password;

    @JsonIgnore
    @ManyToMany(mappedBy = "subscribersUserSet")
    private Set<TvSeries> tvSeriesSet = new HashSet<>();

    @PreRemove
    private void removeUserFromTvSeries() {
        tvSeriesSet.forEach(tvSeries -> tvSeries.getSubscribersUserSet().remove(this));
    }
}
