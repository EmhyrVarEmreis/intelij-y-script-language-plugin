package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.completion.provider;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorModificationUtil;
import com.intellij.openapi.util.TextRange;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

public class YScriptSimpleBodyCompletionProvider extends CompletionProvider<CompletionParameters> {

    @Override
    protected void addCompletions(@NotNull CompletionParameters completionParameters, @NotNull ProcessingContext processingContext, @NotNull CompletionResultSet completionResultSet) {
        final Editor completionParametersEditor = completionParameters.getEditor();
        final int lineNumber = completionParametersEditor.getCaretModel().getCurrentCaret().getVisualPosition().getLine();
        final Document document = completionParametersEditor.getDocument();
        final String lineText = document.getText(TextRange.create(document.getLineStartOffset(lineNumber), document.getLineEndOffset(lineNumber)));
        completionResultSet.addElement(LookupElementBuilder.create("BEGIN\n").withPresentableText("BEGIN ... END").withInsertHandler((context, item) -> {
            context.setAddCompletionChar(false);
            final String lineTextTrimmed = lineText.trim();
            if (!lineTextTrimmed.isEmpty()) {
                final String lineTextStartingWhitespaces = lineText.substring(0, lineText.indexOf(lineTextTrimmed.charAt(0)));
                EditorModificationUtil.insertStringAtCaret(context.getEditor(), lineTextStartingWhitespaces + lineTextStartingWhitespaces);
                EditorModificationUtil.insertStringAtCaret(context.getEditor(), "\n" + lineTextStartingWhitespaces + "END;", false, false);
            }
            context.commitDocument();
        }));
    }

}
