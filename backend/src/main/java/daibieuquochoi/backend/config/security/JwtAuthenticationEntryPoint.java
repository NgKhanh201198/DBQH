package daibieuquochoi.backend.config.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import daibieuquochoi.backend.response.ResponseMessage;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		logger.error("Unauthorized error: {}", authException.getMessage());

		ResponseMessage messageResponse = new ResponseMessage();
		messageResponse.setTimestamp(new Date());
		messageResponse.setstatusCode(HttpServletResponse.SC_UNAUTHORIZED);
		messageResponse.setError("Unauthorized");
		messageResponse.setMessage("Xin lỗi, bạn cần xác thực để truy cập tài nguyên này");
		messageResponse.setPath(request.getRequestURI());
		Gson gson = new Gson();
		String errorMessage = gson.toJson(messageResponse);

		response.resetBuffer();
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getWriter().write(errorMessage);
		response.flushBuffer();

	}

}
