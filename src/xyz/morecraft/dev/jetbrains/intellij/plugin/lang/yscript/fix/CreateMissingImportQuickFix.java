package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.fix;

import com.intellij.codeInsight.intention.impl.BaseIntentionAction;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.YScriptFileType;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index.YScriptFileContentFBIdx;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index.YScriptProgramNameFBIdx;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.*;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.util.YScriptUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class CreateMissingImportQuickFix extends BaseIntentionAction {

    private final List<String> possibleImports;

    public CreateMissingImportQuickFix(final List<String> possibleImports) {
        this.possibleImports = possibleImports;
    }

    @NotNull
    @Override
    public String getText() {
        return "Create import";
    }

    @Nls(capitalization = Nls.Capitalization.Sentence)
    @NotNull
    @Override
    public String getFamilyName() {
        return "YScript import";
    }

    @Override
    public boolean isAvailable(@NotNull Project project, Editor editor, PsiFile psiFile) {
        return true;
    }

    @Override
    public void invoke(@NotNull Project project, Editor editor, PsiFile psiFile) throws IncorrectOperationException {
        ApplicationManager.getApplication().invokeLater(() -> {
            final ArrayList<VirtualFile> virtualFiles = new ArrayList<>();
            for (String possibleImport : this.possibleImports) {
                Collection<YScriptProgram> yScriptPrograms = YScriptProgramNameFBIdx.getInstance().get(possibleImport, project, GlobalSearchScope.projectScope(project));
                for (YScriptProgram yScriptProgram : yScriptPrograms) {
                    final String packageName = YScriptUtil.getPackageName(yScriptProgram.getContainingFile());
                    Collection<YScriptFileContent> yScriptFileContents = YScriptFileContentFBIdx.getInstance().get(packageName, project, GlobalSearchScope.projectScope(project));
                    for (YScriptFileContent yScriptFileContent : yScriptFileContents) {
                        virtualFiles.add(yScriptFileContent.getContainingFile().getVirtualFile());
                    }
                }
            }
            if (virtualFiles.size() == 1) {
                createImport(project, psiFile.getVirtualFile(), virtualFiles.get(0));
            } else {
                final FileChooserDescriptor descriptor = FileChooserDescriptorFactory.createSingleFileDescriptor(YScriptFileType.INSTANCE).withFileFilter(virtualFile -> {
                    for (VirtualFile file : virtualFiles) {
                        final String canonicalPath = virtualFile.getCanonicalPath();
                        if (canonicalPath == null) {
                            return false;
                        }
                        if (canonicalPath.equals(file.getCanonicalPath())) {
                            return true;
                        }
                    }
                    return false;
                });
                FileChooser.chooseFile(descriptor, project, null, virtualFile -> createImport(project, psiFile.getVirtualFile(), virtualFile));
            }
        });
    }

    private void createImport(final Project project, final VirtualFile file, final VirtualFile importFile) {
        WriteCommandAction.writeCommandAction(project).run(() -> {
            final YScriptFile yScriptFile = (YScriptFile) PsiManager.getInstance(project).findFile(file);
            if (Objects.isNull(yScriptFile)) {
                return;
            }
            ASTNode insertNode = yScriptFile.getNode().findChildByType(YScriptTypes.IMPORT);
            if (Objects.isNull(insertNode)) {
                insertNode = yScriptFile.getNode().getFirstChildNode();
            }
            yScriptFile.getNode().addChild(
                    YScriptElementFactory.createImport(project, YScriptUtil.getPackageName(importFile)).getNode()
            );
        });
    }

}
