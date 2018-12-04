package paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Ellipse;

public class CircleTool extends DrawTool {
	public CircleTool() {
		super("Circle");
	}

	@Override
	public void onMousePress(MouseEvent e, GraphicsContext gc) {
		currentShape = new Ellipse();
		Ellipse currentEllipse = ((Ellipse)currentShape);
		currentEllipse.setCenterX(e.getX());
		currentEllipse.setCenterY(e.getY());
	}

	@Override
	public void onMouseDrag(MouseEvent e, GraphicsContext gc) { }

	@Override
	public void onMouseRelease(MouseEvent e, GraphicsContext gc) {
		Ellipse currentEllipse = ((Ellipse)currentShape);
		double width = e.getX() - currentEllipse.getCenterX();
		double height = e.getY() - currentEllipse.getCenterY();

		if(currentEllipse.getCenterX() > e.getX()) {
			currentEllipse.setCenterX(e.getX());
			width = width * -1;
		}

		if(currentEllipse.getCenterY() > e.getY()) {
			currentEllipse.setCenterY(e.getY());
			height = height * -1;
		}
		currentEllipse.setRadiusX(width);
		currentEllipse.setRadiusY(height);

		gc.strokeOval(currentEllipse.getCenterX(), currentEllipse.getCenterY(), currentEllipse.getRadiusX(), currentEllipse.getRadiusY());
		gc.fillOval(currentEllipse.getCenterX(), currentEllipse.getCenterY(), currentEllipse.getRadiusX(), currentEllipse.getRadiusY());
		currentShape = null;
	}
}
