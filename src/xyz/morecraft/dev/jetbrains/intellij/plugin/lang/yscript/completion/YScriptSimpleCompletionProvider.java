package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.completion;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index.YScriptFileContentFBIdx;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.YScriptProgram;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.YScriptTypes;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.YScriptVar;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.YScriptVars;

import java.util.Collection;
import java.util.Objects;

import static xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.completion.YScriptCompletionProviderHelper.STRUCTURAL_ITEM_HANDLER;

public class YScriptSimpleCompletionProvider extends CompletionProvider<CompletionParameters> {

    @Override
    protected void addCompletions(@NotNull CompletionParameters completionParameters, @NotNull ProcessingContext processingContext, @NotNull CompletionResultSet completionResultSet) {
        System.out.println("YScriptSimpleCompletionProvider");
        final PsiElement position = completionParameters.getPosition();
        final YScriptProgram yScriptProgram = getProgram(position);
        if(Objects.nonNull(yScriptProgram)){
            final YScriptVars yScriptProgramVars = yScriptProgram.getVars();
            if(Objects.nonNull(yScriptProgramVars)){
                for (YScriptVar yScriptVar : yScriptProgramVars.getVarList()) {
                    completionResultSet.addElement(LookupElementBuilder.create(yScriptVar.getVarDef().getVarName().getText()));
                }
            }
        }
    }

    private static YScriptProgram getProgram(final PsiElement element){
        if(Objects.isNull(element)){
            return null;
        }
        if(element instanceof YScriptProgram){
            return (YScriptProgram)element;
        }
        return getProgram(element.getParent());
    }

}
