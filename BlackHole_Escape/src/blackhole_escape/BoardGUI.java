/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blackhole_escape;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author yuzuh
 */
public class BoardGUI {
    private JButton[][] buttons;
    private BlackHoleBoard board;
    private JPanel boardPanel;
    private JLabel statusLabel;
    private BlackHoleGUI mainGUI;
    
    public BoardGUI(int boardSize, int stage, BlackHoleGUI mainGUI){
        this.mainGUI = mainGUI;
        board = new BlackHoleBoard(boardSize, stage);
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(board.getBoardSize(), board.getBoardSize()));
        buttons = new JButton[board.getBoardSize()][board.getBoardSize()];
        
        for(int i = 0; i < board.getBoardSize(); i++){
            for(int j = 0; j < board.getBoardSize(); j++){
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(65, 65));
                button.setFont(new Font("Arial", Font.BOLD, 14));
                button.addActionListener(new ButtonListener(i, j));
                buttons[i][j] = button;
                boardPanel.add(button);
            }
        }
        statusLabel = new JLabel(" ", JLabel.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 13));
        refresh();
    }
    
    public void refresh(){
        int size = board.getBoardSize();
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                Field field = board.getField(i, j);
                JButton button = buttons[i][j];
                
                switch(field.getType()){
                    case BLACK_HOLE:
                        button.setText("BH");
                        button.setBackground(Color.BLACK);
                        button.setForeground(Color.WHITE);
                        break;
                    case SHIP:
                        button.setText("Ship");
                        button.setBackground(Color.YELLOW);
                        button.setForeground(Color.BLACK);
                        break;
                    case WALL:
                        button.setText("Wall");
                        button.setBackground(Color.GRAY);
                        button.setForeground(Color.BLACK);
                        break;
                    case EARTH:
                        button.setText("Earth");
                        button.setBackground(Color.GREEN);
                        button.setForeground(Color.WHITE);
                        break;
                    case EMPTY:
                    default:
                        button.setText("");
                        button.setBackground(Color.LIGHT_GRAY);
                        break;
                }
            }
        }
        //Highlight selected ship
        Point selected = board.getSelectedShip();
        if(selected != null){
            buttons[selected.x][selected.y].setBackground(Color.PINK);
        }
        
        statusLabel.setText(String.format("Stage: %d | Move Count: %d", board.getStage(), board.getMoveCount()));
    }
    
    public void resetGame(){
        board.initGame();
        refresh();
    }
    
    private void checkGameOver(){
        if(board.isOver()){
            JOptionPane.showMessageDialog(boardPanel, board.getMessage(), "Result: ", JOptionPane.INFORMATION_MESSAGE);
            
            if(board.isWin()){
                int nextStage = board.getStage() + 1;
                if(nextStage > 3){
                    JOptionPane.showMessageDialog(boardPanel, "All Stages Clear!!", "Game Clear", JOptionPane.INFORMATION_MESSAGE);
                    nextStage = 1;
                }
                mainGUI.loadStage(nextStage);
            } else {
                board.initGame();
                refresh();
            }
        }
    }
    
    public JPanel getBoardPanel(){
        return boardPanel;
    }
    
    public JLabel getStatusLabel(){
        return statusLabel;
    }
    
    private class ButtonListener implements ActionListener{
        private final int x, y;
        
        public ButtonListener(int x, int y){
            this.x = x;
            this.y = y;
        }
        
        @Override
        public void actionPerformed(ActionEvent e){
            if(board.handleTileClick(x, y)){
                refresh();
                checkGameOver();
            }
        }
    }
}
