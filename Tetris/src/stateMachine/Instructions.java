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
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 *
 * @author Omar
 */
public class Instructions implements GameState{

    GameContext context;
    
    public Instructions(GameContext c){
        context=c;
    }
    
    @Override
    public void draw(Graphics g) {//every instruction printed on the window
        g.setColor(Color.BLACK);
        g.setFont(new Font("Times New Roman", Font.ITALIC, 60));
        g.drawString("BLITZKRIEG - LIGHTNING WAR", 350, 200);
        g.setFont(new Font("Times New Roman", Font.ITALIC, 40));
        g.drawString("Instructions:", 350, 300);
        g.drawString("-Clear lines as fast as you can", 350, 400);
        g.drawString("-Every line cleared will make the oponent's pieces fall faster", 350, 450);
        g.drawString("-Survive as long as you can", 350, 500);
        g.drawString("Controls: ", 700, 600);
        g.drawString("Player 1:", 400, 650);
        g.drawString("Player 2:", 950, 650);
        g.drawString("Rotate: W", 350,700);
        g.drawString("Left: A",350,750);
        g.drawString("Right: D",350,800);
        g.drawString("Soft Drop: S",350,850);
        g.drawString("Hard Drop: SpaceBar",350,900);
        g.drawString("Rotate: Up key", 900,700);
        g.drawString("Left: Left key",900,750);
        g.drawString("Right: Right key",900,800);
        g.drawString("Soft Drop: Down key",900,850);
        g.drawString("Hard Drop: SHIFT",900,900);
        
        
        
        g.setFont(new Font("Times New Roman", Font.ITALIC, 50));
        g.drawString("Return to Main Manu", 100, 1000);
    }

    @Override
    public void transit(int x) {
        if (x == 0) {//Menu
            context.setState(context.getMenuState());
        }
    }

    @Override
    public void processKey(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_ENTER) {
            transit(0);
        }
    }
    
    @Override
    public void releasedKey(KeyEvent e) {}
    
    @Override
    public void processClick(MouseEvent me) {
        if(me.getLocationOnScreen().getX()>100 && me.getLocationOnScreen().getX()<550
                && me.getLocationOnScreen().getY()<1050 && me.getLocationOnScreen().getY()>1000){
            transit(0);
        }
    }
    
}
