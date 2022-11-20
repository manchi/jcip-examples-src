package net.jcip.examples;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.jcip.annotations.NotThreadSafe;
import net.jcip.annotations.ThreadSafe;

/**
 ListHelder
 <p/>
 Examples of thread-safe and non-thread-safe implementations of
 put-if-absent helper methods for List

 @author Brian Goetz and Tim Peierls */

@NotThreadSafe
class BadListHelper<E> {

    public List<E> list = Collections.synchronizedList(new ArrayList<E>());

    /**
     The problem is that it synchronizes on the wrong lock. Whatever lock the List uses
     to guard its state, it sure isn’t the lock on the ListHelper. ListHelper provides
     only the illusion of synchronization; the various list operations, while all synchronized,
     use different locks, which means that putIfAbsent is not atomic relative to
     other operations on the List. So there is no guarantee that another thread won’t
     modify the list while putIfAbsent is executing.
     */
    public synchronized boolean putIfAbsent(E x) {
        boolean absent = !list.contains(x);
        if (absent) {
            list.add(x);
        }
        return absent;
    }
}

@ThreadSafe
class GoodListHelper<E> {

    public List<E> list = Collections.synchronizedList(new ArrayList<E>());

    public boolean putIfAbsent(E x) {
        synchronized (list) {
            boolean absent = !list.contains(x);
            if (absent) {
                list.add(x);
            }
            return absent;
        }
    }
}