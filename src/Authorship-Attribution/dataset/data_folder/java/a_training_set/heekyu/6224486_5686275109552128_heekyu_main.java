import java.io.InputStreamReader;
 import java.io.IOException;
 import java.util.Locale;
 import java.io.BufferedReader;
 import java.io.OutputStream;
 import java.io.FileOutputStream;
 import java.io.PrintWriter;
 import java.io.FileInputStream;
 import java.io.File;
 import java.util.StringTokenizer;
 import java.io.FilenameFilter;
 import java.io.InputStream;
 
 
 public class Main {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        InputStream inputStream;
        try {
            final String regex = "B-(small|large).*[.]in";
            File directory = new File(".");
            File[] candidates = directory.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return name.matches(regex);
                }
            });
            File toRun = null;
            for (File candidate : candidates) {
                if (toRun == null || candidate.lastModified() > toRun.lastModified())
                    toRun = candidate;
            }
            inputStream = new FileInputStream(toRun);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        OutputStream outputStream;
        try {
            outputStream = new FileOutputStream("b.out");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        TaskB solver = new TaskB();
        int testCount = Integer.parseInt(in.next());
        for (int i = 1; i <= testCount; i++)
            solver.solve(i, in, out);
        out.close();
    }
 }
 
 class TaskB {
     public void solve(int testNumber, InputReader in, PrintWriter out) {
         out.print("Case #" + testNumber + ": ");
         int D = in.nextInt();
         int[] cakes = new int[D];
         int minutes = 0;
         for (int i = 0; i < D; i++) {
             cakes[i] = in.nextInt();
             minutes = Math.max(minutes, cakes[i]);
         }
         int ans = minutes;
         for (int time = 2; time < minutes; time++) {
             int special = 0;
             for (int i = 0; i < D; i++) {
                 special += (cakes[i] + time-1)/time - 1;
             }
             ans = Math.min(ans, time + special);
         }
         out.println(ans);
     }
 }
 
 class InputReader {
     public BufferedReader reader;
     public StringTokenizer tokenizer;
 
     public InputReader(InputStream stream) {
         reader = new BufferedReader(new InputStreamReader(stream));
         tokenizer = null;
     }
 
     public String next() {
         while (tokenizer == null || !tokenizer.hasMoreTokens()) {
             try {
                 tokenizer = new StringTokenizer(reader.readLine());
             } catch (IOException e) {
                 throw new RuntimeException(e);
             }
         }
         return tokenizer.nextToken();
     }
 
     public int nextInt() {
         return Integer.parseInt(next());
     }
 
 }
 
