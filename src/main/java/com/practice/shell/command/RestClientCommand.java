package com.practice.shell.command;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.text.MessageFormat;
import java.time.Duration;

import org.springframework.shell.standard.AbstractShellComponent;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import com.practice.shell.exception.RestClientException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ShellComponent
public class RestClientCommand extends AbstractShellComponent {    

    private final HttpClient httpClient;

    @ShellMethod(key = "tasks get", value = "getting tasks from the remote TASK server via REST api")
    public void getTasks() {
            getTerminal().writer().write(String.format(">> %s getTasks\n", this.getClass().getSimpleName()));

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8091/api/v1/tasks"))    
                .GET()
                .build();

            HttpResponse<String> response = sendRequest(request, BodyHandlers.ofString());
            showResponseResult(response);
            
    }

    @ShellMethod(key = "tasks create", value = "creating new task")
    public void createTask(@ShellOption String n) throws FileNotFoundException {
        getTerminal().writer().write(String.format(">> %s createTask\n", this.getClass().getSimpleName()));
    
        String requestBody = MessageFormat.format("'{'\"name\": \"{0}\"'}'", n);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8091/api/v1/tasks"))
            .POST(BodyPublishers.ofString(requestBody))
            .header("Content-Type", "application/json")
            .timeout(Duration.ofSeconds(2))
            .build();
        
        HttpResponse<String> response = sendRequest(request, BodyHandlers.ofString());
        showResponseResult(response);
    }

    // *******************************************************************************************

    private<T> HttpResponse<T> sendRequest(HttpRequest httpRequest, HttpResponse.BodyHandler<T> bodyHandler) {
        try {
            HttpResponse<T> response = httpClient.send(httpRequest, bodyHandler);
            return response;
        } catch (ConnectException exc) {
            getTerminal().writer().write(String.format(">> failure(connection-exception): %s\n", exc.getMessage()));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } 
        throw new RestClientException(">> can't send request: ");
    }

    private <T> void showResponseResult(HttpResponse<T> response) {
        if(response.statusCode() >= 200 && response.statusCode() < 300) {
            getTerminal().writer().write(String.format(">> success(status: %d): %s\n", response.statusCode(), response.body()));
        }
        else {
            getTerminal().writer().write(String.format(">> failure(status: %d): %s\n", response.statusCode(), response.body()));
        }
    }

}
