package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript;

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.annotations.NotNull;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index.YScriptProgramNameFBIdx;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.YScriptCall;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.YScriptPackage;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.YScriptProgram;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class YScriptLineMarkerProvider extends RelatedItemLineMarkerProvider {

    @Override
    protected void collectNavigationMarkers(@NotNull PsiElement element, @NotNull Collection<? super RelatedItemLineMarkerInfo> result) {
        super.collectNavigationMarkers(element, result);
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
            if (properties.size() > 0) {
                final List<YScriptPackage> targets = new ArrayList<>(properties.size());
                for (YScriptProgram property : properties) {
                    targets.add(property.getPackage());
                }
                NavigationGutterIconBuilder<PsiElement> builder =
                        NavigationGutterIconBuilder.create(YScriptIcons.PROGRAM_REF)
                                .setTargets(targets)
                                .setTooltipText("Navigate to program `" + programName + "`");
                result.add(builder.createLineMarkerInfo(yScriptPackage.getFirstChild()));
            } else {
                NavigationGutterIconBuilder<PsiElement> builder =
                        NavigationGutterIconBuilder.create(YScriptIcons.PROGRAM_ERR)
                                .setTargets(Collections.emptyList())
                                .setTooltipText("Program `" + programName + "` does not exist!");
                result.add(builder.createLineMarkerInfo(yScriptPackage.getFirstChild()));
            }
        }
    }

}
