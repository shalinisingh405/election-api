package bbc.news.elections.scoreboard;

import bbc.news.elections.model.ConstituencyResult;
import bbc.news.elections.model.PartyResult;
import bbc.news.elections.model.Scoreboard;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class VoteCalculationStrategy implements ScoreboardCalculationStrategy {

  @Override
  public void calculate(Scoreboard scoreboard, List<ConstituencyResult> constituencyResults) {
    Map<String, Integer> totalVotes = new HashMap<>();

    for (ConstituencyResult constituencyResult : constituencyResults) {

      List<PartyResult> partyResultList = constituencyResult.getPartyResults();

      for (PartyResult partyResult : partyResultList) {

        String partyName = partyResult.getParty();
        totalVotes.put(partyName, totalVotes.getOrDefault(partyName, 0) + partyResult.getVotes());

      }
    }
    scoreboard.setTotalVotes(totalVotes);

  }
}
