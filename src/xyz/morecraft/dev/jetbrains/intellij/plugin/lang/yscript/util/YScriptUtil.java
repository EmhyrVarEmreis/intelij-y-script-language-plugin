package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.util;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.indexing.FileBasedIndex;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.YScriptFileType;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.YScriptFile;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.YScriptProgram;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.YScriptStructuralItem;

import java.util.*;

public class YScriptUtil {

    public static List<YScriptProgram> findPrograms(Project project, String programName) {
        List<YScriptProgram> result = null;
        Collection<VirtualFile> virtualFiles = FileBasedIndex.getInstance().getContainingFiles(
                FileTypeIndex.NAME,
                YScriptFileType.INSTANCE,
                GlobalSearchScope.allScope(project)
        );
        for (VirtualFile virtualFile : virtualFiles) {
            YScriptFile simpleFile = (YScriptFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (Objects.nonNull(simpleFile)) {
                YScriptStructuralItem[] properties = PsiTreeUtil.getChildrenOfType(simpleFile, YScriptStructuralItem.class);
                if (Objects.nonNull(properties)) {
                    for (YScriptStructuralItem property : properties) {
                        final YScriptProgram yScriptProgram = property.getProgram();
                        if (Objects.isNull(yScriptProgram)) {
                            continue;
                        }
                        if (programName.equals(yScriptProgram.getPackageName())) {
                            if (result == null) {
                                result = new ArrayList<>();
                            }
                            result.add(yScriptProgram);
                        }
                    }
                }
            }
        }
        return result != null ? result : Collections.emptyList();
    }

}
