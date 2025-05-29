package microservices.book.multiplication.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import microservices.book.multiplication.domain.Multiplication;
import microservices.book.multiplication.service.MultiplicationService;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MultiplicationController.class)
public class MultiplicationControllerTest {

  @MockitoBean
  private MultiplicationService multiplicationService;

  @Autowired
  private MockMvc mvc;

  // 이 객체는 initFields() 메소드를 이용해 자동으로 초기화
  private JacksonTester<Multiplication> json;

  @BeforeEach
  public void setup() {
    JacksonTester.initFields(this, new ObjectMapper());
  }

  @Test
  public void getRandomMultiplicationTest() throws Exception {
    // given
    given(multiplicationService.createRandomMultiplication())
            .willReturn(new Multiplication(70, 20));

    // when
    MockHttpServletResponse response = mvc.perform(
            get("/multiplications/random")
                    .accept(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();

    // then
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.getContentAsString())
            .isEqualTo(json.write(new Multiplication(70, 20)).getJson());
  }

}
