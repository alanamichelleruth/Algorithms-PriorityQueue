//Class to represent the comparator for the PriorityQueue pq
package csi403;

import java.util.*;

public class InputObjectPriority implements Comparator<Object>{
    public int compare(Object o1, Object o2){
        InputObject uo1 = (InputObject)o1;
        InputObject uo2 = (InputObject)o2;

        return Integer.compare(uo1.getNum(), uo2.getNum());
    }
}