/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panel;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
public class PanelButtonAll extends javax.swing.JPanel {
	private Color startColor;
	private Color endColor;
	
	public PanelButtonAll(Color _startColor, Color _endColor) {
		this.startColor = _startColor;
		this.endColor = _endColor;
	}
	
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
