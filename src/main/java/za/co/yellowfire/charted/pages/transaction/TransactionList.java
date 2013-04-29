package za.co.yellowfire.charted.pages.transaction;

import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.BeanModelSource;
import org.apache.tapestry5.services.PageRenderLinkSource;
import za.co.yellowfire.charted.database.DataAccessException;
import za.co.yellowfire.charted.domain.*;
import za.co.yellowfire.charted.domain.dao.BudgetDao;
import za.co.yellowfire.charted.domain.dao.TransactionDao;
import za.co.yellowfire.charted.domain.grid.TransactionDataSource;

import java.util.Arrays;
import java.util.List;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
@Import(library={"context:js/bootstrap.js"})
public class TransactionList {

    @Inject
    private TransactionDao transactionDao;

    @Property
    private Transaction transaction;

    @Property
    private TransactionDataSource transactionDataSource;

    @Property
    private BeanModel<Transaction> transactionModel;

    @Inject
    private BeanModelSource beanModelSource;

    @Inject
    private Messages messages;

    @Inject
    private PageRenderLinkSource pageRenderLinkSource;

    @InjectPage
    private TransactionAllocate transactionAllocate;

    public void onActivate(EventContext eventContext) throws DataException {
        transactionDataSource = new TransactionDataSource(transactionDao.findAll());
    }

    public Object onActionFromAllocate(String transactionId) {
        transactionAllocate.setTransactionId(transactionId);
        return transactionAllocate;
    }

    public void setupRender() {
        transactionModel = beanModelSource.createDisplayModel(Transaction.class, messages);
        transactionModel.add("action", null);
        transactionModel.include("id", "datePosted", "memo", "amount", "action");
        transactionModel.get("id").label("Id");
        transactionModel.get("amount").label("Amount");
        transactionModel.get("datePosted").label("Date");
        transactionModel.get("memo").label("Memo");
    }
    
    public List<MenuSection> getMenuSections() {
        return Arrays.asList(
                new MenuSection(
                        "Budgeting",
                        new MenuItem[]{
                                new MenuItem("Current Budget", "budget/current"),
                                new MenuItem("Statement Upload", "statement/upload"),
                                new MenuItem("Transactions", "transaction/list"),
                        })
        );
    }
}
