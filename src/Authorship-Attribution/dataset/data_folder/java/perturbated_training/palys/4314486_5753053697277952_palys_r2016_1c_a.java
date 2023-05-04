package r2016.round1c;
 
 import java.io.File;
 import java.io.IOException;
 import java.io.PrintStream;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Scanner;
 
 public class R2016_1C_A {
    
    private final static String CASE = "Case #%d: ";
    
    private int t;
    
    public static void main(final String[] args) {
        new R2016_1C_A().solve();
    }
    
    public void solve(final Scanner in, final PrintStream out) throws IOException {
        readNumberCases(in);
        for (int i = 0; i < numberCases(); i++) {
            printCaseLabel(i, out);
            readCase(in);
            solveCase();
            printOutput(out);
            out.println();
        }
    }
 
    private String folder() {
        return this.getClass().getSimpleName() + "_cases";
    }
 
    private String inFolder() {
        return folder() + "/in";
    }
    
    private String outFolder() {
        return folder() + "/out";
    }
    
    private void ensureDirectoryExists(final File f) {
        if (!f.isDirectory()) {
            f.mkdir();
        }
    }
    
    private String filePath(final String file, final String dir) {
        return dir + "/" + file;
    }
    
    private String caseLabel(final int n) {
        return String.format(CASE, n + 1);
    }
    
    private void printCaseLabel(final int n, final PrintStream out) {
        out.print(caseLabel(n));
    }
    
    private void solve() {
        
        final File directory = new File(folder());
        final File inDirectory = new File(inFolder());
        final File outDirectory = new File(outFolder());
        
        ensureDirectoryExists(directory);
        ensureDirectoryExists(outDirectory);
        ensureDirectoryExists(inDirectory);
        
        for (final String in : inDirectory.list()) {
            final String fileNameCore = in.replace(".in", "");
            final String outFileName = fileNameCore + ".out";
            
            final File inFile = new File(filePath(in, inFolder()));
            final File outFile = new File(filePath(outFileName, outFolder()));
            try (final PrintStream ps =  new PrintStream(outFile);
                    final Scanner s = new Scanner(inFile)){
                solve(s, ps);
            } catch (final IOException e) {
                e.printStackTrace();
            } 
        }
    }
    
    private int numberCases() {
        return t;
    }
    
    private void readNumberCases(final Scanner in) {
        t = in.nextInt();
    }
    
    int N;
    int[] P;
    
    List<String> solution;
    
    private void readCase(final Scanner in) {
        N = in.nextInt();
        P = new int[N];
        
        for (int i = 0; i < N; i++) {
            P[i] = in.nextInt();
        }
    }
    
    private void solveCase() {
        solution = new ArrayList<>();
        
        int sum = 0;
        for (int i = 0; i < N; i++) {
            sum += P[i];
        }
        
        while(sum > 2) {
            int m = 0;
            int x = 0;
            int x2 = 0;
            for (int i = 0; i < N; i++) {
                if (P[i] >= m) {
                    m = P[i];
                    x2 = x;
                    x = i;
                }
            }
 
            if (P[x2] * 2 > sum - 1) {
                solution.add("" + (char)('A' + x) + (char)('A' + x2));
                P[x] --;
                P[x2]--;
                sum -= 2;
            } else {
                solution.add("" + (char)('A' + x));
                P[x]--;
                sum--;
            }
        }
        
        String s = "";
        for (int i = 0; i < N; i++) {
            if (P[i] != 0) {
                s += (char)('A' + i);
            }
        }
        
        solution.add(s);
    }
    
    private void printOutput(final PrintStream out) {
        for (int i = 0; i < solution.size() - 1; i++) {
            out.print(solution.get(i) + " ");
        }
        out.print(solution.get(solution.size() - 1));
    }
 }
