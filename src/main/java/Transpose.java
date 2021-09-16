import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Transpose {
    private static final byte SIZE_IS_NOT_SET = -1;


    public static List<List<String>> transpose(List<List<String>> inputMatrix, int cellSize, boolean truncate, boolean right) {
        List<List<String>> out = new ArrayList<>();
        int maxLineSize = 0;
        if (cellSize == SIZE_IS_NOT_SET && (truncate || right)) cellSize = 10;
        for (List<String> row : inputMatrix)
            if (row.size() > maxLineSize) {
                maxLineSize = row.size();
            }
        for (int column = 0; column < maxLineSize; column++) {
            List<String> newLine = new ArrayList<>();
            for (List<String> row : inputMatrix) {
                if (row.size() > column) {
                    if (row.get(column).length() >= cellSize) {
                        if (truncate) newLine.add(row.get(column).substring(0, cellSize));
                        else newLine.add(row.get(column));
                    } else if (right) newLine.add(" ".repeat(cellSize - row.get(column).length()) + row.get(column));
                    else newLine.add(row.get(column) + " ".repeat(cellSize - row.get(column).length()));
                }
            }
            out.add(newLine);
        }
        return out;
    }

    public static List<List<String>> getText(Scanner sc) {
        List<List<String>> out = new ArrayList<>();
        String line;
        while (sc.hasNextLine()) {
            ArrayList<String> a = new ArrayList<>();
            line = sc.nextLine();
            for (String s : line.split("\\s+")) {
                if (!s.isEmpty()) a.add(s);
            }
            out.add(a);
        }
        sc.close();
        return out;
    }

    public static String print(List<List<String>> in) {
        StringBuilder out = new StringBuilder();
        for (List<String> x : in) {
            for (int i = 0; i < x.size() - 1; i++) {
                out.append(x.get(i)).append(' ');
            }
            out.append(x.get(x.size() - 1));
            out.append('\n');
        }
        return out.toString();
    }

    public static void main(String[] args) {
        int a = SIZE_IS_NOT_SET;
        int numberOfFlags = 0;
        boolean trunc = false, right = false;
        String out = "";
        List<List<String>> transposed = new ArrayList<>();

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-a" -> {
                    i++;
                    a = Integer.parseInt(args[i]);
                    if (a < 0) {
                        System.out.println("The word size cannot be negative!");
                        System.exit(1);
                    }
                    numberOfFlags++;
                }
                case "-t" -> {
                    trunc = true;
                    numberOfFlags++;
                }
                case "-r" -> {
                    right = true;
                    numberOfFlags++;
                }
                case "-o" -> {
                    if (i < args.length - 1) {
                        i++;
                        out = args[i];
                        numberOfFlags += 2;
                    } else {
                        System.out.println("Empty output filename!");
                        System.exit(1);
                    }
                }
                default -> {
                    if (!new File(args[i]).isFile()) {
                        System.out.println("Wrong argument!");
                        System.exit(1);
                    }
                }
            }
        }

        if (args.length != numberOfFlags && new File(args[args.length - 1]).isFile()) {
            try {
                transposed = transpose(getText(new Scanner(new File(args[args.length - 1]))), a, trunc, right);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.exit(2);
            }
        } else if (args.length != numberOfFlags && !(new File(args[args.length - 1]).isFile())) {
            System.out.println("Wrong input filename!");
            System.exit(1);
        } else {
            transposed = transpose(getText(new Scanner(System.in)), a, trunc, right);
        }

        if (args.length > 0 && !out.equals("")) {
            try {
                FileWriter fw = new FileWriter(out);
                fw.write(print(transposed));
                fw.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.exit(2);
            }
        } else {
            System.out.print(print(transposed));
        }
    }
}