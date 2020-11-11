package lambdasinaction.chap8;

import static org.junit.Assert.assertEquals;
import java.util.stream.*;
import java.util.*;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import org.junit.Test;

public class Debugging {
	public static void main(String[] args) throws Exception {
		List<Point> points = Arrays.asList(new Point(12, 2), null);
		points.stream().map(p -> p.getX()).forEach(System.out::println);
//		testMoveRightBy();
	}

//	@Test
//	public void testMoveRightBy() throws Exception {
//		Point p1 = new Point(5, 5);
//		Point p2 = p1.moveRightBy(10);
//		assertEquals(15, p2.getX());
//		assertEquals(5, p2.getY());
//	}

	@Test
	public void testComparingTwoPoints() throws Exception {
		Point p1 = new Point(10, 15);
		Point p2 = new Point(10, 20);
		int result = Point.compareByXAndThenY.compare(p1, p2);
		assertEquals(-1, result);
	}

	@Test
	public void testMoveAllPointsRightBy() throws Exception {
		List<Point> points = Arrays.asList(new Point(5, 5), new Point(10, 5));
		List<Point> expectedPoints = Arrays.asList(new Point(15, 5), new Point(20, 5));
		List<Point> newPoints = Point.moveAllPointsRightBy(points, 10);
		assertEquals(expectedPoints, newPoints);
	}

//	@Test
//	public void testFilter() throws Exception {
//		List<Integer> numbers = Arrays.asList(1, 2, 3, 4);
//		List<Integer> even = filter(numbers, i -> i % 2 == 0);
//		List<Integer> smallerThanThree = filter(numbers, i -> i < 3);
//		assertEquals(Arrays.asList(2, 4), even);
//		assertEquals(Arrays.asList(1, 2), smallerThanThree);
//	}

	private static class Point {
		private int x;
		private int y;

		private Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public Point moveRightBy(int x) {
			return new Point(this.x + x, this.y);
		}

		public final static Comparator<Point> compareByXAndThenY = comparing(Point::getX).thenComparing(Point::getY);

		public static List<Point> moveAllPointsRightBy(List<Point> points, int x) {
			return points.stream().map(p -> new Point(p.getX() + x, p.getY())).collect(toList());
		}
	}
}
