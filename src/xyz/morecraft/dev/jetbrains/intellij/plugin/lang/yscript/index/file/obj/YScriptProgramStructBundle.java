package xyz.morecraft.dev.jetbrains.intellij.plugin.lang.yscript.index.file.obj;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class YScriptProgramStructBundle {

    private Set<YScriptProgramStruct> programs;

    public YScriptProgramStructBundle() {
        this(Collections.emptyList());
    }

    public YScriptProgramStructBundle(Collection<YScriptProgramStruct> programs) {
        this.programs = new HashSet<>();
        this.programs.addAll(programs);
    }

    public void add(YScriptProgramStruct program) {
        this.programs.add(program);
    }

    public Collection<YScriptProgramStruct> getPrograms() {
        return this.programs;
    }

}
