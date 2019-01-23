package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.completion;

import com.intellij.codeInsight.completion.*;
import com.intellij.openapi.util.Pair;
import com.intellij.patterns.ElementPattern;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import com.intellij.util.containers.MultiMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class YScriptCustomCompletionContributor extends CompletionContributor {

    private final MultiMap<CompletionType, Pair<ElementPattern<? extends PsiElement>, CompletionProvider<CompletionParameters>>> completionForOriginalMap = new MultiMap<>();

    public final void customExtend(@Nullable CompletionType type, @NotNull ElementPattern<? extends PsiElement> place, @NotNull CompletionProvider<CompletionParameters> provider, boolean isOriginal) {
        if (isOriginal) {
            this.completionForOriginalMap.putValue(type, new Pair<>(place, provider));
        } else {
            this.extend(type, place, provider);
        }
    }

    @Override
    public void fillCompletionVariants(@NotNull CompletionParameters parameters, @NotNull CompletionResultSet result) {
        super.fillCompletionVariants(parameters, result);
        ProcessingContext context;
        for (Pair<ElementPattern<? extends PsiElement>, CompletionProvider<CompletionParameters>> pair : this.completionForOriginalMap.get(parameters.getCompletionType())) {
            context = new ProcessingContext();
            if (pair.first.accepts(parameters.getOriginalPosition(), context)) {
                pair.second.addCompletionVariants(parameters, context, result);
                if (result.isStopped()) {
                    return;
                }
            }
        }
    }

}
