package mysql.batchjpa.config;

import mysql.batchjpa.model.MultiUsers;
import org.springframework.batch.item.ItemProcessor;

public class UserItemProcessor implements ItemProcessor<MultiUsers, MultiUsers> {
    @Override
    public MultiUsers process(MultiUsers item) throws Exception {
        return item;
    }
}
