package exo2;
 
 import java.io.File;
 import java.io.FileInputStream;
 import java.io.FileNotFoundException;
 import java.io.PrintStream;
 import java.util.Arrays;
 import java.util.Collection;
 import java.util.HashMap;
 import java.util.Map;
 import java.util.Map.Entry;
 import java.util.Scanner;
 import java.util.stream.Collectors;
 
 public class Exo extends Base {
 
    public static void main(String[] argv) {
 
        
        
        
 
        
        configSmall(0, false);
        
 
        
        
 
        try {
            int testCaseCount = input().nextInt();
            for (int i = 1; i <= testCaseCount; ++i) {
                info("CASE #" + i + " ###################################################");
 
                
                
                
 
                output().println("Case #" + i + ": " + solveTestCase());
 
                
                
            }
        }
        catch (Exception e) {
            e.printStackTrace(System.err);
        }
        finally {
            done();
        }
    }
 
    
    
    
 
    private static String solveTestCase() {
 
        int N = input().nextInt();
 
        Map<Character, Integer> remainings = new HashMap<>();
        remainings.put('R', input().nextInt());
        input().nextInt(); 
        remainings.put('Y', input().nextInt());
        input().nextInt(); 
        remainings.put('B', input().nextInt());
        input().nextInt(); 
 
        char first = '-';
        char exclude = '-';
 
        StringBuilder sb = new StringBuilder();
 
        for (int i = 0; i < N; ++i) {
            char nextColor = getMostRepresented(remainings, first, exclude);
 
            if (nextColor == '-') {
                return "IMPOSSIBLE";
            }
 
            if (i == 0) {
                first = nextColor;
            }
            else if (i == N - 1 && nextColor == first) {
                return "IMPOSSIBLE";
            }
 
            sb.append(nextColor);
            remainings.put(nextColor, remainings.get(nextColor) - 1);
            exclude = nextColor;
        }
 
        return sb.toString();
    }
 
    private static char getMostRepresented(Map<Character, Integer> remainings, char first, char exclude) {
 
        char result = '-';
        int maxCount = 0;
 
        for (Entry<Character, Integer> entry : remainings.entrySet()) {
            char color = entry.getKey();
            int count = entry.getValue();
 
            if (color == exclude || count == 0) {
                continue;
            }
 
            if (count > maxCount || (count == maxCount && first == color)) {
                result = color;
                maxCount = count;
            }
        }
 
        return result;
    }
 
 }
 
 
 
 
 
 
 
 
 
 class Base {
 
    
    
    
 
    public static String join(int[] values) {
        return join(" ", values);
    }
 
    public static String join(long[] values) {
        return join(" ", values);
    }
 
    public static String join(Object[] values) {
        return join(" ", values);
    }
 
    public static String join(Collection<?> values) {
        return join(" ", values);
    }
 
    public static String join(String delimiter, int[] values) {
        return Arrays.stream(values).mapToObj(value -> Integer.toString(value)).collect(Collectors.joining(delimiter));
    }
 
    public static String join(String delimiter, long[] values) {
        return Arrays.stream(values).mapToObj(value -> Long.toString(value)).collect(Collectors.joining(delimiter));
    }
 
    public static String join(String delimiter, Object[] values) {
        return Arrays.stream(values).map(value -> value.toString()).collect(Collectors.joining(delimiter));
    }
 
    public static String join(String delimiter, Collection<?> values) {
        return values.stream().map(value -> value.toString()).collect(Collectors.joining(delimiter));
    }
 
    
    
    
 
    private interface Config {
 
        void info(String text);
 
        void debug(String text);
 
        Scanner input();
 
        PrintStream output();
 
        void done();
    }
 
    private static Config CURRENT_CONFIG;
 
    protected static void info(String text) {
        CURRENT_CONFIG.info(text);
    }
 
    protected static void debug(String text) {
        CURRENT_CONFIG.debug(text);
    }
 
    protected static Scanner input() {
        return CURRENT_CONFIG.input();
    }
 
    protected static PrintStream output() {
        return CURRENT_CONFIG.output();
    }
 
    protected static void done() {
        CURRENT_CONFIG.done();
    }
 
    private static final String ECLIPSE_PREFIX = "src";
    private static final String BASE_PATH = ECLIPSE_PREFIX + File.separator + Base.class.getPackage().getName()
            + File.separator;
 
    private static abstract class AbstractConfig implements Config {
 
        private Scanner input;
        protected final boolean debugEnabled;
 
        public AbstractConfig(boolean debugEnabled) {
            this.debugEnabled = debugEnabled;
        }
 
        protected abstract String getInputFile();
 
        @Override
        public Scanner input() {
            if (input == null) {
                String source = BASE_PATH + getInputFile();
                try {
                    input = new Scanner(new FileInputStream(source));
                }
                catch (FileNotFoundException e) {
                    throw new IllegalArgumentException("File " + source + " cannot be read.");
                }
            }
            return input;
        }
 
        @Override
        public void done() {
            if (input != null) {
                input.close();
                input = null;
            }
        }
 
        @Override
        public void info(String text) {
            printWithTag("[INFO]", text);
        }
 
        @Override
        public void debug(String text) {
            if (debugEnabled) {
                printWithTag("[DEBUG]", text);
            }
        }
 
        private void printWithTag(String tag, String text) {
            if (text.indexOf("\n") < 0) {
                System.err.println(tag + " " + text);
            }
            else {
                System.err.println(tag + "[BEGIN]");
                System.err.print(text);
                if (!"\n".equals(text.charAt(text.length() - 1))) {
                    System.err.println();
                }
                System.err.println(tag + "[END]");
            }
        }
    }
 
    private static abstract class DevConfig extends AbstractConfig {
 
        public DevConfig(boolean debugEnabled) {
            super(debugEnabled);
        }
 
        @Override
        public PrintStream output() {
            return System.out;
        }
 
        @Override
        public void info(String text) {
            forceFlushOnOutput();
            super.info(text);
        }
 
        @Override
        public void debug(String text) {
            if (debugEnabled) {
                forceFlushOnOutput();
            }
            super.debug(text);
        }
 
        private void forceFlushOnOutput() {
            System.out.flush();
            try {
                Thread.sleep(1);
            }
            catch (InterruptedException e) {}
        }
    }
 
    private static abstract class ProdConfig extends AbstractConfig {
 
        private PrintStream output;
 
        public ProdConfig(boolean debugEnabled) {
            super(debugEnabled);
        }
 
        protected abstract String getOutputFile();
 
        @Override
        public PrintStream output() {
            if (output == null) {
                String target = BASE_PATH + getOutputFile() + ".txt";
                try {
                    output = new PrintStream(target);
                }
                catch (FileNotFoundException e) {
                    throw new IllegalArgumentException("File " + target + " cannot be written.");
                }
            }
            return output;
        }
 
        @Override
        public void done() {
            if (output == null) {
                System.err.println("[ERROR] No output");
            }
            else {
                output.close();
                output = null;
                System.out.println("Done.");
            }
            super.done();
        }
    }
 
    protected static void configTest(final int index, boolean debugEnabled) {
        installConfig(new DevConfig(debugEnabled) {
 
            @Override
            protected String getInputFile() {
                return "test" + index;
            }
        });
    }
 
    protected static void configSmall(final int index, boolean debugEnabled) {
        System.out.println("Processing small problem (attempt " + index + ")...");
        installConfig(new ProdConfig(debugEnabled) {
 
            @Override
            protected String getInputFile() {
                return "small" + index;
            }
 
            @Override
            protected String getOutputFile() {
                return "output-small" + index;
            }
        });
    }
 
    protected static void configLarge(boolean debugEnabled) {
        System.out.println("Processing large problem...");
        installConfig(new ProdConfig(debugEnabled) {
 
            @Override
            protected String getInputFile() {
                return "large";
            }
 
            @Override
            protected String getOutputFile() {
                return "output-large";
            }
        });
    }
 
    private static void installConfig(Config config) {
        if (CURRENT_CONFIG != null) {
            throw new IllegalStateException("I/O config already defined.");
        }
        CURRENT_CONFIG = config;
    }
 }
