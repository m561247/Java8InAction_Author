package lambdasinaction.chap5;

import lambdasinaction.chap4.*;

import java.util.*;
import static java.util.stream.Collectors.toList;
import static lambdasinaction.chap4.Dish.menu;

public class Mapping{

    public static void main(String...args){

        // map
        List<String> dishNames = menu.stream()
                                     .map(Dish::getName)
                                     .collect(toList());
        System.out.println(dishNames);

        // map
        List<String> words = Arrays.asList("Hello", "World");
        List<Integer> wordLengths = words.stream()
                                         .map(String::length)
                                         .collect(toList());
        System.out.println(wordLengths);
        System.out.println("­ì¥»ªº words = " + words);
        // Incorrect map
        words.stream()
        	 .map(word -> word.split(""))
        	 .distinct()
        	 .collect(toList());
        System.out.println("Incorrect map = " + words);
        
        // flatMap
        words.stream()
                 .flatMap((String line) -> Arrays.stream(line.split("")))
                 .distinct()
                 .forEach(System.out::println);

        // flatMap
        List<Integer> numbers1 = Arrays.asList(1,2,3,4,5);
        List<Integer> numbers2 = Arrays.asList(6,7,8);
        List<int[]> pairs =
                        numbers1.stream()
                                .flatMap((Integer i) -> numbers2.stream()
                                                       			.map((Integer j) -> new int[]{i, j}))
                                .filter(pair -> (pair[0] + pair[1]) % 3 == 0)
                                .collect(toList());
        pairs.forEach(pair -> System.out.println("(" + pair[0] + ", " + pair[1] + ")"));
    
    
        // Quiz 1
        List<Integer> quiz = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> squares = quiz.stream()
        							.map(i -> i * i)
        							.collect(toList());
        System.out.println(squares);
        
        // Quiz 2
        List<Integer> numbers = Arrays.asList(1, 2, 3);
        List<Integer> numberss = Arrays.asList(3, 4);
        List<int[]> pair = numbers.stream()
        							.flatMap((Integer i) -> numberss.stream()
        															.map((Integer j) -> new int[] {i, j})
        									)
        							.collect(toList());
        pair.forEach(i -> System.out.println("(" + i[0] + ", " + i[1] + ")"));

    }
    
}
