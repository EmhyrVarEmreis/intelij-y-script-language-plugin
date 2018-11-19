package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index;

import com.intellij.psi.stubs.AbstractStubIndex;
import com.intellij.psi.stubs.StubIndexKey;
import com.intellij.util.io.KeyDescriptor;
import org.jetbrains.annotations.NotNull;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.YScriptFileContent;

public class YScriptFileContentFBIdx extends AbstractStubIndex<String, YScriptFileContent> {

    private static final YScriptFileContentFBIdx ourInstance = new YScriptFileContentFBIdx();

    public static YScriptFileContentFBIdx getInstance() {
        return ourInstance;
    }

    @NotNull
    @Override
    public StubIndexKey<String, YScriptFileContent> getKey() {
        return YScriptStubIndexKeys.FILE_PACKAGE;
    }

    @Override
    public int getVersion() {
        return 3;
    }

    @NotNull
    @Override
    public KeyDescriptor<String> getKeyDescriptor() {
        return YScriptStringKeyDescriptor.INSTANCE;
    }

    @Override
    public int getCacheSize() {
        return 1024;
    }

}
