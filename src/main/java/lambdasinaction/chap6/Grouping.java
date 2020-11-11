package lambdasinaction.chap6;

import java.util.*;
import java.util.stream.*;
import static java.util.Comparator.*; 
import java.util.stream.Collectors;
import java.util.stream.Collector.*;
import static java.util.stream.Collectors.*;
import static lambdasinaction.chap6.Dish.menu;
import static lambdasinaction.chap6.Dish.dishTags;

public class Grouping {

    enum CaloricLevel { DIET, NORMAL, FAT };

    public static void main(String ... args) {
        System.out.println("Dishes grouped by type: " + groupDishesByType());
        System.out.println("Dish names grouped by type: " + groupDishNamesByType());
//        System.out.println("Dish tags grouped by type: " + groupDishTagsByType());
        System.out.println("Caloric dishes grouped by type: " + groupCaloricDishesByType());
        System.out.println("Dishes grouped by caloric level: " + groupDishesByCaloricLevel());
        System.out.println("Dishes grouped by type and caloric level: " + groupDishedByTypeAndCaloricLevel());
        System.out.println("Count dishes in groups: " + countDishesInGroups());
        System.out.println("Most caloric dishes by type: " + mostCaloricDishesByType());
        System.out.println("Most caloric dishes by type: " + mostCaloricDishesByTypeWithoutOprionals());
        System.out.println("Sum calories by type: " + sumCaloriesByType());
        System.out.println("Caloric levels by type: " + caloricLevelsByType());
        Map<Dish.Type, Optional<Dish>> mostCaloricByType =
        		menu.stream()
        		.collect(groupingBy(Dish::getType,
        		maxBy(comparingInt(Dish::getCalories))));
//        System.out.println("mostCaloricByType = " + mostCaloricByType);
        // Finding the highest-calorie Dish in each subgroup
        Map<Dish.Type, Dish> mostCaloricByType2 = 
        		menu.stream()
        			.collect(groupingBy(Dish::getType, 
        					 collectingAndThen(
        							 maxBy(comparingInt(Dish::getCalories)), 
        							 Optional::get)));
//        System.out.println("mostCaloricByType2 = " + mostCaloricByType2);
        
        // total
        Map<Dish.Type, Integer> totalCaloriesByType =
        		menu.stream().collect(groupingBy(Dish::getType,
        		summingInt(Dish::getCalories)));
        
        // caloricLevelsByType
        Map<Dish.Type, Set<CaloricLevel>> caloricLevelsByType =
        		menu.stream().collect(
        		groupingBy(Dish::getType, mapping(
        		dish -> { 
        		if (dish.getCalories() <= 400) return CaloricLevel.DIET;
        		else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
        		else return CaloricLevel.FAT; },
        		toSet() )));
//        System.out.println("caloricLevelsByType = " + caloricLevelsByType);
        
        // HashSet
        Map<Dish.Type, Set<CaloricLevel>> caloricLevelsByType2 =
        		menu.stream().collect(
        		groupingBy(Dish::getType, mapping(
        		dish -> { if (dish.getCalories() <= 400) return CaloricLevel.DIET;
        		else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
        		else return CaloricLevel.FAT; },
        		toCollection(HashSet::new) )));
//        System.out.println("caloricLevelsByType2 = " + caloricLevelsByType2);
        
        // Partitioning
        Map<Boolean, List<Dish>> partitionedMenu = 
        		menu.stream().collect(partitioningBy(Dish::isVegetarian));
        System.out.println("partitionedMenu = " + partitionedMenu);
        List<Dish> vegetarianDishes = partitionedMenu.get(true);
        System.out.println("vegetarianDishes = " + vegetarianDishes);
        
        Map<Boolean, Map<Dish.Type, List<Dish>>> vegetarianDishesByType = 
        		menu.stream().collect(
        				partitioningBy(Dish::isVegetarian, groupingBy(Dish::getType)));
        System.out.println("vegetarianDishesByType = " + vegetarianDishesByType);
        
        Map<Boolean, Dish> mostCaloricPartitionedByVegetarian =
        		menu.stream().collect(
        		partitioningBy(Dish::isVegetarian,
        		collectingAndThen(
        		maxBy(comparingInt(Dish::getCalories)),
        		Optional::get)));
        System.out.println("mostCaloricPartitionedByVegetarian = " + mostCaloricPartitionedByVegetarian);
    
        // Partitioning numbers into prime and nonprime
        
    }

    private static Map<Dish.Type, List<Dish>> groupDishesByType() {
        return menu.stream().collect(groupingBy(Dish::getType));
    }

    private static Map<Dish.Type, List<String>> groupDishNamesByType() {
        return menu.stream().collect(groupingBy(Dish::getType, mapping(Dish::getName, toList())));
    }

//    private static Map<Dish.Type, Set<String>> groupDishTagsByType() {
//        return menu.stream().collect(groupingBy(Dish::getType, flatMapping(dish -> dishTags.get( dish.getName() ).stream(), toSet())));
//    }

	private static Map<Dish.Type, List<Dish>> groupCaloricDishesByType() {
        return menu.stream().filter(dish -> dish.getCalories() > 500).collect(groupingBy(Dish::getType));
//        return menu.stream().collect(groupingBy(Dish::getType, filtering(dish -> dish.getCalories() > 500, toList())));
    }

    private static Map<CaloricLevel, List<Dish>> groupDishesByCaloricLevel() {
        return menu.stream().collect(
                groupingBy(dish -> {
                    if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                    else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                    else return CaloricLevel.FAT;
                } ));
    }

    private static Map<Dish.Type, Map<CaloricLevel, List<Dish>>> groupDishedByTypeAndCaloricLevel() {
        return menu.stream().collect(
                groupingBy(Dish::getType,
                        groupingBy((Dish dish) -> {
                            if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                            else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                            else return CaloricLevel.FAT;
                        } )
                )
        );
    }

    private static Map<Dish.Type, Long> countDishesInGroups() {
        return menu.stream().collect(groupingBy(Dish::getType, counting()));
    }

    private static Map<Dish.Type, Optional<Dish>> mostCaloricDishesByType() {
        return menu.stream().collect(
                groupingBy(Dish::getType,
                        reducing((Dish d1, Dish d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2)));
    }

    private static Map<Dish.Type, Dish> mostCaloricDishesByTypeWithoutOprionals() {
        return menu.stream().collect(
                groupingBy(Dish::getType,
                        collectingAndThen(
                                reducing((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2),
                                Optional::get)));
    }

    private static Map<Dish.Type, Integer> sumCaloriesByType() {
        return menu.stream().collect(groupingBy(Dish::getType,
                summingInt(Dish::getCalories)));
    }

    private static Map<Dish.Type, Set<CaloricLevel>> caloricLevelsByType() {
        return menu.stream().collect(
                groupingBy(Dish::getType, mapping(
                        dish -> { if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                        else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                        else return CaloricLevel.FAT; },
                        toSet() )));
    }
}
