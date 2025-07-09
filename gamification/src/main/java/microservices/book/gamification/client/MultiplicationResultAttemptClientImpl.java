package microservices.book.gamification.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import microservices.book.gamification.client.dto.MultiplicationResultAttempt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Multiplication 마이크로서비스와 REST 로 연결하기 위한
 * MultiplicationResultAttemptClient 인터페이스의 구현체
 */
@Slf4j
@Component
class MultiplicationResultAttemptClientImpl implements MultiplicationResultAttemptClient {

  private final RestTemplate restTemplate;
  private final String multiplicationHost;

  @Autowired
  public MultiplicationResultAttemptClientImpl(final RestTemplate restTemplate,
                                               @Value("${multiplicationHost}") final String multiplicationHost) {
    this.restTemplate = restTemplate;
    this.multiplicationHost = multiplicationHost;
  }

  @CircuitBreaker(name = "myCircuitBreaker",fallbackMethod = "defaultResult")
  @Override
  public MultiplicationResultAttempt retrieveMultiplicationResultAttemptById(final Long multiplicationResultAttemptId) {
    return restTemplate.getForObject(
            multiplicationHost + "/results/" + multiplicationResultAttemptId,
            MultiplicationResultAttempt.class);
  }

  public MultiplicationResultAttempt defaultResult(final Long multiplicationResultAttemptId, final Throwable t) {
    log.error("Fallback called due to: {}", t.getMessage(), t);
    log.debug(multiplicationResultAttemptId.toString());

    return new MultiplicationResultAttempt("fakeAlias",
            10, 10, 100, true);
  }
}
