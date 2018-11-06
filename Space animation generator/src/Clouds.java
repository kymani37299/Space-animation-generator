import java.awt.image.WritableRaster;

import rafgfxlib.Util;

public class Clouds {

	private int h;
	private int w;
	
	private float[][] clouds;
	private int[][] gradient = new int[256][3];
	private final static int color1[] = {0x24,0x1B,0x52};
	private final static int color2[] = {0x01,0x01,0x10};
	private final static int firstLayerSize = 4;
	private final static float alphaFactor = 0.6f;
	
	public Clouds(int w,int h) {
		this.w = w;
		this.h = h;
		this.clouds = new float[w][h];
		this.generateClouds();
		this.generateGradient();
	}
	
	private void generateClouds() {
		int layerSize = firstLayerSize;
		float alpha = 1.0f;
		float[][] scaledMap = new float[w][h];
		clouds = new float[w][h];
		
		while(layerSize < Math.min(h, w)) {
			float layer[][] = new float[layerSize][layerSize];
			for(int x=0;x<layerSize;x++) {
				for(int y=0;y<layerSize;y++) {
					layer[x][y] = ((float)Math.random()-.5f)*2f;
				}
			}
			Util.floatMapRescaleCos(layer, scaledMap);
			Util.floatMapMAD(scaledMap, clouds, alpha);
			layerSize*=1.5;
			alpha*=alphaFactor;
		}
		float layer[][] = new float[w][h];
		for(int x=0;x<w;x++) {
			for(int y=0;y<h;y++) {
				layer[x][y] = ((float)Math.random()-.5f)*2f;
			}
		}
		Util.floatMapRescaleCos(layer, scaledMap);
		Util.floatMapMAD(scaledMap, clouds, alpha);
	}
	
	private void generateGradient() {
		for(int i=0;i<256;i++) {
			for(int j=0;j<3;j++) {
				gradient[i][j] = color1[j] + (color2[j]-color1[j])*i/255;
			}
		}
	}
	
	public void draw(WritableRaster raster,int offset) {
		int rgb[] = new int[3];
		for(int y=0;y<h;y++) {
			for(int x=0;x<w;x++) {
				int value = (int)(clouds[y][x]*255/2 + 255/2);
				value = Math.min(255, value);
				value = Math.max(0, value);
				for(int i=0;i<3;i++) {
					rgb[i] = gradient[value][i];
				}
				raster.setPixel(x, offset+y, rgb);
			}
		}
	}
	
}
