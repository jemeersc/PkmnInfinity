/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Random;

/**
 *
 * @author Jens
 */
public enum Direction {

    /**
     *  Northern direction is -1 to y 
     */
    NORTH(0,-1),

    /**
     * Eastern direction is +1 to x
     */
    EAST(1,0),

    /**
     * Southern direction is +1 to y
     */
    SOUTH(0,1),

    /**
     * Western direction is -1 to x
     */
    WEST(-1,0);

    private final int xpos;
    private final int ypos;
    private static Random randgen = new Random();

    
    private Direction(int xpos, int ypos) {
        this.xpos = xpos;
        this.ypos = ypos;
    }

    /**
     * Return the x-coordinate of the direction
     * @return x-coordinate
     */
    public int getXpos() {
        return xpos;
    }

    /**
     * Return the y-coordinate of the direction
     * @return y-coordinate
     */
    public int getYpos() {
        return ypos;
    }
    
    /**
     * Returns the direction that is opposite to the given Direction.
     * 
     * For example:
     * If the given Direction is NORTH,
     * this function returns SOUTH.
     * @param d the Direction to which the opposite has to be given
     * @return opposite Direction
     */
    public static Direction getOpposite(Direction d){
        if(d==NORTH){
            return SOUTH;
        }else if(d==EAST){
            return WEST;
        }else if(d==SOUTH){
            return NORTH;
        }else{
            return EAST;
        }
    }

    /**
     * Returns a random Direction, based on a random generator.
     * 
     * @return random Direction
     */
    public static Direction getRandomDirection(){
        int rand = randgen.nextInt(4);
        if(rand==0){
            return SOUTH;
        }else if(rand==1){
            return WEST;
        }else if(rand==2){
            return NORTH;
        }else{
            return EAST;
        }
    }



}
