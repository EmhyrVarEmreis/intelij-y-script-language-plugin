package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileVisitor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index.YScriptFileContentFBIdx;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index.YScriptProgramNameFBIdx;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.*;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.impl.YScriptPsiImplUtil;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.util.StopVisitingException;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.util.YScriptUtil;

import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import static xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.util.YScriptConstants.CONFIG_INCLUDE_DIRECTORIES_WHILE_IMPORT;
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
                final String name = YScriptPsiImplUtil.getName(yScriptCall);
                if (!isProgramImportedOrUsed(name, YScriptPsiImplUtil.getYScriptFileContent(yScriptCall), project, CONFIG_INCLUDE_DIRECTORIES_WHILE_IMPORT)) {
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
                final boolean directoryExists = checkIfImportExists(filePackage, yScriptImport, CONFIG_INCLUDE_DIRECTORIES_WHILE_IMPORT);
                if (!directoryExists) {
                    holder.createErrorAnnotation(yScriptImport.getTextRange(), "Unresolved file");
                }
            }
        }
    }

    private static boolean checkIfImportExists(final String packageName, final PsiElement element, @SuppressWarnings("SameParameterValue") boolean includeDirectories) {
        final VirtualFile virtualFileFromPackageName = YScriptUtil.getVirtualFileFromPackageName(packageName, element.getContainingFile());
        if(Objects.isNull(virtualFileFromPackageName)){
            return false;
        }
        if(!virtualFileFromPackageName.isDirectory()){
            return true;
        }
        return includeDirectories;
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

    private static boolean isProgramImportedOrUsed(@NotNull final String name, @Nullable final YScriptFileContent yScriptFileContent, @NotNull final Project project, boolean includeDirectories) {
        if (Objects.isNull(yScriptFileContent)) {
            return false;
        }
        for (String programName : yScriptFileContent.getProgramNames()) {
            if (name.equalsIgnoreCase(programName)) {
                return true;
            }
        }
        for (String importName : yScriptFileContent.getImportNames()) {
            final Collection<YScriptFileContent> filesFromImport = getYScriptFileContentFBIdx(importName, project);
            if (filesFromImport.size() == 0) {
                final VirtualFile virtualFile = YScriptUtil.getVirtualFileFromPackageName(importName, yScriptFileContent.getContainingFile());
                if (Objects.isNull(virtualFile)) {
                    continue;
                }
                if (!virtualFile.isDirectory() || !includeDirectories) {
                    continue;
                }
                final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
                try {
                    VfsUtilCore.visitChildrenRecursively(virtualFile, new VirtualFileVisitor<Object>() {
                        @Override
                        public boolean visitFile(@NotNull VirtualFile file) {
                            final PsiFile psiFile = PsiManager.getInstance(project).findFile(file);
                            if (Objects.nonNull(psiFile)) {
                                final YScriptFileContent yScriptFileContent = YScriptPsiImplUtil.getYScriptFileContent(psiFile);
                                if (Objects.nonNull(yScriptFileContent)) {
                                    @SuppressWarnings("ConstantConditions") final boolean programImportedOrUsed = isProgramImportedOrUsed(name, yScriptFileContent, project, includeDirectories);
                                    if (programImportedOrUsed) {
                                        atomicBoolean.set(true);
                                        throw new StopVisitingException();
                                    }
                                }
                            }
                            return super.visitFile(file);
                        }
                    });
                } catch (StopVisitingException ignored) {
                }
                if (atomicBoolean.get()) {
                    return true;
                }
            } else {
                for (YScriptFileContent fileFromImport : filesFromImport) {
                    for (String programNameFromImportedFiles : fileFromImport.getProgramNames()) {
                        if (name.equalsIgnoreCase(programNameFromImportedFiles)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

}
