package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.impl;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.util.indexing.IndexingDataKeys;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.*;

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
        final String path = getPathFromContainingFile(containingFile);
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

    private static String getPathFromContainingFile(final PsiFile containingFile) {
        final PsiFile originalFile = containingFile.getOriginalFile();
        VirtualFile virtualFile = originalFile.getVirtualFile();
        if (Objects.isNull(virtualFile)) {
            virtualFile = originalFile.getUserData(IndexingDataKeys.VIRTUAL_FILE);
            if(Objects.isNull(virtualFile)){
                return null;
            }
        }
        return virtualFile.getCanonicalPath();
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
        for (YScriptStructuralItem yScriptStructuralItem : element.getStructuralItemList()) {
            final YScriptImport yScriptElement = yScriptStructuralItem.getImport();
            if (Objects.nonNull(yScriptElement)) {
                list.add(getName(yScriptElement));
            }
        }
        return list;
    }

    public static List<String> getProgramNames(YScriptFileContent element) {
        List<String> list = new ArrayList<>();
        for (YScriptStructuralItem yScriptStructuralItem : element.getStructuralItemList()) {
            final YScriptProgram yScriptElement = yScriptStructuralItem.getProgram();
            if (Objects.nonNull(yScriptElement)) {
                list.add(getName(yScriptElement));
            }
        }
        return list;
    }

}
