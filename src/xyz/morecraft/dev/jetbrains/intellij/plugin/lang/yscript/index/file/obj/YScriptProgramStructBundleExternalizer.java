package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index.file.obj;

import com.intellij.util.io.DataExternalizer;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

public class YScriptProgramStructBundleExternalizer implements DataExternalizer<YScriptProgramStructBundle> {

    @Override
    public void save(@NotNull DataOutput dataOutput, YScriptProgramStructBundle programStructBundle) throws IOException {
        if (Objects.isNull(programStructBundle)) {
            dataOutput.writeInt(0);
            return;
        }
        final Collection<YScriptProgramStruct> programs = programStructBundle.getPrograms();
        dataOutput.writeInt(programs.size());
        for (YScriptProgramStruct yScriptProgramStruct : programs) {
            yScriptProgramStruct.save(dataOutput);
        }
    }

    @Override
    public YScriptProgramStructBundle read(@NotNull DataInput dataInput) throws IOException {
        final int size = dataInput.readInt();
        final YScriptProgramStructBundle programStructBundle = new YScriptProgramStructBundle();
        for (int i = 0; i < size; i++) {
            programStructBundle.add(YScriptProgramStruct.read(dataInput));
        }
        return programStructBundle;
    }

}
