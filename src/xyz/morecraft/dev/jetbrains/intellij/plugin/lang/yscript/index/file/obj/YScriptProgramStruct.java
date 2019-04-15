package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index.file.obj;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index.util.ProgramArgument;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index.util.VariableType;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.YScriptCall;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.psi.YScriptProgram;
import xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.util.YScriptUtil;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class YScriptProgramStruct {

    private final static String KEY_VOID = "_VOID_";

    private final String name;
    private final String packageName;
    private final ProgramArgument[] arguments;
    private final VariableType returnType;

    public YScriptProgramStruct(@NotNull final String name, @NotNull final String packageName, @NotNull final ProgramArgument[] argumentTypes, @Nullable final VariableType returnType) {
        this.name = name;
        this.packageName = packageName;
        this.arguments = argumentTypes;
        this.returnType = returnType;
    }

    public static YScriptProgramStruct create(@NotNull YScriptProgram yScriptProgram) {
        return new YScriptProgramStruct(
                yScriptProgram.getName(),
                YScriptUtil.getPackageName(yScriptProgram.getContainingFile()),
                YScriptUtil.getProgramArguments(yScriptProgram),
                YScriptUtil.getProgramReturnType(yScriptProgram)
        );
    }

    public String getName() {
        return name;
    }

    public String getPackageName() {
        return packageName;
    }

    public ProgramArgument[] getArguments() {
        return arguments;
    }

    public VariableType getReturnType() {
        return returnType;
    }

    public void save(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(name);
        dataOutput.writeUTF(packageName);
        dataOutput.writeInt(arguments.length);
        for (ProgramArgument argumentType : arguments) {
            dataOutput.writeUTF(argumentType.getName());
            dataOutput.writeUTF(argumentType.getType());
            dataOutput.writeUTF(Objects.isNull(argumentType.getXsdPath()) ? "" : argumentType.getXsdPath());
        }
        if (Objects.isNull(returnType)) {
            dataOutput.writeUTF(KEY_VOID);
        } else {
            dataOutput.writeUTF(returnType.getType());
            dataOutput.writeUTF(Objects.isNull(returnType.getXsdPath()) ? "" : returnType.getXsdPath());
        }
    }

    public static YScriptProgramStruct read(DataInput dataInput) throws IOException {
        final String name = dataInput.readUTF();
        final String packageName = dataInput.readUTF();
        final int argumentsCount = dataInput.readInt();
        final ProgramArgument[] arguments = new ProgramArgument[argumentsCount];
        for (int i = 0; i < argumentsCount; i++) {
//            final String xsdPath = dataInput.readUTF();
            final String argumentName = dataInput.readUTF();
            final String argumentType = dataInput.readUTF();
            final String argumentXsdPath = dataInput.readUTF();
            arguments[i] = new ProgramArgument(
                    argumentName,
                    argumentType,
                    argumentXsdPath.isEmpty() ? null : argumentXsdPath
            );
        }
        final String returnTypeType = dataInput.readUTF();
        if (KEY_VOID.equalsIgnoreCase(returnTypeType)) {
            return new YScriptProgramStruct(name, packageName, arguments, null);
        } else {
            final String returnTypeXsdPath = dataInput.readUTF();
            final VariableType returnType = new VariableType(
                    returnTypeType,
                    returnTypeXsdPath.isEmpty() ? null : returnTypeXsdPath
            );
            return new YScriptProgramStruct(name, packageName, arguments, returnType);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        YScriptProgramStruct that = (YScriptProgramStruct) o;
        return name.equals(that.name) &&
                Arrays.equals(arguments, that.arguments) &&
                Objects.equals(returnType, that.returnType);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, returnType);
        result = 31 * result + Arrays.hashCode(arguments);
        return result;
    }

    @SuppressWarnings("RedundantIfStatement")
    public boolean match(@NotNull final YScriptCall yScriptCall, final boolean strict) {
        if (!this.name.equalsIgnoreCase(yScriptCall.getPackage().getText())) {
            return false;
        }
        if (!strict) {
            return true;
        }
        if (Objects.isNull(yScriptCall.getArguments())) {
            return this.arguments.length == 0;
        }
        if (this.arguments.length != yScriptCall.getArguments().getExpressionList().size()) {
            return false;
        }
        return true;
    }

}
