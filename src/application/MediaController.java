package application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

public class MediaController implements Initializable {
	 @FXML
	    private MediaView mediaView;

	 	private File file;
		private Media media;
		private MediaPlayer mediaPlayer;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
//		String videoURL = getClass().getResource("/application.video/8094520674442125722.mp4").toExternalForm();
//        Media media = new Media(videoURL);
//        MediaPlayer mediaPlayer = new MediaPlayer(media);
//
//        // Kết nối MediaPlayer với MediaView
//        mediaPlayer.setMediaPlayer(mediaPlayer);
//
//        mediaPlayer.setAutoPlay(true);
		URL videoUrl = getClass().getResource("/application/video/7099827502010490952.mp4");
		media = new Media(videoUrl.toString());
		mediaPlayer = new MediaPlayer(media);
		mediaView.setMediaPlayer(mediaPlayer);
		
		
		
	}
	
	@FXML
    void pauseMedia(ActionEvent event) {
		mediaPlayer.pause();
    }

    @FXML
    void playMedia(ActionEvent event) {
    	
    	mediaPlayer.play();
    }

    @FXML
    void resetMedia(ActionEvent event) {
    	if(mediaPlayer.getStatus() != MediaPlayer.Status.READY) {
			mediaPlayer.seek(Duration.seconds(0.0));
		}
    }

	 
}
