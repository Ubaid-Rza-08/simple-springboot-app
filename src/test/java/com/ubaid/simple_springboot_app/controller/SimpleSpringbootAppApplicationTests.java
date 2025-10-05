package com.ubaid.simple_springboot_app.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.containsString;

@WebMvcTest(SimpleController.class)
class SimpleSpringbootAppApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testHome() throws Exception {
		mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(content().string("Welcome to Simple Spring Boot Application!"));
	}

	@Test
	public void testHello() throws Exception {
		mockMvc.perform(get("/hello"))
				.andExpect(status().isOk())
				.andExpect(content().string("Hello, World!"));
	}

	@Test
	public void testHelloWithName() throws Exception {
		mockMvc.perform(get("/hello/John"))
				.andExpect(status().isOk())
				.andExpect(content().string("Hello, John! Welcome to our Spring Boot application."));
	}

	@Test
	public void testGreetWithParam() throws Exception {
		mockMvc.perform(get("/greet?name=Alice"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Greetings, Alice!")))
				.andExpect(content().string(containsString("Today is")));
	}

	@Test
	public void testGreetWithDefaultParam() throws Exception {
		mockMvc.perform(get("/greet"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Greetings, Guest!")))
				.andExpect(content().string(containsString("Today is")));
	}

	@Test
	public void testWelcomeWithParams() throws Exception {
		mockMvc.perform(get("/welcome?name=John&city=Boston"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Welcome John from Boston!")))
				.andExpect(content().string(containsString("Hope you're having a great day!")));
	}

	@Test
	public void testWelcomeWithDefaultParams() throws Exception {
		mockMvc.perform(get("/welcome"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Welcome Guest from Unknown!")))
				.andExpect(content().string(containsString("Hope you're having a great day!")));
	}

	@Test
	public void testInfo() throws Exception {
		mockMvc.perform(get("/info"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.application").value("Simple Spring Boot App"))
				.andExpect(jsonPath("$.version").value("1.0.0"))
				.andExpect(jsonPath("$.status").value("running"))
				.andExpect(jsonPath("$.developer").value("Ubaid"));
	}

	@Test
	public void testHealthCheck() throws Exception {
		mockMvc.perform(get("/health"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("UP"))
				.andExpect(jsonPath("$.uptime").exists())
				.andExpect(jsonPath("$.timestamp").exists())
				.andExpect(jsonPath("$.memory").exists());
	}

	@Test
	public void testGetUsers() throws Exception {
		mockMvc.perform(get("/users"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$.length()").value(3))
				.andExpect(jsonPath("$[0].id").value(1))
				.andExpect(jsonPath("$[0].name").value("John Doe"))
				.andExpect(jsonPath("$[0].email").value("john@example.com"))
				.andExpect(jsonPath("$[0].city").value("New York"))
				.andExpect(jsonPath("$[1].name").value("Jane Smith"))
				.andExpect(jsonPath("$[2].name").value("Mike Johnson"));
	}

	@Test
	public void testGetUserById() throws Exception {
		mockMvc.perform(get("/users/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("John Doe"))
				.andExpect(jsonPath("$.email").value("john@example.com"))
				.andExpect(jsonPath("$.city").value("New York"));
	}

	@Test
	public void testGetUserById2() throws Exception {
		mockMvc.perform(get("/users/2"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(2))
				.andExpect(jsonPath("$.name").value("Jane Smith"))
				.andExpect(jsonPath("$.email").value("jane@example.com"))
				.andExpect(jsonPath("$.city").value("London"));
	}

	@Test
	public void testGetUserById3() throws Exception {
		mockMvc.perform(get("/users/3"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(3))
				.andExpect(jsonPath("$.name").value("Mike Johnson"))
				.andExpect(jsonPath("$.email").value("mike@example.com"))
				.andExpect(jsonPath("$.city").value("Tokyo"));
	}

	@Test
	public void testGetUserByInvalidId() throws Exception {
		mockMvc.perform(get("/users/999"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.error").value("User not found"))
				.andExpect(jsonPath("$.id").value(999));
	}

	@Test
	public void testStatus() throws Exception {
		mockMvc.perform(get("/status"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.api").value("Simple Spring Boot API"))
				.andExpect(jsonPath("$.version").value("v1.0"))
				.andExpect(jsonPath("$.status").value("active"))
				.andExpect(jsonPath("$.endpoints").isArray())
				.andExpect(jsonPath("$.endpoints.length()").value(11))
				.andExpect(jsonPath("$.timestamp").exists());
	}

	@Test
	public void testCalculateAdd() throws Exception {
		mockMvc.perform(get("/calculate?num1=10&num2=5&operation=add"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.num1").value(10))
				.andExpect(jsonPath("$.num2").value(5))
				.andExpect(jsonPath("$.operation").value("add"))
				.andExpect(jsonPath("$.result").value(15));
	}

	@Test
	public void testCalculateSubtract() throws Exception {
		mockMvc.perform(get("/calculate?num1=10&num2=3&operation=subtract"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.result").value(7));
	}

	@Test
	public void testCalculateMultiply() throws Exception {
		mockMvc.perform(get("/calculate?num1=7&num2=3&operation=multiply"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.result").value(21));
	}

	@Test
	public void testCalculateDivide() throws Exception {
		mockMvc.perform(get("/calculate?num1=15&num2=3&operation=divide"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.result").value(5.0));
	}

	@Test
	public void testCalculateDivideByZero() throws Exception {
		mockMvc.perform(get("/calculate?num1=10&num2=0&operation=divide"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.error").value("Cannot divide by zero"))
				.andExpect(jsonPath("$.result").doesNotExist());
	}

	@Test
	public void testCalculateInvalidOperation() throws Exception {
		mockMvc.perform(get("/calculate?num1=10&num2=5&operation=invalid"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.error").value("Invalid operation. Use: add, subtract, multiply, divide"))
				.andExpect(jsonPath("$.result").doesNotExist());
	}

	@Test
	public void testCalculateDefaultValues() throws Exception {
		mockMvc.perform(get("/calculate"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.num1").value(10))
				.andExpect(jsonPath("$.num2").value(5))
				.andExpect(jsonPath("$.operation").value("add"))
				.andExpect(jsonPath("$.result").value(15));
	}
}