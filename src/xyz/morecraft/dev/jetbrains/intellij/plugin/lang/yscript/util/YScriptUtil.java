package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.util;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.indexing.FileBasedIndex;
import com.intellij.util.indexing.IndexingDataKeys;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.YScriptFileType;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index.util.ProgramArgument;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index.util.VariableType;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.*;

import java.io.File;
import java.util.*;

public class YScriptUtil {

    public static List<YScriptProgram> findPrograms(Project project, String programName) {
        List<YScriptProgram> result = null;
        Collection<VirtualFile> virtualFiles = FileBasedIndex.getInstance().getContainingFiles(
                FileTypeIndex.NAME,
                YScriptFileType.INSTANCE,
                GlobalSearchScope.allScope(project)
        );
        for (VirtualFile virtualFile : virtualFiles) {
            YScriptFile simpleFile = (YScriptFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (Objects.nonNull(simpleFile)) {
                YScriptStructuralItem[] properties = PsiTreeUtil.getChildrenOfType(simpleFile, YScriptStructuralItem.class);
                if (Objects.nonNull(properties)) {
                    for (YScriptStructuralItem property : properties) {
                        final YScriptProgram yScriptProgram = property.getProgram();
                        if (Objects.isNull(yScriptProgram)) {
                            continue;
                        }
                        if (programName.equals(yScriptProgram.getName())) {
                            if (result == null) {
                                result = new ArrayList<>();
                            }
                            result.add(yScriptProgram);
                        }
                    }
                }
            }
        }
        return result != null ? result : Collections.emptyList();
    }

    public static String getPathFromContainingFile(final PsiFile containingFile) {
        final PsiFile originalFile = containingFile.getOriginalFile();
        VirtualFile virtualFile = originalFile.getVirtualFile();
        if (Objects.isNull(virtualFile)) {
            virtualFile = originalFile.getUserData(IndexingDataKeys.VIRTUAL_FILE);
            if (Objects.isNull(virtualFile)) {
                return null;
            }
        }
        return virtualFile.getCanonicalPath();
    }

    public static String getPathFromContainingFile(final VirtualFile containingFile) {
        return containingFile.getCanonicalPath();
    }

    public static String getLangPath(final PsiFile file, final boolean suffix) {
        return getLangPath(getPathFromContainingFile(file), suffix);
    }

    public static String getLangPath(final VirtualFile file, final boolean suffix) {
        return getLangPath(getPathFromContainingFile(file), suffix);
    }

    public static String getLangPath(final String rawPath, final boolean suffix) {
        if (Objects.isNull(rawPath)) {
            return null;
        }
        final int i = rawPath.indexOf("/lang/");
        if (i == -1) {
            return null;
        }
        if (suffix) {
            return rawPath.substring(i + 6, rawPath.length() - 2);
        } else {
            return rawPath.substring(0, i + 6);
        }
    }

    public static VirtualFile getVirtualFileFromPackageName(final String packageName, final PsiFile containingFile) {
        final String rawPath = getLangPath(containingFile, false);
        if (Objects.isNull(rawPath)) {
            return null;
        }
        final String path = rawPath + packageName.replaceAll("::", "/");
        return LocalFileSystem.getInstance().findFileByPath(path);
    }

    public static String getPackageName(final PsiFile containingFile) {
        return getPackageName(getLangPath(containingFile, true));
    }

    public static String getPackageName(final VirtualFile containingFile) {
        return getPackageName(getLangPath(containingFile, true));
    }

    public static String getPackageName(String rawPath) {
        if (Objects.isNull(rawPath)) {
            return null;
        }
        return rawPath.replaceAll("/", "::");
    }

    public static ProgramArgument[] getProgramArguments(final YScriptProgram yScriptProgram) {
        final YScriptVars vars = yScriptProgram.getVars();
        if (Objects.isNull(vars)) {
            return new ProgramArgument[0];
        }
        final List<YScriptVar> varList = vars.getVarList();
        final ProgramArgument[] arguments = new ProgramArgument[varList.size()];
        int i = 0;
        for (YScriptVar yScriptVar : varList) {
            arguments[i++] = new ProgramArgument(
                    yScriptVar.getVarDef().getVarName().getIdentifier().getText(),
                    yScriptVar.getVarDef().getType().getVarName().getIdentifier().getText(),
                    Objects.isNull(yScriptVar.getVarDef().getType().getXmlType()) ? null : yScriptVar.getVarDef().getType().getXmlType().getXmlTypeNamespace().getText()
            );
        }
        return arguments;
    }

    public static VariableType getProgramReturnType(YScriptProgram yScriptProgram) {
        final YScriptType type = yScriptProgram.getType();
        if (Objects.isNull(type)) {
            return null;
        }
        return new VariableType(
                type.getVarName().getIdentifier().getText(),
                Objects.isNull(type.getXmlType()) ? null : type.getXmlType().getXmlTypeNamespace().getVString().getText()
        );
    }

}
