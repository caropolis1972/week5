package week5;
/*
*
Section Handout #8: Data Structures
Parts of this handout by Brandon Burr and Patrick Young
Your task for this section is to write a program that reads in a file 
containing flight destinations from various cities, and then allow the user 
to plan a round-trip flight route.
The flight data come from a file named flights.txt, which has the following format:
• Each line consists of a pair of cities 
  separated by an arrow indicated by the two character combination ->, as in
  New York -> Anchorage
• The file may contain blank lines for readability (you should just ignore these).
Your program should:
• Read in the flight information from the file flights.txt and store it in an appropriate data structure.
• Display the complete list of cities.
• Allow the user to select a city from which to start.
• In a loop, print out all the destinations that the user may reach directly from the current city, 
  and prompt the user to select the next city.
• Once the user has selected a round-trip route (i.e., once the user has selected a flight that returns them to the starting city), 
  exit from the loop and print out the route that was chosen.
A critical issue in building this program is designing appropriate data structures
to keep track of the information you'll need in order to produce flight plans. 
You'll need to both have a way of keeping track of information on available flights 
that you read in from the flights.txt file, as well as a means for keeping track of 
the flight routes that the user is choosing in constructing their flight plan. 
Consider how both ArrayLists and HashMaps might be useful to keep track of the information you care about.
*
*	Author: Rosa C. Rodriguez
*	File: FlightPlanner
*	week5
*/
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import acm.graphics.GObject;
import acm.program.ConsoleProgram;
import acm.util.ErrorException;

public class FlightPlanner extends ConsoleProgram {
	//Instance variables
	//List of Flights objects
	private HashMap<String, ArrayList<String>> flights = new HashMap<String, ArrayList<String>>();
	private ArrayList<String> roundTripPlan = new ArrayList<String>();
	private String startingCity = "";

	String filename = "";
	String line = "";
	
	public void run() {
		loadFlights();
		selectRoundTripRoute();
		printRoundTripPlan();
	}

	//Read each line of the file and parse it
	private void loadFlights() {
		try {
			BufferedReader rd = new BufferedReader(new FileReader("C:\\\\Users\\\\carop\\\\Desktop\\\\Flights2.txt"));
			while (true) {
				String line = rd.readLine();
				if (line == null) break;
				parseLine(line);
			}
			rd.close();
		} catch(IOException ex) {
			throw new ErrorException(ex);
		}
	}

	//User entry city origin and partial destinations
	private void selectRoundTripRoute() {
		println("Welcome to Flight Planner!");
		println("Here's a list of all the cities in our database:");
		
		//Read the hashMap Flights and display the origin cities which are the keys
		for(String originCity: flights.keySet()) {
			println(originCity);
		}
				
		println("Let's plan a round-trip route!");
		
		// this city is the origin and destination for the round trip
		startingCity = readLine("Enter the starting city: ").trim();
		roundTripPlan.add(startingCity);
		
		String currentCity = startingCity;
		String nextCity = "";
		
		while(!startingCity.equalsIgnoreCase(nextCity)) {
			nextCity = displayDestinationCities(currentCity);			

			if (!currentCity.equalsIgnoreCase(nextCity))  {
				roundTripPlan.add(nextCity);
				currentCity = nextCity;					
			}
		}	
	}
	
	//display destination cities for any departing city key
	private String displayDestinationCities(String currentCity) {
		while(true) {

			println("From " + currentCity + " you can fly directly to: ");		
			lookupCity(currentCity);

			//request the next city
			String nextCity = readLine("Where do you want to go from " + currentCity + "? ").trim();
			if(flights.containsKey(nextCity))
				return nextCity;
			
			println("You can't get to that city by a direct flight.");
			
		}
	}
	
	//Look up for any given city in the data
	private void lookupCity(String city) {
		if(flights.containsKey(city)) {
			ArrayList<String> destinationCities = flights.get(city);	
			for(String destinationCity: destinationCities) {
				println(destinationCity);
			}
		}
	}

	// Parse a single line from Flights file and populate the hashMap flights   
	private void parseLine(String line) {
		
		int departCityEnd = line.indexOf("-") - 1;
		int destinationCityStart = line.indexOf(">") + 2;
		
		String departCity = line.substring(0, departCityEnd).trim();
		String destinationCity = line.substring(destinationCityStart).trim();
		
		ArrayList<String> destinationCities;
		
		if(flights.containsKey(departCity)) {
			destinationCities = flights.get(departCity);
		} else {
			destinationCities = new ArrayList<String>();
		}
		destinationCities.add(destinationCity);
		flights.put(departCity, destinationCities);
	}	

	//displays the round trip plan
	private void printRoundTripPlan() {
		println("The route you've chosen is: ");
		
		//concatenate all items in the list array except for the last one.
		String plan = "";
		for (int i = 0; i < roundTripPlan.size() - 1; i++) {
			plan += roundTripPlan.get(i) + " -> ";
		}
		
		//append last item to complete plan.
		plan += roundTripPlan.get(roundTripPlan.size() - 1);

		println(plan);	
	}
	
	//Open the file and read it into memory 
	private BufferedReader openFile(String filename) {

		BufferedReader rd = null;
		
		while (rd == null) {
			try {
				rd = new BufferedReader(new FileReader(filename));
			} catch (IOException ex) {
				println("That file doesn't exist");
			}
		}
		return rd;
	}
}
