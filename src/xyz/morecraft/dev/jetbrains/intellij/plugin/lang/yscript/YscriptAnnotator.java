package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.annotations.NotNull;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index.YScriptFilePackageFBIdx;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index.YScriptProgramNameFBIdx;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.*;

import java.util.Collection;

public class YscriptAnnotator implements Annotator {

    @Override
    public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
        if (element instanceof YScriptCall) {
            final YScriptCall yScriptCall = (YScriptCall) element;
            final YScriptPackage yScriptPackage = yScriptCall.getPackage();
            final String programName = yScriptPackage.getText();
            final Project project = element.getProject();
            final Collection<YScriptProgram> properties = YScriptProgramNameFBIdx.getInstance().get(
                    programName,
                    project,
                    GlobalSearchScope.projectScope(project)
            );
            if (properties.size() == 0) {
                holder.createErrorAnnotation(yScriptCall.getTextRange(), "Unresolved program");
            }
        } else if (element instanceof YScriptImport) {
            final YScriptImport yScriptImport = (YScriptImport) element;
            final YScriptPackage yScriptPackage = yScriptImport.getPackage();
            final String filePackage = yScriptPackage.getText();
            final Project project = element.getProject();
            final Collection<YScriptFileContent> properties = YScriptFilePackageFBIdx.getInstance().get(
                    filePackage,
                    project,
                    GlobalSearchScope.projectScope(project)
            );
            if (properties.size() == 0) {
                holder.createErrorAnnotation(yScriptImport.getTextRange(), "Unresolved file");
            }
        }
    }

}
