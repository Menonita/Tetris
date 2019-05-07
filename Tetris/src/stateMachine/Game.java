/*
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

package stateMachine;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.Timer;
import tetris.PlayerBoard;

/**
 *
 * @author Omar
 */
public class Game implements GameState, ActionListener{
    
    //Aqui es donde se van a implementar las partes del juego
    //Here is where is going to be implemented the game
    GameContext context;
    
    private PlayerBoard player1, player2;
    private Timer updateSpd;
    
    public Game(GameContext c, int w, int h){
        context=c;
        
        player1=new PlayerBoard(w/2, h, 0, 1); //width, height, position, player controls
        player2=new PlayerBoard(w/2, h, w/2, 2); 
        
        updateSpd=new Timer(500,this); //check timer for the game
        updateSpd.start();
    }
    
    @Override
    public void draw(Graphics g) {
        player1.paint(g);
        player2.paint(g);
    }

    @Override
    public void transit(int x) {
        if(x==0){
            context.setState(context.getMenuState());
        }
    }

    @Override
    public void processKey(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_BACK_SPACE) {
            transit(0);
        }
        else{
            player1.move(e);
            player2.move(e);
        }
    }
    
    @Override
    public void releasedKey(KeyEvent e) {
        player1.release(e);
        player2.release(e);
    }
    
    @Override
    public void processClick(MouseEvent me) {}

    @Override
    //main part of the versus area. Checks the amount of cleared lines of one player and increases the drop speed. Also checks if one player loses and decleares the winner
    public void actionPerformed(ActionEvent ae) { 
        if(ae.getSource() == updateSpd){
            if(player1.getLinesCleared()>0){
                player2.setDropTimer(player1.getLinesCleared()*10);
                player1.setLinesCleard();
            }
            if(player2.getLinesCleared()>0){
                player1.setDropTimer(player2.getLinesCleared()*10);
                player2.setLinesCleard();
            }
            if(!player1.playerIsAlive()){
                player2.declareWinner();
            }
            if(!player2.playerIsAlive()){
                player1.declareWinner();
            }
        }
    }
    
}
