package es.ufv.utils;

public class Utils {
    public String defaultValues(String original) {
        if (original.contains(".") || original.isEmpty() || original.contains("\"")) {
            return "0";
        }
        return original;
    }
}
