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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class GameContext {

    private int width, height; // window width & height
    
    GameState menu;
    GameState instrucciones;
    GameState juego;

    GameState currentState;

    public GameContext(int w, int h) {
        width=w;
        height=h;
        
        menu = (GameState) new Menu(this, width, height);
        instrucciones = (GameState) new Instructions(this);
        juego = (GameState) new Game(this, width, height);
        
        currentState = menu;
    }

    public void processKey(KeyEvent e) {
        currentState.processKey(e);
    }
    
    public void releasedKey(KeyEvent e) {
        currentState.releasedKey(e);
    }
    public void processClick(MouseEvent e){
        currentState.processClick(e);
    }

    public GameState getMenuState() {
        return this.menu;

    }

    public GameState getInstructionsState() {
        return this.instrucciones;
    }

    public GameState getGameState() {
        return this.juego;

    }

    public void setState(GameState s) {
        this.currentState = s;
    }

    public void draw(Graphics g) {
        currentState.draw(g);
    }
	
}
