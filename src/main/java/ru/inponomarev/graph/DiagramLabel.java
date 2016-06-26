package ru.inponomarev.graph;

import java.awt.FontMetrics;
import java.awt.Graphics;

/**
 * Text block for diagram.
 */
public class DiagramLabel extends DiagramObject {

	/**
	 * Label font size in logical pixels.
	 */
	public static final int FONTSIZEPT = 10;

	private final LabelParent object;
	private double width;

	public DiagramLabel(LabelParent object) {
		this.object = object;
	}

	@Override
	protected void internalDraw(Graphics canvas) {
		canvas.setFont(canvas.getFont().deriveFont((float) scale(FONTSIZEPT)));
		FontMetrics metrics = canvas.getFontMetrics();
		width = metrics.stringWidth(object.getText());
		canvas.drawString(object.getText(), scaleX(object.getLabelX()), scaleY(object.getLabelY()));
	}

	@Override
	protected boolean internalTestHit(double x, double y) {
		double dX = x - getMinX();
		double dY = getMinY() - y;
		return dX >= 0 && dY >= 0 && dX <= width / getScale() && dY <= FONTSIZEPT;
	}

	@Override
	protected void internalDrop(double dX, double dY) {
		object.setLabelX(object.getLabelX() + dX);
		object.setLabelY(object.getLabelY() + dY);
	}

	@Override
	protected double getMinX() {
		return object.getLabelX();
	}

	@Override
	protected double getMinY() {
		return object.getLabelY();
	}

	@Override
	protected double getMaxX() {
		return object.getLabelX() + width / getScale();
	}

	@Override
	protected double getMaxY() {
		return object.getLabelY() - FONTSIZEPT;
	}

}
