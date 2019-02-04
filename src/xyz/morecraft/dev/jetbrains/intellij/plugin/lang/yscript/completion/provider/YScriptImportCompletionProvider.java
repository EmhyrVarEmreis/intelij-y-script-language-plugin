package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.completion.provider;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.completion.YScriptCompletionProviderHelper;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index.YScriptFileContentFBIdx;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.YScriptTypes;

import java.util.Collection;
import java.util.Objects;

import static xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.completion.YScriptCompletionProviderHelper.STRUCTURAL_ITEM_HANDLER;

public class YScriptImportCompletionProvider extends CompletionProvider<CompletionParameters> {

    @Override
    protected void addCompletions(@NotNull CompletionParameters completionParameters, @NotNull ProcessingContext processingContext, @NotNull CompletionResultSet completionResultSet) {
        final Collection<String> allKeys = YScriptFileContentFBIdx.getInstance().getAllKeys(completionParameters.getPosition().getProject());
        final String text = YScriptCompletionProviderHelper.getTextUntil(completionParameters.getPosition(), YScriptTypes.KEY_IMPORT);
        final int textLength = text.length();
        if (Objects.nonNull(allKeys)) {
            for (String key : allKeys) {
                if (key.startsWith(text)) {
                    completionResultSet.addElement(LookupElementBuilder.create(key.substring(textLength)).withLookupString(text).withInsertHandler(STRUCTURAL_ITEM_HANDLER));
                }
            }
        }
    }

}
