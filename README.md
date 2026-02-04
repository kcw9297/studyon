> ## StudyOn (스터디온)
#### 천재IT교육센터 Java 풀스택 과정 12기 2차 팀 프로젝트 gif 자료


### Spring Boot + JSP 기반 프로젝트
자바 스프링 부트 프레임워크 심화학습을 위해 이 프로젝트를 진행했습니다.

| 이름 | 기간 |
| :--- |:--  |
| ** StudyOn ** | 2025.10.13 ~ 2025.11.02|

***

### 회원가입
이메일 기반 회원 가입이 가능합니다.
회원가입 시 이메일 인증 후 쉽게 가입이 가능합니다.

- 이메일 인증 메일 전송 : 스프링 부트 메일 라이브러리

- 로그인 : 스프링 시큐리티, OAuth2 (소셜 로그인)

* * *

>![회원가입](https://raw.githubusercontent.com/DarkLight0418/Project_GIF_Files/f8782fe0e7fd5b1edc95324344901e8d2e934db5/studyon%20gif%20%EC%9E%90%EB%A3%8C/%ED%9A%8C%EC%9B%90%EA%B0%80%EC%9E%85.gif)

***
### 강사 기능


#### 1. 강사 강의 등록 기본 폼

강사가 본인의 강의를 등록할 수 있도록 설계했습니다.
- 외부 에디터를 적용하여 사용성 개선, 업로드 편의성을 고려했습니다.

- 강의의 기본 정보를 입력할 수 있습니다.

* * *

>![강사 기능 1 : 강의 등록 (내용 위주)](https://raw.githubusercontent.com/DarkLight0418/Project_GIF_Files/f8782fe0e7fd5b1edc95324344901e8d2e934db5/studyon%20gif%20%EC%9E%90%EB%A3%8C/%EA%B0%95%EC%82%AC%20%EA%B0%95%EC%9D%98%20%EB%93%B1%EB%A1%9D.gif)

#### 2. 강의 등록 시 난이도 및 세부 설정
강의의 세부 정보 등을 설정할 수 있는 화면입니다.

- 강사의 정보 등록 편의성을 위해 **[NginX](https://nginx.org/)**를 이용하여 실시간 파일 업로드 기능을 구현했습니다.

- 강의별 썸네일, 난이도, 강의 영상 업로드 등을 설정할 수 있습니다.


* * *
> ![강사 강의 등록 (난이도 및 세부 설정)](https://raw.githubusercontent.com/DarkLight0418/Project_GIF_Files/f8782fe0e7fd5b1edc95324344901e8d2e934db5/studyon%20gif%20%EC%9E%90%EB%A3%8C/%EA%B0%95%EC%82%AC%20%EA%B0%95%EC%9D%98%20%EB%93%B1%EB%A1%9D%20%ED%8E%B8%EC%A7%91%20%ED%99%94%EB%A9%B4.gif)
* * *
![강사 강의 등록 (난이도 및 세부 설정2)](https://raw.githubusercontent.com/DarkLight0418/Project_GIF_Files/f8782fe0e7fd5b1edc95324344901e8d2e934db5/studyon%20gif%20%EC%9E%90%EB%A3%8C/%EA%B0%95%EC%82%AC%20%EA%B0%95%EC%9D%98%20%EB%82%9C%EC%9D%B4%EB%8F%84%20%EB%B0%8F%20%EC%8D%B8%EB%84%A4%EC%9D%BC%20%EB%93%B1%EB%93%B1%20%EC%84%A4%EC%A0%95.gif)

#### 3. 강사 관리 (내 강의평 등등 조회)
강사 본인의 설정 화면에서 정보 수정, 강의 수정, 내 강의평 조회 등이 가능하도록 설계했습니다.
- 본인이 조회를 원하는 강의별로 필터링이 가능합니다.

- 강의 중 학생 사용자로부터 남겨진 Q&A 등의 정보를 확인할 수 있습니다.

* * *
>![강사 관리 (내 강의평 등등 조회)](https://raw.githubusercontent.com/DarkLight0418/Project_GIF_Files/f8782fe0e7fd5b1edc95324344901e8d2e934db5/studyon%20gif%20%EC%9E%90%EB%A3%8C/%EA%B0%95%EC%82%AC%20%EA%B0%95%EC%9D%98%20%EA%B4%80%EB%A6%AC%2C%20%EC%88%98%EA%B0%95%ED%8F%89%20%EC%A1%B0%ED%9A%8C%2C%20Q%26A.gif)


***
### 회원 기능


#### 1. 챗봇 기능


채팅/챗봇 기능을 통해 원하는 강의 내용 및 문의 사항을 입력할 수 있고, 관련 정보들을 즉시 답변을 통해 확인 가능합니다.

- **OpenAI API + Spring WebSocket** 기능을 이용하여 사용자가 궁금한 점이 있을 때 우선 OpenAI API를 통해 RAG 시스템을 거친 답변이 생성되고,
자세한 답변은 관리자가 답변할 수 있게끔 설계, 구현했습니다.


* * *

>![챗봇 기능](https://raw.githubusercontent.com/DarkLight0418/Project_GIF_Files/f8782fe0e7fd5b1edc95324344901e8d2e934db5/studyon%20gif%20%EC%9E%90%EB%A3%8C/%EC%B1%97%EB%B4%87.gif)


#### 2. 강의 검색 기능
사용자가 검색 기능 이용 시 원하는 강의를 쉽고 편리하게 찾을 수 있습니다.
- 검색 및 강의 필터링이 가능합니다.

- 과목, 난이도 등 상세 검색 조건을 설정하여, 탐색 편의성을 높였습니다.

* * *

>![강의 검색 기능](https://raw.githubusercontent.com/DarkLight0418/Project_GIF_Files/f8782fe0e7fd5b1edc95324344901e8d2e934db5/studyon%20gif%20%EC%9E%90%EB%A3%8C/%EA%B2%80%EC%83%89.gif)
* * *
 ![강의 검색 기능 2](https://raw.githubusercontent.com/DarkLight0418/Project_GIF_Files/f8782fe0e7fd5b1edc95324344901e8d2e934db5/studyon%20gif%20%EC%9E%90%EB%A3%8C/%EA%B2%80%EC%83%89%20%EC%A1%B0%EA%B1%B4%20%EC%84%A0%ED%83%9D%EC%8B%9C%20%EB%B0%98%EC%9D%91.gif)


#### 3. 강의 상세 조회 및 강의 구매 화면
강의를 상세 조회할 수 있고, 실제 결제 시스템을 통해 수강신청이 가능하게끔 설계했습니다.
- **[PortOne](https://portone.io/korea/ko) 결제 API**를 이용하여, 익숙한 결제 서비스를 이용할 수 있도록 결제 편의성을 향상시켰습니다.
* * *

>![강의 상세 조회 및 강의 구매 화면](https://raw.githubusercontent.com/DarkLight0418/Project_GIF_Files/f8782fe0e7fd5b1edc95324344901e8d2e934db5/studyon%20gif%20%EC%9E%90%EB%A3%8C/%ED%95%99%EC%83%9D%20-%20%EA%B0%95%EC%9D%98%20%EC%83%81%EC%84%B8%20%EC%A1%B0%ED%9A%8C%2C%20%EA%B0%95%EC%9D%98%20%EA%B5%AC%EB%A7%A4.gif)

#### 4. 강의 구매 후 시청 화면
강의 구매 후 강의 시청 등 관련 서비스를 이용할 수 있습니다. 
결제 후 즉시 강의 시청이 가능하게끔 하여 강의 접근 용이성을 향상시켰습니다.


* * *
> ![강의 구매 후 시청 화면](https://raw.githubusercontent.com/DarkLight0418/Project_GIF_Files/f8782fe0e7fd5b1edc95324344901e8d2e934db5/studyon%20gif%20%EC%9E%90%EB%A3%8C/%ED%95%99%EC%83%9D%20%EA%B0%95%EC%9D%98%20%EA%B5%AC%EB%A7%A4%20%ED%9B%84%20%EC%8B%9C%EC%B2%AD%20%ED%99%94%EB%A9%B4.gif)


#### 5. Q&A 남기기
사용자가 강의 시청 수강 중 궁금한 점이 있을 때 곧바로 질문을 등록할 수 있도록 하여 편의성을 높였습니다.

* * *
> ![Q&A 남기기](https://raw.githubusercontent.com/DarkLight0418/Project_GIF_Files/f8782fe0e7fd5b1edc95324344901e8d2e934db5/studyon%20gif%20%EC%9E%90%EB%A3%8C/%ED%95%99%EC%83%9D%20%EA%B0%95%EC%9D%98%20%EC%8B%9C%EC%B2%AD%20%EC%A4%91%20Q%26A%20%EB%82%A8%EA%B8%B0%EA%B8%B0.gif)



#### 6. 선생님 목록 및 선생님 상세 조회
별도의 선생님(강사) 목록 및 상세 프로필 페이지를 구현하여, 선생님의 정보를 쉽게 파악할 수 있도록 구현했습니다.
- 과목별로 선생님들의 정보를 조회할 수 있습니다.

- 선생님 UI를 클릭하면 곧바로 상세 프로필 페이지로 접근이 가능하며, 등록 강의 및 수강평을 확인할 수 있습니다.

* * *
> ![선생님 목록 및 선생님 상세 조회](https://raw.githubusercontent.com/DarkLight0418/Project_GIF_Files/f8782fe0e7fd5b1edc95324344901e8d2e934db5/studyon%20gif%20%EC%9E%90%EB%A3%8C/%ED%95%99%EC%83%9D%20-%20%EC%84%A0%EC%83%9D%EB%8B%98%20%EB%AA%A9%EB%A1%9D%20%EB%B0%8F%20%EC%83%81%EC%84%B8%20%EB%B3%B4%EA%B8%B0.gif)

***

### 관리자 기능

사이트 관리에 필요한 정보들을 관리자가 쉽게 조회 및 관리할 수 있도록 설계했습니다.


#### 1. 회원 관리

회원 목록을 한번에 조회할 수 있고 회원 활성화 여부 등 조회 및 설정이 가능합니다.
- 회원별로 활성화 여부를 확인할 수 있고, 활성화 여부를 선택, 설정할 수  있습니다.

* * *
> ![회원 관리](https://raw.githubusercontent.com/DarkLight0418/Project_GIF_Files/f8782fe0e7fd5b1edc95324344901e8d2e934db5/studyon%20gif%20%EC%9E%90%EB%A3%8C/%EA%B4%80%EB%A6%AC%EC%9E%90%20%ED%9A%8C%EC%9B%90%20%EA%B4%80%EB%A6%AC.gif)


#### 강의 관리

홈 화면에 표시되는 강의를 관리할 수 있습니다.
- 강의별 대기 상태, 반려, 등록 완료 여부를 조회할 수 있습니다.

- 강의 등록 대기, 등록 반려, 등록 완료 여부를 설정하여 실제 강의 수강이 이루어지게끔, 홈 화면에 보여지게끔 설정할 수 있습니다.
* * *

>![강의 관리 1](https://raw.githubusercontent.com/DarkLight0418/Project_GIF_Files/f8782fe0e7fd5b1edc95324344901e8d2e934db5/studyon%20gif%20%EC%9E%90%EB%A3%8C/%EA%B4%80%EB%A6%AC%EC%9E%90%20%EA%B0%95%EC%9D%98%20%EA%B4%80%EB%A6%AC%201(%EB%93%B1%EB%A1%9D).gif)
* * *
![강의 관리 2](https://raw.githubusercontent.com/DarkLight0418/Project_GIF_Files/f8782fe0e7fd5b1edc95324344901e8d2e934db5/studyon%20gif%20%EC%9E%90%EB%A3%8C/%EA%B4%80%EB%A6%AC%EC%9E%90%20%EA%B0%95%EC%9D%98%20%EA%B4%80%EB%A6%AC%202(%EC%8A%B9%EC%9D%B8%20%EB%B0%98%EB%A0%A4).gif)

#### 채팅 및 배너 관리
앞서 소개했던 채팅 목록 관리, 실제로 홈 화면에서 보여지는 공지사항 배너들을 조회할 수 있습니다.

##### 1. 채팅 관리
- 관리자 권한으로 채팅 목록 조회 및 관리가 가능합니다. 

- 별도 문의사항이 있을 경우 관리자가 실제로 답변이 가능합니다.
##### 2. 배너 관리
- 실제 홈 화면에 표시되는 공지사항 등을 이미지 배너를 통해 설정 가능합니다.

- 배너별 표시/미표시 여부를 설정할 수 있습니다.

* * *
>![채팅 및 배너 관리](https://raw.githubusercontent.com/DarkLight0418/Project_GIF_Files/f8782fe0e7fd5b1edc95324344901e8d2e934db5/studyon%20gif%20%EC%9E%90%EB%A3%8C/%EA%B4%80%EB%A6%AC%EC%9E%90%20%EC%B1%84%ED%8C%85%20%EA%B4%80%EB%A6%AC%20%EB%B0%8F%20%EB%B0%B0%EB%84%88%20%EA%B4%80%EB%A6%AC.gif)

#### 결제 관리
회원별로 결제했던 기록을 실제로 조회할 수 있습니다.
- 결제 기록 검색 및 필터링이 가능합니다.
- 관리자는 요청에 따라 결제 환불 여부, 환불 사유 등을 등록, 처리할 수 있습니다.

* * *
>![결제 관리](https://raw.githubusercontent.com/DarkLight0418/Project_GIF_Files/f8782fe0e7fd5b1edc95324344901e8d2e934db5/studyon%20gif%20%EC%9E%90%EB%A3%8C/%EA%B4%80%EB%A6%AC%EC%9E%90%20%EA%B2%B0%EC%A0%9C%20%EA%B4%80%EB%A6%AC.gif)

***

### 그 외

#### 사이트 배너
사이트 배너 및 플로팅 팝업창을 띄울 수 있는 기능을 제공합니다.
위에서 확인 가능한 관리자 배너 관리 페이지에서 설정이 가능합니다.
* * *
>![사이트 배너](https://raw.githubusercontent.com/DarkLight0418/Project_GIF_Files/f8782fe0e7fd5b1edc95324344901e8d2e934db5/studyon%20gif%20%EC%9E%90%EB%A3%8C/%ED%99%88%ED%99%94%EB%A9%B4%20-%20%EC%82%AC%EC%9D%B4%ED%8A%B8%20%EB%B0%B0%EB%84%88%20%EB%93%B1.gif)

***

### 기타

[노션 소개 페이지](https://www.notion.so/STUDY-ON-2f5251660b4a80eca55df4b9ebf93913?source=copy_link)


***
Ver 0.3 - 2026.02.01.
