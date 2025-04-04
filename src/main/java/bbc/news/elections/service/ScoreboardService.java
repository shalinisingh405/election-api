package bbc.news.elections.service;

import bbc.news.elections.model.ConstituencyResult;
import bbc.news.elections.model.PartyResult;
import bbc.news.elections.model.Scoreboard;
import bbc.news.elections.scoreboard.ScoreboardCalculationStrategy;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public class ScoreboardService {

  private static final int WINNER_SEAT = 325;
  private static final String NO_WINNER = "noone";

  private final List<ScoreboardCalculationStrategy> scoreboardCalculationStrategies;

  public ScoreboardService(List<ScoreboardCalculationStrategy> scoreboardCalculationStrategies) {
    this.scoreboardCalculationStrategies = scoreboardCalculationStrategies;
  }

  public Scoreboard getScoreboardDP(List<ConstituencyResult> constituencyResultList) {
    Scoreboard scoreboard = new Scoreboard();

    for (ScoreboardCalculationStrategy scoreboardCalculationStrategy : scoreboardCalculationStrategies) {
      scoreboardCalculationStrategy.calculate(scoreboard, constituencyResultList);
    }

    return scoreboard;
  }

  public Scoreboard getScoreboard(List<ConstituencyResult> constituencyResultList) {

    Scoreboard scoreboard = new Scoreboard();

    Map<String, Integer> totalSeats = calculateSeats(constituencyResultList);
    scoreboard.setTotalSeats(totalSeats);

    Map<String, Integer> totalVotes = calculateTotalVotes(constituencyResultList);
    scoreboard.setTotalVotes(totalVotes);

    scoreboard.setWinner(calculateWinner(totalSeats));
    scoreboard.setSharePercentage(calculatesShare(totalVotes));

    int overallSeat = totalSeats.values().stream().mapToInt(Integer::intValue).sum();
    scoreboard.setOverallSeat(overallSeat);
    return scoreboard;
  }

  private Map<String, Integer> calculateSeats(List<ConstituencyResult> constituencyResultList) {

    Map<String, Integer> totalSeats = new HashMap<>();
    for (ConstituencyResult constituencyResult : constituencyResultList) {
      List<PartyResult> partyResultList = constituencyResult.getPartyResults();
      PartyResult winningParty = partyResultList.stream()
          .max((p1, p2) -> p1.getVotes().compareTo(p2.getVotes()))
          .orElse(null);

      if (Objects.nonNull(winningParty)) {
        totalSeats.put(winningParty.getParty(),
            totalSeats.getOrDefault(winningParty.getParty(), 0) + 1);
      }

    }
    return totalSeats;
  }

  private Map<String, BigDecimal> calculatesShare(Map<String, Integer> totalVotes) {

    Map<String, BigDecimal> sharePercentage = new HashMap<>();
    int overallVotes = totalVotes.values().stream().mapToInt(Integer::intValue).sum();

    for (Map.Entry<String, Integer> party : totalVotes.entrySet()) {
      int votes = party.getValue();

      // 100*n/total
      BigDecimal percentage = BigDecimal.valueOf(100).multiply(BigDecimal.valueOf(votes))
          .divide(BigDecimal.valueOf(overallVotes), 2, BigDecimal.ROUND_UP);
      sharePercentage.put(party.getKey(), percentage);
    }

    return sharePercentage;
  }

  private Map<String, Integer> calculateTotalVotes(
      List<ConstituencyResult> constituencyResultList) {

    Map<String, Integer> totalVotes = new HashMap<>();

    for (ConstituencyResult constituencyResult : constituencyResultList) {

      List<PartyResult> partyResultList = constituencyResult.getPartyResults();

      for (PartyResult partyResult : partyResultList) {

        String partyName = partyResult.getParty();
        totalVotes.put(partyName, totalVotes.getOrDefault(partyName, 0) + partyResult.getVotes());

      }
    }
    return totalVotes;

  }

  private String calculateWinner(Map<String, Integer> totalSeats) {
    for (Map.Entry<String, Integer> party : totalSeats.entrySet()) {
      if (party.getValue() >= WINNER_SEAT) {
        return party.getKey();
      }
    }
    return NO_WINNER;
  }

}
