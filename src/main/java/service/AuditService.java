package service;

import java.io.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AuditService {

    public AuditService() {
    }

    public void timeStamp(String actionName, String threadName){
        File newFile = new File("C:/Users/Andreea/IdeaProjects/PAO - vanzare de bilete online/audit.csv");

        if (newFile.length() == 0) {
            try (PrintWriter writer = new PrintWriter("C:/Users/Andreea/IdeaProjects/PAO - vanzare de bilete online/audit.csv")) {

                StringBuilder sb = new StringBuilder();
                sb.append("Action name");
                sb.append(", ");
                sb.append("Thread name");
                sb.append(", ");
                sb.append("Action timestamp");
                sb.append("\n");

                writer.write(sb.toString());
                writer.flush();

            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (FileWriter writer = new FileWriter("C:/Users/Andreea/IdeaProjects/PAO - vanzare de bilete online/audit.csv", true)) {

            writer.append(actionName);
            writer.append(", ");
            writer.append(threadName);
            writer.append(", ");
            String timeStamp = new SimpleDateFormat("dd.MM.yyyy HH.mm.ss").format(new Date());
            writer.append(timeStamp);
            writer.append("\n");
            writer.flush();

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

