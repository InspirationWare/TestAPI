package com.techbeetraining.ExercisePart2;

import java.util.ArrayList;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification; 
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JSONTest {
	
	private int heightThreshold = 200; 
	private int heightCount = 10;
	private String [] expectedNames = new String []{"Darth Vader", "Chewbacca", "Roos Tarpals", "Rugor Nass", "Yarael Poof", "Lama Su", "Tuan Wu", "Grievous", "Tarfful", "Tion Medon"};
	private int checkedNames = 82;
	
	private RequestSpecification request = null;
	private Response response = null;     
    private JsonPath jsonResponse = null;
	
	private ArrayList<String> names = new ArrayList();
	private ArrayList<String> heights = new ArrayList();
	private ArrayList<String> acceptedNames = new ArrayList();
		
	@Test
	public void testNull() {
		RestAssured.baseURI = "https://swapi.dev";
		request = RestAssured.given();
		response = request.get("/api/people");  
		jsonResponse = response.jsonPath();	
		
		Assertions.assertTrue(200 == response.getStatusCode());
	}
	
	@Test
	public void testHeightCount() {
		RestAssured.baseURI = "https://swapi.dev";
		request = RestAssured.given();
		response = request.get("/api/people");  
		jsonResponse = response.jsonPath();	
		
		names = jsonResponse.get("results.name");
		heights = jsonResponse.get("results.height");
		for (int i = 0; i < names.size(); i++)
		{
			if (Integer.parseInt(heights.get(i)) > heightThreshold) {	
				acceptedNames.add(names.get(i));
			}
		}	
		
		Assertions.assertTrue(heightCount == acceptedNames.size());
	}
	
	@Test
	public void testHeightNames() {
		RestAssured.baseURI = "https://swapi.dev";
		request = RestAssured.given();
		response = request.get("/api/people");  
		jsonResponse = response.jsonPath();	
		
		boolean result = false;
		if (acceptedNames.size() == expectedNames.length) {
			loop:
			for (int i = 0; i < expectedNames.length; i++) {
				if(acceptedNames.get(i).equals(expectedNames[i])) {
					result = true;
				}
				else {
					result = false;
					break loop;
				}
			}
		}
		else
			result = false;
		
		Assertions.assertTrue(result);
	}
	
	@Test
	public void testCount() {
		RestAssured.baseURI = "https://swapi.dev";
		request = RestAssured.given();
		response = request.get("/api/people");  
		jsonResponse = response.jsonPath();	
		
		Assertions.assertTrue(checkedNames == (int)jsonResponse.get("count"));		
	}
}