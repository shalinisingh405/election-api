package bbc.news.elections.controllers;

import bbc.news.elections.model.ConstituencyResult;
import bbc.news.elections.model.Scoreboard;
import bbc.news.elections.service.ResultNotFoundException;
import bbc.news.elections.service.ResultService;
import bbc.news.elections.service.ScoreboardService;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResultsController {

  private final ResultService resultService;
  private final ScoreboardService scoreboardService;

  public ResultsController(ResultService resultService, ScoreboardService scoreboardService) {
    this.resultService = resultService;
    this.scoreboardService = scoreboardService;
  }

  @GetMapping("/result/{id}")
  ConstituencyResult getResult(@PathVariable Integer id) {
    ConstituencyResult result = resultService.GetResult(id);
    if (result == null) {
      throw new ResultNotFoundException(id);
    }
    return resultService.GetResult(id);
  }

  @PostMapping("/result")
  ResponseEntity<String> newResult(@RequestBody ConstituencyResult result) {
    if (result.getId() != null) {
      resultService.NewResult(result);
      return ResponseEntity.created(URI.create("/result/" + result.getId())).build();
    }
    return ResponseEntity.badRequest().body("Id was null");
  }

  @GetMapping("/scoreboard")
  Scoreboard getScoreboard() {

    List<ConstituencyResult> constituencyResults = new ArrayList<>(resultService.GetAll().values());
  //  return scoreboardService.getScoreboard(constituencyResults);
    return scoreboardService.getScoreboardDP(constituencyResults); // with Design Pattern
  }

}
