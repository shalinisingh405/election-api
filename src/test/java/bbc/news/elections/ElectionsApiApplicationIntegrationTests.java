package bbc.news.elections;

import bbc.news.elections.model.ConstituencyResult;
import bbc.news.elections.model.Scoreboard;
import bbc.news.elections.service.ResultService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.io.InputStream;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ElectionsApiApplicationIntegrationTests {
	@LocalServerPort
	private int port;

	private URL base;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private TestRestTemplate template;

	@Autowired
	private ResultService resultService;

	@BeforeEach
	public void setUp() throws Exception {
		this.base = new URL("http://localhost:" + port + "/");
	}

	@AfterEach
	public void reset() throws Exception {
		resultService.reset();
	}

	@Test
	public void first2Test() throws Exception {
		Scoreboard scoreboard = runTest(2);

		assertNotNull(scoreboard);

		// LD = 5197+3942=39
		assertEquals(5197+3942, scoreboard.getTotalVotes().get("LD"));
		assertEquals(BigDecimal.valueOf(17.48), scoreboard.getSharePercentage().get("LD"));

		// assert LD == 1
		// assert LAB = 4
		// assert winner = noone
	}

	@Test
	public void first5Test() throws Exception {
		Scoreboard scoreboard = runTest(5);

		assertNotNull(scoreboard);
		assertEquals(1, scoreboard.getTotalSeats().get("LD"));
		assertEquals(4, scoreboard.getTotalSeats().get("LAB"));
		assertEquals("noone", scoreboard.getWinner());

		// assert LD == 1
		// assert LAB = 4
		// assert winner = noone
	}

	@Test
	public void first100Test() throws Exception {
		Scoreboard scoreboard = runTest(100);

		assertNotNull(scoreboard);

		assertEquals(12, scoreboard.getTotalSeats().get("LD"));
		assertEquals(56, scoreboard.getTotalSeats().get("LAB"));
		assertEquals(31, scoreboard.getTotalSeats().get("CON"));
		assertEquals("noone", scoreboard.getWinner());
		// assert LD == 12
		// assert LAB == 56
		// assert CON == 31
		// assert winner = noone
	}

	@Test
	public void first554Test() throws Exception {
		Scoreboard scoreboard = runTest(554);

		assertNotNull(scoreboard);

		assertEquals(52, scoreboard.getTotalSeats().get("LD"));
		assertEquals(325, scoreboard.getTotalSeats().get("LAB"));
		assertEquals(167, scoreboard.getTotalSeats().get("CON"));
		assertEquals("LAB", scoreboard.getWinner());
		// assert LD == 52
		// assert LAB = 325
		// assert CON = 167
		// assert winner = LAB
	}

	@Test
	public void allTest() throws Exception {
		Scoreboard scoreboard = runTest(650);

		assertNotNull(scoreboard);

		assertEquals(62, scoreboard.getTotalSeats().get("LD"));
		assertEquals(349, scoreboard.getTotalSeats().get("LAB"));
		assertEquals(210, scoreboard.getTotalSeats().get("CON"));
		assertEquals("LAB", scoreboard.getWinner());
		assertEquals(650, scoreboard.getOverallSeat());

		// assert LD == 62
		// assert LAB == 349
		// assert CON == 210
		// assert winner = LAB
		// assert sum = 650
	}


	private Scoreboard runTest(int numberOfResults) throws Exception {
		for (int i = 1; i <= numberOfResults; i++ ) {
			Class<?> clazz  = this.getClass();
			InputStream is = clazz.getResourceAsStream(String.format("/sample-election-results/result%s.json",String.format("%03d",i)));
			ConstituencyResult cr = objectMapper.readValue(is, ConstituencyResult.class);
			template.postForEntity(base.toString()+"/result", cr,String.class);
		}
		ResponseEntity<Scoreboard> scores = template.getForEntity(base.toString()+"/scoreboard", Scoreboard.class);
		return scores.getBody();
	}
}
