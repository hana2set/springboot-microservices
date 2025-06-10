스프링 부트를 활용한 마이크로서비스 개발, 모이세스 메이세로 지음  
https://github.com/wikibook/springboot-microservices/tree/master/microservices-v1/social-multiplication

## 변경사항
- Java8 -> Java17
- JUnit4 -> JUnit5
  - @Before -> @BeforeEach
  - @MockBean -> @MockitoBean
- User h2테이블명 = Users
- spring.datasource.username=sa 추가 (없으면 h2-console 접근 불가)
- findOne -> findById(resultId).orElse(null)


--- 
