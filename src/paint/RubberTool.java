package paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

public class RubberTool extends DrawTool {

	public RubberTool() {
		super("Rubber");
	}

	@Override
	public void onMousePress(MouseEvent e, GraphicsContext gc) {
		gc.clearRect(e.getX() - gc.getLineWidth() / 2, e.getY() - gc.getLineWidth() / 2, gc.getLineWidth(), gc.getLineWidth());
	}

	@Override
	public void onMouseDrag(MouseEvent e, GraphicsContext gc) {
		gc.clearRect(e.getX() - gc.getLineWidth() / 2, e.getY() - gc.getLineWidth() / 2, gc.getLineWidth(), gc.getLineWidth());
	}

	@Override
	public void onMouseRelease(MouseEvent e, GraphicsContext gc) {
		gc.clearRect(e.getX() - gc.getLineWidth() / 2, e.getY() - gc.getLineWidth() / 2, gc.getLineWidth(), gc.getLineWidth());
	}

}
