package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.impl;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.Nullable;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.*;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.util.YScriptUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class YScriptPsiImplUtil {

    public static String getName(YScriptProgram element) {
//        ASTNode keyNode = element.getNode().findChildByType(YScriptTypes.PACKAGE);
        return getName(element.getPackage());
    }

    public static String getName(YScriptFileContent element) {
        final PsiFile containingFile = element.getContainingFile();
        final String path = YScriptUtil.getPathFromContainingFile(containingFile);
        if (Objects.isNull(path)) {
            return "";
        }
        final String dottedPath = path.replaceAll("[\\\\/]", "::").replace(".y", "").replace("file:", "");
        final int idx = dottedPath.indexOf("::lang::");
        if (idx >= 0) {
            return dottedPath.substring(idx + 8);
        } else {
            return dottedPath;
        }
    }

    public static String getName(YScriptCall element) {
        return getName(element.getPackage());
    }

    public static String getName(YScriptPropertyBase element) {
        return getName(element.getVarName());
    }

    public static String getName(YScriptImport element) {
        return getName(element.getPackage());
    }

    public static String getName(YScriptPackage element) {
        return element.getText();
    }

    public static String getName(YScriptVarName element) {
        return element.getText();
    }

    public static List<String> getImportNames(YScriptFileContent element) {
        List<String> list = new ArrayList<>();
        for (YScriptImport yScriptElement : element.getImportList()) {
            list.add(getName(yScriptElement));
        }
        return list;
    }

    public static List<String> getProgramNames(YScriptFileContent element) {
        List<String> list = new ArrayList<>();
        for (YScriptProgram yScriptElement : element.getProgramList()) {
            if (Objects.nonNull(yScriptElement)) {
                list.add(getName(yScriptElement));
            }
        }
        return list;
    }

    public static YScriptFileContent getYScriptFileContent(@Nullable PsiElement element) {
        return getYScriptFileContent(element, false);
    }

    public static YScriptFileContent getYScriptFileContent(@Nullable PsiElement element, final boolean isFromParent) {
        if (Objects.isNull(element)) {
            return null;
        }
        if (element instanceof YScriptFileContent) {
            return (YScriptFileContent) element;
        } else if (!isFromParent && element instanceof PsiFile) {
            for (PsiElement psiFileChild : element.getChildren()) {
                final YScriptFileContent yScriptFileContent = getYScriptFileContent(psiFileChild, false);
                if (Objects.nonNull(yScriptFileContent)) {
                    return yScriptFileContent;
                }
            }
        } else {
            return getYScriptFileContent(element.getParent(), true);
        }
        return null;
    }

}
