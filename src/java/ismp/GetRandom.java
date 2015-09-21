package ismp;
import java.util.Random;

public class GetRandom {
public static final String allChar = "xlzxhxjnyj5u0evam0cmc8zkpxvg28ok"; 
    public static String generateString(int length)

    { 
     StringBuffer sb = new StringBuffer(); 
     Random random = new Random(); 
     for (int i = 0; i < length; i++) { 
             sb.append(allChar.charAt(random.nextInt(allChar.length()))); 
             
     } 
      return sb.toString(); 
     }

	public static void main(String[] args) {
	   System.out.println(generateString(32));
      // System.out.println(System.getProperty("java.version"));
	  // sb.delete(0, sb.length());
	}
}
