import java.util.*;

public final class PartitionExtended<T> extends AbstractList<List<T>> {
	private final List<T> list;
	private final int chunkSize;
	private final int numberOfChunks;
	public enum Mode{MAX_CHUNK_SIZE, MAX_NUM_OF_CHUNKS}

	public PartitionExtended(List<T> list, int size, Mode mode) throws IllegalArgumentException, NullPointerException {
		if(size <= 0) {
			throw new IllegalArgumentException("size should be positive");
		}
		Objects.requireNonNull(mode, () -> Mode.class.getSimpleName() + " cannot be null");

		this.list = new ArrayList<>(list);
		if(mode == Mode.MAX_CHUNK_SIZE) {
			this.chunkSize = size;
			this.numberOfChunks = (int) Math.ceil((double) list.size() / (double) size);
		} else {
			this.chunkSize = (int) Math.ceil((double) list.size() / (double) size);
			this.numberOfChunks = (int) Math.ceil((double) list.size() / (double) chunkSize);
		}
	}

	public static <T> PartitionExtended<T> ofSize(List<T> list, int chunkSize, Mode mode) {
		return new PartitionExtended<>(list, chunkSize, mode);
	}

	@Override
	public List<T> get(int index) {
		int start = index * chunkSize;
		int end = Math.min(start + chunkSize, list.size());

		if (start > end) {
			throw new IndexOutOfBoundsException("Index " + index + " is out of the list range <0," + (size() - 1) + ">");
		}

		return new ArrayList<>(list.subList(start, end));
	}

	@Override
	public int size() {
		return numberOfChunks;
	}

	public static void main(String[] args) {
		final List<Integer> numbers = Arrays.asList(1,2,3,4,5,6,7);

		System.out.println(PartitionExtended.ofSize(new ArrayList<>(), 3, Mode.MAX_CHUNK_SIZE));
		//System.out.println(PartitionExtended.ofSize(null, 3, Mode.CHUNK_SIZE));

//		System.out.println(PartitionExtended.ofSize(numbers, 3, null));
		System.out.println(PartitionExtended.ofSize(numbers, 300, Mode.MAX_CHUNK_SIZE));
		System.out.println(PartitionExtended.ofSize(numbers, 3, Mode.MAX_CHUNK_SIZE));
		System.out.println(PartitionExtended.ofSize(numbers, 1, Mode.MAX_CHUNK_SIZE));

		System.out.println(PartitionExtended.ofSize(numbers, 2, Mode.MAX_NUM_OF_CHUNKS));
		System.out.println(PartitionExtended.ofSize(numbers, 5, Mode.MAX_NUM_OF_CHUNKS));
		System.out.println(PartitionExtended.ofSize(numbers, 500, Mode.MAX_NUM_OF_CHUNKS));
	}
}