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

/**
 *
 * @author Omar
 */
public class PieceFactory {
    
    public Figure getNewPiece(int i, int l, int x, int y){
        switch (i) { //just fancy IF's
            case 0:
                return new Square(l,x,y);
            case 1:
                return new Line(l,x,y);
            case 2:
                return new T(l,x,y);
            case 3:
                return new LeftL(l,x,y);
            case 4:
                return new RightL(l,x,y);
            case 5:
                return new LeftZ(l,x,y);
            case 6:
                return new RightZ(l,x,y);
            default:
                return null;
        }
    }
}
