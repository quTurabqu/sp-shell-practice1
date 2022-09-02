package com.practice.shell.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;

import org.springframework.shell.standard.AbstractShellComponent;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@ShellCommandGroup(value = "ExecuteCommand")
public class ExecuteCommand extends AbstractShellComponent {
    
    @ShellMethod(key = "ip", value = "getting ip configurations")
    public void ipconfig() {
        
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("ipconfig");// "cmd", "/c", "dir", "C:\\Users\\Turab\\Desktop\\notes");

            Process process = processBuilder.start(); // Runtime.getRuntime().exec("ipconfig");
            // int exitValue = process.waitFor();
            // if(exitValue == 0) {
            //     // success
            // }
            // else {
            //     // failure
            // }
            write(process.getInputStream());

        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    @ShellMethod(key = "p", value = "path")
    public void commands(
        @ShellOption(defaultValue = "C:\\Users\\Turab\\Desktop\\utils\\learn-path\\shell\\sp-shell-practice1") String d,
        @ShellOption(defaultValue = "dir") String c) {
        
        try {
            // p --p "manet.txt" --d C:\\Users\\Turab\\Desktop\\notes
            // to shootdown process
            // netstat -ano | findstr :<PORT>   then  ---> taskkill /PID <PID> /F 

            // use \ in order to specify whitespace for-ex: p --c cd\ C:\\Users\\Turab

            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("cmd.exe", "/c", c);
            processBuilder.directory(Paths.get(d).toFile());
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start(); // Runtime.getRuntime().exec("ipconfig");
            // int exitValue = process.waitFor();
            // if(exitValue == 0) {// success } else {// failure }

            write("pid: " + process.pid());
            write(process.getInputStream());

        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    @ShellMethod(key = "pid", value = "show PID according to port")
    public void showPID(String port) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("cmd.exe", "/c", "netstat -ano | findstr :" + port);
        processBuilder.redirectErrorStream(true);

        try {
            Process process = processBuilder.start();
            write(process.getInputStream());

        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    @ShellMethod(key = "kill", value = "kill process according to PID")
    public void killProcess(String pid) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("cmd.exe", "/c", String.format("taskkill /PID %s %s", pid, "/F"));
        processBuilder.redirectErrorStream(true);

        try {
            Process process = processBuilder.start();
            write(process.getInputStream());

        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    @ShellMethod(key = "jar", value = "run jar")
    public void runJar(String p) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("cmd.exe", "/c", "java -jar " + p);
        processBuilder.redirectErrorStream(true);

        try {
            Process process = processBuilder.start();
            write(process.getInputStream());

        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    private void write(InputStream in) throws IOException {
        try(
            BufferedReader reader = new BufferedReader(new InputStreamReader(in))
        ) {
            String line;			
            while ((line = reader.readLine())!= null) {
                getTerminal().writer().write(line + "\n");
                getTerminal().writer().flush();
            }
        }
    }

    private void write(String output) {
        getTerminal().writer().write(output + "\n");
        getTerminal().writer().flush();
    }

}
