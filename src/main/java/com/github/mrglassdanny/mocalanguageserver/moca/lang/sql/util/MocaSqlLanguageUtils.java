package com.github.mrglassdanny.mocalanguageserver.moca.lang.sql.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.mrglassdanny.mocalanguageserver.moca.lang.sql.ast.MocaSqlSyntaxError;
import com.github.mrglassdanny.mocalanguageserver.util.lsp.Positions;

import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;

public class MocaSqlLanguageUtils {

    public static final Pattern SQL_RANGE_START_WORD_PATTERN = Pattern
            .compile("(?i)\\b(select|update|delete|insert|create|alter|drop)\\b");

    public static Position createMocaPosition(int sqlLine, int sqlColumn, Range sqlScriptRange) {
        int lspLine = sqlLine;
        if (lspLine > 0) {
            lspLine--;
        }
        int lspColumn = sqlColumn;
        if (lspColumn > 0) {
            lspColumn--;
        }

        // +1 to column due to antlr lexer starting the column an index too early.
        // We are doing something similar in MocaTokenUtils class.
        lspColumn++;

        // If the line is 0, we need to keep the char offset in mind.
        if (lspLine == 0) {
            return new Position(lspLine + sqlScriptRange.getStart().getLine(),
                    lspColumn + sqlScriptRange.getStart().getCharacter() + 1); // +1 for '['.
        } else {
            return new Position(lspLine + sqlScriptRange.getStart().getLine(), lspColumn);
        }
    }

    public static Position createMocaPosition(String sqlScript, int sqlOffset, Range sqlScriptRange) {

        Position sqlPos = Positions.getPosition(sqlScript, sqlOffset + 1); // +1 for '['.

        // This probably wont happen..
        if (sqlPos == null) {
            return new Position(0, 0);
        }

        int lspLine = sqlPos.getLine(); // No need to subtract from line in this case.

        int lspColumn = sqlPos.getCharacter();
        if (lspColumn > 0) {
            lspColumn--;
        }

        // +1 to column due to antlr lexer starting the column an index too early.
        // We are doing something similar in MocaTokenUtils class.
        lspColumn++;

        if (lspLine == 0) {
            return new Position(lspLine + sqlScriptRange.getStart().getLine(),
                    lspColumn + sqlScriptRange.getStart().getCharacter());
        } else {
            return new Position(lspLine + sqlScriptRange.getStart().getLine(), lspColumn);
        }

    }

    public static Range syntaxExceptionToRange(MocaSqlSyntaxError err, Range sqlScriptRange) {

        return new Range(createMocaPosition(err.line, err.charPositionInLine, sqlScriptRange),
                createMocaPosition(err.line, err.charPositionInLine, sqlScriptRange));
    }

    public static boolean isMocaTokenValueSqlScript(String mocaTokenValue) {
        Matcher sqlStartWordMatcher = MocaSqlLanguageUtils.SQL_RANGE_START_WORD_PATTERN.matcher(mocaTokenValue);
        if (sqlStartWordMatcher.find()) {
            return true;
        } else {
            return false;
        }

    }

}