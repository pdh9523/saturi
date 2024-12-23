"use client";

import Image from "next/image";
import { useRouter } from "next/navigation";
import { grey } from "@mui/material/colors";
import { useMemo, useState, useEffect } from "react";
import { goSocialLogin, handleLogin } from "@/utils/authutils";
import { validateEmail, handleValueChange } from "@/utils/utils";
import {
  Box,
  Container,
  Grid,
  Link,
  TextField,
  Typography,
  Button,
  Divider,
} from "@mui/material";

export default function App() {
  const router = useRouter();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const isEmailValid = useMemo(() => validateEmail(email), [email]);

  useEffect(() => {
    if (sessionStorage.getItem("accessToken")) {
      router.push("/")
    }
  }, []);

  return (
    <Container component="main" maxWidth="xs">
      <Box
        sx={{
          marginTop: 8,
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
        }}
      >
        <Typography component="h1" variant="h5">
          로그인
        </Typography>
        <Box
          component="form"
          onSubmit={event => {
            event.preventDefault();
            handleLogin({ email, password, router, goTo: "/" });
          }}
          noValidate
          sx={{ mt: 1 }}
        >
          <TextField
            margin="normal"
            required
            fullWidth
            id="email"
            label="이메일"
            autoComplete="email"
            value={email}
            onChange={event => handleValueChange(event, setEmail)}
            autoFocus
            error={!isEmailValid}
            helperText={isEmailValid ? "" : "이메일이 유효하지 않습니다."}
          />
          <TextField
            margin="normal"
            required
            fullWidth
            id="password"
            label="비밀번호"
            type="password"
            value={password}
            onChange={event => handleValueChange(event, setPassword)}
            autoComplete="current-password"
          />
          <Button
            type="submit"
            fullWidth
            variant="contained"
            sx={{
              mt: 3,
              mb: 2,
              height: "56px",
            }}
          >
            로그인
          </Button>
          <Grid
              container
              sx={{
              display: "flex",
                  justifyContent: "space-between"
          }}>
            <Grid item>
              <Link href="/findpassword" variant="body2">
                비밀번호 찾기
              </Link>
            </Grid>
            <Grid item>
              <Link href="/register" variant="body2">
                계정이 없으신가요?
              </Link>
            </Grid>
          </Grid>
          <Divider
            sx={{
              mt: 2,
              color: grey[500],
            }}
          >
            또는
          </Divider>
          <Grid container spacing={1} mt={2}>
            <Grid item xs={6}>
              <Box
                sx={{
                  width: '100%',
                  height: '56px',
                  cursor: 'pointer',
                  position: 'relative',
                }}
                onClick={() => goSocialLogin("naver")}
              >
                <Image
                  src="/naverBtn.png"
                  alt="naverLogin"
                  layout="fill"  // 부모의 크기에 맞게 조절
                  objectFit="contain"
                />
              </Box>
            </Grid>
            <Grid item xs={6}>
              <Box
                sx={{
                  width: '100%',
                  height: '56px',
                  cursor: 'pointer',
                  position: 'relative',
                }}
                onClick={() => goSocialLogin("kakao")}
              >
                <Image
                  src="/kakao_login_medium_narrow.png"
                  alt="kakaoLogin"
                  layout="fill"  // 부모의 크기에 맞게 조절
                  objectFit="contain"
                />
              </Box>
            </Grid>
          </Grid>
        </Box>
      </Box>
    </Container>
  );
}
