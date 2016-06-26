package ru.inponomarev.graphexample;

/**
 * Possible dependency stereotypes.
 */
public enum DependencyStereotype {
	/**
	 * <<extend>>.
	 */
	EXTEND {
		@Override
		public String getDescription() {
			return "<<extend>>";
		}
	},
	/**
	 * <<include>>.
	 */
	INCLUDE {
		@Override
		public String getDescription() {
			return "<<include>>";
		}
	};

	/**
	 * Textual description for a stereotype.
	 */
	public abstract String getDescription();
}
