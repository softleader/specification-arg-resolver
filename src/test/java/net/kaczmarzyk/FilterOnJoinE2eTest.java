package net.kaczmarzyk;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.kaczmarzyk.spring.data.jpa.Customer;
import net.kaczmarzyk.spring.data.jpa.CustomerRepository;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.In;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.JoinedSpec;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;


public class FilterOnJoinE2eTest extends E2eTestBase {

	@Controller
	public static class TestController {
		
		@Autowired
		CustomerRepository customerRepo;
		
		@RequestMapping(value = "/customersByOrders", method = RequestMethod.GET)
		public Object filterByOrders(
				@JoinedSpec(joinOn="orders", path="itemName", spec = In.class) Specification<Customer> spec) {
			
			return customerRepo.findAll(spec);
		}
		
		@RequestMapping(value = "/customersByNameAndOrders", method = RequestMethod.GET)
		public Object filterByNameAndOrders(
				@Spec(
						path = "firstName", spec = Like.class,
						joins = @JoinedSpec(joinOn = "orders", path = "itemName", spec = Equal.class)
				) Specification<Customer> spec) {
			
			return customerRepo.findAll(spec);
		}
	}
	
	@Test
	public void filtersByAttributeOnJoinedEntity() throws Exception {
		mockMvc.perform(get("/customersByOrders")
				.param("itemName", "Pizza")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$").isArray())
			.andExpect(jsonPath("$[0].firstName").value("Homer"))
			.andExpect(jsonPath("$[1].firstName").value("Bart"))
			.andExpect(jsonPath("$[2]").doesNotExist());
		
		mockMvc.perform(get("/customersByOrders")
				.param("itemName", "Duff Beer")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$").isArray())
			.andExpect(jsonPath("$[0].firstName").value("Homer"))
			.andExpect(jsonPath("$[1]").doesNotExist());
	}
	
	@Test
	public void filtersBySimpleAttributeAndAttributeOnJoinedEntity() {
		Assert.fail();
	}
}
