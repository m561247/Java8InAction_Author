package lambdasinaction.chap7;
import java.util.stream.*;
public class Practice {
	public static long sequentialSum(long n) {
		return Stream.iterate(1L, i -> i + 1)
					 .limit(n)
					 .parallel()
					 .reduce(0L, Long::sum);
	}
	
	public static long rangedSum(long n) {
		return LongStream.rangeClosed(1, n).reduce(0L, Long::sum);
	}
	public static void main(String[] args) {
//		System.out.println(sequentialSum(100));
		System.out.println(rangedSum(100));
	}
}
