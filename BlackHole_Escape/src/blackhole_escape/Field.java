/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blackhole_escape;

/**
 *
 * @author yuzuh
 */
public class Field {
    public enum Type{ EMPTY, BLACK_HOLE, SHIP, WALL, EARTH }
    
    private Type type;
    
    public Field(){
        this.type = type.EMPTY;
    }
    
    public Type getType(){
        return type;
    }
    
    public void setType(Type type){
        this.type = type;
    }
    
    public boolean isEmpty(){
        return type == Type.EMPTY;
    }
    
    public boolean isEarth(){
        return type == Type.EARTH;
    }
    
    public boolean isWall(){
        return type == Type.WALL;
    }
    
    public boolean isBlack_Hole(){
        return type == Type.BLACK_HOLE;
    }
}
