package ru.inponomarev.graphexample;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;

/**
 * Actor-Actor generalization arrow.
 */
class DiagramGeneralization extends AbstractDiagramLink {

	private final AbstractDiagramNode nFrom;
	private final AbstractDiagramNode nTo;

	DiagramGeneralization(DiagramActor nFrom, DiagramActor nTo) {
		this.nFrom = nFrom;
		this.nTo = nTo;
	}

	@Override
	protected void internalDraw(Graphics canvas) {
		final double dX = nTo.getmX() - nFrom.getmX();
		final double dY = nFrom.getmY() - nTo.getmY();

		final Point2D.Double from = getActorBorder(dX, dY);

		final double x1 = nFrom.getmX() + from.getX();
		final double y1 = nFrom.getmY() + from.getY();
		final double x2 = nTo.getmX() - from.getX();
		final double y2 = nTo.getmY() - from.getY();

		int[] abscissae = new int[3];
		int[] ordinates = new int[3];
		getArrowPoints(new Point2D.Double(x1, y1), new Point2D.Double(x2, y2), abscissae, ordinates);

		getCanvas().drawLine(scaleX(x1), scaleY(y1), scaleX(x2), scaleY(y2));
		getCanvas().setColor(Color.WHITE);
		getCanvas().fillPolygon(abscissae, ordinates, 3);
		getCanvas().setColor(Color.BLACK);
		getCanvas().drawPolygon(abscissae, ordinates, 3);

	}

}