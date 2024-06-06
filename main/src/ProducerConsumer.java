import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducerConsumer {
	private static final int QUEUE_CAPACITY = 10;
	private static final int PRODUCER_COUNT = 2;
	private static final int CONSUMER_COUNT = 2;
	private static final int PRODUCE_COUNT = 100;

	public static void main(String[] args) {
		BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(QUEUE_CAPACITY);

	}

	static class Producer implements Runnable {

		@Override
		public void run() {

		}
	}

	static class Consumer implements Runnable {

		@Override
		public void run() {

		}
	}
}
