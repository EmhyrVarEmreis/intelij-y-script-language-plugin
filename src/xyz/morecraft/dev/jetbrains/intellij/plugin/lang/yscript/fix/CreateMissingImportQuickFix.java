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
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.YscriptAnnotator;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index.file.YScriptPackageFBIdx;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.YScriptElementFactory;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.YScriptFile;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.YScriptFileContent;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.YScriptImport;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.util.Std;
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
            if (this.possibleImports.size() == 1 && this.possibleImports.iterator().next().equalsIgnoreCase(Std.STD_DEFAULT)) {
                createImport(project, psiFile.getVirtualFile(), Std.STD_DEFAULT);
                return;
            }
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

    private void createImport(final Project project, final VirtualFile file, final String importFile) {
        final YScriptFile yScriptFile = (YScriptFile) PsiManager.getInstance(project).findFile(file);
        if (Objects.isNull(yScriptFile)) {
            return;
        }
        WriteCommandAction.writeCommandAction(project).run(() -> {
            final YScriptFileContent yScriptFileContent = YScriptUtil.getYScriptFileContent(yScriptFile);
            if (Objects.isNull(yScriptFileContent)) {
                return;
            }
            final PsiElement psiLine0 = YScriptElementFactory.createCRLF(project).getNode().getPsi();
            final PsiElement psiLine1 = YScriptElementFactory.createCRLF(project).getNode().getPsi();
            final PsiElement psiImport = YScriptElementFactory.createImport(project, importFile).getNode().getPsi();

            ASTNode insertNode = this.getLast(yScriptFileContent.getChildren(), YScriptImport.class);

            boolean addSecondLine = false;

            if (Objects.isNull(insertNode)) {
                insertNode = yScriptFileContent.getNode().getFirstChildNode();
                addSecondLine = true;
            }
            yScriptFileContent.getNode().addChild(psiImport.getNode(), insertNode);
            yScriptFileContent.getNode().addChild(psiLine0.getNode(), insertNode);
            if (addSecondLine) {
                yScriptFileContent.getNode().addChild(psiLine1.getNode(), insertNode);
            }
            DaemonCodeAnalyzer.getInstance(project).restart();
        });
    }

    private void createImport(final Project project, final VirtualFile file, final VirtualFile importFile) {
        createImport(project, file, YScriptUtil.getPackageName(importFile));
    }

    private ASTNode getLast(final PsiElement[] elements, final Class type) {
        PsiElement element = null;
        for (PsiElement psiElement : elements) {
            if (type.isInstance(psiElement)) {
                element = psiElement;
            }
        }
        return Objects.isNull(element) ? null : element.getNode();
    }

}
