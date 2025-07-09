스프링 부트를 활용한 마이크로서비스 개발, 모이세스 메이세로 지음  
https://github.com/wikibook/springboot-microservices/tree/master/microservices-v1/social-multiplication

## 변경사항
- maven -> gradle
- Java8 -> Java17
- JUnit4 -> JUnit5
  - @Before -> @BeforeEach
  - @MockBean -> @MockitoBean
- H2
  - User h2테이블명 = Users
  - `spring.datasource.username=sa` 추가 (없으면 h2-console 접근 불가)
  - `DB_CLOSE_ON_EXIT=FALSE;` 삭제 (`AUTO_SERVER=TRUE`와 같이 못쓰게 바뀜)
- findOne -> findById(resultId).orElse(null)
- WebMvcConfigurerAdapter -> WebMvcConfigurer(인터페이스로 변경됨).
  - [Spring 공식 문서 > webmvc-cors](https://docs.spring.io/spring-framework/reference/web/webmvc-cors.html) 참고
- 상당 수 라이브러리가 spring 관리 라이브러리에서 제거됐음으로, 변경 
  - API 게이트웨이: `Zuul` -> Spring Cloud `Gateway`
  - 로드밸런서: `Ribbon` -> Spring Cloud `Gateway`
    - application.yml 수정
    - default-filter의 StripPrefix 속성이 안먹어 하나씩 필터링
  - 서비스 디스커버리: `Eureka` -> Spring Cloud `Consul`
  - 서비스 모니터링: `Actuator`
  - 서킷 브레이커: `hystrix` -> `resilience4j`

## 실행법

### JETTY
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

### RabbitMQ
1. [해당 문서(AMQP)](https://docs.spring.io/spring-boot/reference/messaging/amqp.html)의 안내를 따름
2. `rabbitMQ service - start` 실행

### Consul
1. registry에 등록할 서버(client)에 의존성 설치
    ``` bash
   // spring boot 3.5.0 이상
    ext {
        set('springCloudVersion', "2025.0.0")
    }
   
   ...

	implementation 'org.springframework.cloud:spring-cloud-starter-consul-discovery'
   
    // consul에서 Health Check용 
	implementation 'org.springframework.boot:spring-boot-starter-actuator'
   
   ...

    
    dependencyManagement {
        imports {
            mavenBom "org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}"
        }
    }
    ```
3. Consul 설치 및 실행 (docker로 진행함)
    ```shell
    docker run -d --name=consul -p 8500:8500 consul:latest
    ```
3. Consul UI 접속
    ```
    http://localhost:8500/
    ```

--- 
