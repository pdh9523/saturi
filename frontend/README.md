## 시작하기

```bash
# in terminal

npm install # install dependencies

npm run dev # run server
```


```
'/login' (로그인)
│
├───── '/register' (회원가입) ───── '/step' (회원가입 이후 추가 질문)
│
│                ┌───────────────────────┬ '/changepassword' (비밀번호변경)
'/' ──┬── '/accounts' ── '/profile'      │
      │                    │             │
      │                    └────── '/update' (개인정보수정)
      │     (레슨)
      ├── '/lesson' ── '/{lesson_group_id}' (문제 그룹 설정)
      │                       └─ '/{category_id}' (카테고리 설정)
      │                              └─'/{lesson_id}'────'/result'  (결과 페이지)
      │                                  (문제 풀이)
      └── '/game' ─┬─ '/in-queue'  (게임 큐)
                   │
                   └─ '/in-game'   (게임 화면 페이지)
                            └─────────── '/result'   (결과 페이지)
```

## Dev Stack(예상)

### 디자인 툴
- Figma          ( 디자인 및 목업, 와이어프레임 제작 )

### 협업 툴
- Gitlab         ( 버전 관리 )
- JIRA,notion    ( 일정 및 이벤트 관리 )

### 개발 툴
- Typescript     
왜 굳이 TS? -> 자바스크립트에 대한 확실한 학습을 목적으로 함
- React.js       ( 프론트엔드 프레임워크 )
- Next.js        ( 프론트엔드 프레임워크 )
- Next auth      ( 소셜 인증 구현을 위한 라이브러리 )
- Next.ui        ( CSS 관련 라이브러리 )
- Tailwind       ( CSS 관련 라이브러리 )
- Socket.io      ( 소켓 통신을 위한 라이브러리 )
- ws             ( 웹소켓 서버 라이브러리 )
- Axios          ( api 요청을 위한 라이브러리 )
-> fetch 대신 쓰는 이유가 있느냐? axios는 json형태로 반환하기에 추가적인 변환을 필요로 하지 않음

* 수음 관련하여 Openvidu를 사용하는 것이 과하지 않을까 싶음.