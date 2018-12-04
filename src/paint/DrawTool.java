package paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;

public abstract class DrawTool {
	/**
	 * The name of the drawing tool
	 */
	private String name;
	/**
	 * The current shape currently being drawn by the tool.
	 * This is used so that information about the shape being drawn such as x can be shared between the
	 */
	protected Shape currentShape;

	/**
	 * Specifiy the name of the drawing tool.
	 * @param name
	 */
	public DrawTool(String name) {
		this.name = name;
	}

	/**
	 *
	 * @param event
	 * @param graphicsContext
	 */
	public abstract void onMousePress(MouseEvent event, GraphicsContext graphicsContext);

	/**
	 * @param event
	 * @param graphicsContext
	 */
	public abstract void onMouseDrag(MouseEvent event, GraphicsContext graphicsContext);

	/**
	 * @param event
	 * @param graphicsContext
	 */
	public abstract void onMouseRelease(MouseEvent event, GraphicsContext graphicsContext);

	/**
	 * Returns the name of the drawing tool.
	 * @return
	 */
	public String getName() {
		return this.name;
	}
}
