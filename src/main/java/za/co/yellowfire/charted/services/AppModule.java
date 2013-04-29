package za.co.yellowfire.charted.services;

import java.io.IOException;

import org.apache.tapestry5.*;
import org.apache.tapestry5.ioc.*;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Local;
import org.apache.tapestry5.ioc.services.ApplicationDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.apache.tapestry5.ioc.services.ThreadLocale;
import org.apache.tapestry5.jpa.EntityManagerSource;
import org.apache.tapestry5.jpa.PersistenceUnitConfigurer;
import org.apache.tapestry5.services.*;
import org.apache.tapestry5.services.compatibility.Compatibility;
import org.apache.tapestry5.services.compatibility.Trait;
import org.slf4j.Logger;
import za.co.yellowfire.charted.domain.dao.*;
import za.co.yellowfire.charted.domain.BudgetSection;
import za.co.yellowfire.charted.domain.manager.OfxStatementImporter;
import za.co.yellowfire.charted.domain.manager.StatementImporter;
import za.co.yellowfire.charted.translator.BudgetSectionValueEncoder;
import za.co.yellowfire.charted.translator.CashFlowDirectionTranslator;
import za.co.yellowfire.charted.translator.ColorTranslator;

/**
 * This module is automatically included as part of the Tapestry IoC Registry, it's a good place to
 * configure and extend Tapestry, or to place your own service definitions.
 */
public class AppModule {
    public static void bind(ServiceBinder binder) {

        final AccountDao accountDao = new AccountDao("jdbc:postgresql://localhost/charted-test", "uncharted", "uncharted");
        final TransactionDao transactionDao = new TransactionDao("jdbc:postgresql://localhost/charted-test", "uncharted", "uncharted");
        final BudgetAllocationDao budgetAllocationDao = new BudgetAllocationDao("jdbc:postgresql://localhost/charted-test", "uncharted", "uncharted");

        binder.bind(BudgetDao.class, new ServiceBuilder<BudgetDao>() {
            @Override
            public BudgetDao buildService(ServiceResources serviceResources) {
                return new BudgetDao("jdbc:postgresql://localhost/charted-test", "uncharted", "uncharted");
            }
        });
        binder.bind(BudgetSectionDao.class, new ServiceBuilder<BudgetSectionDao>() {
            @Override
            public BudgetSectionDao buildService(ServiceResources serviceResources) {
                return new BudgetSectionDao("jdbc:postgresql://localhost/charted-test", "uncharted", "uncharted");
            }
        });
        binder.bind(BudgetCategoryDao.class, new ServiceBuilder<BudgetCategoryDao>() {
            @Override
            public BudgetCategoryDao buildService(ServiceResources serviceResources) {
                return new BudgetCategoryDao("jdbc:postgresql://localhost/charted-test", "uncharted", "uncharted");
            }
        });
        binder.bind(TransactionDao.class, new ServiceBuilder<TransactionDao>() {
            @Override
            public TransactionDao buildService(ServiceResources serviceResources) {
                return transactionDao;
            }
        });
        binder.bind(BudgetAllocationDao.class, new ServiceBuilder<BudgetAllocationDao>() {
            @Override
            public BudgetAllocationDao buildService(ServiceResources serviceResources) {
                return budgetAllocationDao;
            }
        });
        binder.bind(AccountDao.class, new ServiceBuilder<AccountDao>() {
            @Override
            public AccountDao buildService(ServiceResources serviceResources) {
                return accountDao;
            }
        });
        binder.bind(StatementImporter.class, new ServiceBuilder<StatementImporter>() {
            @Override
            public StatementImporter buildService(ServiceResources serviceResources) {
                return new OfxStatementImporter(accountDao, transactionDao);
            }
        });
    }

    public static void contributeValueEncoderSource(MappedConfiguration<Class,Object> configuration) {
        configuration.addInstance(BudgetSection.class, BudgetSectionValueEncoder.class);
    }

    public static void contributeFactoryDefaults(
            MappedConfiguration<String, Object> configuration)
    {
        // The application version number is incorprated into URLs for some
        // assets. Web browsers will cache assets because of the far future expires
        // header. If existing assets are changed, the version number should also
        // change, to force the browser to download new versions. This overrides Tapesty's default
        // (a random hexadecimal number), but may be further overriden by DevelopmentModule or
        // QaModule.
        configuration.override(SymbolConstants.APPLICATION_VERSION, "0.1.0");
    }

    public static void contributeApplicationDefaults(
            MappedConfiguration<String, Object> configuration)
    {
        // Contributions to ApplicationDefaults will override any contributions to
        // FactoryDefaults (with the same key). Here we're restricting the supported
        // locales to just "en" (English). As you add localised message catalogs and other assets,
        // you can extend this list of locales (it's a comma separated series of locale names;
        // the first locale name is the default when there's no reasonable match).
        configuration.add(SymbolConstants.SUPPORTED_LOCALES, "en");
    }


    @SuppressWarnings("rawtypes")
    public static void contributeTranslatorAlternatesSource(
            MappedConfiguration<String, Translator> configuration,
            ThreadLocale threadLocale) {
        configuration.add("cash-flow-direction", new CashFlowDirectionTranslator());
        configuration.add("color", new ColorTranslator());
    }

    /**
     * This is a service definition, the service will be named "TimingFilter". The interface,
     * RequestFilter, is used within the RequestHandler service pipeline, which is built from the
     * RequestHandler service configuration. Tapestry IoC is responsible for passing in an
     * appropriate Logger instance. Requests for static resources are handled at a higher level, so
     * this filter will only be invoked for Tapestry related requests.
     * <p/>
     * <p/>
     * Service builder methods are useful when the implementation is inline as an inner class
     * (as here) or require some other kind of special initialization. In most cases,
     * use the static bind() method instead.
     * <p/>
     * <p/>
     * If this method was named "build", then the service id would be taken from the
     * service interface and would be "RequestFilter".  Since Tapestry already defines
     * a service named "RequestFilter" we use an explicit service id that we can reference
     * inside the contribution method.
     */
    public RequestFilter buildTimingFilter(final Logger log)
    {
        return new RequestFilter()
        {
            public boolean service(Request request, Response response, RequestHandler handler)
                    throws IOException
            {
                long startTime = System.currentTimeMillis();

                try
                {
                    // The responsibility of a filter is to invoke the corresponding method
                    // in the handler. When you chain multiple filters together, each filter
                    // received a handler that is a bridge to the next filter.

                    return handler.service(request, response);
                } finally
                {
                    long elapsed = System.currentTimeMillis() - startTime;

                    log.info(String.format("Request time: %d ms", elapsed));
                }
            }
        };
    }

    /**
     * This is a contribution to the RequestHandler service configuration. This is how we extend
     * Tapestry using the timing filter. A common use for this kind of filter is transaction
     * management or security. The @Local annotation selects the desired service by type, but only
     * from the same module.  Without @Local, there would be an error due to the other service(s)
     * that implement RequestFilter (defined in other modules).
     */
    public void contributeRequestHandler(OrderedConfiguration<RequestFilter> configuration,
                                         @Local
                                         RequestFilter filter)
    {
        // Each contribution to an ordered configuration has a name, When necessary, you may
        // set constraints to precisely control the invocation order of the contributed filter
        // within the pipeline.

        //configuration.add("Timing", filter);
    }

    public static void contributeClasspathAssetAliasManager(MappedConfiguration<String, String> configuration){
        configuration.add("editable/js",  "editable/js");
        configuration.add("editable/css", "editable/css");
        configuration.add("jquery/js", "jquery/js");
    }

    @Contribute(EntityManagerSource.class)
    public static void configurePersistenceUnitInfos(MappedConfiguration<String, PersistenceUnitConfigurer> cfg) {
        /*
        PersistenceUnitConfigurer configurer = new PersistenceUnitConfigurer() {
            public void configure(TapestryPersistenceUnitInfo unitInfo) {
                unitInfo.addProperty("javax.persistence.jdbc.driver", "org.postgresql.Driver")
                        .addProperty("javax.persistence.jdbc.url", "jdbc:postgresql://localhost/charted")
                        .addProperty("javax.persistence.jdbc.user", "uncharted")
                        .addProperty("javax.persistence.jdbc.password", "uncharted")
                        .addProperty("eclipselink.ddl-generation", "create-tables")
                        .addProperty("eclipselink.logging.level", "fine");
            }
        };

        cfg.add("JTAUnit", configurer);
        */
    }



    @Contribute(SymbolProvider.class)
    @ApplicationDefaults
    public static void switchProviderToJQuery(MappedConfiguration<String, Object> configuration)
    {
        configuration.add(SymbolConstants.JAVASCRIPT_INFRASTRUCTURE_PROVIDER, "jquery");
    }

    @Contribute(Compatibility.class)
    public static void disableScriptaculous(MappedConfiguration<Trait, Boolean> configuration)
    {
        configuration.add(Trait.SCRIPTACULOUS, false);
        configuration.add(Trait.INITIALIZERS, false);
    }

    /**
     * silently redirect the user to the intended page when browsing through
     * tapestry forms through browser history
     */
    public RequestExceptionHandler decorateRequestExceptionHandler(
            final ComponentSource componentSource,
            final Response response,
            final RequestExceptionHandler oldHandler) {
        return new RequestExceptionHandler() {
            @Override
            public void handleRequestException(Throwable exception) throws IOException {
                if (!exception.getMessage().contains("Forms require that the request method be POST and that the t:formdata query parameter have values")) {
                    oldHandler.handleRequestException(exception);
                    return;
                }
                ComponentResources cr = componentSource.getActivePage().getComponentResources();
                Link link = cr.createEventLink("");
                String uri = link.toRedirectURI().replaceAll(":", "");
                response.sendRedirect(uri);
            }
        };
    }
}
