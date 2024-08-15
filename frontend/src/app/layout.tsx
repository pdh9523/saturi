"use client"

import "@/styles/globals.css";
import localFont from "next/font/local"
import { ReactNode , useEffect } from "react";
import CssBaseline from "@mui/material/CssBaseline";
import Header from "@/components/layout/Header";
import Footer from "@/components/layout/Footer";
import { createTheme } from "@mui/material";
import { ThemeProvider } from "@emotion/react";
import { useRouter, usePathname } from "next/navigation"
import { authToken } from "@/utils/authutils";
import { teal } from "@mui/material/colors";
import { generateMetadata } from "@/components/DynamicTitles";

const mainFont = localFont({
  src: "./PretendardVariable.woff2"
})

const theme = createTheme({
  palette: {
    mode: 'light',
    primary: teal
  },
  typography: {
    fontFamily: 'Pretendard, mainFont, sans-serif',
  }
});

export { generateMetadata };

export default function RootLayout({ children }: { children: ReactNode }) {
  const router = useRouter()
  const pathname = usePathname()
  useEffect(() => {
    if (typeof window !== "undefined" && sessionStorage.getItem("accessToken")) {
      authToken()
    }
  },[router])

  const isStartPage = pathname === '/' || pathname === '/start';
  
  if (pathname && ( pathname.startsWith("/admin") || (pathname.startsWith("/game") && !(pathname.includes("/in-game/") && pathname.endsWith("/result"))))) {
    return (
      <html lang="ko">
        <body className={mainFont.className}>
          <ThemeProvider theme={theme}>
            <main>
              {children}
            </main>
          </ThemeProvider>
        </body>
      </html>
    )
  }

  return (
    <html lang="ko">
      <body className={`${mainFont.className} bg-gray-100 min-h-svh`}>
        <ThemeProvider theme={theme}>
          <CssBaseline />
          <Header isTransparent={isStartPage} />
          <main
            className={mainFont.className}
            style={{ minHeight: 'calc(100vh - 350px)' }}
          >
            {children}
          </main>
          <Footer />
        </ThemeProvider>
      </body>
    </html>
  )
}