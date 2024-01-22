/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package mayintarlasi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;


/**
 *
 * @author Muhammed
 */
public class MineSweeperGame extends JFrame{
    private final int ROWS = 10;
    private final int COLS = 10;
    private final int NUM_MINES = 15;

    private JButton[][] buttons;
    private boolean[][] mines;
    private int[][] neighbors;

    public MineSweeperGame() {
        setTitle("Mayın Tarlası Oyunu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(ROWS, COLS));

        buttons = new JButton[ROWS][COLS];
        mines = new boolean[ROWS][COLS];
        neighbors = new int[ROWS][COLS];

        initializeGame();
        updateNeighbors();

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                final int row = i;
                final int col = j;
                buttons[i][j] = new JButton();
                buttons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        handleButtonClicked(row, col);
                    }
                });
                add(buttons[i][j]);
            }
        }

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeGame() {
        Random random = new Random();
        int count = 0;

        while (count < NUM_MINES) {
            int row = random.nextInt(ROWS);
            int col = random.nextInt(COLS);
            if (!mines[row][col]) {
                mines[row][col] = true;
                count++;
            }
        }
    }

    private void updateNeighbors() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                neighbors[i][j] = countNeighborMines(i, j);
            }
        }
    }

    private int countNeighborMines(int row, int col) {
        int count = 0;
        for (int i = Math.max(0, row - 1); i <= Math.min(ROWS - 1, row + 1); i++) {
            for (int j = Math.max(0, col - 1); j <= Math.min(COLS - 1, col + 1); j++) {
                if (mines[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }

    private void handleButtonClicked(int row, int col) {
        if (mines[row][col]) {
            buttons[row][col].setText("X");
            revealAllMines();
            JOptionPane.showMessageDialog(this, "Mayına bastınız! Oyun bitti.");
            System.exit(0);
        } else {
            int neighborMines = neighbors[row][col];
            buttons[row][col].setText(Integer.toString(neighborMines));
            buttons[row][col].setEnabled(false);
            if (neighborMines == 0) {
                revealEmptyNeighbors(row, col);
            }
        }
    }

    private void revealAllMines() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (mines[i][j]) {
                    buttons[i][j].setText("X");
                }
                buttons[i][j].setEnabled(false);
            }
        }
    }

    private void revealEmptyNeighbors(int row, int col) {
        for (int i = Math.max(0, row - 1); i <= Math.min(ROWS - 1, row + 1); i++) {
            for (int j = Math.max(0, col - 1); j <= Math.min(COLS - 1, col + 1); j++) {
                if (neighbors[i][j] == 0 && buttons[i][j].isEnabled()) {
                    buttons[i][j].setText("");
                    buttons[i][j].setEnabled(false);
                    revealEmptyNeighbors(i, j);
                } else if (buttons[i][j].isEnabled()) {
                    buttons[i][j].setText(Integer.toString(neighbors[i][j]));
                    buttons[i][j].setEnabled(false);
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MineSweeperGame();
            }
        });
    }

   
    
}
