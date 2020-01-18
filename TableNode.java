package _666_;

public class TableNode {

	private int value;
	private int occurence = 0;

	public TableNode(int value) {
		super();
		this.value = value;
	}

	public TableNode(int value, int occurence) {
		super();
		this.value = value;
		this.occurence = occurence;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getOccurence() {
		return occurence;
	}

	public void setOccurence(int occurence) {
		this.occurence = occurence;
	}
	
	public String toString() {
        return "[value=" + value + ", occ=" + occurence + "]";
    }
	
}
