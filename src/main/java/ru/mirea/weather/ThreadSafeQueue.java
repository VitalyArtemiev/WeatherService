package ru.mirea.weather;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class ThreadSafeQueue<T> {
    public ThreadSafeQueue() {
        q = new LinkedList<>();
    }

    public void add(T element) {
        synchronized (q) {
            q.add(element);
        }

    }

    public T remove() throws NoSuchElementException {
        T result;
        synchronized (q) {
            result = q.remove();
        }

        return result;
    }

    public boolean isEmpty() {
        synchronized (q) {
            return q.isEmpty();
        }
    }

    public int size() {
        synchronized (q) {
            return q.size();
        }
    }

    public void clear() {
        synchronized (q){
            q.clear();
        }
    }

    private LinkedList<T> q;
}
