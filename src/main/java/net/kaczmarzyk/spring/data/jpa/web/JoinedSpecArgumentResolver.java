package net.kaczmarzyk.spring.data.jpa.web;

import org.springframework.core.MethodParameter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import net.kaczmarzyk.spring.data.jpa.web.annotation.JoinedSpec;

/**
 * @author Tomasz Kaczmarzyk
 */
public class JoinedSpecArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public Object resolveArgument(MethodParameter param, ModelAndViewContainer mav, NativeWebRequest req, WebDataBinderFactory bf)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supportsParameter(MethodParameter param) {
		return param.getParameterType() == Specification.class && param.hasParameterAnnotation(JoinedSpec.class);
	}

}
