package com.ubaid.simple_springboot_app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SimpleController {

    // Basic welcome endpoint
    @GetMapping("/")
    public String home() {
        return "Welcome to Simple Spring Boot Application!";
    }

    // Simple hello endpoint
    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }

    // Hello with path variable
    @GetMapping("/hello/{name}")
    public String helloWithName(@PathVariable String name) {
        return "Hello, " + name + "! Welcome to our Spring Boot application.";
    }

    // Greet with query parameter
    @GetMapping("/greet")
    public String greetWithParam(@RequestParam(defaultValue = "Guest") String name) {
        return "Greetings, " + name + "! Today is " + LocalDateTime.now().toLocalDate();
    }

    // Multiple query parameters
    @GetMapping("/welcome")
    public String welcomeUser(
            @RequestParam(defaultValue = "Guest") String name,
            @RequestParam(defaultValue = "Unknown") String city) {
        return String.format("Welcome %s from %s! Hope you're having a great day!", name, city);
    }

    // Return JSON object - application info
    @GetMapping("/info")
    public Map<String, Object> getAppInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("application", "Simple Spring Boot App");
        info.put("version", "1.0.0");
        info.put("status", "running");
        info.put("timestamp", LocalDateTime.now());
        info.put("developer", "Ubaid");
        return info;
    }

    // Health check endpoint
    @GetMapping("/health")
    public Map<String, Object> healthCheck() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("timestamp", LocalDateTime.now());
        health.put("uptime", "Application is running smoothly");
        health.put("memory", Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
        return health;
    }

    // Return a list of users (mock data)
    @GetMapping("/users")
    public List<Map<String, Object>> getUsers() {
        return List.of(
                Map.of("id", 1, "name", "John Doe", "email", "john@example.com", "city", "New York"),
                Map.of("id", 2, "name", "Jane Smith", "email", "jane@example.com", "city", "London"),
                Map.of("id", 3, "name", "Mike Johnson", "email", "mike@example.com", "city", "Tokyo")
        );
    }

    // Get user by ID - CORRECTED VERSION
    @GetMapping("/users/{id}")
    public Map<String, Object> getUserById(@PathVariable int id) {
        // Create a more efficient data structure
        Map<Integer, Map<String, Object>> users = Map.of(
                1, Map.of("id", 1, "name", "John Doe", "email", "john@example.com", "city", "New York"),
                2, Map.of("id", 2, "name", "Jane Smith", "email", "jane@example.com", "city", "London"),
                3, Map.of("id", 3, "name", "Mike Johnson", "email", "mike@example.com", "city", "Tokyo")
        );

        if (users.containsKey(id)) {
            return users.get(id); // No need to cast anymore
        } else {
            return Map.of("error", "User not found", "id", id);
        }
    }

    // API status endpoint - FIXED endpoints list
    @GetMapping("/status")
    public Map<String, Object> getStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("api", "Simple Spring Boot API");
        status.put("version", "v1.0");
        status.put("status", "active");
        status.put("endpoints", List.of(
                "/",
                "/hello",
                "/hello/{name}",
                "/greet",
                "/welcome",
                "/info",
                "/health",
                "/users",
                "/users/{id}",
                "/status",
                "/calculate"
        ));
        status.put("timestamp", LocalDateTime.now());
        return status;
    }

    // Simple calculation endpoint
    @GetMapping("/calculate")
    public Map<String, Object> calculate(
            @RequestParam(defaultValue = "10") int num1,
            @RequestParam(defaultValue = "5") int num2,
            @RequestParam(defaultValue = "add") String operation) {

        Map<String, Object> result = new HashMap<>();
        result.put("num1", num1);
        result.put("num2", num2);
        result.put("operation", operation);

        switch (operation.toLowerCase()) {
            case "add":
                result.put("result", num1 + num2);
                break;
            case "subtract":
                result.put("result", num1 - num2);
                break;
            case "multiply":
                result.put("result", num1 * num2);
                break;
            case "divide":
                if (num2 != 0) {
                    result.put("result", (double) num1 / num2);
                } else {
                    result.put("error", "Cannot divide by zero");
                }
                break;
            default:
                result.put("error", "Invalid operation. Use: add, subtract, multiply, divide");
        }

        return result;
    }
}