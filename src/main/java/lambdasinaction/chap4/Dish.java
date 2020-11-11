package lambdasinaction.chap4;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;

public class Dish {

	private final String name;
	private final boolean vegetarian;
	private final int calories;
	private final Type type;

	public Dish(String name, boolean vegetarian, int calories, Type type) {
		this.name = name;
		this.vegetarian = vegetarian;
		this.calories = calories;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public boolean isVegetarian() {
		return vegetarian;
	}

	public int getCalories() {
		return calories;
	}

	public Type getType() {
		return type;
	}

	public enum Type {
		MEAT, FISH, OTHER
	}

	@Override
	public String toString() {
		return name;
	}

	public static final List<Dish> menu = Arrays.asList(
			new Dish("pork", false, 800, Dish.Type.MEAT),
			new Dish("beef", false, 700, Dish.Type.MEAT), 
			new Dish("chicken", false, 400, Dish.Type.MEAT),
			new Dish("french fries", true, 530, Dish.Type.OTHER), 
			new Dish("rice", true, 350, Dish.Type.OTHER),
			new Dish("season fruit", true, 120, Dish.Type.OTHER), 
			new Dish("pizza", true, 550, Dish.Type.OTHER),
			new Dish("prawns", false, 400, Dish.Type.FISH), 
			new Dish("salmon", false, 450, Dish.Type.FISH));

	public static void main(String[] args) {
		// Before java7
		List<Dish> lowCaloricDishes = new ArrayList<>();
		for (Dish d : menu) {
			if (d.getCalories() < 400) {
				lowCaloricDishes.add(d);
			}
		}
		Collections.sort(lowCaloricDishes, new Comparator<Dish>() {

			@Override
			public int compare(Dish o1, Dish o2) {
				return Integer.compare(o1.getCalories(), o2.getCalories());
			}

		});
		List<String> lowCaloricDishesName = new ArrayList<>();
		for (Dish d : lowCaloricDishes) {
			lowCaloricDishesName.add(d.getName());
		}
		System.out.println(lowCaloricDishesName);

		// After java8
		List<String> lowCaloricDishesName2 = menu.stream()
												 .filter(d -> d.getCalories() < 400)
												 .sorted(comparing(Dish::getCalories))
												 .map(Dish::getName)
												 .collect(toList());
		System.out.println("lowCaloricDishesName2 = " + lowCaloricDishesName2);
		
		List<String> lowCaloricDishesName3 = menu.parallelStream()
				 								 .filter(d -> d.getCalories() < 400)
				 								 .sorted(comparing(Dish::getCalories))
				 								 .map(Dish::getName)
				 								 .collect(toList());
		System.out.println("lowCaloricDishesName3 = " + lowCaloricDishesName3);
		
		// findAny
		Optional<Dish> dish = menu.stream()
								  .filter(Dish::isVegetarian)
								  .findAny();
		System.out.println(dish);
		int count = menu.stream()
						.map(d -> 1)
						.reduce(0, (a, b) -> a + b);
		System.out.println("count = " + count);
	}

}