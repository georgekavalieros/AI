//Νίκος Κεφαλληνός (Α.Μ. 3120065) Γιώργιος Καβαλιέρος (Α.Μ. 3120048) Γιώργος Καραλής (Α.Μ. 3120058)

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
	
public class Gui extends JFrame{
	private JButton board[][]=new JButton[8][8];
	private JPanel gui=new JPanel(new BorderLayout());
	private JPanel game_board=new JPanel(new GridLayout(0,9));
	private final String COLS="ABCDEFGH";
	ImageIcon bl_img=new ImageIcon("bl.jpg");
	ImageIcon wh_img=new ImageIcon("wh.jpg");
	Image image=bl_img.getImage();
	Image img=wh_img.getImage();
	Image scl_bl_img=image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
	Image scl_wh_img=img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
	final Color rgb=new Color(0,141,83);
	ArrayList<JButton> moves_made=new ArrayList<JButton>();
	ArrayList<JButton> moves_valid=new ArrayList<JButton>();
	private int states[][]=new int[8][8];
	final int BLACK=1;
	final int WHITE=2;
	final int EMPTY=0;
	final int VALID=3;
	int score_bl=2;
	int score_wh=2;
	final int playerColor,depth=2;
	final JLabel score = new JLabel("Black : " + score_bl + " White : " + score_wh, JLabel.CENTER);
	final JLabel header = new JLabel("Black's Turn", JLabel.CENTER);
	
	public Gui(int playerChose){
		playerColor=playerChose;
		createGui();
		setTitle("Othello");
		add(gui);
		setSize(500,500);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
	}
	
	public void createGui(){
		bl_img=new ImageIcon(scl_bl_img);
		wh_img=new ImageIcon(scl_wh_img);
		
		gui.add(game_board);
		gui.add(score,BorderLayout.SOUTH);
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				JButton sq=new JButton();
				states[i][j]=EMPTY;
				
				sq.setBackground(rgb);
				
				if(i==4 && j==4 || (i==3 && j==3) ){
					sq.setIcon(bl_img);
					moves_made.add(sq);
					states[i][j]=BLACK;
				}else if(i==3 && j==4 || (i==4 && j==3)){
					sq.setIcon(wh_img);
					moves_made.add(sq);
					states[i][j]=WHITE;
				}
				
				board[i][j]=sq;				
			}
		}
		
		game_board.add(new JLabel(""));
		for (int ii = 0; ii < 8; ii++) {
            game_board.add(new JLabel(COLS.substring(ii, ii + 1),SwingConstants.CENTER));
        }
		
		for(int k=0;k<8;k++){
			 for (int l = 0; l < 8; l++){
				 switch (l) {
	             	case 0:
	             		game_board.add(new JLabel("" + (k + 1),SwingConstants.CENTER));
	                default:
	                    game_board.add(board[k][l]);
	             }
			 }
		}

		find_Valids(BLACK,1);

		gui.add(header,BorderLayout.NORTH);
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				final int state = states[i][j];
				final int x=i;
				final int y=j;
				if(moves_valid.contains(board[i][j])){
					board[i][j].setBackground(highlight());
				}
				board[i][j].addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						JButton button_pressed=(JButton) e.getSource();
						score_bl=0;
						score_wh=0;
						if(playerColor==BLACK){
							if(moves_made.contains(button_pressed) || (state==EMPTY && !moves_valid.contains(button_pressed))){
								header.setText("Invalid move for black");
							}else if(moves_valid.contains(button_pressed)){
								button_pressed.setIcon(bl_img);
								states[x][y]=BLACK;
								for(int i=0; i<8; i++){
									for(int j=0; j<8; j++){
										if(states[i][j]==VALID){
											states[i][j]=EMPTY;
										}
									}
								}
								flip(x,y,BLACK,1);
								score();
								score.setText("Black : " + score_bl + " White : " + score_wh);
								moves_made.add(button_pressed);
								moves_valid.clear();
								find_Valids(WHITE,-1);
								if(!moves_valid.isEmpty()){
									header.setText("White's Turn");
									MiniMax aiMove = new MiniMax(playerColor,states,score_bl,score_wh,depth);
									aiMakesMove(aiMove.findBestMove(depth),WHITE);
								}else{
									header.setText("No valid moves for white, turn goes to black");
									find_Valids(BLACK,1);
									if(moves_valid.isEmpty()){
										score();
										header.setText("No valid moves for anyone " + winner());
									}
								}
								
								for(int p=0;p<8;p++){
									for(int k=0;k<8;k++){
										
										if(moves_valid.contains(board[p][k])){
											board[p][k].setBackground(highlight());
										}else{
											board[p][k].setBackground(rgb);
										}
									}
								}
							}
						}else if(playerColor==WHITE){
							if(moves_made.contains(button_pressed) || (state==EMPTY && !moves_valid.contains(button_pressed))){
								header.setText("Invalid move for white");
							}else if(moves_valid.contains(button_pressed)){
								button_pressed.setIcon(wh_img);
								states[x][y]=WHITE;
								for(int i=0; i<8; i++){
									for(int j=0; j<8; j++){
										if(states[i][j]==VALID){
											states[i][j]=EMPTY;
										}
									}
								}
								flip(x,y,WHITE,-1);
								score();
								score.setText("Black : " + score_bl + " White : " + score_wh);
								moves_made.add(button_pressed);
								moves_valid.clear();
								find_Valids(BLACK,1);
								if(!moves_valid.isEmpty()){
									header.setText("Black's Turn");
									MiniMax aiMove = new MiniMax(playerColor,states,score_bl,score_wh,depth);
									aiMakesMove(aiMove.findBestMove(depth),BLACK);
								}else{
									header.setText("No valid moves for black, turn goes to white");
									find_Valids(WHITE,-1);
									if(moves_valid.isEmpty()){
										score();
										header.setText("No valid moves for anyone " + winner());
									}
								}
								
								for(int p=0;p<8;p++){
									for(int k=0;k<8;k++){
										if(moves_valid.contains(board[p][k])){
											board[p][k].setBackground(highlight());
										}else{
											board[p][k].setBackground(rgb);
										}
									}
								}
							}
						}
						
					}
				});
			}
		}

		if(playerColor==WHITE){
			MiniMax aiMove = new MiniMax(playerColor,states,score_bl,score_wh,depth);
			aiMakesMove(aiMove.findBestMove(depth),BLACK);
		}
		
	}
	
	public void aiMakesMove(Node n, int state){
		if(state==WHITE){
			states[n.getX()][n.getY()]=WHITE;
			for(int i=0; i<8; i++){
				for(int j=0; j<8; j++){
					if(states[i][j]==VALID){
						states[i][j]=EMPTY;
					}
				}
			}
			flip(n.getX(),n.getY(),WHITE,-1);
			board[n.getX()][n.getY()].setIcon(wh_img);
			score();
			score.setText("Black : " + score_bl + " White : " + score_wh);
			moves_made.add(board[n.getX()][n.getY()]);
			moves_valid.clear();
			find_Valids(BLACK,1);
			if(!moves_valid.isEmpty()){
				header.setText("Black's Turn");
			}else{
				header.setText("No valid moves for black, turn goes to white");
				find_Valids(WHITE,-1);
				if(moves_valid.isEmpty()){
					score();
					header.setText("No valid moves for anyone " + winner());
				}else{
					MiniMax aiMove = new MiniMax(playerColor,states,score_bl,score_wh,depth);
					aiMakesMove(aiMove.findBestMove(depth),WHITE);
				}
			}
			
			for(int p=0;p<8;p++){
				for(int k=0;k<8;k++){
					if(moves_valid.contains(board[p][k])){
						board[p][k].setBackground(highlight());
					}else{
						board[p][k].setBackground(rgb);
					}
				}
			}
			
		}else{
			states[n.getX()][n.getY()]=BLACK;
			for(int i=0; i<8; i++){
				for(int j=0; j<8; j++){
					if(states[i][j]==VALID){
						states[i][j]=EMPTY;
					}
				}
			}
			flip(n.getX(),n.getY(),BLACK,1);
			board[n.getX()][n.getY()].setIcon(bl_img);
			score();
			score.setText("Black : " + score_bl + " White : " + score_wh);
			moves_made.add(board[n.getX()][n.getY()]);
			moves_valid.clear();
			find_Valids(WHITE,-1);
			if(!moves_valid.isEmpty()){
				header.setText("White's Turn");
			}else{
				header.setText("No valid moves for white, turn goes to black");
				find_Valids(BLACK,-1);
				if(moves_valid.isEmpty()){
					score();
					header.setText("No valid moves for anyone " + winner());
				}else{
					MiniMax aiMove = new MiniMax(playerColor,states,score_bl,score_wh,depth);
					aiMakesMove(aiMove.findBestMove(depth),BLACK);
				}
			}

			for(int p=0;p<8;p++){
				for(int k=0;k<8;k++){
					if(moves_valid.contains(board[p][k])){
						board[p][k].setBackground(highlight());
					}else{
						board[p][k].setBackground(rgb);
					}
				}
			}
		}
	}
	
	public void score(){
		score_bl=0;
		score_wh=0;
		for(int p=0;p<8;p++){
			for(int k=0;k<8;k++){
				if(states[p][k]==BLACK){
					score_bl++;
				}else if(states[p][k]==WHITE){
					score_wh++;
				}
			}
		}
	}
	
	public String winner(){
		return (score_bl>score_wh ? "Black wins" : "White wins");
	}
	
	public Color highlight(){
		Color rgb = new Color(0,168,99);
		return rgb;
	}
	
	public void flip(int x, int y, int state, int offset){
		int i=1;
		if(x<7){
			if(states[x+i][y]==state+offset){
				while(states[x+i][y]==state+offset){
					int k=i;
					if(x+i<7){
						i++;
					}else{
						break;
					}
					if(states[x+i][y]==state){
						while(states[x+k][y]!=state){
							states[x+k][y]=state;
							if(state==BLACK){
								board[x+k][y].setIcon(bl_img);
							}else{
								board[x+k][y].setIcon(wh_img);
							}
							k--;
						}
					}
				}
			}	
		}
		
		
		i=1;
		if(x>0){
			if(states[x-i][y]==state+offset){
				while(states[x-i][y]==state+offset){
					int k=i;
					if(x-i>0){
						i++;
					}else{
						break;
					}
					if(states[x-i][y]==state){
						while(states[x-k][y]!=state){
							states[x-k][y]=state;
							if(state==BLACK){
								board[x-k][y].setIcon(bl_img);
							}else{
								board[x-k][y].setIcon(wh_img);
							}
							k--;
						}
					}
				}
			}
		}
		
		
		i=1;
		if(y<7){
			if(states[x][y+i]==state+offset){
				while(states[x][y+i]==state+offset){
					int k=i;
					if(y+i<7){
						i++;
					}else{
						break;
					}
					if(states[x][y+i]==state){
						while(states[x][y+k]!=state){
							states[x][y+k]=state;
							if(state==BLACK){
								board[x][y+k].setIcon(bl_img);
							}else{
								board[x][y+k].setIcon(wh_img);
							}
							k--;
						}
					}
				}
			}
		}
		
		
		i=1;
		if(y>0){
			if(states[x][y-i]==state+offset){
				while(states[x][y-i]==state+offset){
					int k=i;
					if(y-i>0){
						i++;
					}else{
						break;
					}
					if(states[x][y-i]==state){
						while(states[x][y-k]!=state){
							states[x][y-k]=state;
							if(state==BLACK){
								board[x][y-k].setIcon(bl_img);
							}else{
								board[x][y-k].setIcon(wh_img);
							}
							k--;
						}
					}
				}
			}
		}
		
		
		i=1;
		if(x<7 && y<7){
			if(states[x+i][y+i]==state+offset){
				while(states[x+i][y+i]==state+offset){
					int k=i;
					if(x+i<7 && y+i<7){
						i++;
					}else{
						break;
					}
					if(states[x+i][y+i]==state){
						while(states[x+k][y+k]!=state){
							states[x+k][y+k]=state;
							if(state==BLACK){
								board[x+k][y+k].setIcon(bl_img);
							}else{
								board[x+k][y+k].setIcon(wh_img);
							}
							k--;
						}
					}
				}
			}
		}
		
		
		i=1;
		if(x>0 && y>0){
			if(states[x-i][y-i]==state+offset){
				while(states[x-i][y-i]==state+offset){
					int k=i;
					if(x-i>0 && y-i>0){
						i++;
					}else{
						break;
					}
					if(states[x-i][y-i]==state){
						while(states[x-k][y-k]!=state){
							states[x-k][y-k]=state;
							if(state==BLACK){
								board[x-k][y-k].setIcon(bl_img);
							}else{
								board[x-k][y-k].setIcon(wh_img);
							}
							k--;
						}
					}
				}
			}
		}
		
		
		i=1;
		if(x>0 && y<7){
			if(states[x-i][y+i]==state+offset){
				while(states[x-i][y+i]==state+offset){
					int k=i;
					if(x-i>0 && y+i<7){
						i++;
					}else{
						break;
					}
					if(states[x-i][y+i]==state){
						while(states[x-k][y+k]!=state){
							states[x-k][y+k]=state;
							if(state==BLACK){
								board[x-k][y+k].setIcon(bl_img);
							}else{
								board[x-k][y+k].setIcon(wh_img);
							}
							k--;
						}
					}
				}
			}
		}
		
		
		i=1;
		if(x<7 && y>0){
			if(states[x+i][y-i]==state+offset){
				while(states[x+i][y-i]==state+offset){
					int k=i;
					if(x+i<7 && y-i>0){
						i++;
					}else{
						break;
					}
					if(states[x+i][y-i]==state){
						while(states[x+k][y-k]!=state){
							states[x+k][y-k]=state;
							if(state==BLACK){
								board[x+k][y-k].setIcon(bl_img);
							}else{
								board[x+k][y-k].setIcon(wh_img);
							}
							k--;
						}
					}
				}
			}
		}
		
	}
	
	public void find_Valids(int state,int offset){
		for(int i=0;i<8;i++){
			for(int j=0; j<8;j++){
				if(states[i][j]==state){					
					int l=1;
					if(i<7){
						if(states[i+l][j]==state+offset){
							while(states[i+l][j]==state+offset){
								if(i+l<7){
									l++;
								}else{
									break;
								}
								
								if(states[i+l][j]==EMPTY){
									moves_valid.add(board[i+l][j]);
									states[i+l][j]=VALID;
								}
							}
						}
					}
					
					
					l=1;
					if(i>0){
						if(states[i-l][j]==state+offset){
							while(states[i-l][j]==state+offset){
								if(i-l>0){
									l++;
								}else{
									break;
								}
								
								if(states[i-l][j]==EMPTY){
									moves_valid.add(board[i-l][j]);
									states[i-l][j]=VALID;
								}
							}
						}
					}
					
					
					l=1;
					if(j<7){
						if(states[i][j+l]==state+offset){
							while(states[i][j+l]==state+offset){
								if(j+l<7){
									l++;
								}else{
									break;
								}
								
								if(states[i][j+l]==EMPTY){
									moves_valid.add(board[i][j+l]);
									states[i][j+l]=VALID;
								}
							}
						}
					}
					
					
					l=1;
					if(j>0){
						if(states[i][j-l]==state+offset){
							while(states[i][j-l]==state+offset){
								if(j-l>0){
									l++;
								}else{
									break;
								}
								
								if(states[i][j-l]==EMPTY){
									moves_valid.add(board[i][j-l]);
									states[i][j-l]=VALID;
								}
							}
						}
					}
					
					
					l=1;
					if(i<7 && j<7){
						if(states[i+l][j+l]==state+offset){
							while(states[i+l][j+l]==state+offset){
								if(i+l<7 && j+l<7){
									l++;
								}else{
									break;
								}
								
								if(states[i+l][j+l]==EMPTY){
									moves_valid.add(board[i+l][j+l]);
									states[i+l][j+l]=VALID;
								}
							}
						}
					}
					
					
					l=1;
					if(i>0 && j>0){
						if(states[i-l][j-l]==state+offset){
							while(states[i-l][j-l]==state+offset){
								if(i-l>0 && j-l>0){
									l++;
								}else{
									break;
								}
								
								if(states[i-l][j-l]==EMPTY){
									moves_valid.add(board[i-l][j-l]);
									states[i-l][j-l]=VALID;
								}
							}
						}
					}
					
					
					l=1;
					if(i>0 && j<7){
						if(states[i-l][j+l]==state+offset){
							while(states[i-l][j+l]==state+offset){
								if(i-l>0 && j+l<7){
									l++;
								}else{
									break;
								}
								if(states[i-l][j+l]==EMPTY){
									moves_valid.add(board[i-l][j+l]);
									states[i-l][j+l]=VALID;
								}
							}
						}
					}
					
					
					l=1;
					if(i<7 && j>0){
						if(states[i+l][j-l]==state+offset){
							while(states[i+l][j-l]==state+offset){
								if(i+l<7 && j-l>0){
									l++;
								}else{
									break;
								}
								if(states[i+l][j-l]==EMPTY){
									moves_valid.add(board[i+l][j-l]);
									states[i+l][j-l]=VALID;
								}
							}
						}
					}
				}
			}
		}
	}
	
}