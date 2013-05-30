public class LongestSubstring {
	public int lengthOfLongestSubstring(String s) {
		// Start typing your Java solution below
		// DO NOT write main() function
		if (s == null || s.equals("")) {
			return 0;
		}

		int[] pos = new int[256];
		int[] lengths = new int[s.length()];
		int maxLength = 0;
		int start = 0;
		int i = 0;

		clear(pos);
		while (i < s.length()) {
			char curr = s.charAt(i);
			System.out.println("curr=" + curr);
			if (pos[curr] == -1) {
				pos[curr] = i;
			} else {
				if (start <= pos[curr]) {
					System.out.println("start changed:" + start+"->"+(pos[curr] + 1));
					start = pos[curr] + 1;
				}
				System.out.println("start=" + start + " " + s.charAt(start));
				pos[curr] = i;
			}
			addLengths(lengths, start, i);
			System.out.println(" pos[curr]=" + pos[curr]
					+ " lengths[curr]=" + lengths[i]);
			i++;
		}
		for (int len : lengths) {
			if (len > maxLength) {
				maxLength = len;
			}
		}
		return maxLength;
	}

	void addLengths(int[] len, int start, int curr) {
		len[curr] = curr - start + 1;
	}

	void clear(int[] tab) {
		for (int i = 0; i < tab.length; i++) {
			tab[i] = -1;
		}
	}
}