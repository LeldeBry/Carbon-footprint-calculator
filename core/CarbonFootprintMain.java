package lv.rcs.java.carbonfootprint.core;

import java.io.FileNotFoundException;
import java.util.Scanner;

import lv.rcs.java.carbonfootprint.utilities.Utilities;

public class CarbonFootprintMain {

	public static void main(String[] args) throws FileNotFoundException {

		Scanner sc = new Scanner(System.in);

		Utilities.printGreetings();

		DailyCFP day = new DailyCFP();

		FoodCFP f = new FoodCFP();
		f.setDayFMap(f.findFoodCFP(sc));
		f.setDayFSum(f.treeMapSum(f.getDayFMap()));

		TransportCFP t = new TransportCFP();
		t.setDayTMap(t.findTransportCFP(sc));
		t.setDayTSum(t.treeMapSum(t.getDayTMap()));

		day.setTotalCFP(day.findTotalCFP(f.getDayFSum(), t.getDayTSum()));
		day.setComments(day.readInputStr(sc, "Enter comments : "));
		sc.close();

		Utilities.printCFP(day.getDate(), day.getTotalCFP(), f.getDayFSum(), t.getDayTSum());
		Utilities.printMap(f.getDayFMap(), "kg CO2eq");
		Utilities.printMap(t.getDayTMap(), "kg CO2eq");

	}
}
