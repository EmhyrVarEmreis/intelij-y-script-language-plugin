package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.completion;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiWhiteSpace;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.YScript;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.YScriptTypes;

public class YScriptCompletionContributor extends CompletionContributor {

    public YScriptCompletionContributor() {
        extend(
                CompletionType.BASIC,
                PlatformPatterns.psiElement(YScriptTypes.IDENTIFIER).afterSiblingSkipping(
                        PlatformPatterns.or(
                                PlatformPatterns.psiElement(PsiWhiteSpace.class),
                                PlatformPatterns.psiElement(YScriptTypes.IDENTIFIER),
                                PlatformPatterns.psiElement(YScriptTypes.X_DOUBLE_COLON)
                        ),
                        PlatformPatterns.psiElement(YScriptTypes.KEY_IMPORT)
                ).withLanguage(YScript.INSTANCE),
                new YScriptImportCompletionProvider()
        );
    }

}
