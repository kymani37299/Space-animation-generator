import java.awt.Graphics2D;

import rafgfxlib.GameFrame;
import rafgfxlib.Util;

@SuppressWarnings("serial")
public class Main extends GameFrame{

	private BackgroundGenerator bgGen = new BackgroundGenerator();

	public static void main(String[] args) {
		Main m = new Main("Universe",800,600);
		m.initGameWindow();
	}
	
	public Main(String title, int sizeX, int sizeY) {
		super(title, sizeX, sizeY);
		
		this.setUpdateRate(60);
		this.startThread();
	}

	@Override
	public void handleWindowInit() {
		
	}

	@Override
	public void handleWindowDestroy() {
		
	}

	@Override
	public void render(Graphics2D g, int sw, int sh) {
		g.drawImage(Util.rasterToImage(bgGen.nextFrame()), null, 0, 0);
		
	}

	@Override
	public void update() {
		
	}

	@Override
	public void handleMouseDown(int x, int y, GFMouseButton button) {
		
	}

	@Override
	public void handleMouseUp(int x, int y, GFMouseButton button) {
		
	}

	@Override
	public void handleMouseMove(int x, int y) {
		
	}

	@Override
	public void handleKeyDown(int keyCode) {
		
	}

	@Override
	public void handleKeyUp(int keyCode) {
		
	}

	
	
}
