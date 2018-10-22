package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript;

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.YScriptCall;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.YScriptPackage;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.YScriptProgram;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.util.YScriptUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class YScriptLineMarkerProvider extends RelatedItemLineMarkerProvider {
//
//    @Override
//    public RelatedItemLineMarkerInfo getLineMarkerInfo(@NotNull PsiElement element) {
//        if (element instanceof YScriptPackage && element.getParent() instanceof YScriptCall) return new RelatedItemLineMarkerInfo(
//                element,
//                element.getTextRange(),
//                YScriptIcons.FILE,
//                1,
//                null,
//                null,
//                GutterIconRenderer.Alignment.CENTER
//        );
//        return null;
//        if (element instanceof YScriptCall) {
//            final YScriptCall yScriptCall = (YScriptCall) element;
//            final YScriptPackage yScriptPackage = yScriptCall.getPackage();
//            return new RelatedItemLineMarkerInfo(
//                    yScriptPackage,
//                    yScriptPackage.getTextRange(),
//                    YScriptIcons.FILE,
//                    1,
//
//                    );
//        }
//        return super.getLineMarkerInfo(element);
//    }

    @Override
    protected void collectNavigationMarkers(@NotNull PsiElement element, @NotNull Collection<? super RelatedItemLineMarkerInfo> result) {
        super.collectNavigationMarkers(element, result);
        if (element instanceof YScriptCall) {
            final YScriptCall yScriptCall = (YScriptCall) element;
            final String programName = yScriptCall.getPackage().getText();
            final Project project = element.getProject();
            final List<YScriptProgram> properties = YScriptUtil.findPrograms(project, programName);
            if (properties.size() > 0) {
                final List<YScriptPackage> targets = new ArrayList<>(properties.size());
                for (YScriptProgram property : properties) {
                    targets.add(property.getPackage());
                }
                NavigationGutterIconBuilder<PsiElement> builder =
                        NavigationGutterIconBuilder.create(YScriptIcons.FILE)
                                .setTargets(targets)
                                .setTooltipText("Navigate to " + programName + "()");
                result.add(builder.createLineMarkerInfo(element));
            }
        }
    }

}
