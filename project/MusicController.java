package project;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class MusicController {

	private Clip backgroundClip;
	private long clipTimePosition = 0;
	
	
	public void playBackgroundMusic(String musicLocation) {
		try {
			File musicPath = new File(musicLocation);
					
			if (musicPath.exists()) {
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
				backgroundClip = AudioSystem.getClip();
				backgroundClip.open(audioInput);
				backgroundClip.start();
				backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);
			}
					
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void playSound (String musicLocation) {
		try {
			File musicPath = new File(musicLocation);
					
			if (musicPath.exists()) {
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
				Clip clip = AudioSystem.getClip();
				clip.open(audioInput);
				clip.start();
			}
					
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void stopBackgroundMusic() {
		clipTimePosition = backgroundClip.getMicrosecondPosition();
		backgroundClip.stop();
	}
	
	
	public void resumeBackgroundMusic() {
		backgroundClip.setMicrosecondPosition(clipTimePosition);
		backgroundClip.start();
		backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);
	}
}
