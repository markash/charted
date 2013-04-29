package za.co.yellowfire.charted.pages.transaction;

import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.BeanModelSource;
import org.apache.tapestry5.services.PageRenderLinkSource;
import za.co.yellowfire.charted.domain.BudgetAllocation;
import za.co.yellowfire.charted.domain.DataException;
import za.co.yellowfire.charted.domain.Transaction;
import za.co.yellowfire.charted.domain.dao.BudgetAllocationDao;
import za.co.yellowfire.charted.domain.dao.TransactionDao;
import za.co.yellowfire.charted.pages.AbstractPage;
import za.co.yellowfire.charted.translator.AjaxLoopHolder;
import za.co.yellowfire.charted.translator.DomainObjectValueEncoder;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public class TransactionAllocate extends AbstractPage {

    @Persist
    private String transactionId;

    @Property
    private Transaction transaction;

    @Property
    private AjaxLoopHolder<BudgetAllocation> holder;

    @Property
    private List<AjaxLoopHolder<BudgetAllocation>> allocations;

    @Inject
    private TransactionDao transactionDao;

    @Inject
    private BudgetAllocationDao allocationDao;

    @Property
    private BeanModel<BudgetAllocation> allocationModel;


    @Property
    private AjaxLoopHolderEncoder allocationHolderEncoder;

    @Inject
    private BeanModelSource beanModelSource;

    @Inject
    private Messages messages;

    @Inject
    private PageRenderLinkSource pageRenderLinkSource;

    @InjectPage
    private TransactionList transactionList;

    private boolean inFormSubmission = false;

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public void setupRender() {
        allocationModel = beanModelSource.createDisplayModel(BudgetAllocation.class, messages);
        allocationModel.add("action", null);
    }

    public void onPrepareForRender() throws DataException {
        inFormSubmission = false;

        this.transaction = transactionDao.findById(transactionId);
        List<BudgetAllocation> list = allocationDao.findByTransactionId(transactionId);
        for (BudgetAllocation budgetAllocation : list) {
            this.allocations.add(new AjaxLoopHolder<BudgetAllocation>(budgetAllocation));
        }

    }

    public void onPrepareForSubmit() {
        inFormSubmission = true;
    }

    public BudgetAllocation onAddRow() {
        return new BudgetAllocation(0 - System.nanoTime(), null, transaction, new BigDecimal("0.00"));
    }

    public BudgetAllocation onRemoveRow(BudgetAllocation allocation) {
        System.out.println("allocation = " + allocation);
        return allocation;
    }

    public String getHideIfRemoved() {
        return holder.isRemoved() ? "display: none;" : "";
    }

    private class AjaxLoopHolderEncoder implements ValueEncoder<AjaxLoopHolder<BudgetAllocation>> {
        @Override
        public String toClient(AjaxLoopHolder<BudgetAllocation> value) {
            return Long.toString(value.getId());
        }

        @Override
        public AjaxLoopHolder<BudgetAllocation> toValue(String clientValue) {
            long id = Long.parseLong(clientValue);

            AjaxLoopHolder<BudgetAllocation> holder;
            if (id < 0) {
                holder = new AjaxLoopHolder<BudgetAllocation>(id, new BudgetAllocation(null, null, transaction, new BigDecimal("0.00")), false);
            } else {
                holder = new AjaxLoopHolder<BudgetAllocation>(allocationDao.findById(id));
            }
            return holder;
        }
    }

}
