package io.github.jroy.droidcordexper;

import io.github.jroy.apkpatcher.ApkPatcher;
import io.github.jroy.apkpatcher.ApkPatcherException;
import io.github.jroy.apkpatcher.patcher.FileInjector;
import io.github.jroy.apkpatcher.patcher.IApply;
import io.github.jroy.apkpatcher.util.Logger;
import io.github.jroy.droidcordexper.patches.CustomExperimentPatch;
import io.github.jroy.droidcordexper.patches.ExperimentsStorePatch;
import io.github.jroy.droidcordexper.patches.WidgetSettingsModelCompanionPatch;
import io.github.jroy.droidcordexper.patches.WidgetSettingsPatch;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.helper.HelpScreenException;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import java.io.File;
import java.io.IOException;

public class DiscordPatcher {
  public static void main(String[] args) throws ApkPatcherException, IOException, ArgumentParserException {
    ArgumentParser parser = ArgumentParsers.newFor("DiscordPatcher").build().defaultHelp(true);
    parser.addArgument("--input-apk").help("Sets the input apk file name.").setDefault("input.apk").action(Arguments.store());
    parser.addArgument("--output-apk").help("Sets the output apk file name.").setDefault("patched.apk").action(Arguments.store());
    parser.addArgument("--keystore-file").help("Sets the keystore file name.").setDefault("default.keystore").action(Arguments.store());
    parser.addArgument("--keystore-alias").help("Sets the keystore alias.").setDefault("android").action(Arguments.store());
    parser.addArgument("--keystore-pass").help("Sets the keystore password.").setDefault("password").action(Arguments.store());
    parser.addArgument("--key-pass").help("Sets the key password.").setDefault("password").action(Arguments.store());

    Namespace namespace;
    try {
      namespace = parser.parseArgs(args);
      if (namespace == null) {
        Logger.error("Namespace is null!");
        System.exit(1);
        return;
      }
    } catch (HelpScreenException e) {
      System.exit(0);
      return;
    }

    new ApkPatcher(new File(namespace.getString("input_apk")),
        new File(namespace.getString("output_apk")),
        false,
        false,
        false,
        "com",
        new File(namespace.getString("keystore_file")),
        namespace.getString("keystore_alias"),
        namespace.getString("keystore_pass"),
        namespace.getString("key_pass"),
        new IApply[]{
            new ExperimentsStorePatch(),
            new WidgetSettingsPatch(),
            new WidgetSettingsModelCompanionPatch(),
            new CustomExperimentPatch(),
            new FileInjector("CUSTOM_EXPERIMENT_INJECTOR", DiscordPatcher.class, "CustomExperimentRegister.smali", "smali_classes3/io/github/jroy/experiment/CustomExperimentRegister.smali")
        })
        .patch();
  }
}
