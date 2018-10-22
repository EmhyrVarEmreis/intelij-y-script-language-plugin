package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.impl;

import com.intellij.psi.PsiElement;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.YScriptProgram;

public class YScriptPsiImplUtil {

    public static String getPackageName(YScriptProgram element) {
//        ASTNode keyNode = element.getNode().findChildByType(YScriptTypes.PACKAGE);
        return element.getPackage().getText();
    }

    public static void consumeToken(PsiElement element) {
        System.out.println(element.getClass());
    }

}
