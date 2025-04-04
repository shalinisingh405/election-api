package bbc.news.elections.scoreboard;

import bbc.news.elections.model.ConstituencyResult;
import bbc.news.elections.model.Scoreboard;
import java.util.List;

public interface ScoreboardCalculationStrategy {

  void calculate(Scoreboard scoreboard, List<ConstituencyResult> constituencyResults);

}
