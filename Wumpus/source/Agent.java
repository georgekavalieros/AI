//Καβαλιέρος Γιώργος (Α.Μ: 3120048) Καραλής Γιώργος (Α.Μ: 3120058) Κεφαλληνός Νίκος (Α.Μ: 3120065)

import java.util.ArrayList;

public class Agent {
	private Node knownMoves[][];
	private ArrayList<Node> validMoves = new ArrayList<Node>();
	private ArrayList<Node> visited = new ArrayList<Node>();
	private ArrayList<Node> notVisitedChecked = new ArrayList<Node>();
	private States s;
	private final int size;
	private boolean wumpusIsDead=false;
	
	Agent(int agX,int agY,int size){
		this.size=size;
		knownMoves=new Node[size][size];

		for(int i=0; i<size; i++){
			for(int j=0; j<size; j++){
				if(i == agX && j == agY){
					knownMoves[i][j] = new Node(i,j,s.A);
				}else{
					knownMoves[i][j] = new Node(i,j,s.U);
				}
				
			}
		}
		
		for(int i=0; i<size; i++){
			for(int j=0; j<size; j++){
				if(knownMoves[i][j].contains(s.A)){
					knownMoves[i][j].cost();
					knownMoves[i][j].addState(s.C);
					visited.add(knownMoves[i][j]);

					if(i<size-1){
						knownMoves[i+1][j].addState(s.C);
						validMoves.add(knownMoves[i+1][j]);
						notVisitedChecked.add(knownMoves[i+1][j]);
					}

					if(i>0){
						knownMoves[i-1][j].addState(s.C);
						validMoves.add(knownMoves[i-1][j]);
						notVisitedChecked.add(knownMoves[i-1][j]);
					}

					if(j<size-1){
						knownMoves[i][j+1].addState(s.C);
						validMoves.add(knownMoves[i][j+1]);
						notVisitedChecked.add(knownMoves[i][j+1]);
					}

					if(j>0){
						knownMoves[i][j-1].addState(s.C);
						validMoves.add(knownMoves[i][j-1]);
						notVisitedChecked.add(knownMoves[i][j-1]);
					}
				}
			}
		}

	}
	
	public Node move(){
		Node min;
		min=validMoves.get(0);
		for(int i=0; i<size; i++){
			for(int j=0; j<size; j++){
				if(knownMoves[i][j].contains(s.A)){
					for(Node n:validMoves){
						if(!visited.contains(n)){
							n.addState(s.A);
							visited.add(n);
							notVisitedChecked.remove(n);
							n.cost();
							knownMoves[i][j].removeState(s.A);
							return n;
						}else{
							if(n.getCost()<min.getCost()){
								min=n;
							}
						}
					}
					min.addState(s.A);
					min.cost();
					knownMoves[i][j].removeState(s.A);
					return min;
				}
			}
		}

		return null;
	}
	
	public void fireArrow(Node agentHere){
		if(agentHere.getX()<size-1) {
			if (knownMoves[agentHere.getX() + 1][agentHere.getY()].contains(s.W)) {
				System.out.println("Fired arrow down and killed Wumpus ! A cry is heard in the cave !");
				wumpusIsDead = true;
				knownMoves[agentHere.getX() + 1][agentHere.getY()].removeState(s.W);
				knownMoves[agentHere.getX() + 1][agentHere.getY()].addState(s.C);
			}
		}

		if(agentHere.getX()>0) {
			if (knownMoves[agentHere.getX() - 1][agentHere.getY()].contains(s.W)) {
				System.out.println("Fired arrow up and killed Wumpus ! A cry is heard in the cave !");
				wumpusIsDead = true;
				knownMoves[agentHere.getX() - 1][agentHere.getY()].removeState(s.W);
				knownMoves[agentHere.getX() - 1][agentHere.getY()].addState(s.C);
			}
		}

		if(agentHere.getY()<size-1) {
			if (knownMoves[agentHere.getX()][agentHere.getY() + 1].contains(s.W)) {
				System.out.println("Fired arrow right and killed Wumpus ! A cry is heard in the cave !");
				wumpusIsDead = true;
				knownMoves[agentHere.getX()][agentHere.getY() + 1].removeState(s.W);
				knownMoves[agentHere.getX()][agentHere.getY() + 1].addState(s.C);
			}
		}

		if(agentHere.getY()>0){
			if(knownMoves[agentHere.getX()][agentHere.getY()-1].contains(s.W)){
				System.out.println("Fired arrow left and killed Wumpus ! A cry is heard in the cave !");
				wumpusIsDead=true;
				knownMoves[agentHere.getX()][agentHere.getY()-1].removeState(s.W);
				knownMoves[agentHere.getX()][agentHere.getY()-1].addState(s.C);
			}
		}
	}

	public boolean isWumpusDead(){
		return wumpusIsDead;
	}

	public void sense(Node agentHere){
		if(agentHere.contains(s.B)) {

			if (agentHere.getX() < size - 1) {
				if (knownMoves[agentHere.getX() + 1][agentHere.getY()].contains(s.U)) {
					knownMoves[agentHere.getX() + 1][agentHere.getY()].addState(s.D);
				}
			}

			if (agentHere.getX() > 0) {
				if (knownMoves[agentHere.getX() - 1][agentHere.getY()].contains(s.U)) {
					knownMoves[agentHere.getX() - 1][agentHere.getY()].addState(s.D);
				}
			}

			if (agentHere.getY() < size - 1) {
				if (knownMoves[agentHere.getX()][agentHere.getY() + 1].contains(s.U)) {
					knownMoves[agentHere.getX()][agentHere.getY() + 1].addState(s.D);
				}
			}

			if (agentHere.getY() > 0) {
				if (knownMoves[agentHere.getX()][agentHere.getY() - 1].contains(s.U)) {
					knownMoves[agentHere.getX()][agentHere.getY() - 1].addState(s.D);
				}
			}

		}else if(agentHere.contains(s.S)){
			if (agentHere.getX() < size - 1) {
				if (knownMoves[agentHere.getX() + 1][agentHere.getY()].contains(s.U) && !isWumpusDead()) {
					knownMoves[agentHere.getX() + 1][agentHere.getY()].addState(s.D);
				}else if(isWumpusDead()){
					if(knownMoves[agentHere.getX() + 1][agentHere.getY()].contains(s.D)){
						knownMoves[agentHere.getX() + 1][agentHere.getY()].removeState(s.D);
					}
					knownMoves[agentHere.getX() + 1][agentHere.getY()].addState(s.C);
				}
			}

			if (agentHere.getX() > 0) {
				if (knownMoves[agentHere.getX() - 1][agentHere.getY()].contains(s.U)&& !isWumpusDead()) {
					knownMoves[agentHere.getX() - 1][agentHere.getY()].addState(s.D);
				}else if(isWumpusDead()){
					if(knownMoves[agentHere.getX() - 1][agentHere.getY()].contains(s.D)){
						knownMoves[agentHere.getX() - 1][agentHere.getY()].removeState(s.D);
					}
					knownMoves[agentHere.getX() - 1][agentHere.getY()].addState(s.C);
				}
			}

			if (agentHere.getY() < size - 1) {
				if (knownMoves[agentHere.getX()][agentHere.getY() + 1].contains(s.U)&& !isWumpusDead()) {
					knownMoves[agentHere.getX()][agentHere.getY() + 1].addState(s.D);
				}else if(isWumpusDead()){
					if(knownMoves[agentHere.getX()][agentHere.getY() + 1].contains(s.D)){
						knownMoves[agentHere.getX()][agentHere.getY() + 1].removeState(s.D);
					}
					knownMoves[agentHere.getX()][agentHere.getY() + 1].addState(s.C);
				}
			}

			if (agentHere.getY() > 0) {
				if (knownMoves[agentHere.getX()][agentHere.getY() - 1].contains(s.U)&& !isWumpusDead()) {
					knownMoves[agentHere.getX()][agentHere.getY() - 1].addState(s.D);
				}else if(isWumpusDead()){
					if(knownMoves[agentHere.getX()][agentHere.getY() - 1].contains(s.D)){
						knownMoves[agentHere.getX()][agentHere.getY() - 1].removeState(s.D);
					}
					knownMoves[agentHere.getX()][agentHere.getY() - 1].addState(s.C);
				}
			}
		}else{

			if (agentHere.getX() < size-1) {
				if(knownMoves[agentHere.getX() + 1][agentHere.getY()].contains(s.D)){
					knownMoves[agentHere.getX() + 1][agentHere.getY()].removeState(s.D);
				}
				knownMoves[agentHere.getX() + 1][agentHere.getY()].addState(s.C);
			}

			if (agentHere.getX() > 0) {
				if(knownMoves[agentHere.getX() - 1][agentHere.getY()].contains(s.D)){
					knownMoves[agentHere.getX() - 1][agentHere.getY()].removeState(s.D);
				}
				knownMoves[agentHere.getX() - 1][agentHere.getY()].addState(s.C);
			}

			if (agentHere.getY() < size-1) {
				if(knownMoves[agentHere.getX()][agentHere.getY() + 1].contains(s.D)){
					knownMoves[agentHere.getX()][agentHere.getY() + 1].removeState(s.D);
				}
				knownMoves[agentHere.getX()][agentHere.getY() + 1].addState(s.C);
			}

			if (agentHere.getY() > 0) {
				if(knownMoves[agentHere.getX()][agentHere.getY() - 1].contains(s.D)){
					knownMoves[agentHere.getX()][agentHere.getY() - 1].removeState(s.D);
				}
				knownMoves[agentHere.getX()][agentHere.getY() - 1].addState(s.C);
			}

		}

		if(agentHere.contains(s.B) || agentHere.contains(s.S)){
			int counter=0;

			if (agentHere.getX() < size-1) {
				if(knownMoves[agentHere.getX() + 1][agentHere.getY()].contains(s.D) || knownMoves[agentHere.getX() + 1][agentHere.getY()].contains(s.P) || knownMoves[agentHere.getX() + 1][agentHere.getY()].contains(s.W)) {
					counter++;
				}
			}

			if (agentHere.getX() > 0) {
				if(knownMoves[agentHere.getX() - 1][agentHere.getY()].contains(s.D) || knownMoves[agentHere.getX() - 1][agentHere.getY()].contains(s.P) || knownMoves[agentHere.getX() - 1][agentHere.getY()].contains(s.W)) {
					counter++;
				}
			}

			if (agentHere.getY() < size-1) {
				if(knownMoves[agentHere.getX()][agentHere.getY() + 1].contains(s.D) || knownMoves[agentHere.getX()][agentHere.getY() + 1].contains(s.P) || knownMoves[agentHere.getX()][agentHere.getY() + 1].contains(s.W)) {
					counter++;
				}
			}

			if (agentHere.getY() > 0) {
				if(knownMoves[agentHere.getX()][agentHere.getY() - 1].contains(s.D) || knownMoves[agentHere.getX()][agentHere.getY() - 1].contains(s.P) || knownMoves[agentHere.getX()][agentHere.getY() - 1].contains(s.W)) {
					counter++;
				}
			}

			if(counter==1){

				if (agentHere.getX() < size-1) {
					if(agentHere.contains(s.B)){
						if(knownMoves[agentHere.getX() + 1][agentHere.getY()].contains(s.D)) {
							knownMoves[agentHere.getX() + 1][agentHere.getY()].removeState(s.D);
							knownMoves[agentHere.getX() + 1][agentHere.getY()].addState(s.P);
						}
					}else if(agentHere.contains(s.S)){
						if(knownMoves[agentHere.getX() + 1][agentHere.getY()].contains(s.D) && !isWumpusDead()) {
							knownMoves[agentHere.getX() + 1][agentHere.getY()].removeState(s.D);
							knownMoves[agentHere.getX() + 1][agentHere.getY()].addState(s.W);
						}
					}
				}

				if (agentHere.getX() > 0) {
					if(agentHere.contains(s.B)){
						if(knownMoves[agentHere.getX() - 1][agentHere.getY()].contains(s.D)) {
							knownMoves[agentHere.getX() - 1][agentHere.getY()].removeState(s.D);
							knownMoves[agentHere.getX() - 1][agentHere.getY()].addState(s.P);
						}
					}else if(agentHere.contains(s.S)){
						if(knownMoves[agentHere.getX() - 1][agentHere.getY()].contains(s.D)&& !isWumpusDead()) {
							knownMoves[agentHere.getX() - 1][agentHere.getY()].removeState(s.D);
							knownMoves[agentHere.getX() - 1][agentHere.getY()].addState(s.W);
						}
					}
				}

				if (agentHere.getY() < size-1) {
					if(agentHere.contains(s.B)){
						if(knownMoves[agentHere.getX() ][agentHere.getY()+ 1].contains(s.D)) {
							knownMoves[agentHere.getX() ][agentHere.getY()+ 1].removeState(s.D);
							knownMoves[agentHere.getX()][agentHere.getY()+ 1].addState(s.P);
						}
					}else if(agentHere.contains(s.S)){
						if(knownMoves[agentHere.getX() ][agentHere.getY()+ 1].contains(s.D)&& !isWumpusDead()) {
							knownMoves[agentHere.getX() ][agentHere.getY()+ 1].removeState(s.D);
							knownMoves[agentHere.getX() ][agentHere.getY()+ 1].addState(s.W);
						}
					}
				}

				if (agentHere.getY() > 0) {
					if(agentHere.contains(s.B)){
						if(knownMoves[agentHere.getX() ][agentHere.getY()- 1].contains(s.D)) {
							knownMoves[agentHere.getX() ][agentHere.getY()- 1].removeState(s.D);
							knownMoves[agentHere.getX()][agentHere.getY()- 1].addState(s.P);
						}
					}else if(agentHere.contains(s.S)){
						if(knownMoves[agentHere.getX() ][agentHere.getY()- 1].contains(s.D)&& !isWumpusDead()) {
							knownMoves[agentHere.getX() ][agentHere.getY()- 1].removeState(s.D);
							knownMoves[agentHere.getX() ][agentHere.getY()- 1].addState(s.W);
						}
					}
				}
			}
		}
		availableMoves();
	}

	public void availableMoves(){
		validMoves.clear();

		for(int i=0; i<size; i++){
			for(int j=0; j<size; j++){
				if(knownMoves[i][j].contains(s.A)){

					if(i<size-1){
						if(knownMoves[i+1][j].contains(s.C)){
							validMoves.add(knownMoves[i+1][j]);
							if(!visited.contains(knownMoves[i+1][j]) && !notVisitedChecked.contains(knownMoves[i+1][j])){
								notVisitedChecked.add(knownMoves[i+1][j]);
							}
						}
					}

					if(i>0){
						if(knownMoves[i-1][j].contains(s.C)){
							validMoves.add(knownMoves[i-1][j]);
							if(!visited.contains(knownMoves[i-1][j]) && !notVisitedChecked.contains(knownMoves[i-1][j])){
								notVisitedChecked.add(knownMoves[i-1][j]);
							}
						}
					}

					if(j<size-1){
						if(knownMoves[i][j+1].contains(s.C)){
							validMoves.add(knownMoves[i][j+1]);
							if(!visited.contains(knownMoves[i][j+1]) && !notVisitedChecked.contains(knownMoves[i][j+1])){
								notVisitedChecked.add(knownMoves[i][j+1]);
							}
						}
					}

					if(j>0){
						if(knownMoves[i][j-1].contains(s.C)){
							validMoves.add(knownMoves[i][j-1]);
							if(!visited.contains(knownMoves[i][j-1]) && !notVisitedChecked.contains(knownMoves[i][j-1])){
								notVisitedChecked.add(knownMoves[i][j-1]);
							}
						}
					}
				}
			}
		}
	}

	public boolean foundGold(Node agentHere){
		if(agentHere.contains(s.G)){
			knownMoves[agentHere.getX()][agentHere.getY()].addState(s.G);
			return true;
		}else{
			return false;
		}
	}

	public boolean unsolvable(){
		if(notVisitedChecked.isEmpty()){
			return true;
		}else{
			return false;
		}
	}
}
