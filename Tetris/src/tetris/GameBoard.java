/*
    Copyright (C) 2019 Omar De Alba Garza

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

import factory.PieceFactory;
import factory.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import javax.swing.Timer;

/**
 *
 * @author Omar
 */
public class GameBoard implements ActionListener{
    private int height, width, posX, posY, end, spd, position,counter;
    private int lenght; //sizes of the pieces
    private boolean drop, alive, lost, play; 
    private Color[][] grid; //the matrix where all the game will be played
    private PieceFactory pieces;
    private Controls controls;
    private Figure pieza;
    private Timer dropTimer;
    private Thread t[]; //threads needed to check collisions
    private ArrayList <Integer> order;
    /*note fot the 4 booleans:
        drop-check if is something below your piece so it can continue falling
        alive-if the player is still playing
        lost-if the player can't continue playing
        play-if the player can still move it's pieces
    probably is a bit redundant using 'alive' and 'lost'
    */
    
    //Constructor
    public GameBoard(int w, int h, int x, int y, int p, int c){
        width=w;
        height=h;
        posX=x;
        posY=y;
        position=p;
        lenght=width/10;
        end=19;
        spd=500;
        counter=0;
        drop=true;
        alive=lost=play=false;
        dropTimer=new Timer(spd,this);
        pieces=new PieceFactory();
        controls=new Controls(c);
        order=new ArrayList<Integer>(Arrays.asList(0,1,2,3,4,5,6));
        
        grid=new Color[10][20]; //20 rows, 10 columns...standad values
        t=new Thread[4];
        for(Color[] row : grid){ //pretty neat
            Arrays.fill(row, Color.LIGHT_GRAY);
        }
        
        Collections.shuffle(order);
        pieza=pieces.getNewPiece(order.get(0), lenght, position+posX+lenght*4, posY);
        //pieza=new Line(lenght, position+posX+lenght*4, posY);
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
    public int getPosX(){
        return posX;
    }
    public void setPosX(int x){
        posX=x;
    }
    
    public int getPosY(){
        return posY;
    }
    public void setPosY(int y){
        posY=y;
    }
    public int getCount(){
        return counter;
    }
    public void setCount(){
        counter=0;
    }
    public void setSpd(int s){
        spd-=s;
        dropTimer.setDelay(spd);
    }
    //----------------------
    //-----Game Related-----
    //----------------------
    public void NewPiece(){
        pieza=pieces.getNewPiece(order.remove(0), lenght, position+posX+lenght*4, posY);
        if(order.isEmpty()){
            order.addAll(Arrays.asList(0,1,2,3,4,5,6));
            Collections.shuffle(order);
        }
        int x[]=new int[4];
        int y[]=new int[4];
        
        for(int i=0; i<4;i++){
            x[i]=(pieza.getPosX(i)-(position+posX))/pieza.getLength();
            y[i]=(pieza.getPosY(i)-posY)/pieza.getLength();
        }
        checkCollision(x,y,3);
        if(drop==false){
            stopGame();
        }
    }
    public boolean isAlive(){
        if(alive==false && lost==false && play==false)
            return true; //This check is only for the beggining of the game
        else if(lost==true)
            return false;
        else{
            return true;
        }
    }
    public void startGame(){
        lost=false;
        alive=true;
        play=true;
        spd=500;
        dropTimer.setDelay(spd);
        dropTimer.start();
    }
    public void stopGame(){
        lost=true;
        play=false;
        alive=false;
        dropTimer.stop();
    }
    public void winner(){
        lost=false;
        play=false;
        alive=true;
        dropTimer.stop();
    }
    public void newGame(){
        grid=new Color[10][20]; //20 rows, 10 columns...standad values
        t=new Thread[4];
        for(Color[] row : grid){ //pretty neat
            Arrays.fill(row, Color.LIGHT_GRAY);
        }
        NewPiece();
        startGame();
    }
    //----------------------
    //----Thread Related----
    //----------------------
    public int getEnd(){
        return end;
    }
    public boolean getDrop(){
        return drop;
    }
    public synchronized void setEnd(int x){
        if(Math.abs(end)>Math.abs(x))
            end=x;
    }
    public synchronized void setDrop(boolean x){
        if(x==false)
            drop=x;
    }
    //----------------------
    //----------------------
    //----------------------
    //Functions & Methods
    public void movePiece(KeyEvent e){
        int keyCode = e.getKeyCode();
        
        int x[]=new int[4];
        int y[]=new int[4];
        
        for(int i=0; i<4;i++){
            x[i]=(pieza.getPosX(i)-(position+posX))/pieza.getLength();
            y[i]=(pieza.getPosY(i)-posY)/pieza.getLength();
        }
        if(play==true){ //only if the boolean "playing" is active, the player can do the following actions
            if (keyCode == controls.getLeft()){
                checkCollision(x, y, -1);
                pieza.move(end);
            }
            if (keyCode == controls.getRight()){
                checkCollision(x, y, 1);
                pieza.move(end);
            }
            if(keyCode == controls.getDown()){
                //spd/=2;
                dropTimer.setDelay(spd/2);
            }if(keyCode == controls.getUp()){ //rotacion de piezas
                pieza.rotate(1);
                int i=checkBounce();
                if(i!=0){
                    pieza.rotate(-1);
                }
            }
            if (keyCode == controls.gethDrop()) {
                checkCollision(x, y, 0);
                checkDelete();
                NewPiece();
            }
        }
        if(keyCode == KeyEvent.VK_ENTER){ //both players had the listener to the same key so they can restart/begin at the same time
            if(play==false)
                newGame();
        }
    }
    public void releaseKey(KeyEvent e){
        int keyCode = e.getKeyCode();
        if(keyCode == controls.down){
            dropTimer.setDelay(spd);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        int x[]=new int[4];
        int y[]=new int[4];
        
        for(int i=0; i<4;i++){
            x[i]=(pieza.getPosX(i)-(position+posX))/pieza.getLength();
            y[i]=(pieza.getPosY(i)-posY)/pieza.getLength();
        }
        
        if(ae.getSource()==dropTimer){
            checkCollision(x,y,2);
            if(drop==false){
                for(int i=0; i<4; i++){
                    grid[x[i]][y[i]]=pieza.getColor();
                    checkDelete();
                }
                NewPiece();
            }else{
                pieza.drop();
            }
        }
    }
    
    public void checkCollision(int x[], int y[], int z){
        
        end=19;
        drop=true;
        switch(z){
            case 0: //Hard-Drop
                for(int i=0; i<4; i++){
                    t[i]=new DropCollision(x[i],y[i],grid);
                    t[i].start();
                }
                for(int i=0; i<4;i++){
                    try {
                        t[i].join();
                    } catch (InterruptedException ex) {}
                }
                for(int i=0; i<4;i++){
                    grid[x[i]][end+y[i]]=pieza.getColor();
                }
            case 2: //Soft-Drop
                for(int i=0; i<4; i++){
                    t[i]=new SoftDrop(x[i],y[i],grid);
                    t[i].start();
                }
                for(int i=0; i<4;i++){
                    try {
                        t[i].join();
                    } catch (InterruptedException ex) {}
                }
            case 3: //Spawn Collision Check
                for(int i=0; i<4;i++){
                    if(y[i]<0)
                        y[i]=0;
                    if(grid[x[i]][y[i]]!=Color.LIGHT_GRAY){
                        drop=false;
                        break;
                    }
                }
            default: //Lateral Movement (1 or -1)
                for(int i=0; i<4; i++){
                    t[i]=new LateralCollision(x[i],y[i],z,grid);
                    t[i].start();
                }
                for(int i=0; i<4;i++){
                    try {
                        t[i].join();
                    } catch (InterruptedException ex) {}
                }
            }
    }
    
    public int checkBounce(){
        for(int i=0; i<4;i++){
            if((pieza.getPosX(i)-(position+posX))/pieza.getLength()<0){
                return 1;
            }else if((pieza.getPosX(i)-(position+posX))/pieza.getLength()>9){
                return -1;
            }
        }
        return 0;
    }
    
    public void checkDelete(){
        for(int i=0; i<grid[0].length; i++){ //this will check every row first
            boolean filled=true;
            for(int j=0; j<grid.length;j++){
                if(grid[j][i]==Color.LIGHT_GRAY){
                    filled=false;
                    break;
                }
            }
            if(filled==true){
                counter+=1;
                for(int k=i; k>1;k--){
                    for(int j=0; j<grid.length;j++){
                        grid[j][k]=grid[j][k-1];
                    }
                }
            }
        }
    }
    
    public void paint(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(position+posX, posY, width, height);
        
        for(int i=0; i<10; i++){
            for(int j=0;j<20;j++){
                if(grid[i][j]!=(Color.LIGHT_GRAY)){
                    g.setColor(grid[i][j]);
                    g.fillRect(position+(posX+i*lenght), posY+j*lenght, lenght, lenght);
                }
                g.setColor(Color.GRAY);
                g.drawRect(position+(posX+i*lenght), posY+j*lenght, lenght, lenght);
            }
        }
        if(alive==true){
            pieza.paint(g, posY);
                if(lost==false && play==false){
                    g.setColor(Color.BLACK);
                    g.setFont(new Font("Times New Roman", Font.ITALIC, 75));
                    g.drawString("You win!", width/4+position, 300);
                    g.drawString("Press ENTER to play", width/4+position, 400);
            }
        }else if(lost==false && alive==false && play==false){   
            g.setColor(Color.BLACK);
            g.setFont(new Font("Times New Roman", Font.ITALIC, 100));
            g.drawString("Press ENTER to play", width/6+position, 300);
        }else if(lost==true){
            g.setColor(Color.BLACK);
            g.setFont(new Font("Times New Roman", Font.ITALIC, 75));
            g.drawString("You lost", width/4+position, 300);
            g.drawString("Press ENTER to play", width/4+position, 400);
        }
    }
    //------------------------------------------
    //-------------Thread Class-----------------
    //------------------------------------------
    public class DropCollision extends Thread{ //Downwards collision

        private int x, y;
        private Color[][] matrix;

        public DropCollision(int x, int y, Color[][] grid){
            this.x=x;
            this.y=y;
            matrix=grid;
        }

        @Override
        public void run() {
            for(int i=y; i<matrix[0].length;i++){ 
                if(matrix[x][i]!=Color.LIGHT_GRAY){
                    setEnd(i-y-1);
                    break;
                }
            }
            setEnd(19-y);
        }
    }
    //------------------------------------------
    //------------------------------------------
    //------------------------------------------
    public class SoftDrop extends Thread{ //Downwards collision

        private int x, y;
        private Color[][] matrix;

        public SoftDrop(int x, int y, Color[][] grid){
            this.x=x;
            this.y=y;
            matrix=grid;
        }

        @Override
        public void run() {
            if(y+1<matrix[0].length){
                if(y<0)
                    y=0;
                if(matrix[x][y+1]!=Color.LIGHT_GRAY){
                    setDrop(false);
                }else{
                    setDrop(true);
                }
                
            }
            else
                setDrop(false);
        }
    }
    //------------------------------------------
    //------------------------------------------
    //------------------------------------------
    public class LateralCollision extends Thread{

        private int x, y, z;
        private Color[][] matrix;

        public LateralCollision(int x, int y, int z, Color[][] grid){
            this.x=x;
            this.y=y;
            this.z=z;
            matrix=grid;
        }

        @Override
        public void run() {
            try{
            if((x+z)<matrix.length && (x+z)>=0){
                if(matrix[x+z][y]!=Color.LIGHT_GRAY)
                    setEnd(0);
                else
                    setEnd(z);
            }else
                setEnd(0);
            }catch(ArrayIndexOutOfBoundsException ex){}
        }
    }
    //------------------------------------------
    //------------------------------------------
    //------------------------------------------
    public class LineElimination extends Thread{

        private int y;
        private Color[][] matrix;

        public LineElimination(int y, Color[][] grid){
            this.y=y;
            matrix=grid;
        }

        @Override
        public void run() {
            boolean full=true;
            for(int i=0; i<matrix.length;i++){
                if(matrix[i][y]==Color.LIGHT_GRAY){
                    full=false;
                    break;
                }
            }
        }
    }
    //------------------------------------------
    //------------------------------------------
    //------------------------------------------
}
