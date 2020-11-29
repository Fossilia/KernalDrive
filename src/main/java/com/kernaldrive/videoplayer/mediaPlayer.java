package com.kernaldrive.videoplayer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.Timer;


import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.UIManager;
import javax.swing.JToggleButton;

import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.fullscreen.adaptive.AdaptiveFullScreenStrategy;

public class mediaPlayer extends JFrame {
   private static final long serialVersionUID = 1L;
   private static String VIDEO_PATH;//= "Z:\\PhonyStark\\Star Wars Episode I The Phantom Menace (1999) BONUS DISC [1080p BluRay DD 2.0 REMUX AVC FGT]\\Complete Podrace Grid Sequence.mkv";
   private final EmbeddedMediaPlayerComponent mediaPlayerComponent;
   private JButton playButton, rewindButton, pauseButton, skipButton;
   private JToggleButton toggleButton;
   private Icon playpic, fullpic;
   public MediaPlayer mp;
   private JProgressBar pbar;
   private JPanel controlsPane, contentPane, mousePane;
   /*Timer timer = new Timer(5000, new ActionListener() {

	@Override
	public void actionPerformed(ActionEvent e) {
		controlsPane.setVisible(false);
	}

   });*/

   public mediaPlayer(String path) {
      super("Media Player");
      mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
      mediaPlayerComponent.mediaPlayer().fullScreen().strategy(new AdaptiveFullScreenStrategy(this));
      VIDEO_PATH = path;
      playpic = new ImageIcon("playIcon.png");
      fullpic = new ImageIcon("full.png");
      pbar = new JProgressBar(0,100);
      mp = mediaPlayerComponent.mediaPlayer();
      mousePane = new JPanel();
      mousePane.setBounds(this.getX(), this.getY(), this.getWidth(), this.getHeight());


   }
   public void initialize() {
      this.setBounds(100, 100, 600, 400);
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.addWindowListener(new WindowAdapter() {
         @Override
         public void windowClosing(WindowEvent e) {
            mediaPlayerComponent.release();
            System.exit(0);
         }
      });

      contentPane = new JPanel();
      contentPane.setLayout(new BorderLayout());
      contentPane.setBounds(this.getX(), this.getY(), this.getWidth(), this.getHeight());
      contentPane.add(mediaPlayerComponent, BorderLayout.CENTER);
      controlsPane = new JPanel();
      controlsPane.setLayout(new BorderLayout());
      JPanel leftPane = new JPanel();
      playButton = new JButton("Play");
      pauseButton = new JButton(playpic);
      leftPane.add(pauseButton);
      toggleButton = new JToggleButton(fullpic);
      controlsPane.add(toggleButton,BorderLayout.EAST);
      rewindButton = new JButton("<<");
      leftPane.add(rewindButton);
      skipButton = new JButton(">>");
      leftPane.add(skipButton,BorderLayout.LINE_END);
      controlsPane.add(pbar, BorderLayout.NORTH);
      controlsPane.add(leftPane, BorderLayout.WEST);
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
      rewindButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            mediaPlayerComponent.mediaPlayer().controls().skipTime(-5000);
            pbar.setValue((int) (mediaPlayerComponent.mediaPlayer().status().position()*100));
         }
      });
      skipButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            mediaPlayerComponent.mediaPlayer().controls().skipTime(5000);
            pbar.setValue((int) (mediaPlayerComponent.mediaPlayer().status().position()*100));
         }
      });

      pbar.addMouseListener( new MouseAdapter() {
         public void mousePressed(MouseEvent e){
            float pos = ((float)e.getX())/pbar.getWidth();
            mediaPlayerComponent.mediaPlayer().controls().setPosition(pos);
            pbar.setValue((int) (mediaPlayerComponent.mediaPlayer().status().position()*100));
         }
      });
      /*contentPane.addMouseMotionListener(new MouseMotionListener() {

   		@Override
   		public void mouseDragged(MouseEvent arg0) {
   			// TODO Auto-generated method stub
   			System.out.println(1);
   		}

   		@Override
   		public void mouseMoved(MouseEvent arg0) {
   			System.out.println(arg0.getX());
   			//controlsPane.setVisible(true);
   			//timer.restart();

   		}
      });*/
      this.setContentPane(contentPane);
      this.setVisible(true);
      //timer.start();

   }
   public void loadVideo(String path) {

      mediaPlayerComponent.mediaPlayer().media().start(path);
      mediaPlayerComponent.mediaPlayer().fullScreen().toggle();
      while(mediaPlayerComponent.mediaPlayer().status().position() != -1) {
         pbar.setValue((int) (mediaPlayerComponent.mediaPlayer().status().position()*100));

      }

   }
   public void start(){
      try {
         UIManager.setLookAndFeel(
                 UIManager.getSystemLookAndFeelClassName());
      }
      catch (Exception e) {
         System.out.println(e);
      }

      initialize();
      setVisible(true);
      loadVideo(VIDEO_PATH);

   }
   public static void main (String[] args){
      mediaPlayer mp = new mediaPlayer("Complete Podrace Grid Sequence.mkv");
      mp.start();
   }
}




