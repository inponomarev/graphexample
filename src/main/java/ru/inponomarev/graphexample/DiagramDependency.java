package ru.inponomarev.graphexample;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Point2D;

import ru.inponomarev.graph.DiagramLabel;
import ru.inponomarev.graph.LabelParent;

/**
 * UseCase-UseCase dependency arrow.
 */
class DiagramDependency extends AbstractDiagramLink implements LabelParent {
	private static final float STROKE_LEN = 10.0f;
	private final DiagramUseCase nFrom;
	private final DiagramUseCase nTo;
	private final DependencyStereotype stereotype;

	private double ldX;
	private double ldY;

	DiagramDependency(DiagramUseCase nFrom, DiagramUseCase nTo, DependencyStereotype stereotype) {
		this.nFrom = nFrom;
		this.nTo = nTo;
		this.stereotype = stereotype;

		if (stereotype != null)
			addToQueue(new DiagramLabel(this));
	}

	@Override
	protected void internalDraw(Graphics canvas) {

		final double dX = nTo.getmX() - nFrom.getmX();
		final double dY = nFrom.getmY() - nTo.getmY();

		final Point2D.Double from = getUseCaseBorder(dX, dY, nFrom);
		final Point2D.Double to = getUseCaseBorder(dX, dY, nTo);

		final double x1 = nFrom.getmX() + from.getX();
		final double y1 = nFrom.getmY() + from.getY();
		final double x2 = nTo.getmX() - to.getX();
		final double y2 = nTo.getmY() - to.getY();

		int[] abscissae = new int[3];
		int[] ordinates = new int[3];
		getArrowPoints(new Point2D.Double(x1, y1), new Point2D.Double(x2, y2), abscissae, ordinates);

		getCanvas().drawPolyline(abscissae, ordinates, 3);
		Graphics2D g2 = (Graphics2D) getCanvas();
		float[] dash1 = { STROKE_LEN };
		BasicStroke dashed = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, STROKE_LEN, dash1,
				0.0f);
		Stroke s = g2.getStroke();
		g2.setStroke(dashed);
		g2.drawLine(scaleX(x1), scaleY(y1), scaleX(x2), scaleY(y2));
		g2.setStroke(s);
	}

	@Override
	public String getText() {
		return stereotype.getDescription();
	}

	@Override
	public double getLabelX() {
		return (nTo.getmX() + nFrom.getmX()) / 2 + ldX;
	}

	@Override
	public double getLabelY() {
		return (nTo.getmY() + nFrom.getmY()) / 2 + ldY - 2;
	}

	@Override
	public void setLabelX(double newX) {
		ldX = newX - (nTo.getmX() + nFrom.getmX()) / 2;
	}

	@Override
	public void setLabelY(double newY) {
		ldY = newY - (nTo.getmY() + nFrom.getmY()) / 2 + 2;
	}

}