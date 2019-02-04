package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.completion.provider;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.YScript;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class YScriptSimpleCompletionProvider extends CompletionProvider<CompletionParameters> {

    @Override
    protected void addCompletions(@NotNull CompletionParameters completionParameters, @NotNull ProcessingContext processingContext, @NotNull CompletionResultSet completionResultSet) {
        final PsiElement originalPosition = completionParameters.getOriginalPosition();
        final YScriptProgram yScriptProgram = getProgram(originalPosition);
        final List<String> variables = new ArrayList<>();

        addProgramVars(variables, yScriptProgram);
        assPreviousVars(variables, yScriptProgram, originalPosition);

        variables.forEach(variable -> completionResultSet.addElement(LookupElementBuilder.create(variable)));
    }

    private static void addProgramVars(final List<String> variables, final YScriptProgram program) {
        if (Objects.isNull(program)) {
            return;
        }
        final YScriptVars yScriptProgramVars = program.getVars();
        if (Objects.isNull(yScriptProgramVars)) {
            return;
        }
        for (YScriptVar yScriptVar : yScriptProgramVars.getVarList()) {
            variables.add(yScriptVar.getVarDef().getVarName().getText());
        }
    }

    private static void assPreviousVars(final List<String> variables, final YScriptProgram program, final PsiElement originalPosition) {
        if (Objects.isNull(program)) {
            return;
        }
        PsiElement cur = originalPosition;
        while (Objects.nonNull(cur) && !cur.equals(program)) {
            if (cur instanceof YScriptStatement) {
                final YScriptVar var = ((YScriptStatement) cur).getVar();
                if (Objects.nonNull(var)) {
                    variables.add(var.getVarDef().getVarName().getText());
                }
            } else if (cur instanceof YScriptVarName) {
                variables.add(cur.getText());
            }
            final PsiElement prevSibling = cur.getPrevSibling();
            if (Objects.isNull(prevSibling)) {
                cur = cur.getParent();
            } else {
                cur = prevSibling;
            }
        }
    }

    private static YScriptProgram getProgram(final PsiElement element) {
        if (Objects.isNull(element)) {
            return null;
        }
        if (element instanceof YScriptProgram) {
            return (YScriptProgram) element;
        }
        return getProgram(element.getParent());
    }

}
