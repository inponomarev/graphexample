package ru.inponomarev.graphexample;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

/**
 * UML Use Case on a diagram.
 */
class DiagramUseCase extends AbstractDiagramNode {

	static final int FONTSIZEPT = 10;
	static final int HEIGHT = 3 * FONTSIZEPT;
	static final double MARGIN = 4;

	private double width;

	public DiagramUseCase(int i, int j, String string) {
		super(i, j, string);
	}

	@Override
	protected void internalDraw(Graphics canvas) {
		double mX = getmX();
		double mY = getmY();
		canvas.setFont(canvas.getFont().deriveFont((float) scale(FONTSIZEPT)));
		FontMetrics metrics = canvas.getFontMetrics();
		width = metrics.stringWidth(getCaption());
		if (width == 0)
			width = FONTSIZEPT;
		
		canvas.setColor(Color.WHITE);
		canvas.fillOval(scaleX(mX - MARGIN) - (int) (width / 2), scaleY(mY - HEIGHT / 2),
				(int) width + scale(2 * MARGIN), scale(HEIGHT));
		canvas.setColor(Color.BLACK);
		
		canvas.drawOval(scaleX(mX - MARGIN) - (int) (width / 2), scaleY(mY - HEIGHT / 2),
				(int) width + scale(2 * MARGIN), scale(HEIGHT));
		canvas.drawString(getCaption(), scaleX(mX) - (int) (width / 2), scaleY(mY + 0.5 * (FONTSIZEPT)));
	}

	@Override
	protected boolean internalTestHit(double x, double y) {
		double dX = 2 * getScale() * (x - getmX()) / (width + 2 * MARGIN / getScale());
		double dY = 2 * (y - getmY()) / HEIGHT;

		return dX * dX + dY * dY <= 1;
	}

	@Override
	protected double getMinX() {
		return getmX() - .5 * width / getScale() - MARGIN;
	}

	@Override
	protected double getMinY() {
		return getmY() - HEIGHT / 2;
	}

	@Override
	protected double getMaxX() {
		return getmX() + .5 * width / getScale() + MARGIN;
	}

	@Override
	protected double getMaxY() {
		return getmY() + HEIGHT / 2;
	}

	@Override
	protected boolean internalGetHint(StringBuilder hintStr) {
		hintStr.append("Use case: " + getCaption());
		return true;
	}

	public double getWidth() {
		return width;
	}

}