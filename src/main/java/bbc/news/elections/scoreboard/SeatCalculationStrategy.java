package bbc.news.elections.scoreboard;

import bbc.news.elections.model.ConstituencyResult;
import bbc.news.elections.model.PartyResult;
import bbc.news.elections.model.Scoreboard;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class SeatCalculationStrategy implements ScoreboardCalculationStrategy {

  private static final int WINNER_SEAT = 325;
  private static final String NO_WINNER = "noone";

  @Override
  public void calculate(Scoreboard scoreboard, List<ConstituencyResult> constituencyResults) {
    Map<String, Integer> totalSeats = new HashMap<>();

    for (ConstituencyResult result : constituencyResults) {
      PartyResult winningParty = result.getPartyResults().stream()
          .max((p1, p2) -> p1.getVotes().compareTo(p2.getVotes()))
          .orElse(null);

      if (Objects.nonNull(winningParty)) {
        totalSeats.merge(winningParty.getParty(), 1, Integer::sum);
      }
    }

    int overallSeat = totalSeats.values().stream().mapToInt(Integer::intValue).sum();
    scoreboard.setOverallSeat(overallSeat);
    scoreboard.setTotalSeats(totalSeats);
    scoreboard.setWinner(NO_WINNER);

    totalSeats.forEach((party, seats) -> {
      if (seats >= WINNER_SEAT) {
        scoreboard.setWinner(party);
      }
    });


  }
}
