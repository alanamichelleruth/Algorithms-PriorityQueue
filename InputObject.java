//Class to represent the "name" and "pri" of the JsonArray inList
package csi403;

public class InputObject{
    private String name;
    private int num;

    public InputObject(String name, int num){
        this.name = name;
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public String getName(){
        return name;
    }
}