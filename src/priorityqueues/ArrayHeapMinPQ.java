package priorityqueues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @see ExtrinsicMinPQ
 */
public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    // IMPORTANT: Do not rename these fields or change their visibility.
    // We access these during grading to test your code.
    static final int START_INDEX = 1;
    List<PriorityNode<T>> items;
    int numElement;
    Map<T, Integer> itemsAndIndex;

    public ArrayHeapMinPQ() {
        items = new ArrayList<>();
        for (int i = 0; i < START_INDEX; i++) {
            items.add(null);

        }
        numElement = 0;
        itemsAndIndex = new HashMap<>();
    }

    // Here's a method stub that may be useful. Feel free to change or remove it, if you wish.
    // You'll probably want to add more helper methods like this one to make your code easier to read.
    /**
     * A helper method for swapping the items at two indices of the array heap.
     */
    private void swap(int a, int b) {
        PriorityNode<T> temp = items.get(a);
        items.set(a, items.get(b));
        items.set(b, temp);
        itemsAndIndex.put(items.get(a).getItem(), a);
        itemsAndIndex.put(items.get(b).getItem(), b);
    }

    @Override
    public void add(T item, double priority) {
        if (itemsAndIndex.containsKey(item)) {
            throw new IllegalArgumentException();
        }
        this.items.add(new PriorityNode<>(item, priority));
        numElement++;
        int currentIndex = numElement;
        while ((currentIndex / 2) >= START_INDEX && priority < items.get(currentIndex / 2).getPriority()) {
            swap(currentIndex, currentIndex / 2);
            currentIndex = currentIndex / 2;
        }
        itemsAndIndex.put(item, currentIndex);
    }

    @Override
    public boolean contains(T item) {
        return itemsAndIndex.containsKey(item);
    }

    @Override
    public T peekMin() {
        if (size() == 0) {
            throw new NoSuchElementException("PQ is empty");
        }
        return items.get(START_INDEX).getItem();
    }

    @Override
    public T removeMin() {
        if (size() == 0) {
            throw new NoSuchElementException("PQ is empty");
        }
        T result = items.get(START_INDEX).getItem();
        itemsAndIndex.remove(result);
        items.set(START_INDEX, items.get(numElement));
        items.remove(numElement);
        numElement--;
        int currentIndex = START_INDEX;
        while ((currentIndex * 2) <= numElement &&
            items.get(currentIndex).getPriority() > items.get(currentIndex * 2).getPriority() ||

            (currentIndex * 2 + 1) <= numElement &&
                items.get(currentIndex).getPriority() > items.get(currentIndex * 2 + 1).getPriority()) {
            if ((currentIndex * 2 + 1) <= numElement) {
                if (items.get(currentIndex * 2).getPriority() < items.get(currentIndex * 2 + 1).getPriority()) {
                    swap(currentIndex, currentIndex * 2);
                    currentIndex = currentIndex * 2;
                } else {
                    swap(currentIndex, currentIndex * 2 + 1);
                    currentIndex = currentIndex * 2 + 1;
                }
            } else {
                swap(currentIndex, currentIndex * 2);
                currentIndex = currentIndex * 2;
            }
        }

        return result;
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!itemsAndIndex.containsKey(item)) {
            throw new NoSuchElementException("PQ does not contain " + item);
        }
        int currentIndex = itemsAndIndex.get(item);
        items.set(currentIndex, new PriorityNode<>(item, priority));


        while ((currentIndex / 2) >= START_INDEX &&
            items.get(currentIndex).getPriority() < items.get(currentIndex / 2).getPriority()) {
            swap(currentIndex, currentIndex / 2);
            currentIndex = currentIndex / 2;
        }

        while ((currentIndex * 2) <= numElement &&
            items.get(currentIndex).getPriority() > items.get(currentIndex * 2).getPriority() ||
            (currentIndex * 2 + 1) <= numElement &&
                items.get(currentIndex).getPriority() > items.get(currentIndex * 2 + 1).getPriority()) {
            if ((currentIndex * 2 + 1) <= numElement) {
                if (items.get(currentIndex * 2).getPriority() < items.get(currentIndex * 2 + 1).getPriority()) {
                    swap(currentIndex, currentIndex * 2);
                    currentIndex = currentIndex * 2;
                } else {
                    swap(currentIndex, currentIndex * 2 + 1);
                    currentIndex = currentIndex * 2 + 1;
                }
            } else {
                swap(currentIndex, currentIndex * 2);
                currentIndex = currentIndex * 2;
            }
        }

        // items.set(currentIndex, new PriorityNode<>(item, priority));
        itemsAndIndex.replace(item, currentIndex);
    }

    @Override
    public int size() {
        return numElement;
    }
}
