package com.simple.spring.simpleservices;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SimpleServicesApplicationTests {

	@Autowired
    private MockMvc mockMvc;
	
	// Test landing page is working
	@Test
	public void test_landing_page() throws Exception {
	    this.mockMvc.perform(get("/"))
	            .andExpect(status().isOk());
	}
	
	// Test different test cases for users output based on query parameters supplied
	@Test
	public void test_users_output() throws Exception {
		
		this.mockMvc.perform(get("/load")
	            )
	            .andExpect(status().isOk());
		
		// test to see if valid range product correct output
	    this.mockMvc.perform(get("/users")
	    		.param("min", "0")
	            .param("max", "4000")
	            )
	            .andExpect(status().isOk()) 
	            .andExpect(content().contentType("application/json;charset=UTF-8"))
	            .andExpect(jsonPath("$.results[*].name", containsInAnyOrder("John", "Mary Posa")))
	            .andExpect(jsonPath("$.results[*].salary", containsInAnyOrder(2500.05, 4000.0))); 
	    
	    // test to see if valid range produce correct output
	    this.mockMvc.perform(get("/users")
	    		.param("min", "0")
	            .param("max", "4001")
	            )
	            .andExpect(status().isOk()) 
	            .andExpect(content().contentType("application/json;charset=UTF-8"))
	            .andExpect(jsonPath("$.results[*].name", containsInAnyOrder("John", "Mary Posa", "Mike")))
	            .andExpect(jsonPath("$.results[*].salary", containsInAnyOrder(2500.05, 4000.0, 4001.0))); 
	    
	    // test min invalid input, max valid input
	    this.mockMvc.perform(get("/users")
	    		.param("min", "abcd")
	            .param("max", "4001")
	            )
	            .andExpect(status().isOk()) 
	            .andExpect(content().contentType("application/json;charset=UTF-8"))
	            .andExpect(jsonPath("$.results[*].name", containsInAnyOrder("John", "Mary Posa", "Mike")))
	            .andExpect(jsonPath("$.results[*].salary", containsInAnyOrder(2500.05, 4000.0, 4001.0))); 
	    
	    // test min valid input, max invalid input
	    this.mockMvc.perform(get("/users")
	    		.param("min", "2501")
	            .param("max", "z21r4ff")
	            )
	            .andExpect(status().isOk()) 
	            .andExpect(content().contentType("application/json;charset=UTF-8"))
	            .andExpect(jsonPath("$.results[*].name", containsInAnyOrder("Mary Posa")))
	            .andExpect(jsonPath("$.results[*].salary", containsInAnyOrder(4000.0)));
	    
	    // test default without arguments
	    this.mockMvc.perform(get("/users"))
	            .andExpect(status().isOk()) 
	            .andExpect(content().contentType("application/json;charset=UTF-8"))
	            .andExpect(jsonPath("$.results[*].name", containsInAnyOrder("John", "Mary Posa")))
	            .andExpect(jsonPath("$.results[*].salary", containsInAnyOrder(2500.05, 4000.0))); 
	}

}
