package com.github.pospolitanv.rock_paper_scissors.game.command_line;

import org.fusesource.jansi.Ansi;

/**
 * Utility class for more friendly usage of ansi
 */
public class CommandLineUtils {

    public static String fgColor(Ansi.Color color, String text) {
        return Ansi.ansi().fg(color).a(text).reset().toString();
    }

    public static String bgAndFgColor(Ansi.Color bgColor, Ansi.Color fgColor, String text) {
        return Ansi.ansi().bgBright(bgColor).fg(fgColor).a(text).reset().toString();
    }

    public static String fgColorAndTextStyle(Ansi.Color color, Ansi.Attribute attr, String text) {
        return Ansi.ansi().fg(color).a(attr).a(text).reset().toString();
    }
}
