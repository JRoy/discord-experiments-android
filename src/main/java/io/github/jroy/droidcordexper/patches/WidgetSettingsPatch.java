package io.github.jroy.droidcordexper.patches;

import io.github.jroy.apkpatcher.patcher.Patch;
import io.github.jroy.apkpatcher.patcher.PatchAttachPoint;
import io.github.jroy.apkpatcher.util.SearchNextLineToken;

import java.util.Scanner;

public class WidgetSettingsPatch extends Patch {
  public WidgetSettingsPatch() {
    super("SETTINGS_WIDGET", "WidgetSettings$Model;->getMeUser");
    addAttachmentPoint(new PatchAttachPoint(this, " {4}invoke-virtual \\{p\\d+}, Lcom/discord/widgets/settings/WidgetSettings\\$Model;->getMeUser\\(\\)Lcom/discord/models/user/MeUser;") {
      @Override
      protected boolean applyLine(String matchedLine, Scanner scanner) {
        SearchNextLineToken token = searchOrNextLine(scanner, " {4}if-nez v\\d+, :cond_\\d+");
        if (token == null) {
          return false;
        }

        final String[] split = token.matchedLine().split(":");
        if (split.length != 2) {
          return false;
        }

        addPatchedLine("\tgoto :" + split[1]);
        return true;
      }
    });
  }
}
