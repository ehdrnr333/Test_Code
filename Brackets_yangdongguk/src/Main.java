import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;

public class Main {
	public static final int LIMIT_NUM = 100000000;

	public static void main(String[] args) {
		for (int i = 1; i <= 10; ++i) {
			// txt file read
			LinkedList<String> inputList = listFromTxt("Brackets_testcase/" + i + ".input.txt");
			LinkedList<String> outputList = listFromTxt("Brackets_testcase/" + i + ".output.txt");

			// print txt file answer
			System.out.println("Case " + i + ".");
			System.out.print("output : ");
			for (int j = 0; j < outputList.size(); ++j) {
				System.out.print(outputList.get(j) + " ");
			}
			System.out.println();

			// solve case
			long start = System.currentTimeMillis();
			LinkedList<Integer> result = new LinkedList<Integer>();
			for (int j = 1; j < inputList.size(); ++j) {
				result.add(bracketsResult(inputList.get(j)));
			}
			long end = System.currentTimeMillis();
			double response_time = (end - start) / 1000.0;

			// print result
			System.out.print("result : ");
			for (int j = 0; j < result.size(); ++j) {
				System.out.print(result.get(j) + " ");
			}
			System.out.println();
			System.out.println("Response Time : " + response_time);
		}
	}

	public static int tsNum(char brarcket) {
		if (brarcket == '(')
			return 1;
		else if (brarcket == '{')
			return 2;
		else if (brarcket == '[')
			return 3;
		else if (brarcket == ')')
			return 4;
		else if (brarcket == '}')
			return 5;
		else if (brarcket == ']')
			return 6;
		return 0;
	}

	public static int bracketsResult(String brackets) {
		LinkedList<LinkedList<Integer>> stairs = new LinkedList<LinkedList<Integer>>();
		int step = 0;
		stairs.add(new LinkedList<Integer>());
		if (tsNum(brackets.charAt(0)) > 3)
			return 0;
		stairs.get(0).add(tsNum(brackets.charAt(0)));

		for (int i = 1; i < brackets.length(); ++i) {
			int b1 = tsNum(brackets.charAt(i - 1));
			int b2 = tsNum(brackets.charAt(i));

			// "X("
			if (b2 < 4) {
				// "(("
				if (b1 < 4) {
					stairs.add(new LinkedList<Integer>());
					++step;
				}
				stairs.get(step).add(b2);
			} else if (b1 > 3) // "))"
			{
				if (step - 1 < 0)
					return 0;
				int lastVal = stairs.get(step - 1).pollLast();
				if (lastVal != (b2 - 3))
					return 0;
				
				int sum = 0;
				for (int k = 0; k < stairs.get(step).size(); ++k) {
					sum = (sum + stairs.get(step).get(k)) % LIMIT_NUM;
				}
				stairs.removeLast();
				--step;
				stairs.get(step).add((lastVal * sum) % LIMIT_NUM);

			} else if (b1 != (b2 - 3)) // "(]"
			{
				return 0;
			}
		}

		int answer = 0;
		if (step == 0) {
			for (int k = 0; k < stairs.get(0).size(); ++k) {
				answer += stairs.get(0).get(k);
			}
		}
		return answer % LIMIT_NUM;
	}

	public static LinkedList<String> listFromTxt(String fileName) {
		LinkedList<String> list = new LinkedList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			while (true) {
				String line = br.readLine();
				if (line == null)
					break;
				list.add(line);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}