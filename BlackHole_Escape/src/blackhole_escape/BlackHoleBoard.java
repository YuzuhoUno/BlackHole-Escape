/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blackhole_escape;

import java.awt.Point;

/**
 *
 * @author yuzuh
 */
public class BlackHoleBoard {
    private final int boardSize;
    private final Field[][] grid;
    private final int stage;
    
    private int moveCount;
    private int remainingShips;
    private boolean isGameOver;
    private boolean isWin;
    
    private Point selectedShip;
    
    public BlackHoleBoard(int boardSize, int stage){
        this.boardSize = boardSize;
        this.grid = new Field[boardSize][boardSize];
        this.stage = stage;
        
        //Create grids
        for(int i = 0; i < boardSize; i++){
            for(int j = 0; j < boardSize; j++){
                grid[i][j] = new Field();
            }
        }
        initGame();
    }
    
    public void initGame(){
        this.moveCount = 0;
        this.remainingShips = 0;
        this.isGameOver = false;
        this.isWin = false;
        this.selectedShip = null;
        
        //Reset all cell
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                grid[i][j].setType(Field.Type.EMPTY);
            }
        }
        
        //Put EARTH in the middle
        int center = boardSize / 2;
        grid[center][center].setType(Field.Type.EARTH);
        
        if(stage == 1){
            //Set ships
            grid[0][0].setType(Field.Type.SHIP);
            grid[4][4].setType(Field.Type.SHIP);
            remainingShips = 2;
            
            //Set BlackHoles and walls
            grid[3][1].setType(Field.Type.BLACK_HOLE);
            grid[0][1].setType(Field.Type.WALL);
            grid[2][3].setType(Field.Type.WALL);
        } else if(stage == 2){
            //Set ships
            grid[0][0].setType(Field.Type.SHIP);
            grid[6][6].setType(Field.Type.SHIP);
            grid[0][6].setType(Field.Type.SHIP);
            remainingShips = 3;
            
            //Set BlackHoles and walls
            grid[5][2].setType(Field.Type.BLACK_HOLE);
            grid[2][4].setType(Field.Type.BLACK_HOLE);
            grid[1][0].setType(Field.Type.WALL);
            grid[3][1].setType(Field.Type.WALL);
            grid[6][4].setType(Field.Type.WALL);
            grid[2][6].setType(Field.Type.WALL);
        } else{
            //Set ships
            grid[0][0].setType(Field.Type.SHIP);
            grid[8][0].setType(Field.Type.SHIP);
            grid[0][8].setType(Field.Type.SHIP);
            grid[8][8].setType(Field.Type.SHIP);
            remainingShips = 4;
            
            //Set BlackHoles and walls
            grid[4][0].setType(Field.Type.BLACK_HOLE);
            grid[1][3].setType(Field.Type.BLACK_HOLE);
            grid[5][4].setType(Field.Type.BLACK_HOLE);
            grid[2][7].setType(Field.Type.BLACK_HOLE);
            grid[7][1].setType(Field.Type.WALL);
            grid[3][3].setType(Field.Type.WALL);
            grid[8][4].setType(Field.Type.WALL);
            grid[0][5].setType(Field.Type.WALL);
            grid[3][6].setType(Field.Type.WALL);
            grid[6][8].setType(Field.Type.WALL);
        }
    }
    
    public boolean handleTileClick(int r, int c){
        if(isGameOver){
            return false;
        }
        Point clicked = new Point(r, c);
        Field clickedField = grid[r][c];
        
        if(clickedField.getType() == Field.Type.SHIP){
            selectedShip = clicked;
            return true;
        }
        
        //Attempt to move selected ship
        if(selectedShip != null && clickedField.getType() != Field.Type.WALL){
            Point destination = calculateToDestination(selectedShip, clicked);
            if(destination != null){
                executeMove(selectedShip, destination);
                selectedShip = null;
                moveCount++;
                return true;
            }
        }
        return false;
    }
    
    private Point calculateToDestination(Point from, Point target){
        int rowDiff = target.x - from.x;
        int colDiff = target.y - from.y;
        
        //If player clicked the same cell
        if(rowDiff == 0 && colDiff == 0){
            return null;
        }
        
        //-1 == upper-left, 0 == nothing, 1 == lower-right
        int dr = Integer.compare(rowDiff, 0);
        int dc = Integer.compare(colDiff, 0);
        
        if(dr != 0 && dc != 0){
            return null;
        }
        
        int currentR = from.x + dr;
        int currentC = from.y + dc;
        Point lastValid = null;
        
        while(currentR >= 0 && currentC >= 0 && currentR < boardSize && currentC < boardSize){
            Field currentField = grid[currentR][currentC];
            
            if(currentField.getType() == Field.Type.EARTH || currentField.getType() == Field.Type.BLACK_HOLE){
                return new Point(currentR, currentC);
            }
            //If the ship reaches to other ship or wall
            else if(!currentField.isEmpty()){
                break;
            }
                 
            //Note cells which is no possible to move
            lastValid = new Point(currentR, currentC);
            currentR += dr;
            currentC += dc;
        }
        return lastValid;
    }
    
    private void executeMove(Point from, Point to){
        grid[from.x][from.y].setType(Field.Type.EMPTY);
        Field.Type targetType = grid[to.x][to.y].getType();
        
        if(null == targetType){
            grid[to.x][to.y].setType(Field.Type.SHIP);
        }
        else switch (targetType) {
            case BLACK_HOLE:
                isGameOver = true;
                isWin = false;
                break;
            case EARTH:
                remainingShips--;
                if(remainingShips == 0){
                    isGameOver = true;
                    isWin = true;
                }   break;
            default:
                grid[to.x][to.y].setType(Field.Type.SHIP);
                break;
        }   
    }
    
    public boolean isOver(){
        return isGameOver;
    }
    
    public String getMessage(){
        if(isWin){
            return "You Won!! Move count: " + moveCount;
        }
        else if(isGameOver){
            return "You Lost!! Your ship fell into the blackhole";
        }
        return "";
    }
    
    public int getBoardSize(){
        return boardSize;
    }
    
    public int getMoveCount(){
        return moveCount;
    }
    
    public Field getField(int r, int c){
        return grid[r][c];
    }
    
    public Point getSelectedShip(){
        return selectedShip;
    }
    
    public int getStage(){
        return stage;
    }
    
    public boolean isWin(){
        return isWin;
    }
}
