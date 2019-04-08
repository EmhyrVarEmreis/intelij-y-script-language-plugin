package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFileFactory;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.YScriptFileType;

public class YScriptElementFactory {

    public static YScriptImport createImport(Project project, String packageName) {
        final YScriptFile file = createFile(project, "IMPORT " + packageName + ";");
        return (YScriptImport) file.getFirstChild().getFirstChild().getFirstChild();
    }

    public static PsiElement createCRLF(Project project) {
        final YScriptFile file = createFile(project, "\n");
        return file.getFirstChild();
    }

    public static YScriptFile createFile(Project project, String text) {
        final String name = "tmp-tmp-tmp.y";
        return (YScriptFile) PsiFileFactory.getInstance(project).
                createFileFromText(name, YScriptFileType.INSTANCE, text);
    }

}
