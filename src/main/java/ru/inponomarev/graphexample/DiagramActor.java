package ru.inponomarev.graphexample;

import java.awt.Graphics;

import ru.inponomarev.graph.DiagramLabel;

/**
 * UML Actor on a diagram.
 */
class DiagramActor extends AbstractDiagramNode {

	static final double ACTOR_WIDTH = 25.0;
	static final double ACTOR_HEIGHT = 35.0;

	DiagramActor(double mX, double mY, String caption) {
		super(mX, mY, caption);
		addToQueue(new DiagramLabel(this));
	}

	@Override
	protected void internalDraw(Graphics canvas) {

		double mX = getmX();
		double mY = getmY();

		canvas.drawOval(scaleX(mX + 10 - ACTOR_WIDTH / 2), scaleY(mY + 0 - ACTOR_HEIGHT / 2), scale(10), scale(10));
		canvas.drawLine(scaleX(mX + 15 - ACTOR_WIDTH / 2), scaleY(mY + 10 - ACTOR_HEIGHT / 2),
				scaleX(mX + 15 - ACTOR_WIDTH / 2), scaleY(mY + 25 - ACTOR_HEIGHT / 2));
		canvas.drawLine(scaleX(mX + 5 - ACTOR_WIDTH / 2), scaleY(mY + 15 - ACTOR_HEIGHT / 2),
				scaleX(mX + 25 - ACTOR_WIDTH / 2), scaleY(mY + 15 - ACTOR_HEIGHT / 2));
		canvas.drawLine(scaleX(mX + 5 - ACTOR_WIDTH / 2), scaleY(mY + 35 - ACTOR_HEIGHT / 2),
				scaleX(mX + 15 - ACTOR_WIDTH / 2), scaleY(mY + 25 - ACTOR_HEIGHT / 2));
		canvas.drawLine(scaleX(mX + 25 - ACTOR_WIDTH / 2), scaleY(mY + 35 - ACTOR_HEIGHT / 2),
				scaleX(mX + 15 - ACTOR_WIDTH / 2), scaleY(mY + 25 - ACTOR_HEIGHT / 2));

	}

	@Override
	protected boolean internalTestHit(double x, double y) {
		double dX = x - getmX();
		double dY = y - getmY();
		return dY > -ACTOR_HEIGHT / 2 && dY < ACTOR_HEIGHT / 2 && dX > -ACTOR_WIDTH / 2 && dX < ACTOR_WIDTH / 2;
	}

	@Override
	protected double getMinX() {
		return getmX() - ACTOR_WIDTH / 2;
	}

	@Override
	protected double getMinY() {
		return getmY() - ACTOR_HEIGHT / 2;
	}

	@Override
	protected double getMaxX() {
		return getmX() + ACTOR_WIDTH / 2;
	}

	@Override
	protected double getMaxY() {
		return getmY() + ACTOR_HEIGHT / 2;
	}

	@Override
	protected boolean internalGetHint(StringBuilder hintStr) {
		hintStr.append("Actor: " + getCaption());
		return true;
	}
}