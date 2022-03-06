package io.github.jroy.droidcordexper.patches;

import io.github.jroy.apkpatcher.patcher.Patch;
import io.github.jroy.apkpatcher.patcher.PatchAttachPoint;

import java.util.Scanner;

public class CustomExperimentPatch extends Patch {
  public CustomExperimentPatch() {
    super("CUSTOM_EXPERIMENTS", false, true, "registeredExperiments", "public static final INSTANCE");
    addAttachmentPoint(new PatchAttachPoint(this, " {4}sput-object v[0-9][0-9]?, Lcom/discord/utilities/experiments/ExperimentRegistry;->registeredExperiments:Ljava/util/LinkedHashMap;") {
      @Override
      protected boolean applyLine(String matchedLine, Scanner scanner) {
        addPatchedLine("\t");
        addPatchedLine("\tinvoke-static {}, Lio/github/jroy/experiment/CustomExperimentRegister;->register()V");
        return true;
      }
    });
  }
}
