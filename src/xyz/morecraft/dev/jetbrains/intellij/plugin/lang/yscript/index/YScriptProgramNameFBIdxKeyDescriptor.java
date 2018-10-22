package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index;

import com.intellij.util.io.IOUtil;
import com.intellij.util.io.KeyDescriptor;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class YScriptProgramNameFBIdxKeyDescriptor implements KeyDescriptor<String>  {

    public static final YScriptProgramNameFBIdxKeyDescriptor INSTANCE = new YScriptProgramNameFBIdxKeyDescriptor();

    @Override
    public int getHashCode(final String value) {
        return value.hashCode();
    }

    @Override
    public boolean isEqual(final String val1, final String val2) {
        return val1.equals(val2);
    }

    @Override
    public void save(@NotNull final DataOutput storage, @NotNull final String value) throws IOException {
        IOUtil.writeUTF(storage, value);
    }

    @Override
    public String read(@NotNull final DataInput storage) throws IOException {
        return IOUtil.readUTF(storage);
    }

}
