package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index;

import com.intellij.psi.stubs.*;
import com.intellij.util.io.StringRef;
import org.jetbrains.annotations.NotNull;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.YScriptProgram;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.impl.YScriptProgramImpl;

import java.io.IOException;

public class YScriptProgramStubElementType extends IStubElementType<YScriptProgramStubElement, YScriptProgram> {

    public YScriptProgramStubElementType() {
        super("PROGRAM", YScriptElementTypes.LANG);
    }

    @Override
    public YScriptProgram createPsi(@NotNull YScriptProgramStubElement stub) {
        return new YScriptProgramImpl(stub, this);
    }

    @NotNull
    @Override
    public YScriptProgramStubElement createStub(@NotNull YScriptProgram yScriptProgram, StubElement parentStub) {
        return new YScriptProgramStubElementImpl(parentStub, yScriptProgram.getPackageName());
    }

    @NotNull
    @Override
    public String getExternalId() {
        return "yScript.program";
    }

    @Override
    public void serialize(@NotNull YScriptProgramStubElement stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getPackageName());
    }

    @NotNull
    @Override
    public YScriptProgramStubElement deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        final StringRef ref = dataStream.readName();
        //noinspection ConstantConditions
        return new YScriptProgramStubElementImpl(parentStub, ref.getString());
    }

    @Override
    public void indexStub(@NotNull YScriptProgramStubElement stub, @NotNull IndexSink indexSink) {
        indexSink.occurrence(YScriptStubIndexKeys.PROGRAM_NAME, stub.getPackageName());
    }

}
