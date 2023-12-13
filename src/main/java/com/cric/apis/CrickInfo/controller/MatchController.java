package com.cric.apis.CrickInfo.controller;

import com.cric.apis.CrickInfo.entity.Match;
import com.cric.apis.CrickInfo.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class MatchController {
    private MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping
    public ResponseEntity<List<Match>> getAllMatchPlayed(){
        return new ResponseEntity<>(this.matchService.getAllMatch(), HttpStatus.OK);
    }

    @GetMapping("/live")
    public ResponseEntity<List<Match> >getAllLiveMatch(){
        return new ResponseEntity<>(this.matchService.getLiveMatchScores(), HttpStatus.OK);
    }

}
