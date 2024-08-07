package kr.co.iltuo.util;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomRequestFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (!request.getRequestURI().contains("/login") && !request.getRequestURI().contains("/css/") 
                && !request.getRequestURI().contains("/js/") && !request.getRequestURI().contains("/images/")) {
            request.getSession().setAttribute("prevPage", request.getHeader("Referer"));
        }
        filterChain.doFilter(request, response);
	}
	
}
