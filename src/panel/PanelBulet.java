package panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class PanelBulet extends JPanel {

	
	private Color backgroundColor;
	private int cornerRadius = 15;
	
	
	//"kenapa banyak banget konstruktor?" biar nanti bisa dipake lagi buat bentuk yang lain walaupun
	//namanya "panelBulet".
	public PanelBulet(LayoutManager layout, int radius) {
		super(layout);
		cornerRadius = radius;
	}
	
	public PanelBulet(LayoutManager layout, int radius, Color bgColor) {
		super(layout);
		cornerRadius = radius;
		backgroundColor = bgColor;
	}
	
	public PanelBulet(int radius) {
		super();
		cornerRadius = radius;
	}
	
	public PanelBulet(int radius, Color bgColor) {
		super();
		cornerRadius = radius;
		backgroundColor = bgColor;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Dimension arcs = new Dimension(cornerRadius, cornerRadius);
		int width = getWidth();
		int height = getHeight();
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		if(backgroundColor != null) {
			g2.setColor(backgroundColor);
		} else {
			g2.setColor(backgroundColor);
		}
		g2.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);
		g2.setColor(getForeground());
		g2.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);
	}
}
