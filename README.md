# spring-tutorial

## 1️⃣ spring-tutorial를 완료하자!
### 1주차 미션 성공 !

  > api 요청 결과
<img width="428" alt="image" src="https://github.com/user-attachments/assets/0e633b91-355a-4947-9ce2-619490ddfcab" />



## 2️⃣ spring이 지원하는 기술들(IoC/DI, AOP, PSA 등)을 자유롭게 조사해요

  ### 1. AOP (Aspect-Oriented Programming)

  > AOP는 관심사를 분리하는 기술
  > 비즈니스 로직과 **공통 기능(로깅, 보안, 트랜잭션 등)**을 분리해서 관리할 수 있게 해

  ### 핵심 개념 용어
  - Aspect : 공통 관심사를 정의한 모듈
  - JoinPoint : Advice가 적용될 수 있는 지점
  - Pointcut : Advice가 적용될 대상을 지정하는 조건식
  - Advice : 실제로 실행되는 공동 기능
  - Weaving : Pointcut 조건에 따라 Aspect를 실제 코드에 결합시키는 과정

  ### 흐름
  ```
  [Controller / Service]
    ↓
  [JoinPoint] <- 메서드 실행 지점
    ↓
  [Advice 실행] <- 로그 찍기 등
    ↓
  [비즈니스 로직 수행]
    ↓
  [Advice 실행] <- 예외 처리 등
  ```

  ### AOP Advice 종류
  - `@Before` : 메서드 실행 전에
  - `@After` : 메서드 실행 후
  - `@AfterReturning` : 메서드가 정상 종료된 후
  - `@AfterThrowing` : 예외 발생 시
  - `@Around` : 메서드 전 후

  > 핵심 로직은 **Service**에
  > 공통 기능은 **Aspect**에
  > 실행은 **Advice**로
  > 적용 대상은 **Pointcut** 으로 !

  <br>

  ### 2. IoC (Inversion of Control)

  > 애플리케이션의 제어 권한을 개발자가 아니라 스프링에게 넘기는 것

  JDBC 방식
  ```java
  public class OrderService {
      private OrderRepository orderRepository = new OrderRepository();
  }
  ```

  - 개발자가 직접 new 해서 객체를 만들고 연결했어

  **IoC 적용 방식**
  ```java
  @Service
  public class OrderService {

      private final OrderRepository orderRepository;

      // 객체는 스프링이 주입해줌
      @Autowired
      public OrderService(OrderRepository orderRepository) {
          this.orderRepository = orderRepository;
      }  
  }
  ```

  - 객체 생성과 관리를 스프링이 대신 만들어서 넣어줌 (IoC Container가 관리)

  ### IoC Container = Spring Container

  > IoC Container 는 스프링이 제공하는 객체 (Bean)을 생성하고 관리하는 공간

  스프링은 애플리케이션 시작 시 :
  1. 필요한 객체들을 찾아서
  2. 생성하고
  3. 필요한 곳에 넣어줌 (@Autowired)



  ### 3. DI(Dependency Injection)

  DI란?
  > 의존하는 객체를 외부에서 주입 받는 것
  
  👉 내가 직접 new로 객체를 만들지 않고, 누군가가 대신 만들어서 넣어주는 것

  <br>

  왜 DI를 써야해?
  - 직접 new 를 해서 객체를 만들면 -> 클래스 간에 강하게 묶여
  - DI를 쓰면 -> 나중에 다른 구현체로 갈아끼우기 편해
  - 유지보수할 때 바꿔야할 게 생겨도, 코드를 거의 안 고치고 연결만 바꾸면 돼

  <br>

  DI 적용하기 전과 후 비교
  ```java
  public class OrderService {
    private final OrderRepository orderRepository = new OrderRepository();  
}
  ```

  - 문제 : OrderRepository 바꿀 때, OrderService 코드를 수정해야해

  적용 후
  ```java
  public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {  // -> 외부에서 주입
        this.orderRepository = orderRepository;
    }
}

```

- 스프링이 대신 넣어줘 !

  ### IoC와 DI의 차이?

  - DI는 구체적인 IoC를 구현하는 기술 -> 그니까 IoC가 큰 개념 !

  <br>

  ### 4. PSA (Portable Service Abstraction) = 서비스 추상화 계층

  > 기술이 바뀌어도 사용하는 코드 방식은 유지할 수 있도록 스프링이 중간에서 포장지 같은 역할을 해

  ### 왜 필요해?

  - 만약 PSA가 없으면,
  - 기술이 바뀔 때마다 코드 다 갈아엎어야 해..
  - 그래서 "이메일이든 트랜잭션이든 스프링이 제공하는 공통 API만 사용해 !
  - 뒤에 무슨 기술이든 처리할게 "
  - 하는게 PSA

  ### 정리해서

  > PSA는 **"인터페이스 + 구현체"** 패턴
  > **"DI + 설정 기반 바인딩"** 으로 구현되어 있어

  ```
  [인터페이스] ← PSA 추상화 계층 (개발자는 인터페이스만 사용)
     ↑
  [여러 기술 기반의 구현체들]  ← JavaMail, Redis, JPA, etc.
     ↑
  [Spring이 설정/환경에 따라 골라서 주입] ← DI

  ```

  ### 예시
  
  - 공통 인터페이스 (Spring이 제공)
  ```java
  public interface MailSender {
      void send(SimpleMailMessage message);
  }
  ```

  - 구현체
  ```java
  public class JavaMailSenderImpl implements JavaMailSender {
  }
  ```

  - DI로 주입
  ```java
  @Configuration
  public class MailConfig {

    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost("smtp.example.com");
        sender.setPort(587);
        return sender;
    }
  }
  ```

  - 개발자는 인터페이스만 사용
  ```java
  @Service
  public class MailService {

    private final JavaMailSender mailSender;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void send() {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("user@example.com");
        msg.setText("Hello!");
        mailSender.send(msg);  // -> 구현체가 뭔지 몰라도 됨
    }
  }
  ```
 <br>

 ---


## 3️⃣ Spring Bean 이 무엇이고, Bean 의 라이프사이클은 어떻게 되는지 조사해요

### Spring Beam

> Spring 이 대신 new 해서 관리해주는 객체


### 어떤 객체들이 Bean이 돼?

- `@Component` : 일반 클래스는 자동으로 등록
- `@Service`, `@Repository`, `@Controller` : 역할별 구분 자동 등록
- `@Bean` : 수동으로 객제를 직접 등록할 때 사용

<br>

### Spring Bean의 생명주기

```
[ IoC 컨테이너 ]
       ↓
[ 1. 객체 생성 (new) ]
       ↓
[ 2. 의존성 주입 (@Autowired) ]
       ↓
[ 3. 초기화 (@PostConstruct 등) ]
       ↓
[ 4. 사용 (서비스 수행) ]
       ↓
[ 5. 소멸 (@PreDestroy 등) ]
```

1. 객체 생성
   - 스프링이 Bean 정의를 보고 객체를 new로 생성함
   - 아직은 의존성 주입 X

2. 의존성 주입

   - 생성자 주입, 필드 주입, 등으로 다른 Bean들을 주입해서 완전한 객체로 만들어

     ```java
     @Autowired
     private OrderRepository orderRepository;
     ```
3. 초기화
4. 사용
   - 이제 Bean은 다른 클래스에서 `@Autowired`로 주입돼서 자유롭게 사용됨   

---

## 4️⃣ 스프링 어노테이션을 심층 분석해요

### 📖 `@Autowired`란?

> 스프링이 자동으로 의존관계를 주입해주는 어노테이션  
> 필요한 객체를 IoC 컨테이너에서 꺼내다가 이 필드에 의존성을 넣어줘! 라는 뜻

---

### 주입 방식 3가지

#### 1. 필드 주입

```java
@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;
}

```
- 테스트할 때 강제 주입이 어려운 단점이 있다

    
#### 2. 생성자 주입

```java
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
}
```
    - `final`키워드로 불변 객체 보장 가능
    - 테스트 작성 시 명확하게 주입할 수 있다 


#### 3. 세터 주입

```java
@Service
public class MemberService {
    private MemberRepository memberRepository;

    @Autowired
    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
}
```
- 중간에 다른 객체로 갈아치울 가능 성 있어 -> 신뢰성 낮음 

  <br>

### 스프링은 어떻게 Bean으로 등록해서 관리?


```
1. @ComponentScan 선언
2. 지정된 패키지의 .class 파일 탐색
3. 어노테이션 붙은 클래스만 추림
4. 객체(Bean) 생성 → IoC 컨테이너에 등록
```


---

### `@ComponentScan` 선언

- 보통 `@SpringBootApplication` 안에 자동으로 포함돼 있음

```java
@SpringBootApplication // 내부에 @ComponentScan 포함
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}
```

  - `SpringApplication.run` 할 때 **현재 + 하위 패키지** 에 있는 클래스들 중 @Component 계열 어노테이션이 붙은 애들을 찾아서 등록!


### Spring은 어떻게 클래스를 찾아?

> Spring은 Java의 **Reflection API + Classpath Scanning** 기술을 사용해 컴포넌트를 탐색

- Spring은 classpath에 있는 `.class` 파일들을 지정된 패키지 기준으로 모두 확인
- 각 클래스의 메타정보(어노테이션 등)를 분석해서 `@Component`, `@Service`, `@Repository`, `@Controller` 같은 어노테이션이 붙어있는지 검사

> 이 과정을 내부적으로 도와주는 클래스가  
> **`ClassPathScanningCandidateComponentProvider`** 

<br>

### BeanDefinition 생성

- Spring은 탐색한 클래스에 대해 **BeanDefinition** 객체를 생성
- 이 객체는 Bean을 등록하기 위한 메타정보를 담고 있어  
  - 어떤 클래스인지
  - 어떤 이름으로 등록할지
  - 싱글톤 여부

> 이렇게 생성된 BeanDefinition은 IoC 컨테이너에 등록돼

<br>

### 객체 생성 → IoC 컨테이너에 등록

- Spring은 개발자가 직접 `new`로 객체를 만들지 않아
- 내부적으로 **Reflection API**를 사용하여 인스턴스를 생성
- 생성된 인스턴스는 **ApplicationContext (스프링 컨테이너)** 에 저장

> 이 과정을 통해 우리는 “Spring Bean이 등록되었다”고 표현 !

---

### 커스텀 어노테이션

> 나만의 어노테이션을 만들어서 스프링 컴포넌트처럼 사용할 수도 있다.

커스텀 어노테이션을 만들 때는 3가지 메타 어노테이션을 사용

- `@Target`: 이 어노테이션을 어디에 붙일 수 있는지 지정  
  (예: 클래스, 메서드, 필드 등)
- `@Retention`: 어노테이션을 언제까지 유지할지 지정  
  (예: 런타임까지 유지하면 Reflection으로 읽을 수 있음)
- `@Documented`: Javadoc 등 문서화 도구에 포함시킬지 여부

```java
// 어노테이션 선언
@Target(ElementType.TYPE)  // 클래스에만 붙일 수 있음
@Retention(RetentionPolicy.RUNTIME)  // 런타임까지 유지됨
@Component  // 이걸 붙이면 스프링이 컴포넌트처럼 인식해서 Bean으로 등록
public @interface MyService {
}

```



## 5️⃣ **단위 테스트와 통합 테스트 탐구**

### 단위데스트 (Unit Test)
```java
@Test
void testAdd() {
    Calculator calculator = new Calculator();
    assertEquals(5, calculator.add(2, 3));
}
```

- DB도 없고, 다른 의존성도 없고, 딱 한 메서드만 테스트
- 의존성 없음

### 통합 테스트 (Integration Test)

> 여러 컴포넌트가 서로 잘 연결되어 있는지 검증하는 테스트

`@SpringBootTest` : 실제로 컨트롤러 -> 서비스 -> 리포지토리 -> DB까지 전부 연결되어 태스트
