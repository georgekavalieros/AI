//���������� ������� (�.�: 3120048) ������� ������� (�.�: 3120058) ���������� ����� (�.�: 3120065)

public enum States {
	U(0), //unknown
	W(1), //wumpus
	A(2), //agent
	G(3), //gold
	P(4), //pit
	B(5), //breeze
	S(6), //stench
	C(7), //checked
	D(8); //danger
	
	int value;
	
	private States(int value) {
		this.value =  value;
	}
}
