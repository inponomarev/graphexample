package ru.inponomarev.graphexample;

import ru.inponomarev.graph.DiagramLabel;
import ru.inponomarev.graph.DiagramObject;
import ru.inponomarev.graph.LabelParent;

/**
 * Base class for diagram nodes, such as Actor and UseCase.
 */
abstract class AbstractDiagramNode extends DiagramObject implements LabelParent {

	private double mX;
	private double mY;

	private double dX;
	private double dY;

	private String caption;

	AbstractDiagramNode(double mX, double mY, String caption) {
		this.mX = mX;
		this.mY = mY;
		this.caption = caption;

		dX = 0;
		dY = getMaxY() - getMinY() + DiagramLabel.FONTSIZEPT;
	}

	@Override
	public String getText() {
		return caption;
	}

	@Override
	public double getLabelX() {

		return mX + dX;
	}

	@Override
	public double getLabelY() {

		return mY + dY;
	}

	@Override
	public void setLabelX(double newX) {
		dX = newX - mX;

	}

	@Override
	public void setLabelY(double newY) {
		dY = newY - mY;
	}

	final double getmX() {
		return mX;
	}

	final double getmY() {
		return mY;
	}

	final String getCaption() {
		return caption;
	}

	@Override
	protected final void internalDrop(double dX, double dY) {
		mX += dX;
		mY += dY;
	}

	@Override
	protected boolean internalGetHint(StringBuilder hintStr) {
		hintStr.append(caption);
		return true;
	}
}