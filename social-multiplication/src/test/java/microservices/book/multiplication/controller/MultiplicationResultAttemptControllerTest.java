package microservices.book.multiplication.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import microservices.book.multiplication.domain.Multiplication;
import microservices.book.multiplication.domain.MultiplicationResultAttempt;
import microservices.book.multiplication.domain.User;
import microservices.book.multiplication.service.MultiplicationService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MultiplicationResultAttemptController.class)
public class MultiplicationResultAttemptControllerTest {

  @MockitoBean
  private MultiplicationService multiplicationService;

  @Autowired
  private MockMvc mvc;

  // 이 객체는 initFields() 메소드를 이용해 자동으로 초기화
  private JacksonTester<MultiplicationResultAttempt> jsonResultAttempt;
  private JacksonTester<List<MultiplicationResultAttempt>> jsonResultAttemptList;

  @BeforeEach
  public void setup() {
    JacksonTester.initFields(this, new ObjectMapper());
  }

  @Test
  public void postResultReturnCorrect() throws Exception {
    genericParameterizedTest(true);
  }

  @Test
  public void postResultReturnNotCorrect() throws Exception {
    genericParameterizedTest(false);
  }

  void genericParameterizedTest(final boolean correct) throws Exception {
    // given (지금 서비스를 테스트하는 것이 아님)
    given(multiplicationService
            .checkAttempt(any(MultiplicationResultAttempt.class)))
            .willReturn(correct);
    User user = new User("john");
    Multiplication multiplication = new Multiplication(50, 70);
    MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(
            user, multiplication, 3500, correct);

    // when
    MockHttpServletResponse response = mvc.perform(
            post("/results").contentType(MediaType.APPLICATION_JSON)
                    .content(jsonResultAttempt.write(attempt).getJson()))
            .andReturn().getResponse();

    // then
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.getContentAsString()).isEqualTo(
            jsonResultAttempt.write(
                    new MultiplicationResultAttempt(attempt.getUser(),
                            attempt.getMultiplication(),
                            attempt.getResultAttempt(),
                            correct)
            ).getJson());
  }

  @Test
  public void getUserStats() throws Exception {
    // given
    User user = new User("john_doe");
    Multiplication multiplication = new Multiplication(50, 70);
    MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(
            user, multiplication, 3500, true);
    List<MultiplicationResultAttempt> recentAttempts = Lists.newArrayList(attempt, attempt);
    given(multiplicationService
            .getStatsForUser("john_doe"))
            .willReturn(recentAttempts);

    // when
    MockHttpServletResponse response = mvc.perform(
            get("/results").param("alias", "john_doe"))
            .andReturn().getResponse();

    // then
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.getContentAsString()).isEqualTo(
            jsonResultAttemptList.write(
                    recentAttempts
            ).getJson());
  }

  @Test
  public void getResultByIdTest() throws Exception {
    // given
    User user = new User("john_doe");
    Multiplication multiplication = new Multiplication(50, 70);
    MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(
            user, multiplication, 3500, true);
    given(multiplicationService.getResultById(4L)).willReturn(attempt);

    // when
    MockHttpServletResponse response = mvc.perform(
            get("/results/4"))
            .andReturn().getResponse();

    // then
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.getContentAsString()).isEqualTo(
            jsonResultAttempt.write(attempt).getJson());
  }

}