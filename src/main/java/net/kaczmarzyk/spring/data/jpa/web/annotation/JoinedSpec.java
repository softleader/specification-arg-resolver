package net.kaczmarzyk.spring.data.jpa.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.data.jpa.domain.Specification;

/**
 * @author Tomasz Kaczmarzyk
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PARAMETER, ElementType.TYPE })
public @interface JoinedSpec {
	
	String[] params() default {};

	String[] config() default {};

	String[] constVal() default {};

	OnTypeMismatch onTypeMismatch() default OnTypeMismatch.EMPTY_RESULT;

	String joinOn();
	
	String path();

	@SuppressWarnings("rawtypes")
	Class<? extends Specification> spec();
}
