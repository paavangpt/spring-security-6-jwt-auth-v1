package com.filoshare.app.utils;

import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class PrintFormatter {
    
    public void print(Object output) {
        System.out.println("#################\n\n");
        System.out.println(output);
        System.out.println("\n\n#################");
    }

}
