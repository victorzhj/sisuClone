package fi.tuni.prog3.Sisu;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fi.tuni.prog3.sisu.networkHandler;
public class NetworkHandlerTest {
    String testFiles = Paths.get("src", "test","java","fi","tuni","prog3","Sisu","networkTestSamples").toFile().getAbsolutePath();
    networkHandler client = new networkHandler();

    @Test
    @DisplayName("Download module")
    public void downloadModule() throws IOException{
        String moduleId = "otm-32134ea3-611b-4a2a-ad5a-5c1340aef471";
        BufferedReader reader = new BufferedReader(new FileReader(testFiles+"\\moduleById.txt"));
        StringBuilder builder = new StringBuilder();
        String line ="";
        while((line = reader.readLine())!= null){
            builder.append(line);
        }
        reader.close();
        String corrResponse = builder.toString();
        String response = client.getModuleById(moduleId)[0];
        assertEquals(response, corrResponse);
    }

    @Test
    @DisplayName("Download module by groupId")
    public void downloadModuleByGroupId() throws IOException{
      String  id = "uta-ok-ykoodi-41176";
        BufferedReader reader = new BufferedReader(new FileReader(testFiles+"\\moduleByGroupId.txt"));
        StringBuilder builder = new StringBuilder();
        String line ="";
        while((line = reader.readLine())!= null){
            builder.append(line);
        }
        reader.close();
        String corrResponse = builder.toString();
    String response = client.getModuleByGroupId(id)[0];
    assertEquals(response, corrResponse);
    }

}
