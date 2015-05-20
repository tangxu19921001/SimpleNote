package com.notedemo.recorditem;

import java.io.IOException;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

public class MediaPlayModel {
	Context context;
	MediaPlayer myMediaPlayer;
	boolean isPlaying = false;
	String playPath = "";

	public MediaPlayModel(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;

	}

	OnCompletionListener myMPCompletionListener = new OnCompletionListener() {

		@Override
		public void onCompletion(MediaPlayer mp) {
			// TODO Auto-generated method stub
			isPlaying = false;
			playPath = "";
			myMediaPlayer.release();
		}
	};

	public String getPlayPath() {
		return playPath;
	}

	public void playMedia(String path) {

		try {
			playPath = path;
			myMediaPlayer = new MediaPlayer();
			myMediaPlayer.setOnCompletionListener(myMPCompletionListener);
			myMediaPlayer.reset();
			myMediaPlayer.setDataSource(path);
			myMediaPlayer.prepare();
			myMediaPlayer.start();
			isPlaying = true;
		} catch (IllegalArgumentException e) {

			e.printStackTrace();

		} catch (IllegalStateException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
	}

	public void stopMedia() {
		if (myMediaPlayer != null) {
			myMediaPlayer.stop();
			myMediaPlayer.release();
			isPlaying = false;
			myMediaPlayer = null;
			playPath = "";
		}
	}

	public boolean getisPlaying() {
		return isPlaying;
	}

	public void pauseMedia() {
		if (isPlaying) {
			myMediaPlayer.pause();
			isPlaying = false;
		} else {
			if (myMediaPlayer != null) {
				myMediaPlayer.start();
				isPlaying = true;
			}
		}
	}

}
