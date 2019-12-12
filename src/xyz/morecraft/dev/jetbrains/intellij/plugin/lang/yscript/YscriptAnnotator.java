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
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index.util.ProgramArgument;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index.util.VariableType;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.*;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.impl.YScriptPsiImplUtil;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.util.YScriptUtil;

import java.util.*;

import static xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.util.YScriptConstants.CONFIG_INCLUDE_DIRECTORIES_WHILE_IMPORT;
import static xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.util.YScriptConstants.SHARED_PROGRAM_NAME_PART;

public class YscriptAnnotator implements Annotator {

    private final static Set<String> BUILT_IN_TYPES;
    private final static Map<String, YScriptProgramStructBundle> BUILT_IN_PROGRAMS;

    static {
        BUILT_IN_TYPES = new HashSet<>();
        BUILT_IN_TYPES.add("Integer");
        BUILT_IN_TYPES.add("String");
        BUILT_IN_TYPES.add("DateTime");
        BUILT_IN_TYPES.add("Time");
        BUILT_IN_TYPES.add("Date");
        BUILT_IN_TYPES.add("Raw");
        BUILT_IN_TYPES.add("Float");
        BUILT_IN_TYPES.add("Boolean");
        BUILT_IN_TYPES.add("AnyType");
        BUILT_IN_PROGRAMS = new HashMap<>();
        BUILT_IN_PROGRAMS.put("std::strlen", new YScriptProgramStructBundle(Collections.singletonList(
                new YScriptProgramStruct(
                        "std::strlen",
                        "dts::default",
                        new ProgramArgument[]{new ProgramArgument("strArgument", "String", null)},
                        new VariableType("Integer", null)
                )
        )));
        BUILT_IN_PROGRAMS.put("std::getTime", new YScriptProgramStructBundle(Collections.singletonList(
                new YScriptProgramStruct(
                        "std::getTime",
                        "dts::default",
                        new ProgramArgument[]{},
                        new VariableType("DateTime", null)
                )
        )));
        BUILT_IN_PROGRAMS.put("std::modifydate", new YScriptProgramStructBundle(Collections.singletonList(
                new YScriptProgramStruct(
                        "std::modifydate",
                        "dts::default",
                        new ProgramArgument[]{
                                new ProgramArgument("tTime", "DateTime", null),
                                new ProgramArgument("iDeltaYears", "Integer", null),
                                new ProgramArgument("iDeltaMonths", "Integer", null),
                                new ProgramArgument("iDeltaDays", "Integer", null)
                        },
                        new VariableType("DateTime", null)
                )
        )));
        BUILT_IN_PROGRAMS.put("std::modifytime", new YScriptProgramStructBundle(Collections.singletonList(
                new YScriptProgramStruct(
                        "std::modifytime",
                        "dts::default",
                        new ProgramArgument[]{
                                new ProgramArgument("tTime", "DateTime", null),
                                new ProgramArgument("iDeltaHours", "Integer", null),
                                new ProgramArgument("iDeltaMinutes", "Integer", null),
                                new ProgramArgument("iDeltaSeconds", "Integer", null)
                        },
                        new VariableType("DateTime", null)
                )
        )));
        BUILT_IN_PROGRAMS.put("std::save", new YScriptProgramStructBundle(Collections.singletonList(
                new YScriptProgramStruct(
                        "std::save",
                        "dts::default",
                        new ProgramArgument[]{
                                new ProgramArgument("file", "String", null),
                                new ProgramArgument("value", "AnyType", null)
                        },
                        null
                )
        )));
        BUILT_IN_PROGRAMS.put("std::str2ts", new YScriptProgramStructBundle(Collections.singletonList(
                new YScriptProgramStruct(
                        "std::str2ts",
                        "dts::default",
                        new ProgramArgument[]{
                                new ProgramArgument("strTs", "String", null),
                                new ProgramArgument("strFmt", "String", null)
                        },
                        new VariableType("DateTime", null)
                )
        )));
        BUILT_IN_PROGRAMS.put("std::replaceRegExp", new YScriptProgramStructBundle(Collections.singletonList(
                new YScriptProgramStruct(
                        "std::replaceRegExp",
                        "dts::default",
                        new ProgramArgument[]{
                                new ProgramArgument("strArgument", "String", null),
                                new ProgramArgument("strPattern", "String", null),
                                new ProgramArgument("strNewText", "String", null)
                        },
                        new VariableType("String", null)
                )
        )));
        BUILT_IN_PROGRAMS.put("std::str2lower", new YScriptProgramStructBundle(Collections.singletonList(
                new YScriptProgramStruct(
                        "std::str2lower",
                        "dts::default",
                        new ProgramArgument[]{
                                new ProgramArgument("strArgument", "String", null)
                        },
                        new VariableType("String", null)
                )
        )));
        BUILT_IN_PROGRAMS.put("std::str2upper", new YScriptProgramStructBundle(Collections.singletonList(
                new YScriptProgramStruct(
                        "std::str2upper",
                        "dts::default",
                        new ProgramArgument[]{
                                new ProgramArgument("strArgument", "String", null)
                        },
                        new VariableType("String", null)
                )
        )));
        BUILT_IN_PROGRAMS.put("std::getHour", new YScriptProgramStructBundle(Collections.singletonList(
                new YScriptProgramStruct(
                        "std::getHour",
                        "dts::default",
                        new ProgramArgument[]{
                                new ProgramArgument("ts", "DateTime", null)
                        },
                        new VariableType("Integer", null)
                )
        )));
        BUILT_IN_PROGRAMS.put("std::getMinute", new YScriptProgramStructBundle(Collections.singletonList(
                new YScriptProgramStruct(
                        "std::getMinute",
                        "dts::default",
                        new ProgramArgument[]{
                                new ProgramArgument("ts", "DateTime", null)
                        },
                        new VariableType("Integer", null)
                )
        )));
        BUILT_IN_PROGRAMS.put("std::getSecond", new YScriptProgramStructBundle(Collections.singletonList(
                new YScriptProgramStruct(
                        "std::getSecond",
                        "dts::default",
                        new ProgramArgument[]{
                                new ProgramArgument("ts", "DateTime", null)
                        },
                        new VariableType("Integer", null)
                )
        )));
        BUILT_IN_PROGRAMS.put("std::getDay", new YScriptProgramStructBundle(Arrays.asList(
                new YScriptProgramStruct(
                        "std::getDay",
                        "dts::default",
                        new ProgramArgument[]{
                                new ProgramArgument("date", "Date", null)
                        },
                        new VariableType("Integer", null)
                ),
                new YScriptProgramStruct(
                        "std::getDay",
                        "dts::default",
                        new ProgramArgument[]{
                                new ProgramArgument("date", "DateTime", null)
                        },
                        new VariableType("Integer", null)
                )
        )));
        BUILT_IN_PROGRAMS.put("std::getMonth", new YScriptProgramStructBundle(Arrays.asList(
                new YScriptProgramStruct(
                        "std::getMonth",
                        "dts::default",
                        new ProgramArgument[]{
                                new ProgramArgument("date", "Date", null)
                        },
                        new VariableType("Integer", null)
                ),
                new YScriptProgramStruct(
                        "std::getMonth",
                        "dts::default",
                        new ProgramArgument[]{
                                new ProgramArgument("date", "DateTime", null)
                        },
                        new VariableType("Integer", null)
                )
        )));
        BUILT_IN_PROGRAMS.put("std::getYear", new YScriptProgramStructBundle(Arrays.asList(
                new YScriptProgramStruct(
                        "std::getYear",
                        "dts::default",
                        new ProgramArgument[]{
                                new ProgramArgument("date", "Date", null)
                        },
                        new VariableType("Integer", null)
                ),
                new YScriptProgramStruct(
                        "std::getYear",
                        "dts::default",
                        new ProgramArgument[]{
                                new ProgramArgument("date", "DateTime", null)
                        },
                        new VariableType("Integer", null)
                )
        )));
        BUILT_IN_PROGRAMS.put("std::substring", new YScriptProgramStructBundle(Arrays.asList(
                new YScriptProgramStruct(
                        "std::substring",
                        "dts::default",
                        new ProgramArgument[]{
                                new ProgramArgument("strArgument", "String", null),
                                new ProgramArgument("iStart", "Integer", null),
                                new ProgramArgument("iLength", "Integer", null)
                        },
                        new VariableType("String", null)
                ),
                new YScriptProgramStruct(
                        "std::substring",
                        "dts::default",
                        new ProgramArgument[]{
                                new ProgramArgument("strArgument", "String", null),
                                new ProgramArgument("iStart", "Integer", null)
                        },
                        new VariableType("String", null)
                )
        )));
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
            final YScriptProgramStructBundle defaultPrograms = BUILT_IN_PROGRAMS.get(programName);
            final Project project = element.getProject();
            final List<YScriptProgramStructBundle> yScriptProgramObjectLists = Objects.isNull(defaultPrograms) ? FileBasedIndex.getInstance().getValues(YScriptProgramNameFBIdx.KEY, programName, GlobalSearchScope.projectScope(project)) : Collections.singletonList(defaultPrograms);
            if (yScriptProgramObjectLists.size() == 0) {
                holder.createErrorAnnotation(yScriptCall.getTextRange(), "Unresolved program");
            } else {
                final YScriptFileContent yScriptFileContent = YScriptPsiImplUtil.getYScriptFileContent(yScriptCall);
                if (isProgramImportedOrUsed(yScriptFileContent, yScriptProgramObjectLists, programName)) {
                    if (!isCallMatched(yScriptCall, yScriptProgramObjectLists)) {
                        holder.createErrorAnnotation(yScriptCall.getTextRange(), "Signature does not match - Probably wrong number of arguments");
                    }
                } else if(Objects.isNull(defaultPrograms)) {
                    final Annotation callAnnotation = holder.createWarningAnnotation(yScriptCall.getTextRange(), "Program is not strictly imported");
                    final Collection<String> possibleImports = getPossibleImports(yScriptCall, yScriptProgramObjectLists);
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
            } else if (element instanceof YScriptCatchBlock) {
                final YScriptCatchBlock catchBlock = ((YScriptCatchBlock) element);
                if (catchBlock.getVar().getVarDef().getName().equals(leadPropertyBase.getName())) {
                    return true;
                }
            } else if ((element instanceof YScriptBlock || element instanceof YScriptBody) && (element.getParent() instanceof YScriptCreate || element.getParent() instanceof YScriptNew || element.getParent() instanceof YScriptWithObj)) {
                return true;
            } else if (element instanceof YScriptWithLoop) {
                final String varName = ((YScriptWithLoop) element).getVarName().getIdentifier().getText();
                if (varName.equals(leadPropertyBase.getName())) {
                    return true;
                }
            } else if (element instanceof YScriptExpressionDotted) {
                return true;
            }
            element = getPrev(element);
        }
        return false;
    }

    private static Collection<String> getPossibleImports(@NotNull final YScriptCall yScriptCall, @NotNull final List<YScriptProgramStructBundle> yScriptProgramStructBundleList) {
        final Set<String> possibleImports = new HashSet<>();
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

    private static boolean isProgramImportedOrUsed(@Nullable final YScriptFileContent yScriptFileContent, @NotNull final List<YScriptProgramStructBundle> yScriptProgramStructBundleList, @NotNull final String programName) {
        if (Objects.isNull(yScriptFileContent)) {
            return false;
        }
        for (YScriptProgram yScriptProgram : yScriptFileContent.getProgramList()) {
            if (yScriptProgram.getName().equalsIgnoreCase(programName)) {
                return true;
            }
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
