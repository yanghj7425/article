package com.yhj.chapter18;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class OSExecute {

    public static void command(String command) {
        boolean err = false;
        Process process = null;
        try {
            process = new ProcessBuilder(command.split(" ")).start();
            BufferedReader results = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String s;
            while ((s = results.readLine()) != null) {
                System.out.println(s);
            }
            BufferedReader errors = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((s = errors.readLine()) != null) {
                System.err.println(s);
                err = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            command("CMD /C " + command);
        }

    }
}
