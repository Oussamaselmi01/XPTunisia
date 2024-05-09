package Controller.Activite;

import java.util.Locale;

public class LanguageSettings {

    private static Locale currentLocale = Locale.getDefault();

    public static Locale getCurrentLocale() {
        return currentLocale;
    }

    public static void setCurrentLocale(Locale locale) {
        currentLocale = locale;
    }
}
