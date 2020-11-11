package lambdasinaction.chap5;

import lambdasinaction.chap4.*;

import java.util.stream.*;
import java.util.*;
import java.util.function.IntSupplier;

import static java.util.stream.Collectors.toList;

import static lambdasinaction.chap4.Dish.menu;

public class Filtering {

	public static void main(String... args) {

		// Filtering with predicate
		List<Dish> vegetarianMenu = menu.stream().filter(Dish::isVegetarian).collect(toList());

		vegetarianMenu.forEach(System.out::println);

		// Filtering unique elements
		List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
		numbers.stream().filter(i -> i % 2 == 0).distinct().forEach(System.out::println);

		// Truncating a stream
		List<Dish> dishesLimit3 = menu.stream().filter(d -> d.getCalories() > 300).limit(3).collect(toList());

		dishesLimit3.forEach(System.out::println);

		// Skipping elements
		List<Dish> dishesSkip2 = menu.stream().filter(d -> d.getCalories() > 300).skip(2).collect(toList());

		dishesSkip2.forEach(System.out::println);

		// iterate
		Stream.iterate(new int[] { 0, 1 }, t -> new int[] { t[1], t[0] + t[1] })
			  .limit(20)
			  .forEach(t -> System.out.println(t[1]));
		Stream.generate(Math::random).limit(5).forEach(System.out::print);
		
		IntSupplier fib = new IntSupplier() {
			private int previous = 0;
			private int current = 1;

			public int getAsInt() {
				int oldPrevious = this.previous;
				int nextValue = this.previous + this.current;
				this.previous = this.current;
				this.current = nextValue;
				return oldPrevious;
			}
		};
		IntStream.generate(fib).limit(10).forEach(System.out::println);
	}
}
