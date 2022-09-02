package com.practice.shell.command;

import org.springframework.shell.component.ConfirmationInput;
import org.springframework.shell.component.PathInput;
import org.springframework.shell.component.StringInput;
import org.springframework.shell.component.ConfirmationInput.ConfirmationInputContext;
import org.springframework.shell.component.PathInput.PathInputContext;
import org.springframework.shell.component.StringInput.StringInputContext;
import org.springframework.shell.standard.AbstractShellComponent;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellCommandGroup("InputCommands")
@ShellComponent
public class InputCommand extends AbstractShellComponent {
    
    @ShellMethod(value = "confirm input component", key = "confirm")
    public String confirmInput() {
        ConfirmationInput input = new ConfirmationInput(getTerminal(), "Enter value: ", false);
        input.setResourceLoader(getResourceLoader());
        input.setTemplateExecutor(getTemplateExecutor());
        ConfirmationInputContext context = input.run(ConfirmationInputContext.empty());
        return "got value: " + context.getResultValue();
    }

    @ShellMethod(value = "string input component", key = "str input")
    public String stringInput(@ShellOption(defaultValue = "false", help = "heelpful for credentials") boolean mask) {
        StringInput input = new StringInput(
            getTerminal(), "Enter string value", "default_"
        );
        input.setResourceLoader(getResourceLoader());
        input.setTemplateExecutor(getTemplateExecutor());
        if(mask) {
            input.setMaskCharater('*');
        }
        StringInputContext context = input.run(StringInputContext.empty());
        return "typed string: " + context.getResultValue();
    }

    @ShellMethod(value = "path input component", key = "path")
    public String pathInput() {
        PathInput input = new PathInput(getTerminal(), "Enter path");
        input.setResourceLoader(getResourceLoader());
        input.setTemplateExecutor(getTemplateExecutor());
        PathInputContext context = input.run(PathInputContext.empty());
        return "typed path: " + context.getResultValue().toString();
    }

}
