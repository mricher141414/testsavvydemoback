package cgi;

public class Application {
 
	public static void main(String[] args) throws Exception {
				try {
					new CalcSalary();
				} catch (Exception e) {
					e.printStackTrace();
				}

	}

}
