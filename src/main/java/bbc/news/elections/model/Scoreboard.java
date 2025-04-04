package bbc.news.elections.model;

import java.math.BigDecimal;
import java.util.Map;

public class Scoreboard {


  private Map<String, Integer> totalSeats;
  private Map<String, Integer> totalVotes;

  public Map<String, BigDecimal> getSharePercentage() {
    return sharePercentage;
  }

  public void setSharePercentage(Map<String, BigDecimal> sharePercentage) {
    this.sharePercentage = sharePercentage;
  }

  private Map<String, BigDecimal> sharePercentage;

  public Map<String, Integer> getTotalVotes() {
    return totalVotes;
  }

  public void setTotalVotes(Map<String, Integer> totalVotes) {
    this.totalVotes = totalVotes;
  }



  public int getOverallSeat() {
    return overallSeat;
  }

  public void setOverallSeat(int overallSeat) {
    this.overallSeat = overallSeat;
  }

  private int overallSeat;

  public String getWinner() {
    return winner;
  }

  public void setWinner(String winner) {
    this.winner = winner;
  }

  private String winner;


  public Map<String, Integer> getTotalSeats() {
    return totalSeats;
  }

  public void setTotalSeats(Map<String, Integer> totalSeats) {
    this.totalSeats = totalSeats;
  }


}
