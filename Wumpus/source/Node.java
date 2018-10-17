//Καβαλιέρος Γιώργος (Α.Μ: 3120048) Καραλής Γιώργος (Α.Μ: 3120058) Κεφαλληνός Νίκος (Α.Μ: 3120065)

import java.util.ArrayList;

public class Node {
	private ArrayList<States> states = new ArrayList<States>();
	private int x,y;
	private States s;
	private int cost=0;
	
	Node(int x,int y,States state){
		this.x=x;
		this.y=y;
		isUnknown();
		states.add(state);
	}

	private void isUnknown(){
		if(states.contains(s.U)){
			removeState(s.U);
		}
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public boolean contains(States state){
		if(states.contains(state)){
			return true;
		}else{
			return false;
		}
	}
	
	public void addState(States s){
		isUnknown();
		if(!states.contains(s)){
			states.add(s);
		}
	}

	public void removeState(States s){
		states.remove(s);
	}

	public void cost(){
		cost++;
	}

	public int getCost(){
		return cost;
	}
}
