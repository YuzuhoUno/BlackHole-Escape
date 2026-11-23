/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blackhole_escape;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author yuzuh
 */
public class BlackHoleGUI {
    private JFrame frame;
    private BoardGUI boardGUI;
    private final int INITIAL_BOARD_SIZE = 5;
    private final int INITIAL_STAGE = 1;
    
    public BlackHoleGUI(){
        frame = new JFrame("BlackHole Escape");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        boardGUI = new BoardGUI(INITIAL_BOARD_SIZE, INITIAL_STAGE, this);
        frame.getContentPane().add(boardGUI.getBoardPanel(), BorderLayout.CENTER);
        frame.getContentPane().add(boardGUI.getStatusLabel(), BorderLayout.SOUTH);

        frame.pack();
        frame.setLocationRelativeTo(null);
        
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        
        JMenu gameMenu = new JMenu("Game");
        menuBar.add(gameMenu);
        
        JMenuItem resetMenu = new JMenuItem("Reset Stage");
        gameMenu.add(resetMenu);
        resetMenu.addActionListener(e -> boardGUI.resetGame());
        
        JMenu stageMenu = new JMenu("Select Stage");
        gameMenu.add(stageMenu);
        
        int[] stages = new int []{1, 2, 3};
        for(int stage : stages){
            JMenuItem stageMenuItem = new JMenuItem("Stage: " + stage);
            stageMenu.add(stageMenuItem);
            stageMenuItem.addActionListener(e -> loadStage(stage));
        }
        gameMenu.addSeparator();
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        gameMenu.add(exitMenuItem);
        exitMenuItem.addActionListener(e -> System.exit(0));
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    public void loadStage(int stage){
        frame.getContentPane().remove(boardGUI.getBoardPanel());
        frame.getContentPane().remove(boardGUI.getStatusLabel());
                    
        int size;
        if(stage == 2){
            size = 7;
        }
        else if(stage == 3){
            size = 9;
        }
        else{
            size = INITIAL_BOARD_SIZE;
        }
        boardGUI = new BoardGUI(size, stage, this);
        frame.getContentPane().add(boardGUI.getBoardPanel(), BorderLayout.CENTER);
        frame.getContentPane().add(boardGUI.getStatusLabel(), BorderLayout.SOUTH);

        frame.pack();
        frame.revalidate();
        frame.repaint();
        frame.setLocationRelativeTo(null);
    }
}
