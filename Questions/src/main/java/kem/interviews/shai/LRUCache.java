package kem.interviews.shai;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by Evgeny Kurtser on 14-Sep-22 at 11:49 AM.
 * <a href=mailto:lopotun@gmail.com>Eugene Kurtzer</a>
 */
public class LRUCache<K, V> {
	private final BoundLRUQueue<K, V> lruQueue;
	private final Map<K, BiDirNode<K, V>> map;

	public LRUCache(int capacity) {
		if(capacity < 1) throw new IllegalArgumentException("capacity should be positive");
		lruQueue = new BoundLRUQueue<>(capacity);
		map = new HashMap<>();
	}

	public @NotNull Optional<V> put(@NotNull final K key, @NotNull final V value) {
		final BiDirNode<K, V> newEntry = new BiDirNode<>(key, value);
		final BiDirNode<K, V> oldEntry = map.put(key, newEntry);
		final Optional<BiDirNode<K, V>> evicted = lruQueue.add(newEntry);
		evicted.ifPresent(x -> map.remove(x.getKey()));
		return oldEntry==null? Optional.empty(): Optional.of(oldEntry.getValue());
	}

	public Optional<V> putIfAbsent(@NotNull final K key, @NotNull final V value) {
		map.putIfAbsent(null, null);
		if(!map.containsKey(key)) {
			return put(key, value);
		}
		return Optional.of(value);
	}

	public @NotNull Optional<V> get(@NotNull final K key) {
		final BiDirNode<K, V> valueNode = map.get(key);
		if(valueNode != null) {
			lruQueue.renew(valueNode);
			return Optional.of(valueNode.getValue());
		}
		return Optional.empty();
	}

	public @NotNull Optional<V> remove(@NotNull final K key) {
		final BiDirNode<K, V> valueNode = map.remove(key);
		if(valueNode != null) {
			lruQueue.remove(valueNode);
			return Optional.of(valueNode.getValue());
		}
		return Optional.empty();
	}

	public boolean remove(@NotNull final K key, @NotNull final V value) {
		final BiDirNode<K, V> test = map.get(key);
		if(test != null && Objects.equals(test.getValue(), value)) {
			remove(key);
			return true;
		}
		return false;
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public int size() {
		return map.size();
	}

	public void clear() {
		map.clear();
		lruQueue.clear();
	}
}

@RequiredArgsConstructor
@Data
class BiDirNode<K, V> {
	private final K key;
	private final V value;
	@ToString.Exclude private BiDirNode<K, V> prev;
	@ToString.Exclude private BiDirNode<K, V> next;
}

class BoundLRUQueue<K, V> {
	private final int capacity;
	private int size;
	private BiDirNode<K, V> oldest;
	private BiDirNode<K, V> youngest;

	public BoundLRUQueue(int capacity) {
		this.capacity = capacity;
		size = 0;
		oldest = youngest = null;
	}

	@NotNull Optional<BiDirNode<K, V>> add(@NotNull BiDirNode<K, V> biDirNode) {
		BiDirNode<K, V> evicted;
		if(oldest == null) {
			oldest = youngest = biDirNode;
			size++;
			evicted = null;
		} else {
			if(size == capacity) { // Max capacity reached. Evict the oldest entry.
				evicted = oldest;
				oldest = evicted.getNext(); // set new 'oldest'
				oldest.setPrev(null); // nothing is older than 'oldest'
			} else {
				evicted = null;
				size++;
			}

			if(size == 1) { //
				oldest = biDirNode;
			}

			final BiDirNode<K, V> curr = youngest;
			curr.setNext(biDirNode);
			youngest = biDirNode;
			youngest.setPrev(curr);
		}
		return Optional.ofNullable(evicted);
	}

	void renew(@NotNull final BiDirNode<K, V> valueNode) {
		if(valueNode == youngest) {
			return;
		}

		//remove(valueNode);
		if(valueNode == oldest) {
			oldest.getNext().setPrev(null);
			oldest = oldest.getNext();
		}

		youngest.setNext(valueNode);
		valueNode.setPrev(youngest);
		youngest = valueNode;
		youngest.setNext(null); // nothing is newer than 'newest'
	}

	void clear() {
		size = 0;
		oldest = youngest = null;
	}

	void remove(@NotNull final BiDirNode<K, V> valueNode) {
		size--;
		if(size == 0) {
			clear();
			return;
		}
		if(valueNode == youngest) {
			youngest.getPrev().setNext(null);
			youngest = youngest.getPrev();
			return;
		}
		if(valueNode == oldest) {
			oldest.getNext().setPrev(null);
			oldest = oldest.getNext();
			return;
		}
		valueNode.getPrev().setNext(valueNode.getNext());
		valueNode.getNext().setPrev(valueNode.getPrev());
	}
}
