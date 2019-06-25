package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index;

import com.intellij.psi.stubs.*;
import org.jetbrains.annotations.NotNull;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index.util.ProgramArgument;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index.util.VariableType;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.YScriptProgram;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.impl.YScriptProgramImpl;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.util.YScriptUtil;

import java.io.IOException;
import java.util.Objects;

public class YScriptProgramStubElementType extends IStubElementType<YScriptProgramStub, YScriptProgram> {

    public YScriptProgramStubElementType() {
        super("PROGRAM", YScriptElementTypes.LANG);
    }

    @Override
    public YScriptProgram createPsi(@NotNull YScriptProgramStub stub) {
        return new YScriptProgramImpl(stub, this);
    }

    @NotNull
    @Override
    public YScriptProgramStub createStub(@NotNull YScriptProgram yScriptProgram, StubElement parentStub) {
        return new YScriptProgramStubImpl(
                parentStub,
                yScriptProgram.getName()
        );
    }

    @NotNull
    @Override
    public String getExternalId() {
        return "yScript.program";
    }

    @Override
    public void serialize(@NotNull YScriptProgramStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public YScriptProgramStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        final String name = dataStream.readNameString();
        return new YScriptProgramStubImpl(parentStub, name);
    }

    @Override
    public void indexStub(@NotNull YScriptProgramStub stub, @NotNull IndexSink indexSink) {
        indexSink.occurrence(YScriptStubIndexKeys.PROGRAM_NAME, stub.getName());
    }

}
