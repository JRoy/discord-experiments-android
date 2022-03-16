import com.google.common.io.CharStreams;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public abstract class VersionCheckTask extends DefaultTask {
  @TaskAction
  public void checkVersion() throws IOException {
    long previousVersion = 0;

    final File versionFile = new File(System.getProperty("user.dir"), "version.txt");
    if (versionFile.exists()) {
      previousVersion = Long.parseLong(FileUtils.readFileToString(versionFile, StandardCharsets.UTF_8).trim());
    }

    try (final CloseableHttpClient client = HttpClients.createDefault()) {
      final HttpPost post = new HttpPost("https://www.apkmirror.com/wp-json/apkm/v1/app_exists/");
      post.addHeader("User-Agent", "APKUpdater-v2.0.5");
      post.addHeader("Authorization", "Basic YXBpLWFwa3VwZGF0ZXI6cm01cmNmcnVVakt5MDRzTXB5TVBKWFc4");
      post.addHeader("Content-type", "application/json");
      post.setEntity(new StringEntity("{\"pnames\": [\"com.discord\"]}", ContentType.APPLICATION_JSON));
      final CloseableHttpResponse existsResponse = client.execute(post);
      final String content = CharStreams.toString(new InputStreamReader(existsResponse.getEntity().getContent()));
      existsResponse.close();

      final JsonObject apk = JsonParser.parseString(content).getAsJsonObject().get("data").getAsJsonArray().get(0)
          .getAsJsonObject().get("apks").getAsJsonArray().get(0).getAsJsonObject();

      final long versionCode = Long.parseLong(apk.get("version_code").getAsString());
      if (previousVersion >= versionCode) {
        throw new RuntimeException("No new version available.");
      }

      FileUtils.writeStringToFile(versionFile, String.valueOf(versionCode), StandardCharsets.UTF_8);

      final HttpGet idGet = new HttpGet("https://www.apkmirror.com" + apk.get("link").getAsString() + "download/");
      idGet.addHeader("User-Agent", "APKUpdater-v2.0.5");
      idGet.addHeader("Authorization", "Basic YXBpLWFwa3VwZGF0ZXI6cm01cmNmcnVVakt5MDRzTXB5TVBKWFc4");
      final CloseableHttpResponse downloadResponse = client.execute(idGet);
      final BufferedReader br = new BufferedReader(new InputStreamReader(downloadResponse.getEntity().getContent()));

      String id = null;
      String line;
      while ((line = br.readLine()) != null) {
        if (line.contains("shortlink")) {
          id = line.split("/\\?p=")[1].split("'")[0];
          break;
        }
      }
      br.close();
      downloadResponse.close();

      if (id == null) {
        throw new IllegalStateException();
      }

      final HttpGet apkGet = new HttpGet("https://www.apkmirror.com/wp-content/themes/APKMirror/download.php?id=" + id);
      apkGet.addHeader("User-Agent", "APKUpdater-v2.0.5");
      apkGet.addHeader("Authorization", "Basic YXBpLWFwa3VwZGF0ZXI6cm01cmNmcnVVakt5MDRzTXB5TVBKWFc4");
      final CloseableHttpResponse apkResponse = client.execute(apkGet);
      FileUtils.copyInputStreamToFile(apkResponse.getEntity().getContent(), new File(System.getProperty("user.dir"), "downloaded.apk"));
      apkResponse.close();
    }
  }
}
