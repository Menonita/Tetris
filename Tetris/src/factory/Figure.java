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

package factory;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

/**
 *
 * @author Omar
 */
public abstract class Figure {
    
    private final int LENGTH;
    private int[] posX, posY;
    private Color color;
    
    //Constructors
    public Figure(int x, int y){
        LENGTH=1;
        posX=new int[4];
        posY=new int[4];
        
        for(int i=0;i<posX.length;i++){
            posX[i]=x;
            posY[i]=y;
        }
        
        color=Color.BLACK;
    }
    
    public Figure(int l, int x1,int x2,int x3,int x4,
            int y1,int y2,int y3,int y4,int px, int py, Color c){
        posX=new int[4];
        posY=new int[4];
        LENGTH=l;
        
        posX[0]=px+x1*LENGTH; 
        posX[1]=px+x2*LENGTH;
        posX[2]=px+x3*LENGTH; 
        posX[3]=px+x4*LENGTH; 
        
        posY[0]=py+y1*LENGTH;
        posY[1]=py+y2*LENGTH;
        posY[2]=py+y3*LENGTH;
        posY[3]=py+y4*LENGTH;
        
        color=c;
    }
    
    //Getters & Setters
    public int getPosX(int i){
        return posX[i];
    }
    public void setPosX(int i, int x){
        posX[i]=x;
    }
    
    public int getPosY(int i){
        return posY[i];
    }
    public void setPosY(int i, int y){
        posY[i]=y;
    }
    public Color getColor(){
        return color;
    }
    
    //GetLenght
    public int getLength(){
        return LENGTH;
    }
    
    //Methods
    public void move(int end) {
        for(int i=0; i<4;i++){
            posX[i]+=end*LENGTH;
        }
    }
    public void rotate(int x){
        int[][] RM=new int[2][2]; //Rotation Matrix
        int[] P=new int[2]; //Pivot-Always will be the final point
        int[] V=new int[2]; //Vector point
        int[] T=new int[2]; //Translation Vector

        if(x==1){
            RM[0][1]=-1;
            RM[1][0]=1;
        }else{
            RM[0][1]=1;
            RM[1][0]=-1;
        }
        P[0]=posX[3];
        P[1]=posY[3];
        
        for(int i=0;i<3;i++){
            V[0]=posX[i];
            V[1]=posY[i];//give values to vector point
            
            V[0]-=P[0]; 
            V[1]-=P[1]; //Getting the Relative Vector
            
            T[0]=(RM[0][0]*V[0])+(RM[0][1]*V[1]);
            T[1]=(RM[1][0]*V[0])+(RM[1][1]*V[1]); //Getting translation vector
            
            V[0]=T[0]+P[0];
            V[1]=T[1]+P[1]; //New position vector
            
            posX[i]=V[0];
            posY[i]=V[1];
        }
        
        
    }
    public void drop(){
        for(int i=0;i<posY.length; i++){
            posY[i]+=LENGTH;
        }
    }
    public void paint(Graphics g, int y) {
        g.setColor(color);
        
        for(int i=0; i<posX.length; i++){
            if(posY[i]>=y)
                g.fill3DRect(posX[i], posY[i], LENGTH, LENGTH, false);
        }
        
        g.setColor(Color.GRAY);
        for(int i=0; i<posX.length; i++){
            if(posY[i]>=y)
                g.drawRect(posX[i], posY[i], LENGTH, LENGTH);
        }
    }
}
