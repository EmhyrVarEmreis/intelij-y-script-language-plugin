package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.fix;

import com.intellij.codeInsight.daemon.DaemonCodeAnalyzer;
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
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.indexing.FileBasedIndex;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.YScriptFileType;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index.file.YScriptPackageFBIdx;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.*;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.impl.YScriptFileContentImpl;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.util.YScriptUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class CreateMissingImportQuickFix extends BaseIntentionAction {

    private final Collection<String> possibleImports;

    public CreateMissingImportQuickFix(final Collection<String> possibleImports) {
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
                virtualFiles.addAll(
                        FileBasedIndex.getInstance().getContainingFiles(YScriptPackageFBIdx.KEY, possibleImport, GlobalSearchScope.projectScope(project))
                );
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
        final YScriptFile yScriptFile = (YScriptFile) PsiManager.getInstance(project).findFile(file);
        if (Objects.isNull(yScriptFile)) {
            return;
        }
        WriteCommandAction.writeCommandAction(project).run(() -> {
            final YScriptFileContent yScriptFileContent = YScriptUtil.getYScriptFileContent(yScriptFile);
            if (Objects.isNull(yScriptFileContent)) {
                return;
            }
            final ASTNode insertNode = yScriptFileContent.getNode().findChildByType(YScriptTypes.IMPORT);
            final PsiElement psi1 = YScriptElementFactory.createCRLF(project).getNode().getPsi();
            final PsiElement psi2 = YScriptElementFactory.createStatementTerminator(project).getNode().getPsi();
            final PsiElement psi3 = YScriptElementFactory.createImport(project, YScriptUtil.getPackageName(importFile)).getNode().getPsi();
            if (Objects.nonNull(insertNode)) {
                final PsiElement insertNodePsi = insertNode.getPsi();
                insertNodePsi.addAfter(psi1, null);
                insertNodePsi.addAfter(psi2, null);
                insertNodePsi.addAfter(psi3, null);
            } else {
                final ASTNode insertNodeBkAST = yScriptFileContent.getNode().getFirstChildNode();
                if (Objects.isNull(insertNodeBkAST)) {
                    return;
                }
                final PsiElement insertNodeBk = insertNodeBkAST.getPsi();
                insertNodeBk.addBefore(psi3, null);
                insertNodeBk.addBefore(psi2, null);
                insertNodeBk.addBefore(psi1, null);
            }
        });
    }

}
