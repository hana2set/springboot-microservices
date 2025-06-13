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
- WebMvcConfigurerAdapter -> WebMvcConfigurer(인터페이스로 변경됨).
  - [Spring 공식 문서 > webmvc-cors](https://docs.spring.io/spring-framework/reference/web/webmvc-cors.html) 참고
- Zuul이 spring 관리 라이브러리에서 제거됐음으로, Spring Gateway 사용
  - application.yml 수정
  - default-filter의 StripPrefix 속성이 안먹어 하나씩 필터링



### JETTY 실행법
1. [JETTY 다운로드](https://jetty.org/download.html)
2. 환경 변수 설정 후 start.jar 실행
    ```cmd
    SET JETTY_HOME=C:\developTool\jetty-home-12.0.22
    echo %JETTY_HOME%
    // jetty_base로 만들 폴더로 이동 (ui)
    cd %project_path%>/ui
    java -jar %JETTY_HOME%/start.jar --list-modules=*
    java -jar %JETTY_HOME%/start.jar --add-modules=http
    java -jar %JETTY_HOME%/start.jar --add-modules=ee10-deploy
    java -jar %JETTY_HOME%/start.jar
    ```
3. 해당 웹페이지가 제대로 뜨는지 확인 (여기서는 localhost:9090/ui)

--- 
