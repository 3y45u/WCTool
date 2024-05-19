import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class WCTool {

    public static void main(String[] args) {
        if (args.length == 0 || (args.length == 1 && args[0].equals("-h"))) {
            printUsage();
            return;
        }

        String command = args[0];
        String filePath = "";
        if (args.length > 1) {
            filePath = args[args.length - 1];
        }

        if (!filePath.isEmpty()) {
            File myFile = new File(filePath);
            if (!myFile.exists() || !myFile.isFile()) {
                System.out.println("File not found: " + filePath);
                return;
            }

            // Process the file based on the command or display all properties
            if (command.equals("-p")) {
                printFileContent(myFile);
            } else if (command.equals("-a")) {
                printAllProperties(myFile);
            } else if (isValidCommand(command)) {
                String result = processFile(command, myFile);
                System.out.println(result);
            } else {
                System.out.println("Invalid command. Use -c for file size, -l for line count, -w for word count, -m for character count, -p to print file content, -a to display all properties, or -h for help.");
            }
        } else {
            // Handle standard input here
            System.out.println("Standard input not supported yet.");
        }
    }

    private static boolean isValidCommand(String command) {
        return command.equals("-c") || command.equals("-l") || command.equals("-w") || command.equals("-m");
    }

    private static String processFile(String command, File file) {
        switch (command) {
            case "-c":
                return "File Size: " + printFileSize(file) + " bytes";
            case "-l":
                return "Line Count: " + printLineCount(file);
            case "-w":
                return "Word Count: " + printWordCount(file);
            case "-m":
                return "Character Count: " + printCharacterCount(file);
            default:
                return "";
        }
    }

    private static void printUsage() {
        System.out.println("Usage: WCTool <command> [<filePath>]\n" +
                           "Commands:\n" +
                           "  -c\tPrint file size\n" +
                           "  -l\tPrint line count\n" +
                           "  -w\tPrint word count\n" +
                           "  -m\tPrint character count\n" +
                           "  -p\tPrint file content\n" +
                           "  -a\tDisplay all properties\n" +
                           "  -h\tPrint this help message");
    }

    private static void printAllProperties(File file) {
        System.out.println("File Size: " + printFileSize(file) + " bytes");
        System.out.println("Line Count: " + printLineCount(file));
        System.out.println("Word Count: " + printWordCount(file));
        System.out.println("Character Count: " + printCharacterCount(file));
        System.out.println("Content:");
        printFileContent(file);
    }

    private static void printFileContent(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static long printFileSize(File file) {
        return file.length();
    }

    private static int printLineCount(File file) {
        int numberOfLines = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while (br.readLine() != null) {
                numberOfLines++;
            }
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
            e.printStackTrace();
        }
        return numberOfLines;
    }

    private static int printWordCount(File file) {
        int numberOfWords = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split("\\s+");
                for (String word : words) {
                    if (!word.isEmpty()) {
                        numberOfWords++;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
            e.printStackTrace();
        }
        return numberOfWords;
    }

    private static int printCharacterCount(File file) {
        int numberOfCharacters = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            int c;
            while ((c = br.read()) != -1) {
                numberOfCharacters++;
            }
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
            e.printStackTrace();
        }
        return numberOfCharacters;
    }
}
