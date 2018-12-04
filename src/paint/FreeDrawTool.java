package paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

public class FreeDrawTool extends DrawTool {
	public FreeDrawTool() {
		super("Free Draw");
	}

	@Override
	public void onMousePress(MouseEvent e, GraphicsContext gc) {
        gc.beginPath();
        gc.moveTo(e.getX(), e.getY());
        gc.stroke();
	}

	@Override
	public void onMouseDrag(MouseEvent e, GraphicsContext gc) {
		gc.lineTo(e.getX(), e.getY());
        gc.stroke();
	}

	@Override
	public void onMouseRelease(MouseEvent e, GraphicsContext gc) {
		gc.lineTo(e.getX(), e.getY());
        gc.stroke();
        gc.closePath();
	}

}
