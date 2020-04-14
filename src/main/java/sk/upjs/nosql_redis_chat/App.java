package sk.upjs.nosql_redis_chat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

public class App extends Application
{

	@Override
	public void start(Stage primaryStage) throws Exception {
        MainSceneController controller = new MainSceneController();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainScene.fxml"));
        fxmlLoader.setController(controller);
        
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Users management");
        primaryStage.show();
		
	}

    public static void main(String[] args) {
		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.INFO);
        launch(args);
    }
}
