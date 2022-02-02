import org.apache.commons.io.FileUtils;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParseExperimentsTask extends DefaultTask {
  @TaskAction
  public void parseExperiments() throws IOException {
    final List<String> currentExperiments = new ArrayList<>();

    final File experimentsFile = new File(System.getProperty("user.dir"), "experiments.txt");
    if (experimentsFile.exists()) {
      Collections.addAll(currentExperiments, FileUtils.readFileToString(experimentsFile, StandardCharsets.UTF_8).split(","));
    }

    final List<String> parsedExperiments = new ArrayList<>();
    final Map<String, String> newExperiments = new HashMap<>();

    final File registryFile = Path.of(System.getProperty("user.dir"), "output", "smali_classes2", "com", "discord", "utilities", "experiments", "ExperimentRegistry.smali").toFile();
    if (registryFile.exists()) {
      try (BufferedReader br = new BufferedReader(new FileReader(registryFile))) {
        String line;
        while ((line = br.readLine()) != null) {
          if (line.contains("listOf")) {
            for (int i = 0; i < 4; i++) {
              line = br.readLine();
            }

            if (line == null || !line.contains("const-string")) {
              break;
            }
            String name = line.substring(line.indexOf("\"") + 1, line.length() - 1);

            for (int i = 0; i < 2; i++) {
              line = br.readLine();
            }

            if (line == null || !line.contains("const-string")) {
              break;
            }
            String key = line.substring(line.indexOf("\"") + 1, line.length() - 1);

            parsedExperiments.add(key);
            if (!currentExperiments.remove(key)) {
              newExperiments.put(key, name);
            }
          }
        }
      }
    }

    final StringBuilder sb = new StringBuilder();
    sb.append("Patched version based on release : ").append(FileUtils.readFileToString(new File(System.getProperty("user.dir"), "version.txt"), StandardCharsets.UTF_8).trim()).append("\n\n");

    final File experimentsDetails = new File(System.getProperty("user.dir"), "experiments.md");
    if (newExperiments.isEmpty() && currentExperiments.isEmpty()) {
      sb.append("This version has no new or removed experiments.");
      FileUtils.writeStringToFile(experimentsDetails, sb.toString(), StandardCharsets.UTF_8);
      return;
    }

    if (!newExperiments.isEmpty()) {
      sb.append("## New experiments:\n");
      for (final Map.Entry<String, String> entry : newExperiments.entrySet()) {
        sb.append("- **").append(entry.getValue()).append("** `").append(entry.getKey()).append("`\n");
      }
      sb.append("\n");
    }

    if (!currentExperiments.isEmpty()) {
      sb.append("## Removed experiments:\n");
      for (final String experiment : currentExperiments) {
        sb.append("- `").append(experiment).append("`\n");
      }
    }

    FileUtils.writeStringToFile(experimentsDetails, sb.toString(), StandardCharsets.UTF_8);
    FileUtils.writeStringToFile(experimentsFile, String.join(",", parsedExperiments), StandardCharsets.UTF_8);
  }
}
