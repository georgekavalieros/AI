//Καβαλιέρος Γιώργος (Α.Μ: 3120048) Καραλής Γιώργος (Α.Μ: 3120058) Κεφαλληνός Νίκος (Α.Μ: 3120065)

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Board extends JFrame{
	int size;
	public Node states[][];
	private String wumpusFile;
	private States s;
	private int agX,agY;
	public Node n=null;
	Agent triggerAgent;
	GridLayout board;
	JLabel squares[][];

	Board(){
		super("Wumpus");
		System.out.println("Give world size (same X and Y) and world txt name");
		Scanner sc = new Scanner(System.in);
		size = sc.nextInt();
		wumpusFile = sc.next();
		states = new Node[size][size];
		board = new GridLayout(size,size);
		squares = new JLabel[size][size];

		initBoard();
		triggerAgent=new Agent(agX,agY,size);
		createGui();
	}

	public void createGui(){
		for (int i = 0; i<size; i++){
			for(int j=0; j<size; j++) {
				squares[i][j]=new JLabel();
				add(squares[i][j]);
				squares[i][j].setOpaque(true);
				squares[i][j].setBorder(BorderFactory.createLineBorder(Color.black));

				if(states[i][j].contains(s.W)){
					squares[i][j].setBackground(Color.RED);
				}else if(states[i][j].contains(s.P)){
					squares[i][j].setBackground(Color.BLACK);
				}else if(states[i][j].contains(s.G)){
					squares[i][j].setBackground(Color.YELLOW);
				}else if(states[i][j].contains(s.A)){
					squares[i][j].setBackground(Color.BLUE);
					n = states[i][j];
				}else{
					squares[i][j].setBackground(Color.WHITE);
				}
			}
		}
		setLayout(board);
		setVisible(true);
		setSize(500,500);
		setLayout(board);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void aiPlay(){
		int moves=0;
		boolean goldFound=false,unsolvable=false;

		while(!goldFound && !unsolvable){
			try {
				Thread.sleep(1000);
			} catch(InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
			squares[n.getX()][n.getY()].setBackground(Color.WHITE);
			n = triggerAgent.move();
			triggerAgent.sense(getNode(n));
			triggerAgent.fireArrow(getNode(n));
			goldFound = triggerAgent.foundGold(getNode(n));
			unsolvable = triggerAgent.unsolvable();
			updateGui(n);
			moves++;
		}

		if(goldFound){
			System.out.println("Gold found in " + moves + " moves");
		}else if (unsolvable){
			System.out.println("World can't be solved (stopped in " + moves + " moves)");
		}
	}

	public void updateGui(Node agentHere){
		if(triggerAgent.isWumpusDead()){
			for (int i = 0; i<size; i++) {
				for (int j = 0; j < size; j++) {
					if(states[i][j].contains(s.W)){
						squares[i][j].setBackground(Color.WHITE);
					}
				}
			}
		}
		squares[agentHere.getX()][agentHere.getY()].setBackground(Color.BLUE);
	}

	private void initBoard(){
		try{
			BufferedReader reader = new BufferedReader(new FileReader(wumpusFile));
			
			for(int i=0; i<size; i++){
				for(int j=0; j<size; j++){
					states[i][j]=new Node(i,j,s.U);
				}
			}
			
			String line;
			while((line=reader.readLine())!=null){
				StringTokenizer tok = new StringTokenizer(line);
				while(tok.hasMoreTokens()){
					int i = Integer.parseInt(tok.nextToken());
					int j = Integer.parseInt(tok.nextToken());
					int state = Integer.parseInt(tok.nextToken());
					if(state==2){
						agX=i;
						agY=j;
					}
					s=s.values()[state];
					states[i][j]=new Node(i,j,s);
				}
			}
			
			for(int i=0; i<size; i++){
				for(int j=0; j<size; j++){
					
					if(states[i][j].contains(s.P)){
						if(i<size-1)	states[i+1][j].addState(s.B);
						if(i>0)	states[i-1][j].addState(s.B);
						if(j<size-1)	states[i][j+1].addState(s.B);
						if(j>0)	states[i][j-1].addState(s.B);
					}
					
					if(states[i][j].contains(s.W)){
						if(i<size-1)	states[i+1][j].addState(s.S);
						if(i>0)	states[i-1][j].addState(s.S);
						if(j<size-1)	states[i][j+1].addState(s.S);
						if(j>0)	states[i][j-1].addState(s.S);
						
					}
					
				}
			}
		}catch(IOException io){
			io.printStackTrace();
		}
	}

	public Node getNode(Node isHere){
		return states[isHere.getX()][isHere.getY()];
	}
}
