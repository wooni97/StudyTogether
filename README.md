## Study Together
### 소개
💻 개발자들이 함께 모여 코딩할 수 있는 플랫폼입니다. 

----

### 사용 기술
- **JDK** : Java17

- **Framework** : SpringBoot
  
- **Database** : MySQL 8.0.24
  
- **CI/CD** : GitHub Actions
  
- **배포** : Naver Cloud Platform


---

### 🚀 프로젝트 Develop 과정

**Develop 이유 :** 
- 해당 비즈니스를 실제로 서비스한다면 어떤 고민이 필요할지, 그리고 비즈니스를 성공적으로 이끌기 위해서는 무엇을 개선해야 할지에 대한 답을 찾고자 고도화 과정을 진행하게 되었습니다.

**Develop 원칙 : 점진적 레거시 개선**
- 실제 운영중인 시스템임을 가정하고, 점진적으로 레거시 개선 진행
- 사용자와 밀접하게 연관된 기능(회원가입, 로그인 등) 변경 필요 -> 기존에 사용하는 Member 모델에 기능을 추가하는 것이 아닌 새로운 MemberV2 도메인 모델 생성
  
[이전 Repository 바로가기](https://github.com/f-lab-edu/study-together)




---

### 기술적 고민
**✔️JPA 도입 과정**

[JpaRepository.save() 안티패턴에 대한 고찰](https://dev-wooni.tistory.com/9)

[다양한 구조로 변경 가능한 Repository 만들기](https://dev-wooni.tistory.com/11)

[일대다(OneToMany) 단방향 매핑이 불러오는 성능 비효율에 관한 고민](https://dev-wooni.tistory.com/12)


**✔️Event-Driven Architecture**

[Event 기반 아키텍처 사용 이유 및 Transactional Outbox 패턴을 통한 메시지 발행 보장](https://dev-wooni.tistory.com/13)

[Event 확장성 고민, 추상화로 해결하기 : 이벤트 동적 매핑 하는 방법](https://dev-wooni.tistory.com/14)


**✔️동시성 제어**
