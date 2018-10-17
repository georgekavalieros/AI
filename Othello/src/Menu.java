//Νίκος Κεφαλληνός (Α.Μ. 3120065) Γιώργιος Καβαλιέρος (Α.Μ. 3120048) Γιώργος Καραλής (Α.Μ. 3120058)

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Menu extends JFrame {
	final JButton black=new JButton("Black");
	final JButton white=new JButton("White");
	Menu(){
		final JButton newGame = new JButton("New Game");
		setLayout(null);
		newGame.setBounds(200, 175, 100, 50);
		add(newGame);
		setTitle("Othello");
		setSize(500,500);
		newGame.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				white.setBounds(200, 175, 100, 50);
				black.setBounds(200, 230, 100, 50);
				add(white);
				add(black);
				remove(newGame);
				repaint();
				
				
				white.addActionListener(new Launch());
				black.addActionListener(new Launch());
			}
			
		});
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
	}
	
	private class Launch implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(white)){
				Gui reversi = new Gui(2);
			}else{
				Gui reversi = new Gui(1);
			}
			setVisible(false);
		}
	}
	
}
