import java.math.BigInteger;

public class BinaryPlus {
	public String addBinary(String a, String b) {
		// Start typing your Java solution below
		// DO NOT write main() function
		BigInteger aValue = getBig(a);
		BigInteger bValue = getBig(b);
		BigInteger value = aValue.add(bValue);
		return getBinary(value);
	}

	public double getValue(String str) {
		if (str.length() > 63)
			return getBig(str).doubleValue();
		else
			return (double) getLong(str);
	}

	public long getLong(String str) {
		return Long.parseLong(str, 2);
	}

	public BigInteger getBig(String str) {
		BigInteger big = new BigInteger(str, 2);
		return big;
	}

	public long power(long n) {
		long result = 1;
		if (n == 0)
			return result;
		else {
			for (int i = 1; i <= n; i++) {
				result *= 2;
			}
		}
		return result;
	}

	public String getBinary(BigInteger n) {
		return n.toString(2);
//		BigInteger bigTwo = new BigInteger("2");
//		BigInteger bigZero = new BigInteger("0");
//		if (n.equals(0))
//			return "0";
//		if (n.equals(1))
//			return "1";
//		StringBuffer result = new StringBuffer();
//		while (n.divide(bigTwo).compareTo(bigZero) > 0) {
//			if (n.mod(bigTwo).equals(0)) {
//				result.append("0");
//			} else {
//				result.append("1");
//			}
//			n = n.divide(bigTwo);
//		}
//		if (n.equals(1))
//			result.append("1");
//		else
//			result.append("0");
//		return result.reverse().toString();
	}
}
