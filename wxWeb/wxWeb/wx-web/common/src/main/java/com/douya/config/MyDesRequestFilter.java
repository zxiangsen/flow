package com.douya.config;

import com.douya.utils.DES;
import com.douya.utils.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by lb on 2018/1/31.
 */
public class MyDesRequestFilter extends OncePerRequestFilter {
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		MyHttpRequestWrapper myWrapper = new MyHttpRequestWrapper(request);

		//解密token和sessionid
		String token = request.getHeader("TOKEN");
		String sessionId = request.getHeader("SESSIONID");
		if (!StringUtils.isBlank(token)) {
			token = DES.decryptDES(token);
			myWrapper.putHeader("TOKEN", token);
		}
		if (!StringUtils.isBlank(sessionId)) {
			sessionId = DES.decryptDES(sessionId);
			myWrapper.putHeader("SESSIONID", sessionId);
		}

		//filterChain.doFilter(myWrapper, response);
		request.getRequestDispatcher(request.getServletPath()).forward(myWrapper, response);
	}

}