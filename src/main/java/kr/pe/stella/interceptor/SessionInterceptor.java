package kr.pe.stella.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 세션 체크
 * 
 * @author jtpark
 *
 */
@Component
public class SessionInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = LoggerFactory
			.getLogger(SessionInterceptor.class);

	/**
	 * 세션이 null이면 처리를 끝냄
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		HttpSession session = request.getSession(false);
		// false: 없어도 새로 생성하지 않음

		if (session == null) {
			logger.info("____________________ Session Null ____________________");

			// TODO [박정태] 로그인/로그아웃 로직 구현
			// throw new ModelAndViewDefiningException(new
			// ModelAndView("views/login"));
		}

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
