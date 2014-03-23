public class ReverseWords {
	public String reverseWords(String s) {
		if (s == null)
			return s;
		s = s.trim();
		s = s.replaceAll(" {2,}", " ");
		if (s.length() <= 1)
			return s;
		char str[] = s.toCharArray();
		reverseWord(str, 0, s.length()-1);

		int i = 0, j = 0;
		for (; j < str.length; j++) {
			if (str[j] == ' ') {
				reverseWord(str, i, j - 1);
				i = j + 1;
			}
			if (j == str.length -1 ) reverseWord(str, i, j);
		}

		String rev = new String(str);
		return rev.replaceAll(" {2,}", " ");

	}

	public void reverseWord(char[] str, int start, int end) {
		if (str == null || str.length <= 1)
			return;
		int length = end - start;
		while(start<end) {
			char tmp = str[start];
			str[start++] = str[end];
			str[end--] = tmp;
		}
	}

	public static void main(String[] args) {
		ReverseWords rw = new ReverseWords();
		String s = "hello world!";
		String rev = rw.reverseWords(s);
		System.out.println(rev);
	}
}