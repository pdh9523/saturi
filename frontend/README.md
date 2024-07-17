This is a [Next.js](https://nextjs.org/) project bootstrapped with [`create-next-app`](https://github.com/vercel/next.js/tree/canary/packages/create-next-app).

## Getting Started

First, run the development server:

```bash
npm run dev
# or
yarn dev
# or
pnpm dev
# or
bun dev
```

Open [http://localhost:3000](http://localhost:3000) with your browser to see the result.

You can start editing the page by modifying `app/page.tsx`. The page auto-updates as you edit the file.

This project uses [`next/font`](https://nextjs.org/docs/basic-features/font-optimization) to automatically optimize and load Inter, a custom Google Font.

## Learn More

To learn more about Next.js, take a look at the following resources:

- [Next.js Documentation](https://nextjs.org/docs) - learn about Next.js features and API.
- [Learn Next.js](https://nextjs.org/learn) - an interactive Next.js tutorial.

You can check out [the Next.js GitHub repository](https://github.com/vercel/next.js/) - your feedback and contributions are welcome!

## Deploy on Vercel

The easiest way to deploy your Next.js app is to use the [Vercel Platform](https://vercel.com/new?utm_medium=default-template&filter=next.js&utm_source=create-next-app&utm_campaign=create-next-app-readme) from the creators of Next.js.

Check out our [Next.js deployment documentation](https://nextjs.org/docs/deployment) for more details.


│─┌┐┘└├┬┤┴

```
(게스트 유저는 메인 접속시 로그인 페이지로 리다이렉트)
'/login' ───── '/register' (회원가입) ───── '/step' (회원가입 이후 추가 질문)
│
│
│                ┌───────────────────────┬ '/changepassword'  (비밀번호변경)
'/' ──┬── '/accounts' ── '/profile'      │
      │                    │             │
      │                    └────── '/update' (개인정보수정)
      │     (레슨)
      ├── '/lesson' ── '/{lesson_group_id}' (문제 그룹 설정)
      │                       └────────────── '/{category_id}' (카테고리 설정)
      │                                             └───────────────────'/{lesson_id}'
      │                                                                  (문제 풀이)
      └── '/game' ─┬─ '/in-queue'                       (게임 큐)
                   │
                   └─ '/in-game'                     (게임 화면 페이지)
```

사용되는 기술 스택: 예상

-- 디자인 툴
Figma          ( 디자인 및 목업, 와이어프레임 제작 )

-- 협업 툴
Gitlab         ( 버전 관리 )
JIRA,notion    ( 일정 및 이벤트 관리 )

-- 개발 툴
Typescript     왜 굳이 TS? -> 자바스크립트에 대한 확실한 학습을 목적으로 함
React.js       ( 프론트엔드 프레임워크 )
Next.js        ( 프론트엔드 프레임워크 )
Next auth      ( Oauth와 맞춰 소셜 인증 구현을 위한 라이브러리 )
Next.ui        ( CSS 관련 라이브러리 )
Tailwind       ( CSS 관련 라이브러리 )
Socket.io      ( 소켓 통신을 위한 라이브러리 )
ws             ( 웹소켓 서버 )
Axios          ( api 요청을 위한 라이브러리 )
-> fetch 대신 쓰는 이유가 있느냐? axios는 json형태로 반환하기에 추가적인 변환을 필요로 하지 않음

수음 관련 기능도 하나 필요할 것 같음. ( 필요 없는 것 같음 )