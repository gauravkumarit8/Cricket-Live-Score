package com.cric.apis.CrickInfo.entity;

import jakarta.persistence.*;

@Entity
@Table
public class matchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long matchId;

    private String teamHeading;
    private String matchNumberVenue;
    private String battingTeam;
    private String bowlTeam;
    private String battingTeamScore;
    private String bowlTeamScore;
    private String liveText;
    private String matchLink;
    private String textComplete;
    @Enumerated
    private MatchStatus status;
}
