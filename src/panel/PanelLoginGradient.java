package panel;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GradientPaint;

public class PanelLoginGradient extends JPanel {
	
	private Color startColor = new Color(248, 220, 196);
	private Color endColor = new Color(255, 253, 208);
	
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g.create();
		int w = getWidth();
		int h = getHeight();
		
		GradientPaint gp = new GradientPaint(0, 0, startColor, 0, h, endColor);
		
		g2d.setPaint(gp);
		g2d.fillRect(0, 0, w, h);
		
		g2d.dispose();
		}
}
