package com.cric.apis.CrickInfo.repository;

import com.cric.apis.CrickInfo.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MatchRepo extends JpaRepository<Match,Long> {
    //get all match with the team name
    Optional<Match> findByTeamHeading(String teamHeading);
}
