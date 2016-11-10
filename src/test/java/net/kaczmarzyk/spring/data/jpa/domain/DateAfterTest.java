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
package net.kaczmarzyk.spring.data.jpa.domain;

import static net.kaczmarzyk.spring.data.jpa.CustomerBuilder.customer;
import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import net.kaczmarzyk.spring.data.jpa.Customer;
import net.kaczmarzyk.spring.data.jpa.IntegrationTestBase;


/**
 * @author Tomasz Kaczmarzyk
 */
public class DateAfterTest extends IntegrationTestBase {

    Customer homerSimpson;
    Customer margeSimpson;
    Customer moeSzyslak;
    
    @Before
    public void initData() {
        homerSimpson = customer("Homer", "Simpson").registrationDate(2014, 03, 07).build(em);
        margeSimpson = customer("Marge", "Simpson").registrationDate(2014, 03, 12).build(em);
        moeSzyslak = customer("Moe", "Szyslak").registrationDate(2014, 03, 18).build(em);
    }
    
    @Test
    public void filtersByRegistrationDateWithDefaultDateFormat() throws ParseException {
    	DateAfter<Customer> after13th = new DateAfter<>("registrationDate", new String[] { "2014-03-13" }, defaultConverter);
        
        List<Customer> result = customerRepo.findAll(after13th);
        assertThat(result)
            .hasSize(1)
            .containsOnly(moeSzyslak);
        
        DateAfter<Customer> after10th = new DateAfter<>("registrationDate", new String[] { "2014-03-10" }, defaultConverter);
        
        result = customerRepo.findAll(after10th);
        assertThat(result)
            .hasSize(2)
            .containsOnly(margeSimpson, moeSzyslak);
    }
    
    @Test
    public void filtersByRegistrationDateWithCustomDateFormat() throws ParseException {
    	DateAfter<Customer> after13th = new DateAfter<>("registrationDate", new String[] {"13-03-2014"},
    			withDateFormat("dd-MM-yyyy"));
        
        List<Customer> result = customerRepo.findAll(after13th);
        assertThat(result)
            .hasSize(1)
            .containsOnly(moeSzyslak);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void rejectsInvalidNumberOfArguments() throws ParseException {
        new DateAfter<>("path", new String[] { "2014-03-10", "2014-03-11" }, defaultConverter);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void rejectsMissingArgument() throws ParseException {
        new DateAfter<>("path", new String[] {}, defaultConverter);
    }
}
