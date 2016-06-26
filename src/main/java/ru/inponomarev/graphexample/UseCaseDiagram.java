package ru.inponomarev.graphexample;

import ru.inponomarev.graph.DiagramObject;

import java.awt.Graphics;

/**
 * UML Use Case Diagram prototype.
 */
public class UseCaseDiagram extends DiagramObject {
	public UseCaseDiagram() {
		// An example from
		// http://creately.com/blog/diagrams/use-case-diagram-tutorial/

		// This is a prototype code only!!
		// In real life you should use your model objects here to
		// construct respective DiagramObjects.
		DiagramActor a1 = new DiagramActor(70, 150, "Customer");
		addToQueue(a1);
		DiagramActor a2 = new DiagramActor(50, 350, "NFRC Customer");
		addToQueue(a2);
		DiagramActor a3 = new DiagramActor(600, 50, "Bank Employee");
		addToQueue(a3);

		DiagramUseCase uc1 = new DiagramUseCase(250, 50, "Open account");
		addToQueue(uc1);
		DiagramUseCase uc2 = new DiagramUseCase(250, 150, "Deposit funds");
		addToQueue(uc2);
		DiagramUseCase uc3 = new DiagramUseCase(250, 250, "Withdraw funds");
		addToQueue(uc3);
		DiagramUseCase uc4 = new DiagramUseCase(250, 350, "Convert currency");
		addToQueue(uc4);
		DiagramUseCase uc5 = new DiagramUseCase(500, 150, "Calculate bonus");
		addToQueue(uc5);
		DiagramUseCase uc6 = new DiagramUseCase(500, 250, "Update balance");
		addToQueue(uc6);

		addToQueue(new DiagramAssociation(a1, uc1));
		addToQueue(new DiagramAssociation(a1, uc2));
		addToQueue(new DiagramAssociation(a1, uc3));

		addToQueue(new DiagramAssociation(a2, uc4));

		addToQueue(new DiagramAssociation(a3, uc1));

		addToQueue(new DiagramDependency(uc2, uc5, DependencyStereotype.EXTEND));
		addToQueue(new DiagramDependency(uc2, uc6, DependencyStereotype.INCLUDE));

		addToQueue(new DiagramDependency(uc3, uc6, DependencyStereotype.INCLUDE));

		addToQueue(new DiagramGeneralization(a2, a1));

	}

	@Override
	protected void internalDraw(Graphics canvas) {
		// canvas.drawRect(scaleX(0), scaleY(0), scale(getMaxX()),
		// scale(getMaxY()));
	}

	@Override
	protected double getMaxX() {
		return 700;
	}

	@Override
	protected double getMinX() {
		return 0;
	}

	@Override
	protected double getMaxY() {
		return 400;
	}

	@Override
	protected double getMinY() {
		return 0;
	}

}
