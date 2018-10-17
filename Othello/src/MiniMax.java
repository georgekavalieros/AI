//Νίκος Κεφαλληνός (Α.Μ. 3120065) Γιώργιος Καβαλιέρος (Α.Μ. 3120048) Γιώργος Καραλής (Α.Μ. 3120058)

import java.util.ArrayList;

public class MiniMax{
	private ArrayList<Integer> simulated_valids=new ArrayList<Integer>();
	private int simulated_states[][]=new int[8][8];
	private int playsNow,score_bl,score_wh,min=64, max=-64,posX,posY,flipped;
	private final int tempDepth;
	private final int WHITE=2, BLACK=1, VALID=3, EMPTY=0;
	private ArrayList<Node> blocksFlipped = new ArrayList<Node>();
	Node maximum,minimum;
	
	MiniMax(int playerChose,int simulated_states[][],int score_bl,int score_wh,int depth){
		if(playerChose==BLACK){
			playsNow=WHITE;
		}else{
			playsNow=BLACK;
		}

		for(int i=0; i<8; i++){
			for(int j=0; j<8; j++){
				if(simulated_states[i][j]==VALID){
					simulated_valids.add(VALID);
				}
			}
		}

		tempDepth=depth;
		this.score_bl=score_bl;
		this.score_wh=score_wh;
		this.simulated_states=simulated_states;
	}
	
	public Node findBestMove(int depth){

		if(depth==1 || simulated_valids.isEmpty()){
			if((tempDepth-depth)%2==0){
				if(min>min()){
					min=min();
					if(playsNow==WHITE){
						score_bl=score_bl-1-flipped;
						score_wh=score_wh+flipped;
						playsNow=BLACK;
					}else{
						score_wh=score_wh-1-flipped;
						score_bl=score_bl+flipped;
						playsNow=WHITE;
					}
					
					minimum=new Node(posX,posY);
					return minimum;
				}

				if(playsNow==WHITE){
					score_bl=score_bl-1-flipped;
					score_wh=score_wh+flipped;
					playsNow=BLACK;
				}else{
					score_wh=score_wh-1-flipped;
					score_bl=score_bl+flipped;
					playsNow=WHITE;
				}
				return minimum;
			}else if ((tempDepth-depth)%2==1){
				if(max<max()){
					max=max();
					if(playsNow==WHITE){
						score_bl=score_bl-1-flipped;
						score_wh=score_wh+flipped;
						playsNow=BLACK;
					}else{
						score_wh=score_wh-1-flipped;
						score_bl=score_bl+flipped;
						playsNow=WHITE;
					}
					
					maximum=new Node(posX,posY);
					return maximum;
				}

				if(playsNow==WHITE){
					score_bl=score_bl-1-flipped;
					score_wh=score_wh+flipped;
					playsNow=BLACK;
				}else{
					score_wh=score_wh-1-flipped;
					score_bl=score_bl+flipped;
					playsNow=WHITE;
				}
				return maximum;
			}
		}
		
		for(int i=0; i<8; i++){
			for(int j=0; j<8; j++){
				if(simulated_states[i][j]==VALID){
					if(playsNow==BLACK){
						simulated_states[i][j]=BLACK;
						flip(i, j, BLACK, 1);
						playsNow=WHITE;
						posX=i;
						posY=j;
					}else{
						simulated_states[i][j]=WHITE;
						flip(i, j, WHITE, -1);
						playsNow=BLACK;
						posX=i;
						posY=j;
					}
					findBestMove(depth-1);
					for(int k=0; k<flipped; k++){
						simulated_states[blocksFlipped.get(blocksFlipped.size()-1).getX()][blocksFlipped.get(blocksFlipped.size()-1).getY()]=playsNow + (playsNow==1 ? 1 : -1);
						blocksFlipped.remove(blocksFlipped.size()-1);
						simulated_states[posX][posY]=VALID;
					}
				}
			}
		}
		
		if(depth==tempDepth){
			return maximum;
		}

		if((tempDepth-depth)%2==0){
			if(min>min()){
				min=min();
				if(playsNow==WHITE){
					score_bl=score_bl-1-flipped;
					score_wh=score_wh+flipped;
					playsNow=BLACK;
				}else{
					score_wh=score_wh-1-flipped;
					score_bl=score_bl+flipped;
					playsNow=WHITE;
				}
				
				minimum=new Node(posX,posY);
				return minimum;
			}

			if(playsNow==WHITE){
				score_bl=score_bl-1-flipped;
				score_wh=score_wh+flipped;
				playsNow=BLACK;
			}else{
				score_wh=score_wh-1-flipped;
				score_bl=score_bl+flipped;
				playsNow=WHITE;
			}
			return minimum;
		}else if((tempDepth-depth)%2==1){
			if(max<max()){
				max=max();
				if(playsNow==WHITE){
					score_bl=score_bl-1-flipped;
					score_wh=score_wh+flipped;
					playsNow=BLACK;
				}else{
					score_wh=score_wh-1-flipped;
					score_bl=score_bl+flipped;
					playsNow=WHITE;
				}
				
				maximum=new Node(posX,posY);
				return maximum;
			}

			if(playsNow==WHITE){
				score_bl=score_bl-1-flipped;
				score_wh=score_wh+flipped;
				playsNow=BLACK;
			}else{
				score_wh=score_wh-1-flipped;
				score_bl=score_bl+flipped;
				playsNow=WHITE;
			}
			return maximum;
		}else{
			return null;
		}
		
	}
	
	public int min(){
		return score();
	}
	
	public int max(){
		return score();
	}
	
	public int score(){
		score_bl=0;
		score_wh=0;
		for(int p=0;p<8;p++){
			for(int k=0;k<8;k++){
				if(simulated_states[p][k]==BLACK){
					score_bl++;
				}else if(simulated_states[p][k]==WHITE){
					score_wh++;
				}
			}
		}

		return score_bl-score_wh;
	}

	public void flip(int x, int y, int state, int offset){
		int i=1;
		flipped=0;
		if(x<7){
			if(simulated_states[x+i][y]==state+offset){
				while(simulated_states[x+i][y]==state+offset){
					int k=i;
					if(x+i<7){
						i++;
					}else{
						break;
					}
					if(simulated_states[x+i][y]==state){
						while(simulated_states[x+k][y]!=state){
							simulated_states[x+k][y]=state;
							blocksFlipped.add(new Node(x+k,y));
							flipped++;
							k--;
						}
					}
				}
			}	
		}
		
		
		i=1;
		if(x>0){
			if(simulated_states[x-i][y]==state+offset){
				while(simulated_states[x-i][y]==state+offset){
					int k=i;
					if(x-i>0){
						i++;
					}else{
						break;
					}
					if(simulated_states[x-i][y]==state){
						while(simulated_states[x-k][y]!=state){
							simulated_states[x-k][y]=state;
							blocksFlipped.add(new Node(x-k,y));
							flipped++;
							k--;
						}
					}
				}
			}
		}
		
		
		i=1;
		if(y<7){
			if(simulated_states[x][y+i]==state+offset){
				while(simulated_states[x][y+i]==state+offset){
					int k=i;
					if(y+i<7){
						i++;
					}else{
						break;
					}
					if(simulated_states[x][y+i]==state){
						while(simulated_states[x][y+k]!=state){
							simulated_states[x][y+k]=state;
							blocksFlipped.add(new Node(x,y+k));
							flipped++;
							k--;
						}
					}
				}
			}
		}
		
		
		i=1;
		if(y>0){
			if(simulated_states[x][y-i]==state+offset){
				while(simulated_states[x][y-i]==state+offset){
					int k=i;
					if(y-i>0){
						i++;
					}else{
						break;
					}
					if(simulated_states[x][y-i]==state){
						while(simulated_states[x][y-k]!=state){
							simulated_states[x][y-k]=state;
							blocksFlipped.add(new Node(x,y-k));
							flipped++;
							k--;
						}
					}
				}
			}
		}
		
		
		i=1;
		if(x<7 && y<7){
			if(simulated_states[x+i][y+i]==state+offset){
				while(simulated_states[x+i][y+i]==state+offset){
					int k=i;
					if(x+i<7 && y+i<7){
						i++;
					}else{
						break;
					}
					if(simulated_states[x+i][y+i]==state){
						while(simulated_states[x+k][y+k]!=state){
							simulated_states[x+k][y+k]=state;
							blocksFlipped.add(new Node(x+k,y+k));
							flipped++;
							k--;
						}
					}
				}
			}
		}
		
		
		i=1;
		if(x>0 && y>0){
			if(simulated_states[x-i][y-i]==state+offset){
				while(simulated_states[x-i][y-i]==state+offset){
					int k=i;
					if(x-i>0 && y-i>0){
						i++;
					}else{
						break;
					}
					if(simulated_states[x-i][y-i]==state){
						while(simulated_states[x-k][y-k]!=state){
							simulated_states[x-k][y-k]=state;
							blocksFlipped.add(new Node(x-k,y-k));
							flipped++;
							k--;
						}
					}
				}
			}
		}
		
		
		i=1;
		if(x>0 && y<7){
			if(simulated_states[x-i][y+i]==state+offset){
				while(simulated_states[x-i][y+i]==state+offset){
					int k=i;
					if(x-i>0 && y+i<7){
						i++;
					}else{
						break;
					}
					if(simulated_states[x-i][y+i]==state){
						while(simulated_states[x-k][y+k]!=state){
							simulated_states[x-k][y+k]=state;
							blocksFlipped.add(new Node(x-k,y+k));
							flipped++;
							k--;
						}
					}
				}
			}
		}
		
		
		i=1;
		if(x<7 && y>0){
			if(simulated_states[x+i][y-i]==state+offset){
				while(simulated_states[x+i][y-i]==state+offset){
					int k=i;
					if(x+i<7 && y-i>0){
						i++;
					}else{
						break;
					}
					if(simulated_states[x+i][y-i]==state){
						while(simulated_states[x+k][y-k]!=state){
							simulated_states[x+k][y-k]=state;
							blocksFlipped.add(new Node(x+k,y-k));
							flipped++;
							k--;
						}
					}
				}
			}
		}
	}

}
