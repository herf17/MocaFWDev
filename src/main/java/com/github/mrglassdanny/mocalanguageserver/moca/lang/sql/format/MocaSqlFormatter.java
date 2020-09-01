package com.github.mrglassdanny.mocalanguageserver.moca.lang.sql.format;

import com.github.mrglassdanny.mocalanguageserver.moca.lang.antlr.MocaSqlLexer;
import com.github.mrglassdanny.mocalanguageserver.moca.lang.antlr.MocaSqlParser;

public class MocaSqlFormatter {

    private static org.antlr.codebuff.misc.LangDescriptor mocaSqlLangDescriptor = null;
    private static org.antlr.codebuff.Corpus mocaSqlCorpus = null;

    public static void configureAndTrain() {
        mocaSqlLangDescriptor = new org.antlr.codebuff.misc.LangDescriptor("MocaSql",
                "C:\\Users\\dglass\\OneDrive - Longbow Advantage\\Desktop\\formatting-training\\mocasql", ".*\\.sql",
                MocaSqlLexer.class, MocaSqlParser.class, "moca_sql_script", 4, MocaSqlLexer.LINE_COMMENT);

        try {
            mocaSqlCorpus = org.antlr.codebuff.Tool.trainCorpusForMocaLanguageServer(mocaSqlLangDescriptor);
        } catch (Exception e) {
            // Do nothing..
        }
    }

    public static String format(String src) {

        String dst = null;
        try {
            dst = org.antlr.codebuff.Tool.formatForMocaLanguageServer(MocaSqlFormatter.mocaSqlLangDescriptor, src,
                    MocaSqlFormatter.mocaSqlCorpus);
        } catch (Exception e) {
        }

        return dst;
    }

}