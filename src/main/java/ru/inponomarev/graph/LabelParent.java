package ru.inponomarev.graph;

/**
 * Interface of the object to which a label is attached.
 */
public interface LabelParent {

	/**
	 * Label text.
	 */
	String getText();
	
	/**
	 * Label's abscissa (in logical pixels).
	 */
	double getLabelX();
	
	/**
	 * Label's ordinate (in logical pixels).
	 */
	double getLabelY();
	
	/**
	 * Sets the label's abscissa.
	 * @param newX new value
	 */
	void setLabelX(double newX);
	
	/**
	 * Sets the label's ordinate.
	 * @param newY new value
	 */
	void setLabelY(double newY);

}
