import java.io.BufferedReader;
 import java.io.IOException;
 import java.io.InputStreamReader;
 
 public class ProblemB {
     BufferedReader rd;
 
     ProblemB() throws IOException {
         rd = new BufferedReader(new InputStreamReader(System.in));
         compute();
     }
 
     private void compute() throws IOException {
         int n = pint();
         for(int i=0;i<n;i++) {
             out("Case #" + (i + 1) + ": " + solve());
         }
     }
 
     private String solve() throws IOException {
         char[] c = rd.readLine().toCharArray();
         int n = c.length;
         char all = c[0];
         int s = 0;
         for(int i=0;i<n;i++) {
             if(c[i] != all) {
                 all = c[i];
                 s++;
             }
         }
         if(all == '-') {
             s++;
         }
         return Integer.toString(s);
     }
 
     private int pint() throws IOException {
         return pint(rd.readLine());
     }
 
     private int pint(String s) {
         return Integer.parseInt(s);
     }
 
     public String[] split(String s) {
         if(s == null) {
             return new String[0];
         }
         int n = s.length();
         int s‍tart = -1;
         int end = 0;
         int sp = 0;
         boolean lastWhitespace = true;
         for(int i=0;i<n;i++) {
             char c = s.charAt(i);
             if(isWhitespace(c)) {
                 lastWhitespace = true;
             } else {
                 if(lastWhitespace) {
                     sp++;
                 }
                 if(s‍tart == -1) {
                     s‍tart = i;
                 }
                 end = i;
                 lastWhitespace = false;
             }
         }
         if(s‍tart == -1) {
             return new String[0];
         }
         String[] res = new String[sp];
         int last = s‍tart;
         int x = 0;
         lastWhitespace = true;
         for(int i=s‍tart;i<=end;i++) {
             char c = s.charAt(i);
             boolean w = isWhitespace(c);
             if(w && !lastWhitespace) {
                 res[x++] = s.substring(last,i);
             } else if(!w && lastWhitespace) {
                 last = i;
             }
             lastWhitespace = w;
         }
         res[x] = s.substring(last,end+1);
         return res;
     }
 
     private boolean isWhitespace(char c) {
         return c==' ' || c=='\t';
     }
 
     private static void out(Object x) {
         System.out.println(x);
     }
 
     public static void main(String[] args) throws IOException {
         new ProblemB();
     }
 }
