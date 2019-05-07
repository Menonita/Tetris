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
import javax.swing.JFrame;

/**
 *
 * @author Omar
 */
public class Menu extends JFrame implements GameState{

    GameContext context;
    
    private int width, height;
    
    public Menu(GameContext c, int w, int h){
        super();
        context=c;
        width=w;
        height=h;
    }
    
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Times New Roman", Font.ITALIC, 100));
        g.drawString("BLITZKRIEG TETRIS", width/4, 300);

        g.setFont(new Font("Times New Roman", Font.ITALIC, 50));
        g.drawString("Play", 4*width/10, 500);
        g.drawString("Instructions", 4*width/10, 600);
        g.drawString("Exit", 4*width/10, 700);
    }

    @Override
    public void transit(int x) {
        if (x == 0) {//Instructions
            context.setState(context.getInstructionsState());
        }
        if (x == 1) {//Game
            context.setState(context.getGameState());
        }
    }

    @Override
    public void processKey(KeyEvent e) {
        int keyCode = e.getKeyCode(); //Key Short cuts to some other stats
        //System.out.println(e.getKeyCode());
        if (keyCode == KeyEvent.VK_I) { 
            transit(0);
        }
        if (keyCode == KeyEvent.VK_P) { 
            transit(1);
        }
    }
    
    @Override
    public void releasedKey(KeyEvent e) {}
    
    @Override
    public void processClick(MouseEvent me) { //MouseListener-checks an area to make transitions
        if(me.getLocationOnScreen().getX()>4*width/10 && me.getLocationOnScreen().getX()<5*width/10
                && me.getLocationOnScreen().getY()>450 && me.getLocationOnScreen().getY()<550){
            transit(1);
        }
        if(me.getLocationOnScreen().getX()>4*width/10 && me.getLocationOnScreen().getX()<6*width/10
                && me.getLocationOnScreen().getY()>600 && me.getLocationOnScreen().getY()<650){
            transit(0);
        }
    }
    
}
