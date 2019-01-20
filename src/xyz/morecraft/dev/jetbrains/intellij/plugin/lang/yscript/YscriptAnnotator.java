package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index.YScriptFileContentFBIdx;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index.YScriptProgramNameFBIdx;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.*;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.impl.YScriptPsiImplUtil;

import java.util.Collection;
import java.util.Objects;

import static xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.util.YScriptConstants.SHARED_PROGRAM_NAME_PREFIX;

public class YscriptAnnotator implements Annotator {

    @Override
    public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
        if (element instanceof YScriptCall) {
            final YScriptCall yScriptCall = (YScriptCall) element;
            final YScriptPackage yScriptPackage = yScriptCall.getPackage();
            final String programName = yScriptPackage.getText();
            if (programName.startsWith(SHARED_PROGRAM_NAME_PREFIX)) {
                return;
            }
            final Project project = element.getProject();
            final Collection<YScriptProgram> collection = getYScriptProgramNameFBIdx(programName, project);
            if (collection.size() == 0) {
                holder.createErrorAnnotation(yScriptCall.getTextRange(), "Unresolved program");
            } else {
                if (!isImportedOrUsed(yScriptCall, project)) {
                    holder.createErrorAnnotation(yScriptCall.getTextRange(), "Program is not imported");
                }
            }
        } else if (element instanceof YScriptImport) {
            final YScriptImport yScriptImport = (YScriptImport) element;
            final YScriptPackage yScriptPackage = yScriptImport.getPackage();
            final String filePackage = yScriptPackage.getText();
            final Project project = element.getProject();
            final Collection<YScriptFileContent> collection = getYScriptFileContentFBIdx(filePackage, project);
            if (collection.size() == 0) {
                holder.createErrorAnnotation(yScriptImport.getTextRange(), "Unresolved file");
            }
        }
    }

    private static Collection<YScriptProgram> getYScriptProgramNameFBIdx(String name, Project project) {
        return YScriptProgramNameFBIdx.getInstance().get(
                name,
                project,
                GlobalSearchScope.projectScope(project)
        );
    }

    private static Collection<YScriptFileContent> getYScriptFileContentFBIdx(String name, Project project) {
        return YScriptFileContentFBIdx.getInstance().get(
                name,
                project,
                GlobalSearchScope.projectScope(project)
        );
    }

    private static YScriptFileContent getFileContent(@Nullable PsiElement element) {
        if (Objects.isNull(element)) {
            return null;
        } else if (element instanceof YScriptFileContent) {
            return (YScriptFileContent) element;
        } else {
            return getFileContent(element.getParent());
        }
    }

    private static boolean isImportedOrUsed(YScriptCall yScriptCall, Project project) {
        final YScriptFileContent yScriptFileContent = getFileContent(yScriptCall);
        if (Objects.nonNull(yScriptFileContent)) {
            final String name = YScriptPsiImplUtil.getName(yScriptCall);
            for (String programName : yScriptFileContent.getProgramNames()) {
                if (name.equalsIgnoreCase(programName)) {
                    return true;
                }
            }
            for (String importName : yScriptFileContent.getImportNames()) {
                final Collection<YScriptFileContent> filesFromImport = getYScriptFileContentFBIdx(importName, project);
                for (YScriptFileContent fileFromImport : filesFromImport) {
                    for (String programNameFromImportedFiles : fileFromImport.getProgramNames()) {
                        if (name.equalsIgnoreCase(programNameFromImportedFiles)) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        return true;
    }

}
