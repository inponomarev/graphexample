package ru.inponomarev.graph;

/**
 * Command manager interface for undo/redo functionality.
 */
public interface CommandManager {
	/**
	 * Sets the start of a macro operation.
	 * 
	 * @param description
	 *            operation descriptions.
	 */
	void beginMacro(String description);

	/**
	 * Sets the end of a macro operation.
	 */
	void endMacro();

	/**
	 * Redo.
	 */
	void redo();

	/**
	 * Undo.
	 */
	void undo();

}
