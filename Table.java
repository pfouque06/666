package _666_;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javaTools.ColorText;

public class Table {
	TableNode[][] tableNode = new TableNode[6][6];
	static int[][] tableValue = { { 28, 4, 3, 31, 35, 10 }, { 36, 18, 21, 24, 11, 1 }, { 7, 23, 12, 17, 22, 30 },
			{ 8, 13, 26, 19, 16, 29 }, { 5, 20, 15, 14, 25, 32 }, { 27, 33, 34, 6, 2, 9 } };
	int[][] tableOccurence = new int[6][6]; // init array to zero
	int betMax = Main.betMax;
	LinkedHashSet<Integer> bets = new LinkedHashSet<Integer>();
	LinkedList<Integer> store = new LinkedList<Integer>();

	int[][] getTable() {
		return tableValue;

	}

	// Value Management
	int[] getPosition(int pValue) {
		int pos[] = { 0, 0 };
		for (int i = 0; i < 6; i++)
			for (int j = 0; j < 6; j++)
				if (tableValue[i][j] == pValue) {
					pos[0] = i;
					pos[1] = j;
					break;
				}
		return pos;

	}

	public int getRandomRoulette(int pDelay) {
		// System.out.print("get Random Spin...");
		int roulette = new Random().nextInt(37);
		while (pDelay-- > 0) {
			try {
				TimeUnit.MILLISECONDS.sleep(100); // or { TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				/* empty */ } // or e.printStackTrace();
			// System.out.print(".");
		}
		// System.out.println(" ["+roulette+"]");
		return roulette;

	}

	// Occurrence management
	public void resetTable() {
		for (int i = 0; i < 6; i++)
			for (int j = 0; j < 6; j++)
				tableOccurence[i][j] = 0;
		resetBets();
		resetStore();
	}

	public int getOccurence(int pValue) {
		int pos[] = getPosition(pValue);
		return tableOccurence[pos[0]][pos[1]];
	}

	public void setOccurence(int pValue, int pOcc) {
		int pos[] = getPosition(pValue);
		tableOccurence[pos[0]][pos[1]] = pOcc;
	}

	public void addOccurence(int pValue) {
		int occ = getOccurence(pValue);
		if (occ < 0)
			occ = 0;
		setOccurence(pValue, ++occ);
		// add pvalue to store
		store.add(pValue);
		// remove pvalue to linkedhashset of bets
		bets.remove(pValue);
		// System.out.println(">> updated bets lhs: "+bets);
	}

	public void remOccurence(int pValue) {
		int occ = getOccurence(pValue);
		setOccurence(pValue, --occ);
		// System.out.print(" -->> " + occ);
		// add pvalue to linkedhashset of bets
		if (occ < 0)
			bets.add(pValue);
		// System.out.print(" >> updated bets lhs: "+bets);
	}

	// Store management
	public String getStore(int pSize) {
		String str = "";
		if (!store.isEmpty()) {
			str = "[";
			int max = store.size() - 1;
			// str+=store.get(max);
			int min = (max > --pSize ? max - pSize : 0);
			str += store.get(min);
			// for(int i=max-1; i >= min ; i--) str+=", "+store.get(i);
			for (int i = min + 1; i <= max; i++)
				str += ", " + store.get(i);
			str += "]";
		}
		return str;
	}

	public String getStore() {
		return this.getStore(store.size());
	}

	public boolean isStoreEmpty() {
		return store.isEmpty();
	}

	public int getStoreSize() {
		return store.size();
	}

	public boolean storeContains(int pValue) {
		return store.contains(pValue);
	}

	void resetStore() {
		store.clear();
	}

	public boolean reduceStore() {
		int oldestValue;

		while (isFull()) {
			// System.out.println(">> store = " + store + " store.size = " + store.size());
			if (store.isEmpty())
				return false;
			else {
				// System.out.print(">> get and remove oldest Value");
				oldestValue = store.removeFirst();
				// System.out.println(">> oldestValue = " + oldestValue + " remOccurence(" +
				// oldestValue + ")");
				remOccurence(oldestValue);
			}
			// System.out.println(">> table.isFull = " + this.isFull());
		}

		// System.out.println(">> return true");
		return true;
	}

	// Bets management
	void resetBets() {
		bets.clear();
	}

	void resetTableBets() {
		for (int i = 0; i < 6; i++)
			for (int j = 0; j < 6; j++)
				if (tableOccurence[i][j] < 0)
					tableOccurence[i][j] = 0;
		return;
	}

	boolean isFull() {
		boolean full = true;
		for (int i = 0; i < 6; i++)
			for (int j = 0; j < 6; j++)
				if (tableOccurence[i][j] <= 0)
					full = false;
		return full;

	}

	public boolean isBetsEmpty() {
		return bets.isEmpty();
	}

	public int getBetsSize() {
		return bets.size();
	}

	public boolean betsContains(int pValue) {
		return bets.contains(pValue);
	}

	public String getBets() {
		return bets.toString();
	}

	void setBets() {

		// System.out.println(">>> setBets()");
		// Reset previous Bets
		resetTableBets();
		// Lines check
		int i = 0, j = 0;
		while (i < 6) {
			j = 0;
			int jSum = 0, jValue = 0;
			while (j < 6) {
				// check occurrence not null
				if (tableOccurence[i][j] > 0)
					jSum++;
				// else store Value not used
				else
					jValue = tableValue[i][j];
				// System.out.println(j+":"+jSum+":"+jValue);
				j++;
			}
			// suggest jValue as bet if single in line
			if (jSum == 5)
				remOccurence(jValue);
			i++;
		}
		// Columns check
		j = 0;
		while (j < 6) {
			i = 0;
			int iSum = 0, iValue = 0;
			while (i < 6) {
				// check occurrence not null
				if (tableOccurence[i][j] > 0)
					iSum++;
				// else store Value not used
				else
					iValue = tableValue[i][j];
				// System.out.println(i+":"+iSum+":"+iValue);
				i++;
			}
			// suggest jValue as bet if single in line
			if (iSum == 5)
				remOccurence(iValue);
			j++;
		}
		// Diagonals check
		i = 0;
		{
			int iSum = 0, iValue = 0;
			while (i < 6) {
				// check occurrence not null
				if (tableOccurence[i][i] > 0)
					iSum++;
				// else store Value not used
				else
					iValue = tableValue[i][i];
				// System.out.println(i+":"+iSum+":"+iValue);
				i++;
			}
			// suggest jValue as bet if single in line
			if (iSum == 5)
				remOccurence(iValue);
		}
		i = 0;
		j = 5;
		{
			int iSum = 0, iValue = 0;
			while (i < 6) {
				// check occurrence not null
				if (tableOccurence[i][j] > 0)
					iSum++;
				// else store Value not used
				else
					iValue = tableValue[i][j];
				// System.out.println(i+":"+iSum+":"+iValue);
				i++;
				j--;
			}
			// suggest jValue as bet if single in line
			if (iSum == 5)
				remOccurence(iValue);
		}

		// reduce bets if betMax is set
		reduceBets();

	}

	void reduceBets() {

		// System.out.println(">>> reduceBets()");
		if (bets.isEmpty())
			return;
		ArrayList<TableNode> nodesArray = new ArrayList<>();

		// create ArrayList of TableNode inherited from bets LinkedHashedSet
		for (int value : bets)
			nodesArray.add(new TableNode(value, -getOccurence(value)));

		// sort by value then sort by occurrence
		System.out.println("unsorted : " + nodesArray);
		//nodesArray.sort(Comparator.comparing(TableNode::getValue).thenComparing(TableNode::getOccurence).reversed());
		nodesArray.sort(Comparator.comparing(TableNode::getValue));
		nodesArray.sort(Comparator.comparing(TableNode::getOccurence).reversed());
		System.out.println("sorted by value then occurrence : " + nodesArray);

		// remove extra bets if betMax is set
		if (betMax != 0) {
			while (nodesArray.size() > betMax)
				nodesArray.remove(betMax);
			if (nodesArray.size() != bets.size())
				System.out.println("shorted ("+betMax+" bets): " + nodesArray);
		}
		
		// sort gain by value
		//nodesArray.sort(Comparator.comparing(TableNode -> TableNode.getValue()));
		nodesArray.sort(Comparator.comparing(TableNode::getValue));
		System.out.println("sorted by value : " + nodesArray);

		// reset and rebuild bets
		resetBets();
		for (TableNode node : nodesArray)
			bets.add(node.getValue());

		return;
	}

	// display
	String getTable(boolean pToBet, boolean pColorMode) {
		String str = "", value = "", onValue = "", offValue = "", betValue = "", occ_ = "";
		int i = 0, occ;
		while (i < 6) {
			int j = 0;
			while (j < 6) {
				// value = format.format(table_value[i][j]);
				value = String.format("%02d", tableValue[i][j]);

				onValue = (pColorMode ? ColorText.RESET + value + ColorText.RESET : value);
				onValue = " " + onValue + " ";

				offValue = (pColorMode ? ColorText.RED + value + ColorText.RESET : "--");
				offValue = " " + offValue + " ";

				betValue = (pColorMode ? ColorText.GREEN_BOLD + value + ColorText.RESET : value);
				betValue = "[" + betValue + "]";

				occ = tableOccurence[i][j];
				occ_ = String.format("% 2d", occ);

				if (occ > 0)
					str += (pToBet ? offValue : onValue);
				else if (occ < 0)
					str += (pToBet ? betValue : betValue);
				else {
					str += (pToBet ? onValue : " -- ");
					occ_ = "   ";
				}

				str += occ_ + (j == 5 ? "\n" : "\t");
				j++;
			}
			i++;
		}
		return str;
	}

	public String toString(boolean pColor) {
		return getTable(false, pColor); // bet OFF
	}

	public String toString() {
		return getTable(false, true); // bet OFF / colorMode ON
	}

	public String betToString(boolean pColor) {
		return getTable(true, pColor); // bet ON
	}

	public String betToString() {
		return getTable(true, true); // bet ON / colorMode ON
	}
}
