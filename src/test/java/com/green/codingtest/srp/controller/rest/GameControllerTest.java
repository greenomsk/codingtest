package com.green.codingtest.srp.controller.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.green.codingtest.srp.service.GameService;
import com.green.codingtest.srp.service.ValidationService;
import com.green.codingtest.srp.service.model.Game;
import com.green.codingtest.srp.service.model.GameState;
import com.green.codingtest.srp.service.model.Gesture;
import com.green.codingtest.srp.service.model.MoveResult;
import com.green.codingtest.srp.service.model.Result;
import com.green.codingtest.srp.service.model.TotalScore;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * class GameControllerTest
 *
 * @author <a href="mailto:greenomsk@gmail.com">Andrey Grinenko</a>
 * @since 25.07.2019
 */
public class GameControllerTest extends AbstractTestNGSpringContextTests {

    private GameService gameServiceMock;

    private ValidationService validationServiceMock;

    private GameController testee;

    private MockMvc mvc;

    @BeforeMethod
    public void setUp() {

        gameServiceMock = Mockito.mock(GameService.class);

        validationServiceMock = Mockito.mock(ValidationService.class);

        testee = new GameController(
            gameServiceMock,
            validationServiceMock,
            new ModelMapper()
        );

        final MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.setObjectMapper(objectMapperBuilder().build());

        mvc = standaloneSetup(testee)
            .setMessageConverters(messageConverter)
            .build();
    }

    @Test
    public void testStartNewGame() throws Exception {

        when(gameServiceMock.startNewGame())
            .thenReturn(
                new Game()
                    .setId(1L)
                    .setState(GameState.STARTED)
            );

        mvc.perform(MockMvcRequestBuilders.put("/1.0/scissors-rock-paper/start-new-game"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json("1"));
    }

    @Test
    public void testFinishGame() throws Exception {
        when(gameServiceMock.finishGame(eq(1L)))
            .thenReturn(
                new TotalScore()
                    .setPlayerPoints(10)
                    .setOpponentPoints(20)
            );

        mvc.perform(MockMvcRequestBuilders.post("/1.0/scissors-rock-paper/1/finish-game"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.player-points", is(10)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.opponent-points", is(20)));
    }

    @Test
    public void testMakeMove() throws Exception {
        when(gameServiceMock.makeMove(argThat(move -> move.getGameId().equals(1L) && move.getPlayerGesture() == Gesture.ROCK)))
            .thenReturn(
                new MoveResult()
                    .setPlayerGesture(Gesture.PAPER)
                    .setOpponentGesture(Gesture.ROCK)
                    .setResult(Result.WIN)
            );

        mvc.perform(
            MockMvcRequestBuilders
                .put("/1.0/scissors-rock-paper/1/make-move")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content("{\"player-gesture\":\"ROCK\"}"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.player-gesture", is("PAPER")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.opponent-gesture", is("ROCK")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.result", is("WIN")));
    }

    @Test
    public void testGetTotalScore() throws Exception {
        when(gameServiceMock.getTotalScore(eq(1L)))
            .thenReturn(
                new TotalScore()
                    .setPlayerPoints(10)
                    .setOpponentPoints(20)
            );

        mvc.perform(MockMvcRequestBuilders.get("/1.0/scissors-rock-paper/1/total-score"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.player-points", is(10)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.opponent-points", is(20)));
    }

    private Jackson2ObjectMapperBuilder objectMapperBuilder() {
        return new Jackson2ObjectMapperBuilder()
            .dateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"))
            .serializationInclusion(JsonInclude.Include.NON_NULL)
            .featuresToEnable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
            .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .failOnUnknownProperties(false);
    }
}