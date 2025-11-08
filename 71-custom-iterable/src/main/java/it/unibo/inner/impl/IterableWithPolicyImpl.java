package it.unibo.inner.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

import it.unibo.inner.api.IterableWithPolicy;
import it.unibo.inner.api.Predicate;

public class IterableWithPolicyImpl<T> implements IterableWithPolicy<T> {

    private ArrayList<T> list = new ArrayList<>();
    private Predicate<T> filter;

    public IterableWithPolicyImpl(final ArrayList<T> list) {
        this(list, 
            new Predicate<T>() {
                @Override
                public boolean test(T elem) {
                    return true;
                }            
            }
        );
    }

    public IterableWithPolicyImpl(final ArrayList<T> elements, Predicate<T> filter) {
        this.list = elements;
        this.setIterationPolicy(filter);
    }

    @Override
    public Iterator<T> iterator() {
        return new IteratorInnerClass();
    }

    @Override
    public void setIterationPolicy(Predicate<T> filter) {
        this.filter = filter;
    }


    private class IteratorInnerClass implements Iterator<T> {

        private int index;

        @Override
        public boolean hasNext() {
            /*scorro solo gli elementi dentro alla lista che soddisfano il filtro*/
            while(index < list.size() && !filter.test(list.get(index))) {
                index++;
            }
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
