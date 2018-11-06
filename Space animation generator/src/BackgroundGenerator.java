import java.awt.image.WritableRaster;
import java.util.ArrayList;

import rafgfxlib.Util;

public class BackgroundGenerator {
	
	
	private final static int w = 800;
	private final static int h = 800;
	
	private final static int transition = 100;
	private final static int realH = 2*h + transition;
	
	private int cameraPos = 0;
	private int cameraSpeed = 2;
	
	private final static int noStars = (w+h)/4;
	private final static double fallingStarChance = 0.02;
	
	private Clouds cloudsUp = new Clouds(w,h);
	private Clouds cloudsDown = new Clouds(w,h);
	private Star[] starsUp = new Star[noStars];
	private Star[] starsDown = new Star[noStars];
	private ArrayList<FallingStar> fallingStars = new ArrayList<>();
	
	public BackgroundGenerator() {
		generateStars();
	}
	
	private void generateStars() {
		for(int i=0;i<noStars;i++) {
			starsUp[i] = new Star(w,h+transition);
			starsDown[i] = new Star(w,h+transition);
		}
	}
	
	private void renderStars(WritableRaster raster) {
		for(int i=0;i<noStars;i++) {
			starsUp[i].draw(raster,0);
			starsDown[i].draw(raster, h+transition);
		}
	}
	
	private void renderClouds(WritableRaster raster) {
		cloudsUp.draw(raster, 0);
		cloudsDown.draw(raster,h+transition);
	}
	
	private void renderFallingStars(WritableRaster raster) {
		if(Math.random() < fallingStarChance) {
			fallingStars.add(new FallingStar(w, realH));
		}
		ArrayList<FallingStar> toRemove = new ArrayList<>();
		for(FallingStar star : fallingStars) {
			star.draw(raster,0);
			if(star.isOut()){
				toRemove.add(star);
			}
		}
		fallingStars.removeAll(toRemove);
	}
	
	private void renderTransition(WritableRaster raster) {
		
		int rgb[] = new int[3];
		int c1[] = new int[3];
		int c2[] = new int[3];
		
		for(int x=0;x<w;x++) {
			raster.getPixel(x, h-1, c1);
			raster.getPixel(x, h+transition, c2);
			for(int y=h;y<h+transition;y++) {
				for(int i=0;i<3;i++) {
					rgb[i] = Util.cosInterp(c1[i], c2[i], (float)(y-h)/transition);
				}
				raster.setPixel(x, y, rgb);
			}
		}
		
	}
	
	private void updateCamera() {
		cameraPos+=cameraSpeed;
		if(cameraPos+h >= realH) {
			cameraPos = cameraSpeed;
			
			Clouds tmp = cloudsUp;
			cloudsUp = cloudsDown;
			cloudsDown = tmp;
			
			starsUp = starsDown;
			starsDown = new Star[noStars];
			for(int i=0;i<noStars;i++) {
				starsDown[i] = new Star(w,h+transition);
			}
			
			ArrayList<FallingStar> toRemove = new ArrayList<FallingStar>();
			for(FallingStar star : fallingStars) {
				star.setY(star.getY()-h);
				if(star.getY() < 0){
					toRemove.add(star);
				}
			}
			fallingStars.removeAll(toRemove);
		}
	}
	
	private WritableRaster crop(WritableRaster raster) {
		WritableRaster crop = Util.createRaster(w, h, false);
		int rgb[] = new int[3];
		for(int y=cameraPos;y<cameraPos+h;y++) {
			for(int x=0;x<w;x++) {
				raster.getPixel(x, y, rgb);
				crop.setPixel(x, y-cameraPos, rgb);
			}
		}
		return crop;
	}
	
	public WritableRaster nextFrame() {
		this.updateCamera();
		WritableRaster raster = Util.createRaster(w, realH, false);
		this.renderClouds(raster);
		this.renderTransition(raster);
		this.renderStars(raster);
		this.renderFallingStars(raster);
		return this.crop(raster);
	}
	
}
