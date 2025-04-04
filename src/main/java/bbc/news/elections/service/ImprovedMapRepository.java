package bbc.news.elections.service;

import bbc.news.elections.model.ConstituencyResult;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImprovedMapRepository implements ResultService {

  private static final Logger logger = LoggerFactory.getLogger(MapBasedRepository.class);
  private final Map<Integer, ConstituencyResult> results;

  public ImprovedMapRepository() {
    results = new ConcurrentHashMap<>();
  }

  @Override
  public ConstituencyResult GetResult(Integer id) {
    if (id == null) {
      logger.error("GetResult called with null id");
      throw new IllegalArgumentException("id cannot be null");
    }
    return results.get(id);
  }

  @Override
  public void NewResult(ConstituencyResult result) {
    if (result == null || result.getId() == null) {
      logger.error("NewResult called with invalid result: {}", result);
      throw new IllegalArgumentException("result and result id cannot be null");
    }
    results.put(result.getId(), result);
    logger.info("New result added: {}", result);
  }

  @Override
  public Map<Integer, ConstituencyResult> GetAll() {
    return results;
  }

  @Override
  public void reset() {
    results.clear();
    logger.info("All results have been reset");
  }
}
