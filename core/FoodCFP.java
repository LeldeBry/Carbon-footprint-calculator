package lv.rcs.java.carbonfootprint.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.TreeMap;

import lv.rcs.java.carbonfootprint.utilities.Utilities;

public class FoodCFP extends DailyCFP {

	// fields
	private TreeMap<String, Double> dayFMap; // map of all individual foods and respective co2 emissions that day
	private double dayFSum; // combined food co2 footprint of the day

	// setters
	public void setDayFMap(TreeMap<String, Double> dayFMap) {
		this.dayFMap = dayFMap;
	}

	public void setDayFSum(double dayFSum) {
		this.dayFSum = dayFSum;
	}

	// getters
	public double getDayFSum() {
		return dayFSum;
	}

	public TreeMap<String, Double> getDayFMap() {
		return dayFMap;
	}

	// constructors
	public FoodCFP() {
		super();
	}

	@Override
	public String toString() {
		return "FoodCFP [dayFMap=" + dayFMap + ", dayFSum=" + dayFSum + "]";
	}

	// make a map of constants from FoodConstants.txt file
	public final TreeMap<String, TreeMap<String, Double>> foodConstants() throws FileNotFoundException {

		File foodFile = new File(FoodCFP.class.getClassLoader()
				.getResource("lv/rcs/java/carbonfootprint/textFiles/FoodConstants.txt").getFile());
		Scanner fileSc = new Scanner(foodFile);

		TreeMap<String, TreeMap<String, Double>> foodConstantMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
		TreeMap<String, Double> valMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

		do {

			String str = fileSc.nextLine().trim();
			Double value;

			if (str.contains("//") || str.isEmpty()) {

			} else if (!str.contains(":")) {
				String keyOut = str;
				valMap = new TreeMap<>();
				foodConstantMap.put(keyOut, valMap);
			} else {
				String keyIn = str.substring(0, str.indexOf(":"));

				try {
					value = Double.parseDouble(str.substring((str.indexOf(":")) + 1));
				} catch (NumberFormatException e) {
					System.out.println("Error in FoodConstants.txt file, line [" + str + "].");
					value = 0d;
				}

				valMap.put(keyIn, value);
			}

		} while (fileSc.hasNextLine());
		fileSc.close();

		return foodConstantMap;
	}

	/*
	 * getting initial data from user (type of food and used amount in grams), using
	 * that data in calculations, returning a set of calculated data (type of food
	 * and its c02 footprint)
	 */

	public TreeMap<String, Double> findFoodCFP(Scanner sc) throws FileNotFoundException {

		TreeMap<String, TreeMap<String, Double>> foodConstantMap = foodConstants();

		Utilities.printInstructions("food", foodConstantMap);

		TreeMap<String, Double> dayFoodCFP = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

		String foodType;
		String foodCat;
		do {

			foodCat = readInputStr(sc, "Enter food category : ");

			if (foodCat.equalsIgnoreCase("s")) {
				System.out.println();

			} else if (foodConstantMap.get(foodCat) != null) {

				boolean notFound = false;
				boolean stop = false;

				System.out.println("Available " + foodCat + " products : " + foodConstantMap.get(foodCat).keySet());
				System.out.println();
				do {
					foodType = readInputStr(sc, "Enter " + foodCat + " product : ");
					if (foodType.equalsIgnoreCase("s")) {
						System.out.println();
						stop = false;
					} else if (foodConstantMap.get(foodCat).get(foodType) != null) {
						double amount = readInputDbl(sc, "Enter amount of " + foodType + " used in grams : ");
						double co2 = (double) Math
								.round(foodConstantMap.get(foodCat).get(foodType) * (amount * 1E-3) * 100d) / 100d;
						dayFoodCFP.put(foodType, co2);
						stop = true;
						notFound = false;
						System.out.println("Added! ");
					} else {
						notFound = true;
						sc.reset();
						System.out.println("Type of food not found!");
					}
				} while (notFound || stop);
			} else {
				sc.reset();
				System.out.println("Type of category not found!");
			}

		} while (!foodCat.equalsIgnoreCase("s"));

		return dayFoodCFP;
	}
}
