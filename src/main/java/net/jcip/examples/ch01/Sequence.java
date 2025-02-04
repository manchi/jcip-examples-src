package net.jcip.examples.ch01;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 Sequence

 @author Brian Goetz and Tim Peierls */

@ThreadSafe
public class Sequence {

    @GuardedBy("this")
    private int nextValue;

    public synchronized int getNext() {
        return nextValue++;
    }
}