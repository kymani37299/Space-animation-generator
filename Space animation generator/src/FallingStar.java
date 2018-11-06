import java.awt.image.WritableRaster;

public class FallingStar extends Star{

	private static final int maxSpeed = 50;
	private static final int minSpeed = 5;
	
	private int dx;
	private int dy;
	
	public FallingStar(int w,int h) {
		super(w,h);
		dx = (int)((Math.random()*(maxSpeed-minSpeed)) + minSpeed);
		dy = (int)((Math.random()*(maxSpeed-minSpeed)) + minSpeed);
	}
	
	public boolean isOut() {
		return !(x<w && x>0 && y<h && y>0);
	}
	
	public void draw(WritableRaster raster,int offset) {
		super.draw(raster,offset);
		
		int lastX = x-dx;
		int lastY = y-dy;
		
		if(lastX>0 && lastX<w && lastY>0 && lastY<h) {
			drawLine(raster,lastX,lastY,x,y);
		}
		x+=dx;
		y+=dy;
		
	}
	
	public static void drawLine(WritableRaster raster, int x1, int y1, int x2, int y2) {
		int rgb[] = {255,255,255};
		
        int d = 0;
 
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
 
        int dx2 = 2 * dx; // slope scaling factors to
        int dy2 = 2 * dy; // avoid floating point
 
        int ix = x1 < x2 ? 1 : -1; // increment direction
        int iy = y1 < y2 ? 1 : -1;
 
        int x = x1;
        int y = y1;
 
        if (dx >= dy) {
            while (true) {
            	raster.setPixel(x, y, rgb);
                if (x == x2)
                    break;
                x += ix;
                d += dy2;
                if (d > dx) {
                    y += iy;
                    d -= dx2;
                }
            }
        } else {
            while (true) {
            	raster.setPixel(x, y, rgb);
                if (y == y2)
                    break;
                y += iy;
                d += dx2;
                if (d > dy) {
                    x += ix;
                    d -= dy2;
                }
            }
        }
    }
	
}
