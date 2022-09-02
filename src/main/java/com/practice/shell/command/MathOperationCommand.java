package com.practice.shell.command;

import java.io.PrintStream;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

import javax.validation.constraints.Size;

import org.springframework.shell.Availability;
import org.springframework.shell.CompletionContext;
import org.springframework.shell.CompletionProposal;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import org.springframework.shell.standard.ValueProvider;
import org.springframework.stereotype.Component;

@ShellComponent
@ShellCommandGroup("MathOperationCommands")
public class MathOperationCommand {
    
    private PrintStream console = System.out;
    private AtomicBoolean isConnected = new AtomicBoolean(false);

    public void write(String msg, String... args) {
        this.console.print("> ");
        this.console.printf(msg, (Object[]) args);
        this.console.println();
    }

    public Availability connectAvailability() {
        return !isConnected.get() 
            ? Availability.available()
            : Availability.unavailable("you are already connected!");
    }

    public Availability operationsAvailability() {
        return isConnected.get() 
            ? Availability.available()
            : Availability.unavailable("you are not connected!");
    }

    @ShellMethod
    @ShellMethodAvailability(value = "connectAvailability")
    public void connect(@Size(min=2, max=8) String key) {
        if(key.equals("me")) {
            isConnected.set(true);
            write("you connected...");
        }
        else {
            write("invalid key", key);
        }
    }

    @ShellMethod
    @ShellMethodAvailability(value = "operationsAvailability")
    public void disconnect(@Size(min=2, max=8) String key) {
        if(key.equals("me")) {
            isConnected.set(false);
            write("you disconnected...");
        }
        else {
            write("invalid key", key);
        }
    }

    @ShellMethodAvailability(value = "operationsAvailability")
    @ShellMethod(value = "Add two integers", key = "add")
    public int add(
        @ShellOption(defaultValue = "10", help = "\"a\" option is optional for add") int a,
        @ShellOption(valueProvider = BOptionValueProvider.class) int b
    ) {
        return a + b;
    }

    @ShellMethod(value = "sum two integers", key = "sum")
    public int sum(
        @ShellOption(defaultValue = "10", help = "\"a\" option is optional for add") int a,
        @ShellOption(valueProvider = BOptionValueProvider.class) int b
    ) {
        return a + b;
    }

    @ShellMethod(value = "Subtract two integers", key = "subtract")
    public int subtract(int a, int b) {
        return a - b;
    }

    @ShellMethod(value = "Multiply two integers", key = "multiply")
    public int multiply(int a, int b) {
        return a * b;
    }

    @ShellMethod(value = "Divide two integers", key = "divide")
    public int divide(int a, int b) {
        return a / b;
    }

}

@Component
class BOptionValueProvider implements ValueProvider {

    @Override
    public List<CompletionProposal> complete(CompletionContext completionContext) {
        
        String userInput = completionContext.currentWordUpToCursor();
        
        return Stream.of("31", "42", "53", "54")
            .filter(t -> t.contains(userInput))
            .map(CompletionProposal::new)
            .toList();
    }

}