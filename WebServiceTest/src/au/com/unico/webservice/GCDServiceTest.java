package au.com.unico.webservice;

import java.util.List;

public class GCDServiceTest {

	public static void main(String[] args) {
		GCDServiceService service = new GCDServiceService();
		GCDService gcd = service.getGCDServicePort();
		System.out.println(gcd.gcd());
		List<Integer> gcds = gcd.gcdList();
		System.out.println(gcds.size());
		for (Integer gcdInt : gcds){
			System.out.println(gcdInt + " " );
		}
		System.out.println(gcd.gcdSum());
	}

}
