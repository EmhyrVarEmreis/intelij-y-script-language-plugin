package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.impl;

import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.YScriptProgram;

public class YScriptPsiImplUtil {

    public static String getPackageName(YScriptProgram element) {
//        ASTNode keyNode = element.getNode().findChildByType(YScriptTypes.PACKAGE);
        return element.getPackage().getText();
    }

}
