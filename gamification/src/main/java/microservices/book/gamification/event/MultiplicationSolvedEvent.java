package microservices.book.gamification.event;

import lombok.*;

import java.io.Serializable;

/**
 * 시스템에서 곱셈 문제가 해결되었다는 사실을 모델링한 이벤트.
 * 곱셈에 대한 컨텍스트 정보를 제공.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
class MultiplicationSolvedEvent implements Serializable {

  private Long multiplicationResultAttemptId;
  private Long userId;
  private boolean correct;

}
