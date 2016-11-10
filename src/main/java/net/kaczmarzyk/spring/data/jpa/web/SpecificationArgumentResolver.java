/**
 * Copyright 2014-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.kaczmarzyk.spring.data.jpa.web;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.List;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import net.kaczmarzyk.spring.data.jpa.utils.Converter;


/**
 * Allow to set custom {@code Converter}, like:
 * 
 * <pre>
 * {
 *   &#64;code
 *   ObjectMapper myMapper = new ObjectMapper();
 *   myMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
 *   new SpecificationArgumentResolver(new Converter(myMapper));
 * }
 * </pre>
 * 
 * @author Tomasz Kaczmarzyk
 * @author Matt S.Y. Ho
 */
public class SpecificationArgumentResolver implements HandlerMethodArgumentResolver {

	private List<HandlerMethodArgumentResolver> delegates;
	
	public SpecificationArgumentResolver() {
      this(Converter.DEFAULT);
    }
	
	public SpecificationArgumentResolver(Converter converter) {
      super();
      requireNonNull("converter");
      delegates = Arrays.asList(
          new JoinFetchSpecificationResolver(this),
          new SimpleSpecificationResolver(converter),
          new AndSpecificationResolver(converter), new ConjunctionSpecificationResolver(converter),
          new OrSpecificationResolver(converter), new DisjunctionSpecificationResolver(converter),
          new AnnotatedSpecInterfaceArgumentResolver(converter));
    }

	Object resolveArgument(MethodParameter param, ModelAndViewContainer mav, NativeWebRequest req,
            WebDataBinderFactory bider, HandlerMethodArgumentResolver recursiveCaller) throws Exception {
	    
	    for (HandlerMethodArgumentResolver delegate : delegates) {
            if (delegate != recursiveCaller && delegate.supportsParameter(param)) {
                return delegate.resolveArgument(param, mav, req, bider);
            }
        }
        
        return null;
	}
	
	@Override
    public Object resolveArgument(MethodParameter param, ModelAndViewContainer mav, NativeWebRequest req,
            WebDataBinderFactory bider) throws Exception {
        
        return resolveArgument(param, mav, req, bider, null);
    }

    @Override
    public boolean supportsParameter(MethodParameter param) {
        for (HandlerMethodArgumentResolver delegate : delegates) {
            if (delegate.supportsParameter(param)) {
                return true;
            }
        }
        return false;
    }

}
