import java.io.OutputStreamWriter;
 import java.io.BufferedWriter;
 import java.util.Locale;
 import java.util.HashMap;
 import java.io.OutputStream;
 import java.io.PrintWriter;
 import java.io.File;
 import java.io.Writer;
 import java.io.FilenameFilter;
 import java.util.Map;
 import java.io.IOException;
 import java.util.InputMismatchException;
 import java.util.Set;
 import java.io.FileOutputStream;
 import java.io.FileInputStream;
 import java.util.NoSuchElementException;
 import java.math.BigInteger;
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
        OutputWriter out = new OutputWriter(outputStream);
        TaskB solver = new TaskB();
        int testCount = Integer.parseInt(in.next());
        for (int i = 1; i <= testCount; i++)
            solver.solve(i, in, out);
        out.close();
    }
 }
 
 class TaskB {
     private static Map<String, Integer> words;
 
     public void solve(int testNumber, InputReader in, OutputWriter out) {
         int count = in.readInt();
         int len = in.readInt();
         int targetLen = in.readInt();
         String keyboard = in.next();
         String target = in.next();
         boolean[] visited = new boolean[26];
         for (int i = 0; i < keyboard.length(); i++) {
             visited[keyboard.charAt(i) - 'A'] = true;
         }
 
         boolean canUse = true;
         for (int i = 0; i < target.length(); i++) {
             canUse = canUse && visited[target.charAt(i) - 'A'];
         }
 
         if (!canUse) {
             out.printLine("Case #" + testNumber + ": 0.0000000");
             return;
         }
 
         words = new HashMap<String, Integer>();
         buildWords(targetLen, new StringBuilder(""), keyboard);
         int maxVal = 0;
         int sum = 0;
         int totalWord = 0;
         for (String word : words.keySet()) {
             int x = words.get(word);
             int total = 0;
             int begin = 0;
             while ((begin = word.indexOf(target, begin)) != -1) {
                 total++;
                 begin++;
             }
             maxVal = Math.max(maxVal, total);
             sum += total * x;
             totalWord += x;
         }
 
         double answer = 1.0 * sum / totalWord;
         answer = 1.0 * maxVal - answer;
         out.printFormat("Case #%d: %.7f\n", testNumber, answer);
     }
 
 
     private void buildWords(int remain, StringBuilder builder, String keyboard) {
         if (remain == 0) {
             if (words.containsKey(builder.toString())) {
                 words.put(builder.toString(), words.get(builder.toString()) + 1);
             } else {
                 words.put(builder.toString(), 1);
             }
             return;
         }
         for (int i = 0; i < keyboard.length(); i++) {
             StringBuilder curBuilder = new StringBuilder(builder);
             curBuilder.append(keyboard.charAt(i));
             buildWords(remain - 1, curBuilder, keyboard);
         }
     }
 }
 
 class InputReader {
 
     private InputStream stream;
    private byte[] buf = new byte[1024];
    private int curChar;
    private int numChars;
    private SpaceCharFilter filter;
 
    public InputReader(InputStream stream) {
        this.stream = stream;
    }
 
    public int read() {
        if (numChars == -1)
            throw new InputMismatchException();
        if (curChar >= numChars) {
            curChar = 0;
            try {
                numChars = stream.read(buf);
            } catch (IOException e) {
                throw new InputMismatchException();
            }
            if (numChars <= 0)
                return -1;
        }
        return buf[curChar++];
    }
 
     public int readInt() {
        int c = read();
        while (isSpaceChar(c))
            c = read();
        int sgn = 1;
        if (c == '-') {
            sgn = -1;
            c = read();
        }
        int res = 0;
        do {
            if (c < '0' || c > '9')
                throw new InputMismatchException();
            res *= 10;
            res += c - '0';
            c = read();
        } while (!isSpaceChar(c));
        return res * sgn;
    }
 
     public String readString() {
        int c = read();
        while (isSpaceChar(c))
            c = read();
        StringBuilder res = new StringBuilder();
        do {
            if (Character.isValidCodePoint(c))
                res.appendCodePoint(c);
            c = read();
        } while (!isSpaceChar(c));
        return res.toString();
    }
 
    public boolean isSpaceChar(int c) {
        if (filter != null)
            return filter.isSpaceChar(c);
        return isWhitespace(c);
    }
 
    public static boolean isWhitespace(int c) {
        return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
    }
 
     public String next() {
        return readString();
    }
 
     public interface SpaceCharFilter {
        public boolean isSpaceChar(int ch);
    }
 }
 
 class OutputWriter {
    private final PrintWriter writer;
 
    public OutputWriter(OutputStream outputStream) {
        writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
    }
 
     public void print(Object...objects) {
        for (int i = 0; i < objects.length; i++) {
            if (i != 0)
                writer.print(' ');
            writer.print(objects[i]);
        }
    }
 
     public void printLine(Object...objects) {
        print(objects);
        writer.println();
    }
 
     public void printFormat(String format, Object...objects) {
        writer.printf(format, objects);
    }
 
    public void close() {
        writer.close();
    }
 
 }
 
