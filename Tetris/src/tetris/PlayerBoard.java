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

package tetris;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

/**
 *
 * @author Omar
 */
public class PlayerBoard {
    
    private int height, width, position;
    private GameBoard board;
    
    //Constructor
    public PlayerBoard(int w, int h, int p, int c){
        width=w;
        height=h;
        position=p;
        board=new GameBoard(width/2, width, width/4, height/25, position, c); //Width,Height,posX,posY...height of the tetris board is double the width
    }
    
    //Getters & Setters
    public int getWidth(){
        return width;
    }
    public void setWidth(int x){
        width=x;
    }
    
    public int getHeight(){
        return height;
    }
    public void setHeight(int y){
        height=y;
    }
    public int getPosition(){
        return position;
    }
    public void setPosition(int p){
        position=p;
    }
    public boolean playerIsAlive(){
        return board.isAlive();
    }
    public void declareWinner(){
        board.winner();
    }
    public int getLinesCleared(){
        return board.getCount();
    }
    public void setLinesCleard(){
        board.setCount();
    }
    public void setDropTimer(int s){
        board.setSpd(s);
    }
    
    //Functions & Methods
    public void move(KeyEvent e){
        board.movePiece(e);
    }
    public void release(KeyEvent e){
        board.releaseKey(e);
    }
    public void paint(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(position, 0, width, height);
        board.paint(g);
    }
}
