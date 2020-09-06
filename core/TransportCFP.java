package lv.rcs.java.carbonfootprint.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.TreeMap;

import lv.rcs.java.carbonfootprint.utilities.Utilities;

public class TransportCFP extends DailyCFP {

	// fields
	private TreeMap<String, Double> dayTMap; // map of all transports used in a day and their individiual co2
	private double dayTSum; // combined transport co2 footprint of the day

	// setters
	public void setDayTMap(TreeMap<String, Double> dayTMap) {
		this.dayTMap = dayTMap;
	}

	public void setDayTSum(double dayTSum) {
		this.dayTSum = dayTSum;
	}

	// getters
	public TreeMap<String, Double> getDayTMap() {
		return dayTMap;
	}

	public double getDayTSum() {
		return dayTSum;
	}

	// constructors
	public TransportCFP() {
		super();
	}

	@Override
	public String toString() {
		return "TransportCFP [dayTMap=" + dayTMap + ", dayTSum=" + dayTSum + "]";
	}

	// make a map of constants from TransportConstants.txt file
	public final TreeMap<String, TreeMap<String, Double>> transportConstants() throws FileNotFoundException {

		File transpFile = new File(TransportCFP.class.getClassLoader()
				.getResource("lv/rcs/java/carbonfootprint/textFiles/TransportConstants.txt").getFile());

		Scanner fileSc = new Scanner(transpFile);

		TreeMap<String, TreeMap<String, Double>> transportConstantMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
		TreeMap<String, Double> valMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

		do {

			String str = fileSc.nextLine().trim();
			double value;

			if (str.contains("//") || str.isEmpty()) {
			} else if (!str.contains(":")) {
				String keyOut = str;
				valMap = new TreeMap<>();
				transportConstantMap.put(keyOut, valMap);
			} else {
				String keyIn = str.substring(0, str.indexOf(":"));

				try {
					value = Double.parseDouble(str.substring((str.indexOf(":")) + 1));
				} catch (NumberFormatException e) {
					System.out.println("Error in TransportConstants.txt file, line [" + str + "].");
					value = 0d;
				}

				valMap.put(keyIn, value);
			}
		} while (fileSc.hasNextLine());
		fileSc.close();

		return transportConstantMap;
	}

	/*
	 * getting initial data from user (type of transport and driven/walked km),
	 * using that data in calculations, returning a set of calculated data (type of
	 * transport and its co2 footprint )
	 */

	public TreeMap<String, Double> findTransportCFP(Scanner sc) throws FileNotFoundException {

		TreeMap<String, TreeMap<String, Double>> transportConstantMap = transportConstants();
		Utilities.printInstructions("transport", transportConstantMap);

		TreeMap<String, Double> dayTranspCFP = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

		String transpType;
		String transpCat;
		do {

			transpCat = readInputStr(sc, "Enter transport category : ");

			if (transpCat.equalsIgnoreCase("s")) {
				System.out.println();

			} else if (transportConstantMap.get(transpCat) != null) {

				boolean notFound = false;
				boolean stop = false;

				System.out.println(
						"Available types of " + transpCat + " transport : " + transportConstantMap.get(transpCat).keySet());
				System.out.println();
				do {
					transpType = readInputStr(sc, "Enter type of " + transpCat + " transport : ");
					if (transpType.equalsIgnoreCase("s")) {
						System.out.println();
						stop = false;
					} else if (transportConstantMap.get(transpCat).get(transpType) != null) {
						double km = readInputDbl(sc, "Enter for how many km you used the " + transpType + " for : ");
						double co2 = (double) Math
								.round(transportConstantMap.get(transpCat).get(transpType) * km * 100d) / 100d;
						dayTranspCFP.put(transpType, co2);
						stop = true;
						notFound = false;
						System.out.println("Added!\n ");
					} else {
						notFound = true;
						sc.reset();
						System.out.println("Type of transport not found!");
					}
				} while (notFound || stop);
			} else {
				sc.reset();
				System.out.println("Type of category not found!");
			}

		} while (!transpCat.equalsIgnoreCase("s"));

		return dayTranspCFP;
	}
}
