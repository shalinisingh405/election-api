package bbc.news.elections.scoreboard;

import bbc.news.elections.model.ConstituencyResult;
import bbc.news.elections.model.Scoreboard;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ShareCalculationStrategy implements ScoreboardCalculationStrategy {

  @Override
  public void calculate(Scoreboard scoreboard, List<ConstituencyResult> constituencyResults) {

    Map<String, Integer> totalVotes = new HashMap<>();
    constituencyResults.forEach(constituencyResult ->
        constituencyResult.getPartyResults().forEach(partyResult ->
            totalVotes.merge(partyResult.getParty(), partyResult.getVotes(), Integer::sum)
        )
    );

    int overallVotes = totalVotes.values().stream().mapToInt(Integer::intValue).sum();
    Map<String, BigDecimal> sharePercentage = new HashMap<>();
    totalVotes.forEach((party, votes) ->
        sharePercentage.put(party, BigDecimal.valueOf(100).multiply(BigDecimal.valueOf(votes))
            .divide(BigDecimal.valueOf(overallVotes), 2, BigDecimal.ROUND_UP))
    );
    scoreboard.setSharePercentage(sharePercentage);
  }
}
