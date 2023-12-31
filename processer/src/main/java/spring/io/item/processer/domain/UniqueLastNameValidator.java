package spring.io.item.processer.domain;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamSupport;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class UniqueLastNameValidator extends ItemStreamSupport implements Validator<Customer> {
    private Set<String> lastNames = new HashSet<>();

    @Override
    public void validate(Customer value) throws ValidationException {
        if (lastNames.contains(value.getLastName())) {
            throw new ValidationException("Duplication last name was found:  " + value.getLastName());
        }
        this.lastNames.add(value.getLastName());
    }

    @Override
    public void open(ExecutionContext executionContext) {
        String lastNames = getExecutionContextKey("lastNames");

        if (executionContext.containsKey(lastNames)) {
            this.lastNames = (Set<String>) executionContext.get(lastNames);
        }
    }

    @Override
    public void update(ExecutionContext executionContext) {
        Iterator<String> itr = lastNames.iterator();
        Set<String> copiedLastNames = new HashSet<String>();
        while (itr.hasNext()) {
            copiedLastNames.add(itr.next());
        }
        executionContext.put(getExecutionContextKey("lastNames"), copiedLastNames);
    }
}
