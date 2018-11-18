package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.impl;

import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.*;

public class YScriptPsiImplUtil {

    public static String getName(YScriptProgram element) {
//        ASTNode keyNode = element.getNode().findChildByType(YScriptTypes.PACKAGE);
        return getName(element.getPackage());
    }

    public static String getName(YScriptCall element) {
        return getName(element.getPackage());
    }

    public static String getName(YScriptPropertyBase element) {
        return getName(element.getVarName());
    }

    public static String getName(YScriptPackage element) {
        return element.getText();
    }

    public static String getName(YScriptVarName element) {
        return element.getText();
    }

}
