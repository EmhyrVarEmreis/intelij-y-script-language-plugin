package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index;

import com.intellij.psi.stubs.*;
import com.intellij.util.io.StringRef;
import org.jetbrains.annotations.NotNull;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.YScriptFileContent;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.impl.YScriptFileContentImpl;

import java.io.IOException;

public class YScriptFileContentStubElementType extends IStubElementType<YScriptFileContentStub, YScriptFileContent> {

    public YScriptFileContentStubElementType() {
        super("PROGRAM", YScriptElementTypes.LANG);
    }

    @Override
    public YScriptFileContent createPsi(@NotNull YScriptFileContentStub stub) {
        return new YScriptFileContentImpl(stub, this);
    }

    @NotNull
    @Override
    public YScriptFileContentStub createStub(@NotNull YScriptFileContent yScriptFileContent, StubElement parentStub) {
        return new YScriptFileContentStubImpl(parentStub, yScriptFileContent.getName());
    }

    @NotNull
    @Override
    public String getExternalId() {
        return "yScript.file";
    }

    @Override
    public void serialize(@NotNull YScriptFileContentStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getPackageName());
    }

    @NotNull
    @Override
    public YScriptFileContentStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        final StringRef ref = dataStream.readName();
        //noinspection ConstantConditions
        return new YScriptFileContentStubImpl(parentStub, ref.getString());
    }

    @Override
    public void indexStub(@NotNull YScriptFileContentStub stub, @NotNull IndexSink indexSink) {
        indexSink.occurrence(YScriptStubIndexKeys.FILE_PACKAGE, stub.getPackageName());
    }

}
