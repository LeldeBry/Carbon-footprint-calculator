package lv.rcs.java.carbonfootprint.core;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.TreeMap;

public class DailyCFP {

	// fields
	private LocalDate date = LocalDate.now();
	private double totalCFP;
	private String comments;

	// getters
	public double getTotalCFP() {
		return totalCFP;
	}

	public LocalDate getDate() {
		return date;
	}

	public String getComments() {
		return comments;
	}
	
	// setters
	public void setTotalCFP(double totalCFP) {
		this.totalCFP = totalCFP;
	}

	public void setDate(String date) {
		this.date = LocalDate.parse(date);
	}
	
	public void setComments(String comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "DailyCFP [date=" + date + ", totalCFP=" + totalCFP + ", comments=" + comments + "]";
	}

	// constructors
	public DailyCFP() {

	}

	// METHODS

	// read input string
	public String readInputStr(Scanner sc, String prompt) {

		System.out.print(prompt);
		String str = sc.nextLine();

		return str.trim();
	}

	// read double input
	public double readInputDbl(Scanner sc, String prompt) {

		while (true) {
			System.out.print(prompt);

			try {
				String str = sc.nextLine();
				double dbl = Double.parseDouble(str);

				return dbl;

			} catch (NumberFormatException e) {
				System.out.println("Not a number!");
			}
		}
	}

	// find the sum of treemap values
	public double treeMapSum(TreeMap<String, Double> tm) {

		double sum = 0;
		for (double elem : tm.values()) {
			sum += elem;
		}
		return Math.round(sum * 100d) / 100d;

	}

	// finding total daily co2 footprint
	public double findTotalCFP(double food, double transp) {

		double totalCFP = food + transp;

		return Math.round(totalCFP * 100d) / 100d;
	}
	
	
}
