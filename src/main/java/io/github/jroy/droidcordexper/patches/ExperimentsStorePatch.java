package io.github.jroy.droidcordexper.patches;

import io.github.jroy.apkpatcher.patcher.Patch;
import io.github.jroy.apkpatcher.patcher.PatchAttachPoint;

import java.util.Scanner;

public class ExperimentsStorePatch extends Patch {
  public ExperimentsStorePatch() {
    super("EXPERIMENTS_STORE", "getExperimentalAlpha", "isStaff");
    addAttachmentPoint(new PatchAttachPoint(this, " {4}invoke-virtual \\{v\\d+, v\\d+}, Lcom/discord/utilities/user/UserUtils;->isStaff\\(Lcom/discord/models/user/User;\\)Z") {
      @Override
      protected boolean applyLine(String matchedLine, Scanner scanner) {
        return searchOrNextLine(scanner, " {4}if-nez v\\d+, :cond_\\d+") != null && searchOrNextLine(scanner, " {4}if-eqz v\\d+, :cond_\\d+") != null;
      }
    });
  }
}
