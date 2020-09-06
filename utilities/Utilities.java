package lv.rcs.java.carbonfootprint.utilities;

import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;


public class Utilities {

	public static void printMap(Map<String, Double> map, String unitOfMeasurment) {

		for (Entry<String, Double> e : map.entrySet()) {
			String key = e.getKey();
			Double val = e.getValue();
			System.out.println(key + " = " + val + " " + unitOfMeasurment);
		}
		System.out.println("--------------------------------------");
	}

	public static void printCFP(LocalDate localDate, double d, double e, double f) {

		System.out.println("--------------------------------------");
		System.out.println("--------------------------------------");
		System.out.println(localDate);
		System.out.println("Your total food carbon footprint for the day is " + e + "kg CO2eq.");
		System.out.println();
		System.out.println("Your total transport carbon footprint for the day is " + f + "kg CO2eq.");
		System.out.println();
		System.out.println("Total CO2 footprint of the day : " + d + " kg CO2eq");
		System.out.println("--------------------------------------");
		System.out.println("--------------------------------------");

	}
	
	public static void printGreetings () {
		
		System.out.println("Daily Carbon Footprint Calculator");
		System.out.println();
		System.out.println("------------------------------------------------------------------------------------------------------------");
		System.out.println();
		System.out.println("Everything we use has some sort of carbon footprint (CFP). "
				+ "\nTo make it comparable across different fields a unit \"carbon dioxide equivalent\" or \"CO2eq\" is used."
				+ "\nIt is a measure to compare the emissions from various greenhouse gases on the basis of their "
				+ "\nglobal warming potential (GWP), by converting amounts of other gases to the equivalent amount "
				+ "\nof carbon dioxide with the same GWP. ");
		System.out.println();
		System.out.println("Globally, the average CFP per person in a year is close to 4 tons CO2eq. "
				+ "\nTo have the best chance of avoiding a 2C rise in global temperatures, "
				+ "\nthe average needs to drop under 2 tons by 2050.");
		System.out.println();
		System.out.println("This calculator converts the amount of bought or eaten food products and amount of km travelled "
				+ "\nwith any transport into \"kg CO2eq\". It is taking into account the full life cycle of the food product "
				+ "\n(farming, processing, retail etc) and full life cycle of the type of transportation "
				+ "\n(production, fuel/electricity consumption, maintenance etc). "
				+ "\nAll data at the moment is global average and comes from studies as recent as available (years 2018-2020). "
				+ "\nP.S. You can change the data to newer, more locally accurate or personalized in the text files. ");
		System.out.println();
		System.out.println("------------------------------------------------------------------------------------------------------------");
		System.out.println();
	}
	
	public static void printInstructions (String foodOrTransport, TreeMap<String, TreeMap<String, Double>> constMap) {
		
		System.out.println("--------------------------------------");
		System.out.println("One by one enter types of " + foodOrTransport + " used today. ");
		System.out.println("When you are want to stop enter \"s\".");
		System.out.println();
		System.out.println("Available " + foodOrTransport + " categories : " + constMap.keySet());
		System.out.println();
	}
	
}
