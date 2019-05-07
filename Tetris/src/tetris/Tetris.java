/*
    Tetris Game - An adaptation to the original Tetris game that focus on clearing lines as fast as posible
    Copyright (C) 2019  Omar De Alba Garza

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/

package tetris;

import java.awt.BorderLayout;
import javax.swing.JFrame;

/**
 *
 * @author Omar
 */
public class Tetris {

    //Main Class-Here we just initialize the window 
    public static void main(String[] args) {
        
        JFrame game = new JFrame("BLITZKRIEG");
        game.getContentPane().add(new Window(), BorderLayout.CENTER);
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        game.pack();
        game.setResizable(false);
        game.setVisible(true);
    }
    
}
