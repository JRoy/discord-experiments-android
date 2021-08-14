package io.github.jroy.droidcordexper.patches;

import io.github.jroy.apkpatcher.patcher.Patch;
import io.github.jroy.apkpatcher.patcher.PatchAttachPoint;

import java.util.Scanner;

public class WidgetSettingsModelCompanionPatch extends Patch {
  public WidgetSettingsModelCompanionPatch() {
    super("SETTINGS_WIDGET_MODEL_COMPANION", "isPureEvilVisible");
    addAttachmentPoint(new PatchAttachPoint(this, " {4}invoke-virtual \\{p\\d+, p\\d+}, Lcom/discord/utilities/user/UserUtils;->isStaff\\(Lcom/discord/models/user/User;\\)Z") {
      @Override
      protected boolean applyLine(String matchedLine, Scanner scanner) {
        return searchOrNextLine(scanner, " {4}if-eqz p\\d+, :cond_\\d+") != null;
      }
    });
  }
}
