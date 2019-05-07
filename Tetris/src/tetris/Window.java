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

import stateMachine.GameContext;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


import javax.swing.JPanel;

/**
 *
 * @author Omar
 */
public class Window extends JPanel implements Runnable {
    
    //GamePanel used on the videogames class
    //Almost Identical panel on marvin's GitHub:
        //https://gist.github.com/marvin/2141499
    //...
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static final int PWIDTH = (int)screenSize.getWidth(); //Panel Width
    private static final int PHEIGHT = (int) screenSize.getHeight(); //Panel Height

    private Thread animator; //controla la animación
    private volatile boolean running = false;
    private volatile boolean gameOver = false;
    private volatile boolean isPaused = false;

    private GameContext game;

    public Window() {
        setBackground(Color.white);
        setPreferredSize(new Dimension(PWIDTH, PHEIGHT));

        setFocusable(true);
        requestFocus();
        readyForTermination();
        
        game = new GameContext(PWIDTH, PHEIGHT);

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                game.processClick(e); //Mouse listener for where is needed on the states
            }
        });
    } //GamePanel()

    public void addNotify() {
        super.addNotify();
        startGame();
    }//addNotify

    private void startGame() {
        if (animator == null || !running) {
            animator = new Thread(this);
            animator.start();
        }
    }//startGame()

    public void stopGame() {
        running = false;
    }//stopGame()

    public void run() {
        running = true;
        while (running) {
            //gameUpdate();
            gameUpdate();
            gameRender();
            paintScreen();

            try {
                Thread.sleep(20);
            } catch (InterruptedException ex) {
            }
        }
        System.exit(0);
    }//run()

    private void gameUpdate() {

        if (!isPaused && !gameOver) {
            //game.update();
        }

    }//gameUpdate()

    private Graphics dbg;
    private Image dbImage = null;

    private void gameRender() {
        if (dbImage == null) {
            dbImage = createImage(PWIDTH, PHEIGHT);
            if (dbImage == null) {
                System.out.println("dbImage is null");
                return;
            } else {
                dbg = dbImage.getGraphics();
            }
        }

        dbg.setColor(Color.white);
        dbg.fillRect(0, 0, PWIDTH, PHEIGHT);

        game.draw(dbg);
    }//gameRender()

    private void gameOverMessage() {
        Graphics g;
        g = this.getGraphics();
        g.drawString("GameOver", 10, 10);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (dbImage != null) {
            g.drawImage(dbImage, 0, 0, null);
        }
    }

    private void readyForTermination() {
        addKeyListener(new KeyAdapter() { // listen for esc, q, end, ctrl-c
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                game.processKey(e);

                if ((keyCode == KeyEvent.VK_ESCAPE)
                        || (keyCode == KeyEvent.VK_END)
                        || ((keyCode == KeyEvent.VK_C) && e.isControlDown())) {
                    running = false;
                }
            }
            public void keyReleased(KeyEvent e) {
                game.releasedKey(e);
            }
        });
    } // end of readyForTermination()

    private void testPress(int x, int y) {
        if (!gameOver && !isPaused) {

            // Do Something
        }
    }

    private void paintScreen() {
        Graphics g;
        try {
            g = this.getGraphics();
            if ((g != null) && (dbImage != null)) {
                g.drawImage(dbImage, 0, 0, null);
            }
            Toolkit.getDefaultToolkit().sync();
            g.dispose();
        } catch (Exception e) {
            System.out.println("Graphics context error: " + e);
        }
    }

    public void pauseGame() {
        isPaused = true;
    }

    public void resumeGame() {
        isPaused = false;
    }

}
