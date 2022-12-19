package kem.interviews.shai;

/**
 * <a href="https://www.interviewcake.com/question/java/stock-price?course=fc1&section=greedy">Apple stock</a>
 * First, I wanna know how much money I could have made yesterday if I'd been trading Apple stocks all day.
 * <p>
 * So I grabbed Apple's stock prices from yesterday and put them in an array called stockPrices, where:
 * <p>
 * The indices are the time (in minutes) past trade opening time, which was 9:30am local time.
 * The values are the price (in US dollars) of one share of Apple stock at that time.
 * So if the stock cost $500 at 10:30am, that means stockPrices[60] = 500.
 * <p>
 * Write an efficient method that takes stockPrices and returns the best profit I could have made from one purchase and one sale of one share of Apple stock yesterday.
 * <p>
 * For example:
 * <p>
 * int[] stockPrices = new int[] {10, 7, 5, 8, 11, 9};
 * getMaxProfit(stockPrices);
 * // returns 6 (buying for $5 and selling for $11)
 * <p>
 * <a href=mailto:lopotun@gmail.com>EvgenyK</a>
 */
public class AppleStock {
	public int getStockProfit(int[] stockPrices) {
		int min = stockPrices[0];
		int max = stockPrices[0];
		int profit = stockPrices[1] - stockPrices[0];
		for(int i = 1; i < stockPrices.length; i++) {
			if(stockPrices[i] > max) {
				max = stockPrices[i];
				profit = Math.max(max - min, profit);
			}
			if(stockPrices[i] < min) {
				min = stockPrices[i];
				profit = Math.max(min - max, profit);
				max = stockPrices[i];
			}
		}
		return profit;
	}
}