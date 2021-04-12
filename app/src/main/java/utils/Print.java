package utils;

public class Print {
    private void printToErr(String prefix, String msg) {
        System.err.println(prefix + ": " + msg);
    }

    private void printToSout(String prefix, String msg) {
        System.out.println(prefix + "| " + msg);
    }

    public void printWarning(String msg) {
        printToErr("{WARNING}", msg);
    }

    public void printError(String msg) {
        printToErr("{ERROR}", msg);
    }

    public void printLog(String msg) {
        printToSout("{LOG}", msg);
    }
}
