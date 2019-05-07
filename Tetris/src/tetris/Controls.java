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

import java.awt.event.KeyEvent;

/**
 *
 * @author Omar
 */
public class Controls {
    
    int left,right,up,down, hDrop;
    
    public Controls(int i){ //Controls for the player, the variable 'i' is to distinguish between player 1 and 2 controlers
        if(i==1){
            left=KeyEvent.VK_A;
            right=KeyEvent.VK_D;
            up=KeyEvent.VK_W;
            down=KeyEvent.VK_S;
            hDrop=KeyEvent.VK_SPACE;
        }else{
            left=KeyEvent.VK_LEFT;
            right=KeyEvent.VK_RIGHT;
            up=KeyEvent.VK_UP;
            down=KeyEvent.VK_DOWN;
            hDrop=KeyEvent.VK_SHIFT;
        }
    }

    //Getters & Setters
    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getUp() {
        return up;
    }

    public void setUp(int up) {
        this.up = up;
    }

    public int getDown() {
        return down;
    }

    public void setDown(int down) {
        this.down = down;
    }

    public int gethDrop() {
        return hDrop;
    }

    public void sethDrop(int hDrop) {
        this.hDrop = hDrop;
    }
    
}
