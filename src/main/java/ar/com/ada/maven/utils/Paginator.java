package ar.com.ada.maven.utils;

import java.util.ArrayList;
import java.util.List;

public class Paginator {
    private static final String OPTION_FIRTS = "[" + Ansi.CYAN + "I" + Ansi.RESET + "nicio]";
    private static final String OPTION_PREVIOUS = "[" + Ansi.CYAN + "A" + Ansi.RESET + "nterior]";
    private static final String OPTION_NEXT = "[" + Ansi.CYAN + "S" + Ansi.RESET + "iguiente]";
    private static final String OPTION_LAST = "[" + Ansi.CYAN + "U" + Ansi.RESET + "ltimo]";
    private static final String OPTION_EXIT = "[" + Ansi.CYAN + "Q" + Ansi.RESET + " para salir]";

    public static final String EDITH = "[" + Ansi.CYAN + "E" + Ansi.RESET + "ditar]";
    public static final String DELETE = "[" + Ansi.CYAN + "E" + Ansi.RESET + "liminar]";
    public static final String SELECT = "[" + Ansi.CYAN + "E" + Ansi.RESET + "lejir]";

    public static List<String> buildPaginator(int currentPage, int totalPages) {
        List<String> pages = new ArrayList<>();

        pages.add(OPTION_FIRTS);
        pages.add(OPTION_NEXT);

        for (int i = 1; i <= totalPages; i++) {
            if (i == currentPage + 1)
                pages.add(Ansi.YELLOW + "[" + i + "]" + Ansi.RESET);
            else
                pages.add("[" + i + "]");
        }

        pages.add(OPTION_PREVIOUS);
        pages.add(OPTION_LAST);
        pages.add(OPTION_EXIT);

        return pages;
    }
}
