package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index;

import com.intellij.psi.stubs.AbstractStubIndex;
import com.intellij.psi.stubs.StubIndexKey;
import com.intellij.util.io.KeyDescriptor;
import org.jetbrains.annotations.NotNull;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.YScriptProgram;

public class YScriptProgramNameFBIdx extends AbstractStubIndex<String, YScriptProgram> {

    private static final YScriptProgramNameFBIdx ourInstance = new YScriptProgramNameFBIdx();

    public static YScriptProgramNameFBIdx getInstance() {
        return ourInstance;
    }

    @NotNull
    @Override
    public StubIndexKey<String, YScriptProgram> getKey() {
        return YScriptStubIndexKeys.PROGRAM_NAME;
    }

    @Override
    public int getVersion() {
        return 3;
    }

    @NotNull
    @Override
    public KeyDescriptor<String> getKeyDescriptor() {
        return YScriptProgramNameFBIdxKeyDescriptor.INSTANCE;
    }

    @Override
    public int getCacheSize() {
        return 4 * 1024;
    }

}
