package com.d2c.common.api.json.support;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class StringIndexSet implements Set<String> {

    private final static int NUMSTR_LEN = 100;
    private final static String[] NUMSTRS = new String[100];

    static {
        for (int i = 0; i < NUMSTR_LEN; ++i)
            NUMSTRS[i] = String.valueOf(i);
    }

    private final int size;

    public StringIndexSet(int size) {
        this.size = size;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<String> iterator() {
        return new Iterator<String>() {
            int index = 0;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public String next() {
                if (index < NUMSTR_LEN)
                    return NUMSTRS[index++];
                return String.valueOf(index++);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public boolean add(String e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends String> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(Object o) {
        int t = Integer.parseInt(String.valueOf(o));
        return t >= 0 && t < size;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray() {
        String[] array = new String[size()];
        for (int i = 0; i < size; ++i) {
            if (i < NUMSTR_LEN) {
                array[i] = NUMSTRS[i];
            } else {
                array[i] = String.valueOf(i);
            }
        }
        return array;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }

}
