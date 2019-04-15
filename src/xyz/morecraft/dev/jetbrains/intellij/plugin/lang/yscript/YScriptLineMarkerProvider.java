package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript;

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.xml.XmlDocument;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.indexing.FileBasedIndex;
import com.intellij.xml.index.IndexedRelevantResource;
import com.intellij.xml.index.XmlNamespaceIndex;
import com.intellij.xml.index.XsdNamespaceBuilder;
import org.jetbrains.annotations.NotNull;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index.YScriptProgramNameSBIdx;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index.file.YScriptPackageFBIdx;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.util.YScriptConstants.SHARED_PROGRAM_NAME_PART;

public class YScriptLineMarkerProvider extends RelatedItemLineMarkerProvider {

    @Override
    protected void collectNavigationMarkers(@NotNull PsiElement element, @NotNull Collection<? super RelatedItemLineMarkerInfo> result) {
        super.collectNavigationMarkers(element, result);
        if (element instanceof YScriptImport) {
            final YScriptImport yScriptImport = (YScriptImport) element;
            final YScriptPackage yScriptPackage = yScriptImport.getPackage();
            final String importName = yScriptPackage.getText();
            final Collection<VirtualFile> containingFiles = FileBasedIndex.getInstance().getContainingFiles(YScriptPackageFBIdx.KEY, importName, GlobalSearchScope.projectScope(element.getProject()));
            final List<PsiFile> targets = new ArrayList<>(containingFiles.size());
            for (VirtualFile containingFile : containingFiles) {
                final PsiFile psiFile = PsiManager.getInstance(element.getProject()).findFile(containingFile);
                if(Objects.nonNull(psiFile)){
                    targets.add(psiFile);
                }
            }
            NavigationGutterIconBuilder<PsiElement> builder =
                    NavigationGutterIconBuilder.create(YScriptIcons.IMPORT_REF)
                            .setTargets(targets)
                            .setTooltipText("Navigate to import `" + importName + "`");
            result.add(builder.createLineMarkerInfo(yScriptPackage.getFirstChild()));
        } else if (element instanceof YScriptCall) {
            final YScriptCall yScriptCall = (YScriptCall) element;
            final YScriptPackage yScriptPackage = yScriptCall.getPackage();
            final String programName = yScriptPackage.getText();
            if (programName.contains(SHARED_PROGRAM_NAME_PART)) {
                NavigationGutterIconBuilder<PsiElement> builder =
                        NavigationGutterIconBuilder.create(YScriptIcons.SHARED)
                                .setTarget(null)
                                .setTooltipText("Environment specific identifier");
                result.add(builder.createLineMarkerInfo(yScriptPackage.getFirstChild()));
                return;
            }
            final Project project = element.getProject();
            final Collection<YScriptProgram> properties = YScriptProgramNameSBIdx.getInstance().get(
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
            }
        } else if (element instanceof YScriptType) {
            final YScriptType type = (YScriptType) element;
            final PsiElement xmlTypeNamespace = type.getVString();
            if (Objects.isNull(xmlTypeNamespace)) {
                return;
            }
            final String rawNamespace = xmlTypeNamespace.getText();
            final String namespace = rawNamespace.substring(1, rawNamespace.length() - 1);
            final String typeName = type.getVarName().getText();
            final Project project = element.getProject();
            final PsiManager psiManager = PsiManager.getInstance(project);
            final List<XmlTag> targets = new ArrayList<>();
            final List<IndexedRelevantResource<String, XsdNamespaceBuilder>> indexedRelevantResourceList = XmlNamespaceIndex.getResourcesByNamespace(namespace, project, null);
            for (IndexedRelevantResource<String, XsdNamespaceBuilder> indexedRelevantResource : indexedRelevantResourceList) {
                final PsiFile file = psiManager.findFile(indexedRelevantResource.getFile());
                if (Objects.isNull(file)) {
                    continue;
                }
                if (!(file instanceof XmlFile)) {
                    return;
                }
                final XmlDocument document = ((XmlFile) file).getDocument();
                if (Objects.isNull(document)) {
                    continue;
                }
                final XmlTag rootTag = document.getRootTag();
                if (Objects.isNull(rootTag)) {
                    continue;
                }
                if (namespace.equalsIgnoreCase(rootTag.getAttributeValue("targetNamespace"))) {
                    final XmlTag[] complexTypes = rootTag.findSubTags("complexType", "http://www.w3.org/2001/XMLSchema");
                    for (XmlTag complexType : complexTypes) {
                        if (typeName.equalsIgnoreCase(complexType.getAttributeValue("name"))) {
                            targets.add(complexType);
                        }
                    }
                }
            }
            if (targets.size() > 0) {
                NavigationGutterIconBuilder<PsiElement> builder =
                        NavigationGutterIconBuilder.create(AllIcons.FileTypes.XsdFile)
                                .setTargets(targets)
                                .setTooltipText("Navigate to declared type in XML `" + namespace + "`");
                result.add(builder.createLineMarkerInfo(xmlTypeNamespace));
            }
        }
    }

}
