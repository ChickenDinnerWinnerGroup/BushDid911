package paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;

public class StraightLineTool extends DrawTool {
	public StraightLineTool() {
		super("Straight Line");
	}

	@Override
	public void onMousePress(MouseEvent e, GraphicsContext gc) {
		currentShape = new Line();
		Line currentLine = ((Line)currentShape);
		currentLine.setStartX(e.getX());
		currentLine.setStartY(e.getY());
	}

	@Override
	public void onMouseDrag(MouseEvent e, GraphicsContext gc) { }

	@Override
	public void onMouseRelease(MouseEvent e, GraphicsContext gc) {
		Line currentLine = ((Line)currentShape);
		currentLine.setEndX(e.getX());
		currentLine.setEndY(e.getY());
        gc.strokeLine(currentLine.getStartX(), currentLine.getStartY(), currentLine.getEndX(), currentLine.getEndY());
		currentShape = null;
	}
}
