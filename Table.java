package _666_;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.text.NumberFormat;
import java.text.DecimalFormat;


public class Table {
	static int[][] table_value = { { 28, 4, 3, 31, 35, 10 }, { 36, 18, 21, 24, 11, 1 }, { 7, 23, 12, 17, 22, 30 },
			{ 8, 13, 26, 19, 16, 29 }, { 5, 20, 15, 14, 25, 32 }, { 27, 33, 34, 6, 2, 9 } };
	int[][] table_occurence = { { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 } };
	LinkedHashSet<Integer> bets = new LinkedHashSet<Integer>();
	LinkedList<Integer> store = new LinkedList<Integer>();

	// constructor
	Table() {
	};

	// Value Managmement
	int[] getPosition(int pValue) {
		int pos[] = { 0, 0 }, i = 0;
		while (i < 6) {
			int j = 0;
			while (j < 6) {
				if (table_value[i][j] == pValue) {
					pos[0] = i;
					pos[1] = j;
				}
				j++;
			}
			i++;
		}
		return pos;

	}

	// Occurence management
	public void resetTable() {
		int i = 0;
		while (i < 6) {
			int j = 0;
			while (j < 6) {
				table_occurence[i][j] = 0;
				j++;
			}
			i++;
		}
	}

	public void addOccurence(int pValue) {
		int pos[] = { 0, 0 };
		pos = getPosition(pValue);
		if (table_occurence[pos[0]][pos[1]]++ < 0)
			table_occurence[pos[0]][pos[1]] = 0;
		table_occurence[pos[0]][pos[1]]++;
		// add pvalue to store
		store.add(pValue);
		// remove pvalue to linkedhashset of bets
		bets.remove(pValue);
		// System.out.println("updated bets lhs: "+bets);
	}

	public void remOccurence(int pValue) {
		int pos[] = { 0, 0 }, occ;
		pos = getPosition(pValue);
		occ = table_occurence[pos[0]][pos[1]];
		// occ = (occ == 0 ? 0 : occ--);
		occ--;
		table_occurence[pos[0]][pos[1]] = occ;
		// add pvalue to linkedhashset of bets
		bets.add(pValue);
		// System.out.println("updated bets lhs: "+bets);
	}

	public int getOccurence(int pValue) {
		int i = 0, occ = 0;
		while (i < 6) {
			int j = 0;
			while (j < 6) {
				if (table_value[i][j] == pValue) {
					occ = table_occurence[i][j];
					// System.out.println(i+":"+j+"="+occ);
				}
				j++;
			}
			i++;
		}
		return occ;
	}

	// Store management
	public String getStore(int pSize) {
		String str="";
		if (! store.isEmpty()) {
			str="[";
			int max = store.size()-1;
			//str+=store.get(max);
			int min = (max > --pSize ? max-pSize : 0);
			str+=store.get(min);
			//for(int i=max-1; i >= min ; i--) str+=", "+store.get(i);
			for(int i=min+1; i <= max ; i++) str+=", "+store.get(i);
			str+="]";
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


	// Bets management
	boolean isFull() {
		boolean full = true;
		int i = 0;
		while (i < 6) {
			int j = 0;
			while (j < 6) {
				if (table_occurence[i][j] < 1)
					full = false;
				j++;
			}
			i++;
		}
		return full;

	}

	boolean resetBets() {
		boolean full = false;
		int i = 0;
		while (i < 6) {
			int j = 0;
			while (j < 6) {
				if (table_occurence[i][j] < 0) {
					table_occurence[i][j] = 0;
					full = true;
				}
				j++;
			}
			i++;
		}
		return full;
	}

	void setBets() {
		// Reset previous Bets
		resetBets();
		// Lines check
		int i = 0, j = 0;
		while (i < 6) {
			j = 0;
			int jSum = 0, jValue = 0;
			while (j < 6) {
				// check occurence not null
				if (table_occurence[i][j] > 0)
					jSum++;
				// else store Value not used
				else
					jValue = table_value[i][j];
				// System.out.println(j+":"+jSum+":"+jValue);
				j++;
			}
			// suggest jValue as bet if single in line
			if (jSum == 5) {
				remOccurence(jValue);
				// limit to 3 Bet suggestion
				// if (betId < 3 ) bet[betId++]=jValue;
			}
			i++;
		}
		// Colomns check
		j = 0;
		while (j < 6) {
			i = 0;
			int iSum = 0, iValue = 0;
			while (i < 6) {
				// check occurence not null
				if (table_occurence[i][j] > 0)
					iSum++;
				// else store Value not used
				else
					iValue = table_value[i][j];
				// System.out.println(i+":"+iSum+":"+iValue);
				i++;
			}
			// suggest jValue as bet if single in line
			if (iSum == 5) {
				remOccurence(iValue);
				// limit to 3 Bet suggestion
				// if (betId < 3 ) bet[betId++]=iValue;
			}
			j++;
		}
		// Diagonales check
		i = 0;
		{
			int iSum = 0, iValue = 0;
			while (i < 6) {
				// check occurence not null
				if (table_occurence[i][i] > 0)
					iSum++;
				// else store Value not used
				else
					iValue = table_value[i][i];
				// System.out.println(i+":"+iSum+":"+iValue);
				i++;
			}
			// suggest jValue as bet if single in line
			if (iSum == 5) {
				remOccurence(iValue);
				// limit to 3 Bet suggestion
				// if (betId < 3 ) bet[betId++]=iValue;
			}
		}
		i = 0;
		j = 5;
		{
			int iSum = 0, iValue = 0;
			while (i < 6) {
				// check occurence not null
				if (table_occurence[i][j] > 0)
					iSum++;
				// else store Value not used
				else
					iValue = table_value[i][j];
				// System.out.println(i+":"+iSum+":"+iValue);
				i++;
				j--;
			}
			// suggest jValue as bet if single in line
			if (iSum == 5) {
				remOccurence(iValue);
				// limit to 3 Bet suggestion
				// if (betId < 3 ) bet[betId++]=iValue;
			}
		}
	}

	public String getBets() {
		return bets.toString();
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

	// display
	String getTable(boolean pToBet, boolean pColorMode) {
		String str = "", value = "", onValue = "", offValue = "", betValue="";
		NumberFormat format = new DecimalFormat("00");
		int i = 0, occ;
		while (i < 6) {
			int j = 0;
			while (j < 6) {
				value = format.format(table_value[i][j]);
				
				onValue = ( pColorMode ? colorText.BLACK + value + colorText.RESET : value );
				onValue = " " + onValue + " ";
				
				offValue = ( pColorMode ? colorText.RED + value + colorText.RESET : "--" );
				offValue = " " + offValue + " ";
				
				betValue = ( pColorMode ? colorText.GREEN_BOLD + value + colorText.RESET : value );
				betValue = "[" + betValue + "]";

				occ = table_occurence[i][j];

				if (occ > 0)
					str += (pToBet ? offValue : onValue);
				else if (occ < 0)
					str += (pToBet ? betValue : betValue);
				else
					str += (pToBet ? onValue : " -- ");
				
				str += (j == 5 ? "\n" : "\t");
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
