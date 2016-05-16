package net.kaczmarzyk.spring.data.jpa.web;

import org.springframework.data.jpa.domain.Specification;

import net.kaczmarzyk.spring.data.jpa.web.annotation.OnTypeMismatch;

/**
 * @author Tomasz Kaczmarzyk
 */
public interface SpecificationDefinition {
	 
	String[] params();
	    
	String[] config();
	    
	String[] constVal();
	    
	OnTypeMismatch onTypeMismatch();
	    
	String path();
	    
    @SuppressWarnings("rawtypes")
    Class<? extends Specification> spec();
}
