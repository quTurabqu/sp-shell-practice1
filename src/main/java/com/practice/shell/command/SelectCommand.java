package com.practice.shell.command;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.shell.component.MultiItemSelector;
import org.springframework.shell.component.SingleItemSelector;
import org.springframework.shell.component.MultiItemSelector.MultiItemSelectorContext;
import org.springframework.shell.component.SingleItemSelector.SingleItemSelectorContext;
import org.springframework.shell.component.support.SelectorItem;
import org.springframework.shell.standard.AbstractShellComponent;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class SelectCommand extends AbstractShellComponent {
    
    @ShellMethod(key = "select single", value = "single selector")
    public String singleSelector() {

        SelectorItem<String> item1 = SelectorItem.of("java", "backend");
        SelectorItem<String> item2 = SelectorItem.of("angular", "frontend");
        
        SingleItemSelector<String, SelectorItem<String>> singleItemSelector = new SingleItemSelector<>(getTerminal(), List.of(item1, item2), "Select", null);
        singleItemSelector.setResourceLoader(getResourceLoader());
        singleItemSelector.setTemplateExecutor(getTemplateExecutor());

        SingleItemSelectorContext<String, SelectorItem<String>> context = singleItemSelector.run(SingleItemSelectorContext.empty());
        return context.getResultItem().flatMap(item -> Optional.ofNullable(item.getItem())).get();
    }

    @ShellMethod(key = "select multi", value = "single selector")
    public String multiSelect() {
        SelectorItem<String> item1 = SelectorItem.of("java", "backend");
        SelectorItem<String> item2 = SelectorItem.of("angular", "frontend");
        SelectorItem<String> item3 = SelectorItem.of("react", "frontend");
        SelectorItem<String> item4 = SelectorItem.of("spring", "backend");

        MultiItemSelector<String, SelectorItem<String>> multiItemSelector = 
            new MultiItemSelector<>(getTerminal(), List.of(item1, item2, item3, item4), "Select", null);

        multiItemSelector.setResourceLoader(getResourceLoader());
        multiItemSelector.setTemplateExecutor(getTemplateExecutor());

        MultiItemSelectorContext<String, SelectorItem<String>> context = multiItemSelector.run(MultiItemSelectorContext.empty());

        return context.getResultItems().stream()
            .map(i -> String.format("%s -> %s", i.getName(), i.getItem()))
            .collect(Collectors.joining(", "));
    }

}
