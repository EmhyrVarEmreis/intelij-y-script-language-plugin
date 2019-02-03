package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.completion;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiWhiteSpace;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.YScript;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.YScriptTypes;

public class YScriptCompletionContributor extends YScriptCustomCompletionContributor {


    public YScriptCompletionContributor() {
        customExtend(
                CompletionType.BASIC,
                PlatformPatterns.psiElement(YScriptTypes.IDENTIFIER).afterSiblingSkipping(
                        PlatformPatterns.or(
                                PlatformPatterns.psiElement(PsiWhiteSpace.class),
                                PlatformPatterns.psiElement(YScriptTypes.IDENTIFIER),
                                PlatformPatterns.psiElement(YScriptTypes.X_DOUBLE_COLON)
                        ),
                        PlatformPatterns.psiElement(YScriptTypes.KEY_IMPORT)
                ).withLanguage(YScript.INSTANCE),
                new YScriptImportCompletionProvider(),
                false
        );
        customExtend(
                CompletionType.BASIC,
                PlatformPatterns.psiElement(YScriptTypes.IDENTIFIER).withTreeParent(PlatformPatterns.or(
                        PlatformPatterns.psiElement(YScriptTypes.BODY)
                )).withLanguage(YScript.INSTANCE),
                new YScriptSimpleCompletionProvider(),
                false
        );
        customExtend(
                CompletionType.BASIC,
                PlatformPatterns.psiElement().afterLeafSkipping(PlatformPatterns.psiElement(PsiWhiteSpace.class), PlatformPatterns.or(
                        PlatformPatterns.psiElement(YScriptTypes.KEY_THEN),
                        PlatformPatterns.psiElement(YScriptTypes.KEY_DO)
                )),
                new YScriptSimpleBodyCompletionProvider(),
                false
        );
        customExtend(
                CompletionType.BASIC,
                PlatformPatterns.psiElement().withParent(PlatformPatterns.or(
                        PlatformPatterns.psiElement(YScriptTypes.FILE_CONTENT)
                )).withLanguage(YScript.INSTANCE),
                new YScriptStructuralItemCompletionProvider(),
                true
        );
    }

}
