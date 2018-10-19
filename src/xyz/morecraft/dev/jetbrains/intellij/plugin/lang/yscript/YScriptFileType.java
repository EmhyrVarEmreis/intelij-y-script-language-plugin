package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class YScriptFileType extends LanguageFileType {

    public static final YScriptFileType INSTANCE = new YScriptFileType();

    private YScriptFileType() {
        super(YScript.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "Y File";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "YScript File";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "y";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return YScriptIcons.FILE;
    }

}
