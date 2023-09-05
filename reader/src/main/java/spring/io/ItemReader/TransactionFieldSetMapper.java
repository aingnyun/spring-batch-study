package spring.io.ItemReader;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

public class TransactionFieldSetMapper implements FieldSetMapper<Transaction> {


    public Transaction mapFieldSet(FieldSet fieldSet) {
        Transaction trans = new Transaction();

        trans.setAccountNumber(fieldSet.readString("accountNumber"));
        trans.setAmount(fieldSet.readDouble("amount"));
        trans.setTransactionDate(fieldSet.readDate("transactionDate",
                "yyyy-MM-dd HH:mm:ss"));

        return trans;
    }
}
