package io.github.ddojai.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MyFilter3 implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse res = (HttpServletResponse) response;

    // id, pw를 통해 로그인 완료시 토큰을 만들고 응답
    // 그 다음부터 요청시 헤더에 토큰값 전달
    // 토큰이 서버에서 만든 토큰이 맞는지만 검증하면 됨
    if (req.getMethod().equals("POST")) {
      System.out.println("포스트 요청됨");
      String headerAuth = req.getHeader("Authorization");
      System.out.println(headerAuth);
      System.out.println("필터3");

      if (headerAuth.equals("cos")) {
        chain.doFilter(req, res);
      } else {
        PrintWriter out = res.getWriter();
        out.println("인증안됨");
      }
    }
  }
}
