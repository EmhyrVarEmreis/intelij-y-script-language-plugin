package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import org.jetbrains.annotations.NotNull;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.YScript;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.YScriptFileType;

import javax.swing.*;

public class YScriptFile extends PsiFileBase {

    public YScriptFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, YScript.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return YScriptFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "YScript File";
    }

    @Override
    public Icon getIcon(int flags) {
        return super.getIcon(flags);
    }

}
