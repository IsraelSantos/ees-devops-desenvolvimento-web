
package br.uece.eesdevop.util;

public class Util {

	public static String urlLimpa(String url) {
		String st[] = url.split("/");
		String res = "";
		for (int i = 0; i<st.length-1; i++) {
			res+=st[i]+"/";
		}
		return res;
	}
}
