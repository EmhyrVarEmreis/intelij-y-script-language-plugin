package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript;

import com.intellij.lang.annotation.Annotation;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.indexing.FileBasedIndex;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.fix.CreateMissingImportQuickFix;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index.YScriptFileContentSBIdx;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index.YScriptProgramNameSBIdx;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index.file.YScriptProgramNameFBIdx;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index.file.obj.YScriptProgramStruct;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index.file.obj.YScriptProgramStructBundle;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.*;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.impl.YScriptPsiImplUtil;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.util.YScriptUtil;

import java.util.*;

import static xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.util.YScriptConstants.CONFIG_INCLUDE_DIRECTORIES_WHILE_IMPORT;
import static xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.util.YScriptConstants.SHARED_PROGRAM_NAME_PART;

public class YscriptAnnotator implements Annotator {

    private static Set<String> BUILT_IN_TYPES;

    static {
        BUILT_IN_TYPES = new HashSet<>();
        BUILT_IN_TYPES.add("Integer");
        BUILT_IN_TYPES.add("String");
        BUILT_IN_TYPES.add("DateTime");
        BUILT_IN_TYPES.add("Float");
        BUILT_IN_TYPES.add("Boolean");
        BUILT_IN_TYPES.add("AnyType");
    }

    @Override
    public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
        if (element instanceof YScriptCall) {
            final YScriptCall yScriptCall = (YScriptCall) element;
            final YScriptPackage yScriptPackage = yScriptCall.getPackage();
            final String programName = yScriptPackage.getText();
            if (programName.contains(SHARED_PROGRAM_NAME_PART)) {
                return;
            }
            final Project project = element.getProject();
            final List<YScriptProgramStructBundle> yScriptProgramObjectLists = FileBasedIndex.getInstance().getValues(YScriptProgramNameFBIdx.KEY, programName, GlobalSearchScope.projectScope(project));
            if (yScriptProgramObjectLists.size() == 0) {
                holder.createErrorAnnotation(yScriptCall.getTextRange(), "Unresolved program");
            } else {
                final YScriptFileContent yScriptFileContent = YScriptPsiImplUtil.getYScriptFileContent(yScriptCall);
                if (isProgramImportedOrUsed(yScriptFileContent, yScriptProgramObjectLists)) {
                    if (!isCallMatched(yScriptCall, yScriptProgramObjectLists)) {
                        holder.createErrorAnnotation(yScriptCall.getTextRange(), "Signature doeas not match - Probably wrong number of arguments");
                    }
                } else {
                    final Annotation callAnnotation = holder.createWarningAnnotation(yScriptCall.getTextRange(), "Program is not strictly imported");
                    final List<String> possibleImports = getPossibleImports(yScriptCall, yScriptProgramObjectLists);
                    if (possibleImports.size() > 0) {
                        callAnnotation.registerFix(new CreateMissingImportQuickFix(possibleImports));
                    }
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
        } else if (element instanceof YScriptPropertySimple) {
            final YScriptPropertySimple propertySimple = (YScriptPropertySimple) element;
            final List<YScriptPropertyBase> propertyBaseList = propertySimple.getPropertyBaseList();
            if (propertyBaseList.size() < 1) {
                return;
            }
            final YScriptPropertyBase leadPropertyBase = propertyBaseList.get(0);
            final boolean present = checkIfDefined(leadPropertyBase);
            if (!present) {
                holder.createErrorAnnotation(leadPropertyBase.getTextRange(), "Undefined variable");
                return;
            }
            for (int i = 0; i < propertyBaseList.size(); i++) {
                final YScriptPropertyBase propertyBase = propertyBaseList.get(i);
            }
        } else if (element instanceof YScriptType) {
            final YScriptType type = (YScriptType) element;
            if (Objects.nonNull(type.getVString())) {
                return;
            }
            if (!BUILT_IN_TYPES.contains(type.getName())) {
                holder.createErrorAnnotation(type.getTextRange(), "Unknown type");
            }
        }
    }

    private PsiElement getPrev(final PsiElement element) {
        return element.getPrevSibling() == null ? element.getParent() : element.getPrevSibling();
    }

    private boolean checkIfDefined(final YScriptPropertyBase leadPropertyBase) {
        if (leadPropertyBase.getName().equals("result")) {
            return true;
        }
        PsiElement element = getPrev(leadPropertyBase);
        while (element != null) {
            if (element instanceof YScriptStatement) {
                final YScriptVar var = ((YScriptStatement) element).getVar();
                if (var != null) {
                    if (var.getVarDef().getName().equals(leadPropertyBase.getName())) {
                        return true;
                    }
                }
            } else if (element instanceof YScriptProgram) {
                final YScriptProgramHeader header = ((YScriptProgram) element).getProgramHeader();
                for (YScriptVar yScriptVar : header.getVarList()) {
                    if (yScriptVar.getVarDef().getName().equals(leadPropertyBase.getName())) {
                        return true;
                    }
                }
                break;
            } else if (element instanceof YScriptBlock && (element.getParent() instanceof YScriptCreate || element.getParent() instanceof YScriptNew)) {
                return true;
            } else if (element instanceof YScriptWithLoop) {
                final String varName = ((YScriptWithLoop) element).getVarName().getIdentifier().getText();
                if (varName.equals(leadPropertyBase.getName())) {
                    return true;
                }
            }
            element = getPrev(element);
        }
        return false;
    }

    private static List<String> getPossibleImports(@NotNull final YScriptCall yScriptCall, @NotNull final List<YScriptProgramStructBundle> yScriptProgramStructBundleList) {
        final List<String> possibleImports = new ArrayList<>();
        for (YScriptProgramStructBundle programStructBundle : yScriptProgramStructBundleList) {
            for (YScriptProgramStruct program : programStructBundle.getPrograms()) {
                if (program.match(yScriptCall, false)) {
                    possibleImports.add(program.getPackageName());
                }
            }
        }
        return possibleImports;
    }

    private static boolean checkIfImportExists(final String packageName, final PsiElement element, @SuppressWarnings("SameParameterValue") boolean includeDirectories) {
        final VirtualFile virtualFileFromPackageName = YScriptUtil.getVirtualFileFromPackageName(packageName, element.getContainingFile());
        if (Objects.isNull(virtualFileFromPackageName)) {
            return false;
        }
        if (!virtualFileFromPackageName.isDirectory()) {
            return true;
        }
        return includeDirectories;
    }

    private static Collection<YScriptProgram> getYScriptProgramNameFBIdx(String name, Project project) {
        return YScriptProgramNameSBIdx.getInstance().get(
                name,
                project,
                GlobalSearchScope.projectScope(project)
        );
    }

    private static Collection<YScriptFileContent> getYScriptFileContentFBIdx(String name, Project project) {
        return YScriptFileContentSBIdx.getInstance().get(
                name,
                project,
                GlobalSearchScope.projectScope(project)
        );
    }

    private static boolean isProgramImportedOrUsed(@Nullable final YScriptFileContent yScriptFileContent, @NotNull final List<YScriptProgramStructBundle> yScriptProgramStructBundleList) {
        if (Objects.isNull(yScriptFileContent)) {
            return false;
        }
        for (YScriptProgramStructBundle programStructBundle : yScriptProgramStructBundleList) {
            final Collection<YScriptProgramStruct> programs = programStructBundle.getPrograms();
            if (programs.size() == 0) {
                continue;
            }
            final YScriptProgramStruct yScriptProgramStruct = programs.iterator().next();
            for (YScriptImport yScriptImport : yScriptFileContent.getImportList()) {
                if (yScriptImport.getPackage().getText().equalsIgnoreCase(yScriptProgramStruct.getPackageName())) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isCallMatched(@NotNull final YScriptCall yScriptCall, @NotNull final List<YScriptProgramStructBundle> yScriptProgramStructBundleList) {
        for (YScriptProgramStructBundle programBundle : yScriptProgramStructBundleList) {
            for (YScriptProgramStruct program : programBundle.getPrograms()) {
                if (program.match(yScriptCall, true)) {
                    return true;
                }
            }
        }
        return false;
    }

}
