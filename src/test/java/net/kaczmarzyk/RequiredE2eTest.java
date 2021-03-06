/**
 * Copyright 2014-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package net.kaczmarzyk;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.NestedServletException;

import net.kaczmarzyk.spring.data.jpa.Customer;
import net.kaczmarzyk.spring.data.jpa.CustomerRepository;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;


/**
 * @author Matt S.Y. Ho
 */
public class RequiredE2eTest extends E2eTestBase {

  @Spec(path = "lastName", spec = Equal.class, required = true)
  public static interface RequireSimpsonSpec extends Specification<Customer> {
  }

  @Controller
  public static class TestController {

    @Autowired
    CustomerRepository customerRepo;

    @RequestMapping("/required")
    @ResponseBody
    public Object listSimpsons(RequireSimpsonSpec spec) {
      return customerRepo.findAll(spec);
    }

  }

  @Test
  public void filtersBySingleSpecWithSingleRequired() throws Exception {
      mockMvc.perform(get("/required")
              .param("lastName", "Simpson")
              .accept(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$").isArray())
          .andExpect(jsonPath("$[?(@.firstName=='Homer')]").exists())
          .andExpect(jsonPath("$[?(@.firstName=='Marge')]").exists())
          .andExpect(jsonPath("$[?(@.firstName=='Bart')]").exists())
          .andExpect(jsonPath("$[?(@.firstName=='Lisa')]").exists())
          .andExpect(jsonPath("$[?(@.firstName=='Maggie')]").exists())
          .andExpect(jsonPath("$[5]").doesNotExist());
  }
  
  @Test
  public void throwsExceptionIfOneOfSpecIsRequired() throws Exception {
    expectedException.expect(NestedServletException.class);
    expectedException.expectCause(CoreMatchers.<IllegalStateException> instanceOf(IllegalStateException.class));
    expectedException.expectMessage("Required http parameter 'lastName' is not present");
    
    mockMvc.perform(get("/required").accept(MediaType.APPLICATION_JSON));
  }
}
