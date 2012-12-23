package CidrQuizzer;

import java.util.Random;

public class IpAddr
{
	int[]  ip = {0, 0, 0, 0};
	int cidr = 1;
	private String startNet = "";
	private String endNet = "";

	// constructor run when instant of class initialized
	public IpAddr() {
		generate();
		//addr.debug();
		getQuestion();
		calculateRange();
		formatRange();
	}
	
	public String StartOfNetRange() {
		return startNet;
	}

	public String EndOfNetRange() {
		return endNet;
	}
	
	public void generate() {
		Random randGenerator = new Random();
		for (int i = 0; i < 4; ++i) {
			ip[i] = randGenerator.nextInt(256);
		}
		cidr = randGenerator.nextInt(31) + 1; // [1,31]
	}
	
	public void debug() {
		Random randGenerator = new Random();
		ip[0] = 222;
		ip[1] = 222;
		ip[2] = 222;
		ip[3] = 222;
		cidr = randGenerator.nextInt(31) + 1; // [1,31]
	}
	
	public String getQuestion() {
		String s = "IP: " + ip[0] + "." + ip[1] + "." + ip[2] + "." + ip[3] +
				"/" + cidr;
		return s;
	}
	

	
	/* Bytes of the IP to keep based on CIDR Mask */
	public String keepPart()
	{
		String result = "";
		int keepBytes = cidr / 8;
		for (int i = 0; i < keepBytes; ++i) {
			result += ip[i] + ".";
		}
		return result;
	}
	
	/*
	 * Builds ip range using keepPart and last portion of the IP address 
	 * where network mask may not line up with decimal numbers
     */
	public void calculateRange()
	{
		String binEndPart = makeBinary(ip[cidr/8]);
		
		// Pre-Pad w/ Zeroes as necessary
		while (binEndPart.length() < 8) {
			binEndPart = "0" + binEndPart;
		}
		
		String resultStart = "";
		String resultEnd = "";
		int remainder = cidr % 8;
		for (int i = 0; i < remainder; ++i){
			resultStart += binEndPart.charAt(i);
			resultEnd += binEndPart.charAt(i);
		}
		// Bottom of the range gets padded w 0s Top of the range gets padded w 1s
		for (int j = remainder; j < 8; j++){
			resultStart += "0";
			resultEnd += "1";
		}
			
		startNet = keepPart();
		endNet = keepPart();
		startNet += makeDecimal(resultStart);
		endNet += makeDecimal(resultEnd);
		
	}
	
	/* Inverts a provided string */
	public String invertString(String s) {
		int c = s.length() - 1;
		String result = "";
		while (c >= 0) {
			result += s.charAt(c);  // charAt used to extract chars from string
			c--;
		}
		return result;
	}
	
	/* Converts decimal integer to binary number */
	public String makeBinary(int n) {
		String binResult = "";
		while (n > 0) {
			binResult += n % 2;
			n /= 2;	
		}
		return invertString(binResult);
	}
	
	/* Converts binary number to decimal integer */
	public int makeDecimal(String bNum) {
		String s = invertString(bNum);
		int result = 0;
		int len = s.length();
		for (int i = 0; i < len; ++i){
			if (s.charAt(i) == '1') {
				result += Math.pow(2, i);
			}
		}
		return result;
	}
	
	public void formatRange() {
		// find number of '.'s
		int count = 0;
		int len = startNet.length(); // len of one is the same as len of the other
		for (int i = 0; i < len; ++i) {
			if (startNet.charAt(i) == '.')
				++count;
		}
		
		// add the extra fields as necessary
		while (count < 3) {
			startNet += ".x";
			endNet += ".x";
			++count;
		}
		
	}
}
