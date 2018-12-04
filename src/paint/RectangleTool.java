package paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

public class RectangleTool extends DrawTool {
	public RectangleTool() {
		super("Rectangle");
	}

	@Override
	public void onMousePress(MouseEvent e, GraphicsContext gc) {
		currentShape = new Rectangle();
		Rectangle currentRectangle = ((Rectangle)currentShape);
		currentRectangle.setX(e.getX());
		currentRectangle.setY(e.getY());
	}

	@Override
	public void onMouseDrag(MouseEvent e, GraphicsContext gc) { }

	@Override
	public void onMouseRelease(MouseEvent e, GraphicsContext gc) {
		Rectangle currentRectangle = ((Rectangle)currentShape);
		double width = e.getX() - currentRectangle.getX();
		double height = e.getY() - currentRectangle.getY();

		if(currentRectangle.getX() > e.getX()) {
			currentRectangle.setX(e.getX());
			width = width * -1;
		}

		if(currentRectangle.getY() > e.getY()) {
			currentRectangle.setY(e.getY());
			height = height * -1;
		}
		currentRectangle.setWidth(width);
		currentRectangle.setHeight(height);

		gc.fillRect(currentRectangle.getX(), currentRectangle.getY(), currentRectangle.getWidth(), currentRectangle.getHeight());
		gc.strokeRect(currentRectangle.getX(), currentRectangle.getY(), currentRectangle.getWidth(), currentRectangle.getHeight());
		currentShape = null;
	}
}
