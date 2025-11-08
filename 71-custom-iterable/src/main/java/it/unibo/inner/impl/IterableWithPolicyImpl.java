package it.unibo.inner.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

import it.unibo.inner.api.IterableWithPolicy;
import it.unibo.inner.api.Predicate;

public class IterableWithPolicyImpl<T> implements IterableWithPolicy<T> {

    private ArrayList<T> list = new ArrayList<>();

    
    public IterableWithPolicyImpl(final ArrayList<T> list) {
        this.list = list;
    }

    @Override
    public Iterator<T> iterator() {
        return new IteratorInnerClass();
    }

    @Override
    public void setIterationPolicy(Predicate<T> filter) {
        /*Empty*/
    }


    private class IteratorInnerClass implements Iterator<T> {

        private int index;

        @Override
        public boolean hasNext() {
            return index < list.size();
        }

        @Override
        public T next() {
            
            if(!hasNext()) {
                throw new NoSuchElementException();
            }
            
            return list.get(index++);
        }
    }
}
