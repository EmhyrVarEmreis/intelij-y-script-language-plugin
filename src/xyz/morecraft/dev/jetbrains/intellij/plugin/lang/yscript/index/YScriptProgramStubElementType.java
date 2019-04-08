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
                yScriptProgram.getName(),
                YScriptUtil.getPackageName(yScriptProgram.getContainingFile()),
                YScriptUtil.getProgramArguments(yScriptProgram),
                YScriptUtil.getProgramReturnType(yScriptProgram)
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
        dataStream.writeName(stub.getPackageName());
        dataStream.write(stub.getArguments().length);
        for (ProgramArgument argumentType : stub.getArguments()) {
            dataStream.writeName(argumentType.getName());
            dataStream.writeName(argumentType.getType());
            dataStream.writeName(Objects.isNull(argumentType.getXsdPath()) ? "" : argumentType.getXsdPath());
        }
        dataStream.writeName(stub.getReturnType().getType());
        dataStream.writeName(Objects.isNull(stub.getReturnType().getXsdPath()) ? "" : stub.getReturnType().getXsdPath());
    }

    @NotNull
    @Override
    public YScriptProgramStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        final String name = dataStream.readNameString();
        final String packageName = dataStream.readNameString();
        final int argumentsCount = dataStream.readVarInt();
        final ProgramArgument[] arguments = new ProgramArgument[argumentsCount];
        for (int i = 0; i < argumentsCount; i++) {
            final String xsdPath = dataStream.readNameString();
            arguments[i] = new ProgramArgument(
                    dataStream.readNameString(),
                    dataStream.readNameString(),
                    Objects.isNull(xsdPath) || xsdPath.isEmpty() ? null : xsdPath
            );
        }
        final String xsdPath = dataStream.readNameString();
        final VariableType returnType = new VariableType(
                dataStream.readNameString(),
                Objects.isNull(xsdPath) || xsdPath.isEmpty() ? null : xsdPath
        );
        return new YScriptProgramStubImpl(parentStub, name, packageName, arguments, returnType);
    }

    @Override
    public void indexStub(@NotNull YScriptProgramStub stub, @NotNull IndexSink indexSink) {
        indexSink.occurrence(YScriptStubIndexKeys.PROGRAM_NAME, stub.getName());
    }

}
