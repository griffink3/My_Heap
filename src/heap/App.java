package heap;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class is the main class for Heap.
 *
 * !You don't need to touch this file at all!
 *
 */

public class App extends Application {

    @Override
	public void start(Stage stage) {
		// Creating top-level object, set up the scene, and show the stage here.
    	stage.setTitle("Heap Visualizer");
    	support.heap.HeapVizFX visualizer = new support.heap.HeapVizFX("heap.MyHeap");
    	Scene scene = new Scene(visualizer.getRoot());
    	stage.setScene(scene);
    	stage.sizeToScene();
		stage.show();
   	}

	public static void main(String[] argv) {
		launch(argv);
	}
}
