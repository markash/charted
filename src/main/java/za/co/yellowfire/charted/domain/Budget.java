package za.co.yellowfire.charted.domain;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import za.co.yellowfire.charted.database.DataAccessException;
import za.co.yellowfire.charted.database.TransactionManager;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * @author Mark P Ashworth
 * @version 0.1.0
 */
@Entity(name = "Budget")
@Table(name = "budget")
@Access(AccessType.FIELD)
public class Budget implements Serializable {

    public static final String DEFAULT_UNALLOCATED_BUDGET_SECTION = BudgetSection.DEFAULT_UNALLOCATED;
    public static final String DEFAULT_GOD_BUDGET_SECTION = "GOD";
    public static final String DEFAULT_EARN_BUDGET_SECTION = "Earn";
    public static final String DEFAULT_DEBT_BUDGET_SECTION = "Debt";
    public static final String DEFAULT_HOUSE_BUDGET_SECTION = "House";
    public static final String DEFAULT_TRANSPORT_BUDGET_SECTION = "Transport";
    public static final String DEFAULT_CHILDREN_BUDGET_SECTION = "Children";
    public static final String DEFAULT_TELECOMS_BUDGET_SECTION = "Telecoms";
    public static final String DEFAULT_MEDICAL_BUDGET_SECTION = "Medical";
    public static final String DEFAULT_BANK_BUDGET_SECTION = "Bank";
    public static final String DEFAULT_PERSONAL_BUDGET_SECTION = "Personal";

    public static final String[] DEFAULT_UNALLOCATED_CATEGORIES = {BudgetCategory.DEFAULT_UNALLOCATED};
    public static final String[] DEFAULT_EARN_CATEGORIES = {"Salary", "Bank"};
    public static final String[] DEFAULT_GOD_CATEGORIES = {"Tithe", "Faith Promise"};
    public static final String[] DEFAULT_DEBT_CATEGORIES = {"House", "Vehicle", "Absa Credit Card", "Discovery Credit Card"};
    public static final String[] DEFAULT_HOUSE_CATEGORIES = {"Water & Lights", "Outsurance", "Momentum", "Axon (FRSA)", "Maid", "Gardener", "Food (Out)", "Food (In)", "Dog Food"};
    public static final String[] DEFAULT_TRANSPORT_CATEGORIES = {"Petrol", "Train", "Toll Fees"};
    public static final String[] DEFAULT_CHILDREN_CATEGORIES = {"School Fees", "Discovery Protector", "Maintenance", "Afterschool activities"};
    public static final String[] DEFAULT_TELECOMS_CATEGORIES = {"MWeb", "Vodacom", "MTN", "WebOnline", "Telkom", "CellC"};
    public static final String[] DEFAULT_MEDICAL_CATEGORIES = {"Vitality", "Virgin Active", "Medicine", "Doctors Fees", "Vet Fees"};
    public static final String[] DEFAULT_BANK_CATEGORIES = {"Bank Fees"};
    public static final String[] DEFAULT_PERSONAL_CATEGORIES = {"Haircut", "Books", "Nails"};

    @Id
    private Long id;

    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @OneToOne
    private Objective objective;

    @OneToMany(targetEntity = BudgetSection.class, cascade = CascadeType.ALL)
    private List<BudgetSection> sections;

    public Budget() { }

    public Budget(Date startDate, Date endDate, Objective objective, BudgetSection...sections) {
        this(null, startDate, endDate, objective, Arrays.asList(sections));
    }

    public Budget(Long id, Date startDate, Date endDate, Objective objective, BudgetSection...sections) {
        this(id, startDate, endDate, objective, Arrays.asList(sections));
    }

    public Budget(Long id, Date startDate, Date endDate, Objective objective, List<BudgetSection> sections) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.objective = objective;
        this.sections = new ArrayList<BudgetSection>(sections);
        for (BudgetSection section : sections) {
            section.setBudget(this);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        if (this.endDate == null) {
            //Add a month and subtract a day to get the last day of the month
            Calendar calendar = GregorianCalendar.getInstance();
            calendar.setTime(firstOfTheMonth(this.endDate));
            calendar.add(Calendar.MONTH, 1);
            calendar.add(Calendar.DATE, -1);
            this.endDate = calendar.getTime();
        }
        return this.endDate;
    }

    public Objective getObjective() {
        return objective;
    }

    public void setObjective(Objective objective) {
        this.objective = objective;
    }

    public List<BudgetSection> getSections() {
        return new ArrayList<BudgetSection>(sections);
    }

    public void setSections(List<BudgetSection> sections) {
        this.sections = new ArrayList<BudgetSection>(sections);
    }

    public List<BudgetSection> getIncomeSections() {
        List<BudgetSection> results = new ArrayList<BudgetSection>();
        for (BudgetSection section : this.sections) {
            if (section.getDirection() == CashFlowDirection.INCOME) {
                results.add(section);
            }
        }
        Collections.sort(results, new Comparator<BudgetSection>() {
            @Override
            public int compare(BudgetSection o1, BudgetSection o2) {
                final String s1 = o1 != null && o1.getName() != null ? o1.getName() : null;
                final String s2 = o2 != null && o2.getName() != null ? o2.getName() : null;

                if (s1 != null && s2 != null) {
                    return s1.compareTo(s2);
                } else if (s1 == null) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
        return results;
    }

    public List<BudgetSection> getExpenseSections() {
        List<BudgetSection> results = new ArrayList<BudgetSection>();
        for (BudgetSection section : this.sections) {
            if (section.getDirection() == CashFlowDirection.EXPENSE) {
                results.add(section);
            }
        }
        Collections.sort(results, new Comparator<BudgetSection>() {
            @Override
            public int compare(BudgetSection o1, BudgetSection o2) {
                final String s1 = o1 != null && o1.getName() != null ? o1.getName() : null;
                final String s2 = o2 != null && o2.getName() != null ? o2.getName() : null;

                if (s1 != null && s2 != null) {
                    return s1.compareTo(s2);
                } else if (s1 == null) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
        return results;
    }


    public List<BudgetCategory> getCategories() {
        List<BudgetCategory> results = new ArrayList<>();
        for (BudgetSection section : sections) {
            results.addAll(section.getCategories());
        }

        Collections.sort(results, new Comparator<BudgetCategory>() {
            public int compare(BudgetCategory o1, BudgetCategory o2) {
                if (o1 == null && o2 == null) {
                    return 0;
                } else if (o1 != null && o2 == null) {
                    return 1;
                } else if (o1 == null && o2 != null) {
                    return -1;
                }

                if (o1.getDirection() != o2.getDirection()) {
                    return Integer.valueOf(o1.getDirection().ordinal()).compareTo(o2.getDirection().ordinal());
                } else if (!(o1.getSection().equals(o2.getSection()))) {
                    return o1.getSection().compareTo(o2.getSection());
                }
                return o1.compareTo(o2);
            }
        });

        return results;
    }

    public List<BudgetCategory> getCategories(String sectionName) {
        if (sectionName == null) {
            return getCategories();
        }

        List<BudgetCategory> results = new ArrayList<>();
        for (BudgetSection section : sections) {
            if (sectionName.equals(section.getName())) {
                results.addAll(section.getCategories());
            }
        }

        Collections.sort(results, new Comparator<BudgetCategory>() {
            public int compare(BudgetCategory o1, BudgetCategory o2) {
                if (o1 == null && o2 == null) {
                    return 0;
                } else if (o1 != null && o2 == null) {
                    return 1;
                } else if (o1 == null && o2 != null) {
                    return -1;
                }

                if (o1.getDirection() != o2.getDirection()) {
                    return Integer.valueOf(o1.getDirection().ordinal()).compareTo(o2.getDirection().ordinal());
                } else if (!(o1.getSection().equals(o2.getSection()))) {
                    return o1.getSection().compareTo(o2.getSection());
                }
                return o1.compareTo(o2);
            }
        });

        return results;
    }

    private static Date currentBudgetStartDate() {
        Date current = currentDate();
        Date prospective = twentyFifthOfTheMonthOrPrior(current);
        if (current.after(prospective)) {
            return prospective;
        } else {
            return twentyFifthOfTheMonthOrPrior(minusMonth(current));
        }
    }

    public static Date budgetStartDateFor(Date date) {
        Date current = clean(date);
        Date prospective = twentyFifthOfTheMonthOrPrior(current);
        if (current.compareTo(prospective) >= 0) {
            return prospective;
        } else {
            return twentyFifthOfTheMonthOrPrior(minusMonth(current));
        }
    }


    private static Date clean(Date date) {
        return new LocalDate(date).toDate();
//        Calendar calendar = GregorianCalendar.getInstance();
//        calendar.setTime(date);
//        calendar.set(Calendar.ZONE_OFFSET, 0);
//        calendar.set(Calendar.DST_OFFSET, 0);
//        calendar.set(Calendar.HOUR, 0);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.MILLISECOND, 0);
//        calendar.set(Calendar.AM_PM, Calendar.AM);
//        return calendar.getTime();
    }

    private static Date currentDate() {
        return new LocalDate().toDate();
//        Calendar calendar = GregorianCalendar.getInstance();
//        calendar.set(Calendar.ZONE_OFFSET, 0);
//        calendar.set(Calendar.DST_OFFSET, 0);
//        calendar.set(Calendar.HOUR, 0);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.MILLISECOND, 0);
//        calendar.set(Calendar.AM_PM, Calendar.AM);
//        return calendar.getTime();
    }

    private static Date minusMonth(Date date) {
        return new LocalDate(date).minusMonths(1).toDate();
//        Calendar calendar = GregorianCalendar.getInstance();
//        calendar.setTime(date);
//        calendar.set(Calendar.ZONE_OFFSET, 0);
//        calendar.set(Calendar.DST_OFFSET, 0);
//        calendar.set(Calendar.HOUR, 0);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.MILLISECOND, 0);
//        calendar.set(Calendar.AM_PM, Calendar.AM);
//        calendar.add(Calendar.MONTH, -1);
//        return calendar.getTime();
    }

    private static Date firstOfTheMonth(Date date) {
        return new LocalDate(date).withDayOfMonth(1).toDate();
//        Calendar calendar = GregorianCalendar.getInstance();
//        calendar.setTime(date);
//        calendar.set(Calendar.ZONE_OFFSET, 0);
//        calendar.set(Calendar.DST_OFFSET, 0);
//        calendar.set(Calendar.DATE, 1);
//        calendar.set(Calendar.HOUR, 0);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.MILLISECOND, 0);
//        calendar.set(Calendar.AM_PM, Calendar.AM);
//        return calendar.getTime();
    }

    /**
     * Returns the 25th of the month or the first working day if the 25th is a weekend
     * TODO Localize this function so that holidays are taken into account
     * @param date The date from which to derive the 25th of that month
     * @return The 25 day of the month for the provided date
     */
    private static Date twentyFifthOfTheMonthOrPrior(Date date) {
        LocalDate d = new LocalDate(date).withDayOfMonth(25);
        int dow = d.getDayOfWeek();
        while (DateTimeConstants.SUNDAY == dow || DateTimeConstants.SATURDAY == dow) {
            d = d.minusDays(1);
            dow = d.getDayOfWeek();
        }
        return d.toDate();

//        Calendar calendar = GregorianCalendar.getInstance();
//        calendar.setTime(date);
//        calendar.set(Calendar.ZONE_OFFSET, 0);
//        calendar.set(Calendar.DST_OFFSET, 0);
//        calendar.set(Calendar.DATE, 25);
//        calendar.set(Calendar.HOUR, 0);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.MILLISECOND, 0);
//        calendar.set(Calendar.AM_PM, Calendar.AM);
//
//        int dow = calendar.get(Calendar.DAY_OF_WEEK);
//        while (Calendar.SUNDAY == dow || Calendar.SATURDAY == dow) {
//            calendar.add(Calendar.DATE, -1);
//            dow = calendar.get(Calendar.DAY_OF_WEEK);
//        }
//        return calendar.getTime();
    }

    public static Budget fromTemplateCurrent() {
        return fromTemplateFor(currentBudgetStartDate());
    }

    public static Budget fromTemplateFor(Date date) {
        return new Budget(
                        clean(date),
                        clean(date),
                        new Objective("par", "amount >= 0 && amount < 500"),
                        new BudgetSection(null, DEFAULT_UNALLOCATED_BUDGET_SECTION,   CashFlowDirection.EXPENSE,      Color.medium_faded_red,      Color.pale_pastel_red,          DEFAULT_UNALLOCATED_CATEGORIES),
                        new BudgetSection(null, DEFAULT_EARN_BUDGET_SECTION,          CashFlowDirection.INCOME,       Color.pale_faded_azure_blue, Color.pale_light_azure_blue,    DEFAULT_EARN_CATEGORIES),
                        new BudgetSection(null, DEFAULT_GOD_BUDGET_SECTION,           CashFlowDirection.EXPENSE,      Color.medium_faded_yellow,   Color.pale_very_light_yellow,   DEFAULT_GOD_CATEGORIES),
                        new BudgetSection(null, DEFAULT_DEBT_BUDGET_SECTION,          CashFlowDirection.EXPENSE,      Color.medium_faded_red,      Color.pale_very_light_red,      DEFAULT_DEBT_CATEGORIES),
                        new BudgetSection(null, DEFAULT_HOUSE_BUDGET_SECTION,         CashFlowDirection.EXPENSE,      Color.vivid_red,             Color.pale_pastel_red,          DEFAULT_HOUSE_CATEGORIES),
                        new BudgetSection(null, DEFAULT_TRANSPORT_BUDGET_SECTION,     CashFlowDirection.EXPENSE,      Color.vivid_red,             Color.pale_pastel_red,          DEFAULT_TRANSPORT_CATEGORIES),
                        new BudgetSection(null, DEFAULT_CHILDREN_BUDGET_SECTION,      CashFlowDirection.EXPENSE,      Color.vivid_red,             Color.pale_pastel_red,          DEFAULT_CHILDREN_CATEGORIES),
                        new BudgetSection(null, DEFAULT_TELECOMS_BUDGET_SECTION,      CashFlowDirection.EXPENSE,      Color.vivid_red,             Color.pale_pastel_red,          DEFAULT_TELECOMS_CATEGORIES),
                        new BudgetSection(null, DEFAULT_MEDICAL_BUDGET_SECTION,       CashFlowDirection.EXPENSE,      Color.vivid_red,             Color.pale_pastel_red,          DEFAULT_MEDICAL_CATEGORIES),
                        new BudgetSection(null, DEFAULT_BANK_BUDGET_SECTION,          CashFlowDirection.EXPENSE,      Color.vivid_red,             Color.pale_pastel_red,          DEFAULT_BANK_CATEGORIES),
                        new BudgetSection(null, DEFAULT_PERSONAL_BUDGET_SECTION,      CashFlowDirection.EXPENSE,      Color.vivid_red,             Color.pale_pastel_red,          DEFAULT_PERSONAL_CATEGORIES)
                        );
    }

    public static Budget current(EntityManager entityManager) {
        return entityManager.find(Budget.class, currentBudgetStartDate());
    }

    public static Budget forId(EntityManager entityManager, Date budgetStartDate) {
        return entityManager.find(Budget.class, clean(budgetStartDate));
    }

    public static Budget save(EntityManager entityManager, Budget budget) throws DataAccessException {
        TransactionManager tx = new TransactionManager(entityManager);
        try {
            tx.beginIfLocal();

            Budget persisted = entityManager.find(Budget.class, budget.getStartDate());
            if (persisted == null) {
                System.out.println("persisted = " + persisted);
                entityManager.persist(budget);
                persisted = budget;
            } else {
                System.out.println("merge = " + budget);
                persisted = entityManager.merge(budget);
            }

            tx.commitIfLocal();

            return persisted;
        } catch (Exception e) {
            tx.rollbackIfLocal();
            throw new DataAccessException("Unable to persist budget", e);
        }
    }

    public void loadTransactions(EntityManager entityManager) {
        List<Transaction> transactions = Transaction.forBudget(entityManager, this).results();
        for (BudgetSection section : this.getSections()) {
            section.allocate(transactions);
        }
    }

    public BudgetCategory getUnallocatedBudgetCategory() {
        for(BudgetSection section : sections) {
            for (BudgetCategory category : section.getCategories()) {
                if (section.getName().equals(DEFAULT_UNALLOCATED_BUDGET_SECTION) && category.getName().equals(BudgetCategory.DEFAULT_UNALLOCATED)) {
                    return category;
                }
            }
        }
        return null;
    }

    public BudgetCategory determineBudgetCategory(Transaction transaction) throws DataAccessException {
        for(BudgetSection section : sections) {
            for (BudgetCategory category : section.getCategories()) {
                if (category.matches(transaction)) {
                    return category;
                }
            }
        }

        return getUnallocatedBudgetCategory();
    }

    @Override
    public String toString() {
        return "Budget{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", objective=" + objective +
                ", sections=" + sections +
                '}';
    }
}
