##### ohgym_final 프로젝트

#### 오짐(OH! GYM) : 헬스장 검색 및 상품 구매 웹 서비스 프로젝트

지도 서비스를 통해 여행을 가서도 운동을 할 수 있도록 헬스장에 대한 접근성을 높이고,
헬스장 상품을 편리하게 결제할 수 있는 웹 서비스.

![image](https://github.com/qkrtiger/ohgym_final/assets/133315262/8ad317e9-9f16-4965-bb92-07c244c44294)

# 1. 목표와 기능
### 1.1 목표
- Spring Boot를 활용한 Web application 개발 경험 및 학습<br>
- Spring Framework에 대한 이해, 활용을 통한 기술 고도화<br>
- MyBatis연동을 통한 데이터 구조 및 데이터 흐름 학습<br>
- 화면 설계 경험을 통한 페이지 구성 요소간 관계 이해<br>
- API 활용 경험<br>
- 프로젝트 진행을 통한 협업 경험
### 1.2 기능
- 내 주변 헬스장 또는 트레이너 검색 기능(카카오맵 API 활용)
- 회원제 서비스
  - 메일 인증 서비스로 중복 회원가입 방지(메일 인증 API 활용)
  - SNS 간편 로그인 기능(카카오 로그인 API 활용)
  - 서비스 제공 대상 : 소비자, 헬스장, 트레이너
- 실시간 채팅 기능(채널톡 API 활용)
- 헬스장 또는 트레이너 검색 시 필터링 기능 제공
- 게시판 기능
- 상품 구매 기능
  - 찜 기능(마이페이지에서 확인 가능)
  - 위치, 별점, 후기 등 상세 정보 출력
  - KG 이니시스 카드 결제 기능(포트원 결제 API 활용)
  - 카카오페이 결제 기능(포트원 결제 API 활용)
- 마이페이지
  - 회원
    - 프로필 관리 (사진 등록, 정보 수정)
    - 일반회원 ←→ 트레이너 전환 기능
    - 찜 목록 확인
    - 결제 내역 확인 → 후기 작성 및 삭제
    - FAQ
  - 트레이너
    - 프로필 관리 (사진 등록, 정보 수정, 트레이너 정보 수정)
    - 상품 관리
    - 결제 내역 확인
    - FAQ
  - 헬스장
    - 프로필 관리 (사진 등록, 정보 수정)
    - 상품 관리
    - 결제 내역 확인
    - FAQ
- 기록 (FullCalendar API 활용)

# 2. 개발도구
- 개발산출문서 – ERDCloud, Excel<br>
- DB설계 - <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> 
- IDE 도구 - IntelliJ<br>
- 웹 서버 - Apache Tomcat 9.0<br>
- 언어 - <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black"> <img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white">  <img src="https://img.shields.io/badge/css-1572B6?style=for-the-badge&logo=css3&logoColor=white"> 
- 주요 라이브러리 - jQuery / Gson (JSON) / Thymeleaf / MyBatis<br>
- 프레임워크 – <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">

# 3. 데이터베이스 모델링(ERD)
![image](https://github.com/qkrtiger/ohgym_final/assets/133315262/51ff2059-50ac-4225-8c89-3242e367a1e0)

