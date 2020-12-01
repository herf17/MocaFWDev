package com.github.mrglassdanny.mocalanguageserver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.github.mrglassdanny.mocalanguageserver.services.highlight.CompilationServiceSemanticHighlightingManager;
import com.github.mrglassdanny.mocalanguageserver.services.MocaServices;
import com.github.mrglassdanny.mocalanguageserver.services.command.ExecuteCommandProvider;

import org.eclipse.lsp4j.CompletionOptions;
import org.eclipse.lsp4j.DocumentOnTypeFormattingOptions;
import org.eclipse.lsp4j.ExecuteCommandOptions;
import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.InitializeResult;
import org.eclipse.lsp4j.SemanticHighlightingServerCapabilities;
import org.eclipse.lsp4j.ServerCapabilities;
import org.eclipse.lsp4j.SignatureHelpOptions;
import org.eclipse.lsp4j.TextDocumentSyncKind;
import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.lsp4j.services.LanguageClientAware;
import org.eclipse.lsp4j.services.LanguageServer;
import org.eclipse.lsp4j.services.TextDocumentService;
import org.eclipse.lsp4j.services.WorkspaceService;

public class MocaLanguageServer implements LanguageServer, LanguageClientAware {

    // Callers will likely crash if this is null. That being said, we want callers
    // to crash if value is null -- it is important that the moca language server's
    // ACTIVATE command is called on startup and that a global storage path is
    // passed in.
    public static String globalStoragePath = null;

    // Single instance of moca language server options given to us from client.
    public static MocaLanguageServerOptions mocaLanguageServerOptions = new MocaLanguageServerOptions();

    public static void main(String[] args) {
        MocaLanguageServer server = new MocaLanguageServer();
        Launcher<LanguageClient> launcher = Launcher.createLauncher(server, LanguageClient.class, System.in,
                System.out);

        server.connect(launcher.getRemoteProxy());
        launcher.startListening();
    }

    private MocaServices services;

    public MocaLanguageServer() {
        this.services = new MocaServices();
    }

    @Override
    public CompletableFuture<InitializeResult> initialize(InitializeParams params) {
        ServerCapabilities serverCapabilities = new ServerCapabilities();

        serverCapabilities.setTextDocumentSync(TextDocumentSyncKind.Full);

        CompletionOptions completionOptions = new CompletionOptions(false, Arrays.asList("."));
        serverCapabilities.setCompletionProvider(completionOptions);

        serverCapabilities.setDefinitionProvider(false);

        serverCapabilities.setHoverProvider(true);

        SignatureHelpOptions signatureHelpOptions = new SignatureHelpOptions();
        signatureHelpOptions.setTriggerCharacters(Arrays.asList("(", ","));
        serverCapabilities.setSignatureHelpProvider(signatureHelpOptions);

        serverCapabilities.setDocumentFormattingProvider(true);
        serverCapabilities.setDocumentRangeFormattingProvider(true);
        DocumentOnTypeFormattingOptions documentOnTypeFormattingProvider = new DocumentOnTypeFormattingOptions();
        documentOnTypeFormattingProvider.setFirstTriggerCharacter(".");
        List<String> additionalOnTypeFormattingTriggerChars = new ArrayList<>();
        {
            additionalOnTypeFormattingTriggerChars.add(";");
            additionalOnTypeFormattingTriggerChars.add("&");
            additionalOnTypeFormattingTriggerChars.add("(");
            additionalOnTypeFormattingTriggerChars.add(")");
            additionalOnTypeFormattingTriggerChars.add("a");
            additionalOnTypeFormattingTriggerChars.add("b");
            additionalOnTypeFormattingTriggerChars.add("c");
            additionalOnTypeFormattingTriggerChars.add("d");
            additionalOnTypeFormattingTriggerChars.add("e");
            additionalOnTypeFormattingTriggerChars.add("f");
            additionalOnTypeFormattingTriggerChars.add("g");
            additionalOnTypeFormattingTriggerChars.add("h");
            additionalOnTypeFormattingTriggerChars.add("i");
            additionalOnTypeFormattingTriggerChars.add("j");
            additionalOnTypeFormattingTriggerChars.add("k");
            additionalOnTypeFormattingTriggerChars.add("l");
            additionalOnTypeFormattingTriggerChars.add("m");
            additionalOnTypeFormattingTriggerChars.add("n");
            additionalOnTypeFormattingTriggerChars.add("o");
            additionalOnTypeFormattingTriggerChars.add("p");
            additionalOnTypeFormattingTriggerChars.add("q");
            additionalOnTypeFormattingTriggerChars.add("r");
            additionalOnTypeFormattingTriggerChars.add("s");
            additionalOnTypeFormattingTriggerChars.add("t");
            additionalOnTypeFormattingTriggerChars.add("u");
            additionalOnTypeFormattingTriggerChars.add("v");
            additionalOnTypeFormattingTriggerChars.add("w");
            additionalOnTypeFormattingTriggerChars.add("x");
            additionalOnTypeFormattingTriggerChars.add("y");
            additionalOnTypeFormattingTriggerChars.add("z");
            additionalOnTypeFormattingTriggerChars.add("A");
            additionalOnTypeFormattingTriggerChars.add("B");
            additionalOnTypeFormattingTriggerChars.add("C");
            additionalOnTypeFormattingTriggerChars.add("D");
            additionalOnTypeFormattingTriggerChars.add("E");
            additionalOnTypeFormattingTriggerChars.add("F");
            additionalOnTypeFormattingTriggerChars.add("G");
            additionalOnTypeFormattingTriggerChars.add("H");
            additionalOnTypeFormattingTriggerChars.add("I");
            additionalOnTypeFormattingTriggerChars.add("J");
            additionalOnTypeFormattingTriggerChars.add("K");
            additionalOnTypeFormattingTriggerChars.add("L");
            additionalOnTypeFormattingTriggerChars.add("M");
            additionalOnTypeFormattingTriggerChars.add("N");
            additionalOnTypeFormattingTriggerChars.add("O");
            additionalOnTypeFormattingTriggerChars.add("P");
            additionalOnTypeFormattingTriggerChars.add("Q");
            additionalOnTypeFormattingTriggerChars.add("R");
            additionalOnTypeFormattingTriggerChars.add("S");
            additionalOnTypeFormattingTriggerChars.add("T");
            additionalOnTypeFormattingTriggerChars.add("U");
            additionalOnTypeFormattingTriggerChars.add("V");
            additionalOnTypeFormattingTriggerChars.add("W");
            additionalOnTypeFormattingTriggerChars.add("X");
            additionalOnTypeFormattingTriggerChars.add("Y");
            additionalOnTypeFormattingTriggerChars.add("Z");
            additionalOnTypeFormattingTriggerChars.add("0");
            additionalOnTypeFormattingTriggerChars.add("1");
            additionalOnTypeFormattingTriggerChars.add("2");
            additionalOnTypeFormattingTriggerChars.add("3");
            additionalOnTypeFormattingTriggerChars.add("4");
            additionalOnTypeFormattingTriggerChars.add("5");
            additionalOnTypeFormattingTriggerChars.add("6");
            additionalOnTypeFormattingTriggerChars.add("7");
            additionalOnTypeFormattingTriggerChars.add("8");
            additionalOnTypeFormattingTriggerChars.add("9");
        }
        documentOnTypeFormattingProvider.setMoreTriggerCharacter(additionalOnTypeFormattingTriggerChars);
        serverCapabilities.setDocumentOnTypeFormattingProvider(documentOnTypeFormattingProvider);

        ExecuteCommandOptions executeCommandOptions = new ExecuteCommandOptions();
        executeCommandOptions.setCommands(ExecuteCommandProvider.mocaLanguageServerCommands);
        serverCapabilities.setExecuteCommandProvider(executeCommandOptions);

        CompilationServiceSemanticHighlightingManager.setTextmateScopes();
        serverCapabilities.setSemanticHighlighting(new SemanticHighlightingServerCapabilities(
                CompilationServiceSemanticHighlightingManager.textmateScopes));

        serverCapabilities.setDefinitionProvider(true);

        InitializeResult initializeResult = new InitializeResult(serverCapabilities);
        return CompletableFuture.completedFuture(initializeResult);
    }

    @Override
    public CompletableFuture<Object> shutdown() {
        return CompletableFuture.completedFuture(new Object());
    }

    @Override
    public void exit() {
        System.exit(0);
    }

    @Override
    public TextDocumentService getTextDocumentService() {
        return this.services;
    }

    @Override
    public WorkspaceService getWorkspaceService() {
        return this.services;
    }

    @Override
    public void connect(LanguageClient client) {
        this.services.connect(client);
    }
}