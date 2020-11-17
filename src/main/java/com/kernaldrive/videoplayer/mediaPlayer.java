package com.kernaldrive.videoplayer;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.JToggleButton;

import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.fullscreen.adaptive.AdaptiveFullScreenStrategy;

public class mediaPlayer extends JFrame {
   private static final long serialVersionUID = 1L;
   private static final String TITLE = "My First Media Player";
   private static String VIDEO_PATH;//= "Z:\\PhonyStark\\Star Wars Episode I The Phantom Menace (1999) BONUS DISC [1080p BluRay DD 2.0 REMUX AVC FGT]\\Complete Podrace Grid Sequence.mkv";
   private final EmbeddedMediaPlayerComponent mediaPlayerComponent;
   private JButton playButton, pauseButton;
   private JToggleButton toggleButton;

   public mediaPlayer(String path) {
      //super(title);
      mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
      mediaPlayerComponent.mediaPlayer().fullScreen().strategy(new AdaptiveFullScreenStrategy(this));
      VIDEO_PATH = path;
   }
   public void initialize() {
      this.setBounds(100, 100, 600, 400);
      this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      this.addWindowListener(new WindowAdapter() {
         @Override
         public void windowClosing(WindowEvent e) {
            mediaPlayerComponent.release();
            //System.exit(0);
         }
      });    	
      JPanel contentPane = new JPanel();
      contentPane.setLayout(new BorderLayout());   	 
      contentPane.add(mediaPlayerComponent, BorderLayout.CENTER);

      JPanel controlsPane = new JPanel();
      playButton = new JButton("Play");
      controlsPane.add(playButton);   
      pauseButton = new JButton("Pause");
      controlsPane.add(pauseButton);
      toggleButton = new JToggleButton("Full Screen");
      controlsPane.add(toggleButton);
      contentPane.add(controlsPane, BorderLayout.SOUTH);
      playButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            mediaPlayerComponent.mediaPlayer().controls().play();
         }
      });   
      pauseButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
             mediaPlayerComponent.mediaPlayer().controls().pause();
          }
       });
      toggleButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            mediaPlayerComponent.mediaPlayer().fullScreen().toggle();
         }
      });
      this.setContentPane(contentPane);
      this.setVisible(true);
   }
   public void loadVideo(String path) {
      mediaPlayerComponent.mediaPlayer().media().startPaused(path);   	
   }
   public void start(){
      try {
         UIManager.setLookAndFeel(
         UIManager.getSystemLookAndFeelClassName());
      } 
      catch (Exception e) {
         System.out.println(e);
      }
      //mediaPlayer application = new mediaPlayer();
      initialize();
      setVisible(true);
      loadVideo(VIDEO_PATH);
   }
}



