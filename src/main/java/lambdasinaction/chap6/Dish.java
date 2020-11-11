package lambdasinaction.chap6;

import java.util.*;

import lambdasinaction.chap6.Grouping.CaloricLevel;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.*;
import static lambdasinaction.chap6.Dish.menu;
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

    public enum Type { MEAT, FISH, OTHER }

    @Override
    public String toString() {
        return name;
    }

    public static final List<Dish> menu =
            asList( new Dish("pork", false, 800, Dish.Type.MEAT),
                    new Dish("beef", false, 700, Dish.Type.MEAT),
                    new Dish("chicken", false, 400, Dish.Type.MEAT),
                    new Dish("french fries", true, 530, Dish.Type.OTHER),
                    new Dish("rice", true, 350, Dish.Type.OTHER),
                    new Dish("season fruit", true, 120, Dish.Type.OTHER),
                    new Dish("pizza", true, 550, Dish.Type.OTHER),
                    new Dish("prawns", false, 400, Dish.Type.FISH),
                    new Dish("salmon", false, 450, Dish.Type.FISH));

    public static final Map<String, List<String>> dishTags = new HashMap<>();
   
    public enum CaloricLevel { DIET, NORMAL, FAT }
   
    static {
        dishTags.put("pork", asList("greasy", "salty"));
        dishTags.put("beef", asList("salty", "roasted"));
        dishTags.put("chicken", asList("fried", "crisp"));
        dishTags.put("french fries", asList("greasy", "fried"));
        dishTags.put("rice", asList("light", "natural"));
        dishTags.put("season fruit", asList("fresh", "natural"));
        dishTags.put("pizza", asList("tasty", "salty"));
        dishTags.put("prawns", asList("tasty", "roasted"));
        dishTags.put("salmon", asList("delicious", "fresh"));
    }
    public static void main(String[] args) {
		long howManyDishes = menu.stream().count();
		System.out.println(howManyDishes);
		int totalCalories = menu.stream().mapToInt(Dish::getCalories).sum();
		System.out.println(totalCalories);
		String shortMenu = menu.stream().map(Dish::getName)
				.collect( reducing( (s1, s2) -> s1 + " " +  s2 ) ).get();
		System.out.println("shortMenu = " + shortMenu);
		Map<Dish.Type, List<Dish>> dishesByType =
				menu.stream().collect(groupingBy(Dish::getType));
		System.out.println("dishesByType = " + dishesByType);
   
		// Multiple Grouping
		menu.stream().collect(
				groupingBy(Dish::getType,
						groupingBy((Dish dish) -> {
                            if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                            else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                            else return CaloricLevel.FAT;
                        } )
                )
        );
    }
}