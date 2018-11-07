import java.awt.image.WritableRaster;

public class Star {
	private static final double maxBlinkSpeed = 0.05;
	
	protected int w;
	protected int h;
	protected int x;
	protected int y;
	protected int r;
	protected double alpha;
	protected double minAlpha;
	protected double maxAlpha;
	protected double speed;
	
	public Star(int w,int h) {
		this.w = w;
		this.h = h;
		
		int rMax = (w+h)/250 + 4;
		int rMin = 2;
		r = (int)(Math.random()*(rMax-rMin) + rMin);
		
		x = (int)(Math.random()*w);
		y = (int)(Math.random()*h);
		
		minAlpha = Math.random()*0.5;
		maxAlpha = Math.random()*0.5 + 0.5;
		alpha = (minAlpha+maxAlpha)/2;
		
		speed = Math.random()*maxBlinkSpeed;
	}
	
	private int dist(int x1,int y1,int x2,int y2) {
		return Math.abs(x1-x2) + Math.abs(y1-y2);
	}
	
	private int quadraticStar(int d,int r) {
		final double b = -510/r;
		final double a = -b/(2*r);
		
		return (int)(a*d*d + b*d + 255);
	}
	
	public void draw(WritableRaster raster,int offset) {
		//Draw
		int y = this.y + offset;
		int original[] = new int[3];
		int rgb[] = new int[3];
		for(int j=Math.max(0, y-r);j<Math.min(y+r, raster.getHeight());j++) {
			for(int i=Math.max(0, x-r);i<Math.min(x+r,raster.getWidth());i++) {
				int d = dist(x,y,i,j);
				if(d<r) {
					int value = quadraticStar(d,r);
					raster.getPixel(i, j, original);
					for(int k=0;k<3;k++) {
						rgb[k] = (int)(alpha*value);
						rgb[k] = Math.max(0, rgb[k]);
						rgb[k] += original[k];
						rgb[k] = Math.min(255, rgb[k]);
					}
					raster.setPixel(i, j, rgb);
				}
			}
		}
		
		//Update
		if(alpha>maxAlpha || alpha < minAlpha) {
			speed *= -1;
		}
		alpha += speed;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getY() {
		return this.y;
	}
		
}