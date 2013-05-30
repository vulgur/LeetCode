import java.util.Arrays;

public class TwoSum {
	public int[] twoSum(int[] numbers, int target) {
		// Start typing your Java solution below
		// DO NOT write main() function
		int[] origin = numbers.clone();
		Arrays.sort(numbers);
		int len = numbers.length;
		int start = 0;
		int end = len - 1;
		while (start < end) {
			while (numbers[end] > target)
				end--;
			if (numbers[start] + numbers[end] > target) {
				end--;
			} else if (numbers[start] + numbers[end] < target) {
				start++;
			} else {
				if (numbers[start] == numbers[end]) {
					int index[] = findAnother(numbers[start], origin);
					return index;
				} else {
					int index11 = getIndex(numbers[start], numbers);
					int index22 = getIndex(numbers[end], numbers);
					int index1 = getIndex(numbers[start], origin);
					int index2 = getIndex(numbers[end], origin);
					if (index11 < index22 && index1 < index2)
						return new int[] { index1, index2 };
					else
						return new int[] { index2, index1 };
				}
			}
		}
		return null;
	}

	public int[] findAnother(int value, int[] nums) {
		int index1 = -1;
		int index2 = -1;
		for (int i = 0; i < nums.length; i++) {
			if (nums[i] == value)
				if (index1 > 0) {
					index2 = i;
					return new int[] { index1 + 1, index2 + 1 };
				} else {
					index1 = i;
				}
		}
		return null;
	}

	public int getIndex(int value, int[] nums) {
		for (int i = 0; i < nums.length; i++) {
			if (value == nums[i])
				return i + 1;
		}
		return -1;
	}
}