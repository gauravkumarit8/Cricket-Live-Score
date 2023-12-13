package com.cric.apis.CrickInfo.service;
import com.cric.apis.CrickInfo.entity.Match;
import com.cric.apis.CrickInfo.repository.MatchRepo;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

@Service
public class MatchService {
    private MatchRepo matchRepo;
    @Autowired
    public MatchService(MatchRepo matchRepo){
        this.matchRepo=matchRepo;
    }

    public List<Match> getAllMatch(){
        return matchRepo.findAll();
    }

    public List<Match> getLiveMatchScores() {
        List<Match> matches = new ArrayList<>();
        try {
            String url = "https://www.cricbuzz.com/cricket-match/live-scores";
            Document document = Jsoup.connect(url).get();
            Elements liveScoreElements = document.select("div.cb-mtch-lst.cb-tms-itm");
            for (Element match : liveScoreElements) {
                HashMap<String, String> liveMatchInfo = new LinkedHashMap<>();
                String teamsHeading = match.select("h3.cb-lv-scr-mtch-hdr").select("a").text();
                String matchNumberVenue = match.select("span").text();
                Elements matchBatTeamInfo = match.select("div.cb-hmscg-bat-txt");
                String battingTeam = matchBatTeamInfo.select("div.cb-hmscg-tm-nm").text();
                String score = matchBatTeamInfo.select("div.cb-hmscg-tm-nm+div").text();
                Elements bowlTeamInfo = match.select("div.cb-hmscg-bwl-txt");
                String bowlTeam = bowlTeamInfo.select("div.cb-hmscg-tm-nm").text();
                String bowlTeamScore = bowlTeamInfo.select("div.cb-hmscg-tm-nm+div").text();
                String textLive = match.select("div.cb-text-live").text();
                String textComplete = match.select("div.cb-text-complete").text();
                //getting match link
                String matchLink = match.select("a.cb-lv-scrs-well.cb-lv-scrs-well-live").attr("href").toString();

                Match match1 = new Match();
                match1.setTeamHeading(teamsHeading);
                match1.setMatchNumberVenue(matchNumberVenue);
                match1.setBattingTeam(battingTeam);
                match1.setBattingTeamScore(score);
                match1.setBowlTeam(bowlTeam);
                match1.setBowlTeamScore(bowlTeamScore);
                match1.setLiveText(textLive);
                match1.setMatchLink(matchLink);
                match1.setTextComplete(textComplete);
                match1.setMatchStatus();

                //update the match data to database
                updateMatchInDb(match1);

                matches.add(match1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return matches;
    }

    private void updateMatchInDb(Match match1) {
        Match match=this.matchRepo.findByTeamHeading(match1.getTeamHeading()).orElse(null);
        if(match==null){
            matchRepo.save(match1);
        }else{
            match1.setMatchId(match.getMatchId());
            matchRepo.save(match1);
        }
    }

    //public List<List<String>> getCWC2023PointTable(){}

    public Optional<Match> getAllMatchOfTeamByTeamName(String teamName){
        return matchRepo.findByTeamHeading(teamName);
    }
}
