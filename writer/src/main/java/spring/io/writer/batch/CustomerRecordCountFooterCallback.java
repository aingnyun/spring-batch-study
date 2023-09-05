package spring.io.writer.batch;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.batch.item.file.FlatFileFooterCallback;
import org.springframework.stereotype.Component;
import spring.io.writer.domain.Customer;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

@Component
@Aspect
public class CustomerRecordCountFooterCallback implements FlatFileFooterCallback {
    private int itemsWrittenInCurrentFile = 0;
    
    @Override
    public void writeFooter(Writer writer) throws IOException {
        writer.write("This file containsd " + itemsWrittenInCurrentFile +  "items");
    }

    @Before("execution(* org.springframework.batch.item.file.FlatFileItemWriter.write(..))")
    public void beforeWrite(JoinPoint joinPoint) {
        List<Customer> items = (List<Customer>) joinPoint.getArgs()[0];

        this.itemsWrittenInCurrentFile += items.size();
    }

    @Before("execution(* org.springframework.batch.item.file.FlatFileItemWriter.open(..))")
    public void resetCounter() {
        this.itemsWrittenInCurrentFile = 0;
    }
}
